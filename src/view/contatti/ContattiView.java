package view.contatti;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.contatti.Contatto;
import controller.Controller;
import view.AbstractSearchListView;
import view.ViewController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContattiView extends AbstractSearchListView<Contatto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -710736374429103001L;

	private static final int SEARCH_FIELD_WIDTH = 20;

	private static final String BTN_NEW_CONTACT_TEXT = "Nuovo Contatto";
	private static final String BTN_DELETE_CONTACT_TEXT = "Elimina Contatto/i";
	private static final String BTN_MODIFY_CONTACT_TEXT = "Modifica Contatto";
	private static final String LABEL_CONTATTI_TEXT = "Contatti";

	private static final String SURE_TO_DELETE = "Sei sicuro di voler eliminare il contatto: ";
	private static final String SURE_TO_DELETE_MANY = "Sei sicuro di voler eliminare tutti i contatti selezionati?";
	private static final String NOTHING_FOUND = "Non sono stati trovati contatti corrispondenti...";
	private static final String NOT_FOUND = "Non Trovato";

	private final JButton btnModify = getGUIFactory().createButton(
			BTN_MODIFY_CONTACT_TEXT);

	/**
	 * Create the frame.
	 */
	public ContattiView(final String frameName, final ViewController view,
			final Controller controller) {
		super(frameName, view, controller);

		getNewElemButton().setText(BTN_NEW_CONTACT_TEXT);

		getActionButton().setText(BTN_DELETE_CONTACT_TEXT);

		getEastPanelConstraints().gridy++;
		getEastButtonPanel().add(btnModify, getEastPanelConstraints());
		btnModify.setEnabled(false);

		getSearchField().setColumns(SEARCH_FIELD_WIDTH);

		final JLabel lblContatti = getGUIFactory().createLabel(
				LABEL_CONTATTI_TEXT);
		getScrollHeaderPanel().add(lblContatti);

		for (final Contatto c : controller.getInsiemeContatti()) {
			getListModel().addElement(c);
		}

		btnModify.addActionListener(e -> modifyHandler());
	}

	/**
	 * Handler bottone modifica
	 */
	protected void modifyHandler() {
		getViewController().displayModificaContatto(getList().getSelectedValue());
	}

	@Override
	public final void refresh() {
		getListModel().clear();
		for (final Contatto c : getController().getInsiemeContatti()) {
			getListModel().addElement(c);
		}
	}

	// TODO se ho tempo inserire la ricerca istantanea con un listener sulla
	// casella di testo
	@Override
	protected void searchHandler() {
		if (getSearchField().getText().trim().isEmpty()) {
			refresh();
		} else {
			final Set<Contatto> set = getController()
					.getInsiemeContatti()
					.stream()
					.filter(c -> stringStarts(c.getRagioneSociale(),
							getSearchField().getText())
							|| stringStarts(c.getNomeCognomeTitolare(),
									getSearchField().getText())
							|| stringStarts(c.getPIVA(), getSearchField()
									.getText())
							|| stringStarts(c.getCF(), getSearchField()
									.getText())
							|| stringStarts(c.getCAP().orElse(" "),
									getSearchField().getText())
							|| stringStarts(c.getProvincia().orElse(" "),
									getSearchField().getText())
							|| stringStarts(c.getCitta().orElse(" "),
									getSearchField().getText())
							|| stringStarts(c.getSedeLegale().orElse(" "),
									getSearchField().getText())
							|| stringStarts(c.getTelefono().orElse(" "),
									getSearchField().getText())).sorted()
					.collect(Collectors.toSet());
			if (set.isEmpty()) {
				JOptionPane.showMessageDialog(this, NOTHING_FOUND, NOT_FOUND,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				getListModel().clear();
				for (final Contatto c : set) {
					getListModel().addElement(c);
				}
			}
		}

	}

	@Override
	protected void addHandler() {
		getViewController().displayInserminetoContatto();
	}

	@Override
	protected void actionHandler() {
		int scelta = JOptionPane.NO_OPTION;
		final List<Contatto> selection = getList().getSelectedValuesList();
		if (selection.size() == 1) {
			scelta = JOptionPane.showConfirmDialog(this, SURE_TO_DELETE + '\n'
					+ selection.get(0) + " ?", null, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
		} else {
			scelta = JOptionPane.showConfirmDialog(this, SURE_TO_DELETE_MANY,
					null, JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
		}
		if (scelta == JOptionPane.YES_OPTION) {
			for (final Contatto c : selection) {
				getController().cancellaContatto(c);
			}
			refresh();
		}
	}

	@Override
	protected void quittingHandler() {
		super.quittingHandler();
		getViewController().displayMainMenu();
	}

	@Override
	protected void listSelectionHandler() {
		super.listSelectionHandler();
		if (getList().getSelectedValuesList().size() == 1) {
			btnModify.setEnabled(true);
		} else {
			btnModify.setEnabled(false);
		}
	}

	/**
	 * Controlla se una stringa parte con un'altra, senza far caso a spazi o
	 * casing.
	 * 
	 * @param toMatch
	 *            la stringa con cui fare match
	 * @param prefix
	 *            la stringa "prefisso" da utilizzare
	 * @return true se l'inizio di toMatch corrispinde con prefix, false
	 *         altrimenti
	 */
	private boolean stringStarts(final String toMatch, final String prefix) {
		return toMatch.trim().toLowerCase()
				.startsWith(prefix.trim().toLowerCase());
	}
}
