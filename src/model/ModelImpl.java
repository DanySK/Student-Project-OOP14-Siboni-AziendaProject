package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import model.conto.Conto;
import model.douments.Document;
import model.operation.Operation;

public class ModelImpl implements Model {

	private Set<Conto> contiStore;
	
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
	public void addOperation(final Operation op) {
		// TODO Auto-generated method stub

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



}
