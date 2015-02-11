package model;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3485815033963843598L;

	private final Controller c = null;

	private int operationCounter = 1;
	private Contatto ourContact;
	private final Set<Conto> contiStore;
	private final Set<Contatto> contattiStore;
	private final Map<Integer, Operation> operationMap;
	private final Map<Integer, Document> documentMap;

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
	}

	@Override
	public void deleteConto(final Conto c) {
		Objects.requireNonNull(c);
		if (this.contiStore.contains(c)) {
			this.contiStore.remove(c);
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
	}

	@Override
	public void deleteContatto(final Contatto contatto) {
		Objects.requireNonNull(contatto);

		if (this.contattiStore.contains(contatto)) {
			this.contattiStore.remove(contatto);
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
	public void reset() {
		this.documentMap.clear();
		this.operationMap.clear();
		operationCounter = 1;
	}

	/*
	 * private void writeObject(final ObjectOutputStream out){
	 * 
	 * }
	 * 
	 * private void readObject(final ObjectInputStream in){
	 * 
	 * }
	 */

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
