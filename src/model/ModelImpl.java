package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import controller.Controller;
import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.data.Data;
import model.douments.Document;
import model.operation.Operation;
import model.situazione.SituazioneEconomica;
import model.situazione.SituazioneEconomicaImpl;
import model.situazione.SituazionePatrimoniale;
import model.situazione.SituazionePatrimonialeImpl;

/**
 * Implementazione concreta della classe Model.
 * 
 * @author Enrico
 *
 */
public final class ModelImpl implements Model {

	private static final String CONTATTI_FILENAME = "contacts.azpj";
	private static final String OUR_CONTACT_FILENAME = "our.azpj";
	private static final String CONTI_FILENAME = "conti.azpj";
	private static final String OPERATIONS_FILENAME = "operations.azpj";

	private static final String LOADING_ERROR = "Errore caricamento file: ";

	private static final List<AccesoA> CONTI_SIT_ECONOMICA = Arrays.asList(
			AccesoA.COSTI_ES, AccesoA.RICAVI_ES);

	private final transient Controller controller;

	private Contatto ourContact;
	private Set<Conto> contiStore;
	private Set<Contatto> contattiStore;
	private SortedSet<Operation> operationSet;
	private transient boolean contiStoreChanged;
	private transient boolean contattiStoreChanged;
	private transient boolean operationSetChanged;
	private transient boolean ourContactChanged;
	private transient State currentState;

	/**
	 * Restituisce un modello vuoto.
	 * 
	 * @param c
	 *            il controller a cui il modello si deve riferire
	 */
	public ModelImpl(final Controller c) {
		this.controller = c;
		this.contiStore = new TreeSet<>();
		this.contattiStore = new TreeSet<>();
		this.operationSet = new TreeSet<>();
	}

	@Override
	public void addConto(final Conto c) {
		Objects.requireNonNull(c);
		if (this.contiStore.contains(c)) {
			throw new IllegalArgumentException("Conto già presente");
		}
		this.contiStore.add(c);
		this.contiStoreChanged = true;
	}

