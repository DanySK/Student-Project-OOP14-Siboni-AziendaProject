package controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import view.ViewController;
import model.Model;
import model.contatti.Contatto;
import model.conto.Conto;
import model.data.Data;
import model.operation.Operation;
import model.situazione.SituazioneEconomica;
import model.situazione.SituazionePatrimoniale;

public class ControllerImpl implements Controller {

	private Model model;
	private ViewController viewController;
	private final String savePath;

	public ControllerImpl(final String path) {
		this.savePath = path;
	}

	@Override
	public Model.State load() {
		return this.model.load(savePath);
	}

	@Override
	public void save() {
		this.model.save(savePath);
	}

	@Override
	public void aggiuntaOperazione(final Operation op) {
		this.model.addOperation(op);
	}

	@Override
	public void aggiuntaConto(final Conto conto) {
		this.model.addConto(conto);
	}

	@Override
	public void aggiuntaContatto(final Contatto contatto) {
		this.model.addContatto(contatto);
	}

	@Override
	public void cancellaConto(final Conto conto) {
		this.model.deleteConto(conto);
	}

	@Override
	public void cancellaContatto(final Contatto contatto) {
		this.model.deleteContatto(contatto);
	}
	
	@Override
	public Set<Conto> getInsiemeConti() {
		return this.model.getConti();
	}

	@Override
	public Set<Contatto> getInsiemeContatti() {
		return this.model.getContatti();
	}
	
	@Override
	public List<Operation> getOperations(final Data dataFrom,final Data dataTo) {
		return this.model.getOperations(dataFrom,dataTo);
	}

	@Override
	public SituazioneEconomica getSitEconomica(){
		return this.model.getSituazioneEconomica();
	}
	
	@Override
	public SituazionePatrimoniale getSitPatrimoniale(){
		return this.model.getSituazionePatrimoniale();
	}
	
	@Override
	public Optional<Operation> getLastOp() {
		return this.model.getLastOperation();
	}

	@Override
	public void setView(final ViewController v) {
		this.viewController = v;
	}

	@Override
	public void setModel(final Model m) {
		this.model = m;

	}

	@Override
	public void showMenu() {
		this.viewController.displayMainMenu();
	}

	@Override
	public void showErrorMessage(final String errorMessage) {
		this.viewController.displayError(errorMessage);
	}

	@Override
	public void reset() {
		this.model.reset();
		save();
	}
	
	@Override
	public void showFirstRunView() {
		this.viewController.displayNostroContatto();
	}

	@Override
	public void setOurContact(final Contatto ourContact) {
		this.model.setOurContact(ourContact);
	}

	@Override
	public Contatto getOurContact() {
		return this.model.getOurContact();
	}

}
