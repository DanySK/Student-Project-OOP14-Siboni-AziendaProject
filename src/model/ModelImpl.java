package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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

	private final transient Controller controller;

	private int operationCounter = 1;
	private Contatto ourContact;
	private Set<Conto> contiStore;
	private Set<Contatto> contattiStore;
	private Map<Integer, Operation> operationMap;
	private Map<Integer, Document> documentMap;
	private transient boolean contiStoreChanged;
	private transient boolean contattiStoreChanged;
	private transient boolean operationMapChanged;
	private transient boolean documentMapChanged;

	/**
	 * Restituisce un modello vuoto.
	 */
	public ModelImpl(final Controller c) {
		this.controller = c;
		this.contiStore = new TreeSet<>();
		this.contattiStore = new TreeSet<>();
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
		return new TreeSet<>(this.contiStore);
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
			ourContact = controller.askOurContact();
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
			this.contattiStore.add(controller.wichContattoToMantain(set));
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
		return new TreeSet<>(this.contattiStore);
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
	public void save(final String path) {

		if (this.operationMapChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ OPERATIONS_FILENAME)));
				out.writeInt(operationCounter);
				out.writeObject(operationMap);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Chiamare metodo visualizzazione errore del controller
				e.printStackTrace();
			}
		}

		if (this.documentMapChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ DOCUMENTS_FILENAME)));
				out.writeObject(documentMap);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Chiamare metodo visualizzazione errore del controller
				e.printStackTrace();
			}

		}

		if (this.contattiStoreChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ CONTATTI_FILENAME)));
				out.writeObject(ourContact);
				out.writeObject(contattiStore);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Chiamare metodo visualizzazione errore del controller
				e.printStackTrace();
			}
		}

		if (this.contiStoreChanged) {
			try {
				final ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(path
								+ CONTI_FILENAME)));
				out.writeObject(contiStore);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// Chiamare metodo visualizzazione errore del controller
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void load(final String path) {
		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ OPERATIONS_FILENAME)));
			operationCounter = in.readInt(); // System.out.println("Letto: operationCounter="+operationCounter);
			operationMap = (Map<Integer, Operation>) in.readObject(); // System.out.println("Letto: operationMap="+operationMap);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Chiamare metodo visualizzazione errore del controller
			e.printStackTrace();
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ DOCUMENTS_FILENAME)));
			documentMap = (Map<Integer, Document>) in.readObject(); // System.out.println("Letto: documentMap="+documentMap);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Chiamare metodo visualizzazione errore del controller
			e.printStackTrace();
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ CONTATTI_FILENAME)));
			ourContact = (Contatto) in.readObject(); // System.out.println("Letto: ourContact="+ourContact);
			contattiStore = (Set<Contatto>) in.readObject(); // System.out.println("Letto: contattiStore="+contattiStore);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Chiamare metodo visualizzazione errore del controller
			e.printStackTrace();
		}

		try {
			final ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(path
							+ CONTI_FILENAME)));
			contiStore = (Set<Conto>) in.readObject(); // System.out.println("Letto: contiStore="+contiStore);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Chiamare metodo visualizzazione errore del controller
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