	@Override
	public void deleteConto(final Conto c) {
		Objects.requireNonNull(c);
		if (this.contiStore.contains(c)) {
			this.contiStore.remove(c);
			this.contiStoreChanged = true;
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Set<Conto> getConti() {
		return new TreeSet<>(this.contiStore);
	}

	@Override
	public void setOurContact(final Contatto c) {
		Objects.requireNonNull(c);
		ourContact = new ContattoImpl(c);
		this.ourContactChanged = true;
	}

	@Override
	public Contatto getOurContact() {
		return ourContact == null ? ourContact : new ContattoImpl(ourContact);
	}

	@Override
	public void addOperation(final Operation op) {
		Objects.requireNonNull(op);
		this.operationSet.add(op);
		op.applicaMovimenti();
		this.contiStoreChanged = true;
		this.operationSetChanged = true;
	}

	@Override
	public Optional<Operation> getLastOperation() {
		if (this.operationSet.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.operationSet.last());
		}
	}

	@Override
	public List<Operation> getOperations(final Data dataFrom, final Data dataTo) {
		return this.operationSet
				.stream()
				.filter(op -> dataFrom.compareTo(op.getData()) <= 0
						&& dataTo.compareTo(op.getData()) >= 0)
				.collect(Collectors.toList());
	}

	@Override
	public boolean addDocumentToOperation(final Operation op, final Document doc) {
		Objects.requireNonNull(op);
		Objects.requireNonNull(doc);

		try {
			op.setDocument(doc);
		} catch (IllegalStateException e) {
			return false;
		}
		return true;
	}

	@Override
	public Optional<Document> getDocumentReferredTo(final Operation op) {
		Objects.requireNonNull(op);

		return op.getDocument();
	}

	@Override
	public void deleteDocumentReferredTo(final Operation op) {
		Objects.requireNonNull(op);
		op.removeDocument();
	}

	@Override
	public void addContatto(final Contatto contatto) {
		Objects.requireNonNull(contatto);
		this.contattiStore.remove(contatto);
		this.contattiStore.add(contatto);
		this.contattiStoreChanged = true;
	}

	@Override
	public void deleteContatto(final Contatto contatto) {
		Objects.requireNonNull(contatto);

		if (this.contattiStore.contains(contatto)) {
			this.contattiStore.remove(contatto);
			this.contattiStoreChanged = true;
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public Set<Contatto> getContatti() {
		return new TreeSet<>(this.contattiStore);
	}

	@Override
	public SituazioneEconomica getSituazioneEconomica() {
		return new SituazioneEconomicaImpl(this.contiStore.stream()
				.filter(c -> CONTI_SIT_ECONOMICA.contains(c.getAccesoA()))
				.collect(Collectors.toSet()));
	}

	@Override
	public SituazionePatrimoniale getSituazionePatrimoniale() {
		return new SituazionePatrimonialeImpl(this.contiStore.stream()
				.filter(c -> !CONTI_SIT_ECONOMICA.contains(c.getAccesoA()))
				.collect(Collectors.toSet()));

	}

	@Override
	public void save(final String path) {
		if (this.operationSetChanged || currentState == State.FIRST_RUN) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ OPERATIONS_FILENAME)));
				out.writeObject(operationSet);
				out.close();
			} catch (IOException e) {
				controller
						.showErrorMessage("Errore salvataggio file delle operazioni\n"
								+ e.getMessage());
			}
			this.operationSetChanged = false;
		}

		if (this.ourContactChanged || currentState == State.FIRST_RUN) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ OUR_CONTACT_FILENAME)));
				out.writeObject(ourContact);
				out.close();
			} catch (IOException e) {
				controller
						.showErrorMessage("Errore salvataggio file nostro contatto\n"
								+ e.getMessage());
			}
			this.ourContactChanged = false;
		}

		if (this.contattiStoreChanged || currentState == State.FIRST_RUN) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ CONTATTI_FILENAME)));
				out.writeObject(contattiStore);
				out.close();
			} catch (IOException e) {
				controller
						.showErrorMessage("Errore salvataggio file dei contatti\n"
								+ e.getMessage());
			}
			this.contattiStoreChanged = false;
		}

		if (this.contiStoreChanged || currentState == State.FIRST_RUN) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ CONTI_FILENAME)));
				out.writeObject(contiStore);
				out.close();
			} catch (IOException e) {
				controller
						.showErrorMessage("Errore salvataggio file dei conti\n"
								+ e.getMessage());
			}
			this.contiStoreChanged = false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public State load(final String path) {
		final Map<String, Exception> exceptionMap = new HashMap<>();
		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ OPERATIONS_FILENAME)));
			operationSet = (SortedSet<Operation>) in.readObject(); // System.out.println("Letto: operationMap="+operationMap);
			in.close();
		} catch (Exception e) {
			exceptionMap.put(OPERATIONS_FILENAME, e);
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ OUR_CONTACT_FILENAME)));
			ourContact = (Contatto) in.readObject(); // System.out.println("Letto: ourContact="+ourContact);
			in.close();
		} catch (Exception e) {
			exceptionMap.put(OUR_CONTACT_FILENAME, e);
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ CONTATTI_FILENAME)));
			contattiStore = (Set<Contatto>) in.readObject(); // System.out.println("Letto: contattiStore="+contattiStore);
			in.close();
		} catch (Exception e) {
			exceptionMap.put(CONTATTI_FILENAME, e);
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ CONTI_FILENAME)));
			contiStore = (Set<Conto>) in.readObject(); // System.out.println("Letto: contiStore="+contiStore);
			in.close();
		} catch (Exception e) {
			exceptionMap.put(CONTI_FILENAME, e);
		}

		if (exceptionMap.containsKey(OUR_CONTACT_FILENAME)) {
			currentState = State.FIRST_RUN;
		} else if (exceptionMap.isEmpty()) {
			currentState = State.LOADING_SUCCESS;
		} else {
			exceptionMap.entrySet().forEach(
					e -> controller.showErrorMessage(LOADING_ERROR + path
							+ e.getKey() + "\n\n" + e.getValue().getMessage()));
			currentState = State.ERROR_LOADING;
		}
		return currentState;
	}

	@Override
	public void reset() {
		this.operationSet.clear();
		this.contiStore.forEach(Conto::reset);
		
		//setOperazioni e storeConti sono cambiati
		this.operationSetChanged = this.contiStoreChanged = true;
	}

}
