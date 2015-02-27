package controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import view.ViewController;
import model.Model;
import model.contatti.Contatto;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.data.Data;
import model.operation.Operation;
import model.situazione.SituazioneEconomica;
import model.situazione.SituazioneEconomicaImpl;
import model.situazione.SituazionePatrimoniale;
import model.situazione.SituazionePatrimonialeImpl;

/**
 * Implementazione concreta del controller.
 * 
 * @author Enrico
 *
 */
public class ControllerImpl implements Controller {

	private Model model;
	private ViewController viewController;
	private final String savePath;
	
	private static final List<AccesoA> CONTI_SIT_ECONOMICA = Arrays.asList(
			AccesoA.COSTI_ES, AccesoA.RICAVI_ES);

	/**
	 * Crea il controller.
	 * 
	 * @param path
	 *            il path di salvataggio per caricare e salvare i dati
	 *            dell'applicazione
	 */
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
	public List<Operation> getOperations(final Data dataFrom, final Data dataTo) {
		return this.model
				.getAllOperations()
				.stream()
				.filter(op -> dataFrom.compareTo(op.getData()) <= 0
						&& dataTo.compareTo(op.getData()) >= 0)
				.collect(Collectors.toList());
	}

	@Override
	public SituazioneEconomica getSitEconomica() {
		return new SituazioneEconomicaImpl(this.model.getConti().stream()
				.filter(c -> CONTI_SIT_ECONOMICA.contains(c.getAccesoA()))
				.collect(Collectors.toSet()));
	}

	@Override
	public SituazionePatrimoniale getSitPatrimoniale() {
		return new SituazionePatrimonialeImpl(this.model.getConti().stream()
				.filter(c -> !CONTI_SIT_ECONOMICA.contains(c.getAccesoA()))
				.collect(Collectors.toSet()));
	}

	@Override
	public Optional<Operation> getLastOp() {
		return this.model.getAllOperations().isEmpty() ? Optional.empty()
				: Optional.of(this.model.getAllOperations().last());
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
