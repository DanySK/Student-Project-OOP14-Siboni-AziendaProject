package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import model.conto.Conto;
import model.douments.Document;
import model.operation.Operation;

public final class ModelImpl implements Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3485815033963843598L;
	
	private static Contatto ourContact;
	private static int operationCounter;
	
	private final Set<Conto> contiStore;
	
	public ModelImpl(){
		this.contiStore = new HashSet<>();
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConto(final Conto c) {
		Objects.requireNonNull(c);
		if(this.contiStore.contains(c)){
			throw new IllegalArgumentException();
		}
		this.contiStore.add(c);
	}

	@Override
	public Set<Conto> getConti() {
		return new HashSet<>(this.contiStore);
	}
	
	@Override
	public void setOurContact(final Contatto c) {
		ModelImpl.ourContact = new ContattoImpl(c);
	}
	
	@Override
	public Contatto getOurContact() {
		return new ContattoImpl(ModelImpl.ourContact);
	}
	
	@Override
	public void addOperation(final Operation op) {
		// TODO Auto-generated method stub
		op.setNumOperation(getNextOperationNumber());
	}

	@Override
	public Operation getOperation(final int numOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getDocumentReferredTo(final int numOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	private static int getNextOperationNumber() {
		return ModelImpl.operationCounter++;
	}
}
