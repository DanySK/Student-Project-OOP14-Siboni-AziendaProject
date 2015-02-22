package view;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.Controller;
import view.contatti.ContattiView;
import view.contatti.InsertContattiView;
import view.contatti.InsertOurContactView;
import view.conti.ContiView;
import view.conti.InsertContoView;
import view.operations.InsertOperationView;
import view.operations.OperationsView;
import view.situazioni.SituazioneEconomicaView;
import view.situazioni.SituazionePatrimonialeView;

public class ViewControllerImpl implements ViewController {

	private static final String TITOLO_ERRORE = "Errore";

	private static final String MENU_VIEW_TITLE = "Menu";
	private static final String OPERATIONS_VIEW_TITLE = "Operazioni";
	private static final String OPERATION_INSERT_TITLE = "Inserimento Operazione";
	private static final String CONTATTI_VIEW_TITLE = "Contatti";
	private static final String CONTATTI_INSERT_TITLE = "Nuovo Contatto";
	private static final String CONTI_VIEW_TITLE = "Conti";
	private static final String CONTI_INSERT_TITLE = "Nuovo Conto";
	private static final String SIT_PATR_VIEW_TITLE = "Situazione Patrimoniale";
	private static final String SIT_ECO_VIEW_TITLE = "Situazione Economica";
	private static final String OUR_CONTACT_TITLE = "Nostro Contatto";

	private final String appName;
	private final List<JFrame> frameList = Arrays
			.asList(new JFrame[FramePosition.values().length]);
	private final Controller controller;

	public ViewControllerImpl(final Controller c, final String appName) {
		this.controller = c;
		this.appName = appName;
	}

	@Override
	public void displayMainMenu() {
		if (get(FramePosition.MAIN) == null) {
			this.frameList.set(FramePosition.MAIN.ordinal(), new MainMenu(
					this.appName + " - " + MENU_VIEW_TITLE, this,
					this.controller));
		}
		setAllInvisibleExcept(get(FramePosition.MAIN));
	}

	@Override
	public void displayOperations() {
		if (get(FramePosition.OP_VIEW) == null) {
			this.frameList.set(FramePosition.OP_VIEW.ordinal(),
					new OperationsView(OPERATIONS_VIEW_TITLE, this,
							this.controller));
		} else {
			((RefreshView) get(FramePosition.OP_VIEW)).refresh();
		}
		setAllInvisibleExcept(get(FramePosition.OP_VIEW));
	}

	@Override
	public void displayConti() {
		if (get(FramePosition.CONTI_VIEW) == null) {
			this.frameList.set(FramePosition.CONTI_VIEW.ordinal(),
					new ContiView(CONTI_VIEW_TITLE, this, this.controller));
		} else {
			((RefreshView) get(FramePosition.CONTI_VIEW)).refresh();
		}
		setAllInvisibleExcept(get(FramePosition.CONTI_VIEW));
	}

	@Override
	public void displayContatti() {
		if (get(FramePosition.CONTATTI_VIEW) == null) {
			this.frameList
					.set(FramePosition.CONTATTI_VIEW.ordinal(),
							new ContattiView(CONTATTI_VIEW_TITLE, this,
									this.controller));
		} else {
			((RefreshView) get(FramePosition.CONTATTI_VIEW)).refresh();
		}
		setAllInvisibleExcept(get(FramePosition.CONTATTI_VIEW));
	}

	@Override
	public void displaySitEconomica() {
		this.frameList.set(FramePosition.SIT_ECO_VIEW.ordinal(),
				new SituazioneEconomicaView(SIT_ECO_VIEW_TITLE, this,
						this.controller));
		setAllInvisibleExcept(get(FramePosition.SIT_ECO_VIEW));

	}

	@Override
	public void displaySitPatrimoniale() {
		this.frameList.set(FramePosition.SIT_PATR_VIEW.ordinal(),
				new SituazionePatrimonialeView(SIT_PATR_VIEW_TITLE, this,
						this.controller));
		setAllInvisibleExcept(get(FramePosition.SIT_PATR_VIEW));

	}

	@Override
	public void displayInserimentoOp() {
		this.frameList.set(FramePosition.OP_INSERT.ordinal(),
				new InsertOperationView(OPERATION_INSERT_TITLE, this,
						this.controller));
		setAllInvisibleExcept(get(FramePosition.OP_INSERT));
	}

	@Override
	public void displayInserimentoConto() {
		this.frameList.set(FramePosition.CONTI_INSERT.ordinal(),
				new InsertContoView(CONTI_INSERT_TITLE, this, this.controller));
		setAllInvisibleExcept(get(FramePosition.CONTI_INSERT));
	}

	@Override
	public void displayInserminetoContatto() {
		this.frameList.set(FramePosition.CONTATTI_INSERT.ordinal(),
				new InsertContattiView(CONTATTI_INSERT_TITLE, this,
						this.controller));
		setAllInvisibleExcept(get(FramePosition.CONTATTI_INSERT));
	}

	@Override
	public void displayNostroContatto() {
		this.frameList.set(FramePosition.OUR_CONTACT_VIEW.ordinal(),
				new InsertOurContactView(OUR_CONTACT_TITLE, this,
						this.controller));
		setAllInvisibleExcept(get(FramePosition.OUR_CONTACT_VIEW));
	}

	@Override
	public void displayDocument() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayError(final String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, TITOLO_ERRORE,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Metodo che setta tutti i frame come non visibili
	 */
	private void setAllInvisibleExcept(final JFrame visibleFrame) {
		this.frameList.stream().filter(f -> f != null)
				.forEach(f -> f.setVisible(false));
		visibleFrame.setVisible(true);
	}

	/**
	 * 
	 * @param framePos
	 *            la posizione del frame data dalla enum FramePosition
	 * @return il frame in quella posizione
	 */
	private JFrame get(final FramePosition framePos) {
		return this.frameList.get(framePos.ordinal());
	}

	/**
	 * Enumerazione per il posizionamento dei frame in una lista
	 * 
	 * @author Enrico
	 *
	 */
	private static enum FramePosition {
		MAIN, OP_VIEW, OP_INSERT, CONTI_VIEW, CONTI_INSERT, CONTATTI_VIEW, CONTATTI_INSERT, SIT_ECO_VIEW, SIT_PATR_VIEW, OUR_CONTACT_VIEW;
	}

}
