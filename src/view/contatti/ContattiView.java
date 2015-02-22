package view.contatti;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.contatti.Contatto;
import controller.Controller;
import view.AbstractSearchListView;
import view.ViewController;

import java.util.Set;
import java.util.stream.Collectors;

public class ContattiView extends AbstractSearchListView<Contatto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -710736374429103001L;

	private static final int SEARCH_FIELD_WIDTH = 20;

	private static final String BTN_NEW_CONTACT_TEXT = "Nuovo Contatto";
	private static final String BTN_DELETE_CONTACT_TEXT = "Elimina Contatto";
	private static final String LABEL_CONTATTI_TEXT = "Contatti";

	private static final String SURE_TO_DELETE = "Sei sicuro di voler eliminare il contatto: ";
	private static final String NOTHING_FOUND = "Non sono stati trovati contatti corrispondenti...";
	private static final String NOT_FOUND = "Non Trovato";

	/**
	 * Create the frame.
	 */
	public ContattiView(final String frameName, final ViewController view,
			final Controller controller) {
		super(frameName, view, controller);

		getNewElemButton().setText(BTN_NEW_CONTACT_TEXT);

		getActionButton().setText(BTN_DELETE_CONTACT_TEXT);

		getSearchField().setColumns(SEARCH_FIELD_WIDTH);

		final JLabel lblContatti = getGUIFactory().createLabel(
				LABEL_CONTATTI_TEXT);
		getScrollHeaderPanel().add(lblContatti);

		for (final Contatto c : controller.getInsiemeContatti()) {
			getListModel().addElement(c);
		}
	}

	@Override
	public final void refresh() {
		getListModel().clear();
		for (final Contatto c : getController().getInsiemeContatti()) {
			getListModel().addElement(c);
		}
	}

	//TODO se ho tempo inserire la ricerca istantanea con un listener sulla casella di testo
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
									getSearchField().getText()))
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
		final int scelta = JOptionPane.showConfirmDialog(this, SURE_TO_DELETE
				+ '\n' + getList().getSelectedValue() + " ?", null,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (scelta == JOptionPane.YES_OPTION) {
			getController().cancellaContatto(getList().getSelectedValue());
			refresh();
		}
	}

	@Override
	protected void quittingHandler() {
		super.quittingHandler();
		getViewController().displayMainMenu();
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
