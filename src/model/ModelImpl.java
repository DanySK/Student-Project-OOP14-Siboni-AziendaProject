package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import controller.Controller;
import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import model.conto.Conto;
import model.douments.Document;
import model.operation.Operation;
import model.situazione.SituazioneEconomica;
import model.situazione.SituazionePatrimoniale;

/**
 * Implementazione concreta della classe Model.
 * 
 * @author Enrico
 *
 */
public final class ModelImpl implements Model {

	private static final String CONTATTI_FILENAME = "contatti.azpj";
	private static final String CONTI_FILENAME = "conti.azpj";
	private static final String OPERATIONS_FILENAME = "operations.azpj";
	private static final String DOCUMENTS_FILENAME = "documents.azpj";

	private transient Controller c;

	private int operationCounter = 1;
	private Contatto ourContact;
	private Set<Conto> contiStore;
	private Set<Contatto> contattiStore;
	private Map<Integer, Operation> operationMap;
	private Map<Integer, Document> documentMap;
	private boolean contiStoreChanged;
	private boolean contattiStoreChanged;
	private boolean operationMapChanged;
	private boolean documentMapChanged;

	/**
	 * Restituisce un modello vuoto.
	 */
	public ModelImpl() {
		this.contiStore = new HashSet<>();
		this.contattiStore = new HashSet<>();
		this.operationMap = new TreeMap<>();
		this.documentMap = new TreeMap<>();
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
		return new HashSet<>(this.contiStore);
	}

	@Override
	public void setOurContact(final Contatto c) {
		Objects.requireNonNull(c);
		ourContact = new ContattoImpl(c);
		this.contattiStoreChanged = true;
	}

	@Override
	public Contatto getOurContact() {
		if (ourContact == null) {
			ourContact = c.askOurContact();
		}
		return new ContattoImpl(ourContact);
	}

	@Override
	public void addOperation(final Operation op) {
		Objects.requireNonNull(op);
		this.operationMap.put(getNextOperationNumber(), op);
		this.operationMapChanged = true;
	}

	@Override
	public Operation getOperation(final int numOperation) {
		controlNumOp(numOperation);
		return this.operationMap.get(numOperation);
	}

	@Override
	public boolean addDocumentToOperation(final int numOperation,
			final Document doc) {
		Objects.requireNonNull(doc);

		controlNumOp(numOperation);

		if (this.documentMap.containsKey(numOperation)) {
			return false;
		}
		this.documentMap.put(numOperation, doc);
		this.documentMapChanged = true;
		return true;
	}

	@Override
	public Document getDocumentReferredTo(final int numOperation) {

		controlNumOp(numOperation);

		if (this.documentMap.containsKey(numOperation)) {
			return this.documentMap.get(numOperation);
		} else {
			throw new NoSuchElementException(
					"Non c'è nessun documento allegato a questa operazione");
		}

	}

	@Override
	public void deleteDocumentReferredTo(final int numOperation) {
		controlNumOp(numOperation);

		this.documentMap.remove(numOperation);
		this.documentMapChanged = true;
	}

	@Override
	public void addContatto(final Contatto contatto) {
		Objects.requireNonNull(contatto);

		final Set<Contatto> set = this.contattiStore.stream()
				.filter(c -> c.equals(contatto)).collect(Collectors.toSet());

		if (set.isEmpty()) {
			this.contattiStore.add(contatto);
		} else {
			this.contattiStore.removeAll(set);
			this.contattiStore.add(c.wichContattoToMantain(set));
		}
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
		return new HashSet<>(this.contattiStore);
	}

	@Override
	public SituazioneEconomica getSituazioneEconomica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SituazionePatrimoniale getSituazionePatrimoniale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setController(final Controller c) {
		Objects.requireNonNull(c);
		this.c = c;
	}

	@Override
	public void save(final String path) throws FileNotFoundException {

		if (this.operationMapChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(path + OPERATIONS_FILENAME));
				out.writeInt(operationCounter);
				out.writeObject(operationMap);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (this.documentMapChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(path + DOCUMENTS_FILENAME));
				out.writeObject(documentMap);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (this.contattiStoreChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(path + CONTATTI_FILENAME));
				out.writeObject(ourContact);
				out.writeObject(contattiStore);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (this.contiStoreChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(path + CONTI_FILENAME));
				out.writeObject(contiStore);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(final String path) throws FileNotFoundException {
		try {
			final ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(path + OPERATIONS_FILENAME));
			operationCounter = in.readInt();
			operationMap = (Map<Integer, Operation>) in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(path + DOCUMENTS_FILENAME));
			documentMap = (Map<Integer, Document>) in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(path + CONTATTI_FILENAME));
			ourContact = (Contatto) in.readObject();
			contattiStore = (Set<Contatto>) in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new FileInputStream(path + CONTI_FILENAME));
			contiStore = (Set<Conto>) in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		this.documentMap.clear();
		this.operationMap.clear();
		operationCounter = 1;
	}

	/**
	 * Metodo che da il numero progressivo alle operazioni
	 * 
	 * @return il numero progressivo da dare alla prossima operazione
	 */
	private int getNextOperationNumber() {
		return operationCounter++;
	}

	/**
	 * Metodo che controlla il numero dell'operazione passato; lancia
	 * IllegalArgumentException se viene passato un numero negativo o 0; lancia
	 * NoSuchElementException se il numero passato supera il numero attualmnte
	 * inserito di operazioni
	 * 
	 * @param numOp
	 *            numero operazione da controllare
	 * @return true, se passa i controlli
	 */
	private boolean controlNumOp(final int numOp) {
		if (numOp <= 0) {
			throw new IllegalArgumentException(
					"Il numero dell'operazione non può essere negativo");
		}
		if (numOp > operationMap.size()) {
			throw new NoSuchElementException();
		}
		return true;
	}

}
