package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import controller.Controller;

/**
 * Classe concreta che crea il menu principale.
 * 
 * @author Enrico
 *
 */
public class MainMenu extends AbstractFrame {

	private static final long serialVersionUID = 4134970724206737559L;

	private static final String ESCI = "Esci";
	private static final String SURE_TO_EXIT = "Sei sicuro di voler uscire?";
	private static final String BTN_OUR_CONTACT_TEXT = "Ns.Contatto";
	private static final String BTN_RESET_TEXT = "Reset";
	private static final String BTN_GEST_OP_TEXT = "Gestione Operazioni";
	private static final String BTN_GEST_CONTI_TEXT = "Gestione Conti";
	private static final String BTN_GEST_CONTATTI_TEXT = "Gestione Contatti";
	private static final String BTN_VIEW_SIT_PATR = "Visualizza Situazione Patrimoniale";
	private static final String BTN_VIEW_SIT_ECO = "Visualizza Situazione Economica";

	private static final Object SURE_TO_RESET_1 = "Sei sicuro di voler resettare l'applicazione?";
	private static final Object SURE_TO_RESET_2 = "Con questa operazione tutti i dati su operazioni e documenti, verranno cancellate!!\nProcedere??";
	private static final String RESET = "Reset";

	/**
	 * Crea il main menu dell'applicazione.
	 * 
	 * @param frameName
	 *            il nome del frame contenente il menu
	 * @param view
	 *            il controller della view
	 * @param controller
	 *            il controller dell'applicazione
	 */
	public MainMenu(final String frameName, final ViewController view,
			final Controller controller) {
		super(frameName, view, controller);

		setBounds(getScreen().width / 3, getScreen().height / 4,
				getScreen().width / 4, getScreen().height / 4);

		setResizable(false);

		final JButton btnExit = getGUIFactory().createButton(ESCI);
		final JButton btnOurContact = getGUIFactory().createButton(
				BTN_OUR_CONTACT_TEXT);
		final JButton btnReset = getGUIFactory().createButton(BTN_RESET_TEXT);
		btnReset.setBackground(Color.RED);

		final JPanel southPanelEast = getGUIFactory().createPanel();
		final JPanel southPanelWest = getGUIFactory().createPanel();

		final JPanel southPanel = getGUIFactory().createPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(southPanelWest, BorderLayout.WEST);
		southPanel.add(southPanelEast, BorderLayout.EAST);

		southPanelEast.add(btnExit);
		southPanelWest.add(btnReset);
		southPanelWest.add(btnOurContact);

		final JButton btnGestOp = getGUIFactory()
				.createButton(BTN_GEST_OP_TEXT);
		btnGestOp.addActionListener(e -> view.displayOperations());

		final JButton btnGestConti = getGUIFactory().createButton(
				BTN_GEST_CONTI_TEXT);
		btnGestConti.addActionListener(e -> view.displayConti());

		final JButton btnGestContatti = getGUIFactory().createButton(
				BTN_GEST_CONTATTI_TEXT);
		btnGestContatti.addActionListener(e -> view.displayContatti());

		final JButton btnSitPatrimoniale = getGUIFactory().createButton(
				BTN_VIEW_SIT_PATR);
		btnSitPatrimoniale
				.addActionListener(e -> view.displaySitPatrimoniale());

		final JButton btnSitEconomica = getGUIFactory().createButton(
				BTN_VIEW_SIT_ECO);
		btnSitEconomica.addActionListener(e -> view.displaySitEconomica());

		final JPanel centerPanel = getGUIFactory().createPanel(btnGestOp,
				btnGestConti, btnGestContatti, btnSitPatrimoniale,
				btnSitEconomica);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		btnExit.addActionListener(e -> quittingHandler());

		btnReset.addActionListener(e -> resetHandler());

		btnOurContact.addActionListener(e -> getViewController()
				.displayNostroContatto());
	}

	@Override
	public void quittingHandler() {
		final int answer = JOptionPane.showConfirmDialog(this, SURE_TO_EXIT,
				ESCI, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
			exitApplication();
		}
	}

	/**
	 * Esegue le operazioni finali di chiusura del'applicazione
	 */
	private void exitApplication() {
		System.exit(0);
	}

	/**
	 * Handler tasto reset
	 */
	private void resetHandler() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
				SURE_TO_RESET_1, RESET, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE)
				&& JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
						this, SURE_TO_RESET_2, RESET,
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE)) {
			getController().reset();
		}
	}

}
