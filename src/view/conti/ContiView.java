package view.conti;

import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.conto.Conto;
import controller.Controller;
import view.AbstractSearchListView;
import view.ViewController;

public class ContiView extends AbstractSearchListView<Conto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8640710017223019457L;

	private static final String SURE_TO_DELETE = "Sei sicuro di voler eliminare il conto: ";
	private static final String NOTHING_FOUND = "Non sono stati trovati conti corrispondenti...";
	private static final String NOT_FOUND = "Non Trovato";

	private static final String BTN_NEW_CONTO_TEXT = "Nuovo Conto";
	private static final String BTN_DELETE_CONTO = "Elimina Conto";
	private static final String LABEL_CONTO_TEXT = "Conto";

	private static final String CONTO_IN_USO_ERROR = "Non puoi cancellare un conto che hai movimentato!!\nQuesta operazione può essere fatta solo se il saldo del conto e' nullo!";

	/**
	 * Create the frame.
	 */
	public ContiView(final String frameName, final ViewController view,
			final Controller controller) {
		super(frameName, view, controller);

		getNewElemButton().setText(BTN_NEW_CONTO_TEXT);

		getActionButton().setText(BTN_DELETE_CONTO);

		final JLabel lblConto = getGUIFactory().createLabel(LABEL_CONTO_TEXT);

		getScrollHeaderPanel().add(lblConto);

		for (final Conto c : getController().getInsiemeConti()) {
			getListModel().addElement(c);
		}

	}

	@Override
	public final void refresh() {
		getListModel().clear();
		for (final Conto c : getController().getInsiemeConti()) {
			getListModel().addElement(c);
		}
	}

	@Override
	protected void searchHandler() {
		if (getSearchField().getText().trim().isEmpty()) {
			refresh();
		} else {
			final Set<Conto> set = getController()
					.getInsiemeConti()
					.stream()
					.filter(c -> c
							.getName()
							.toLowerCase()
							.startsWith(
									getSearchField().getText().trim()
											.toLowerCase()))
					.collect(Collectors.toSet());
			if (set.isEmpty()) {
				JOptionPane.showMessageDialog(this, NOTHING_FOUND, NOT_FOUND,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				getListModel().clear();
				for (final Conto c : set) {
					getListModel().addElement(c);
				}
			}
		}
	}

	@Override
	protected void addHandler() {
		getViewController().displayInserimentoConto();
	}

	@Override
	protected void actionHandler() {
		if (getList().getSelectedValue().getSaldo() == 0) {
			final int scelta = JOptionPane.showConfirmDialog(this,
					SURE_TO_DELETE + getList().getSelectedValue() + " ?", null,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (scelta == JOptionPane.YES_OPTION) {
				getController().cancellaConto(getList().getSelectedValue());
				refresh();
			}
		} else {
			JOptionPane.showMessageDialog(this, CONTO_IN_USO_ERROR,
					TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	protected void quittingHandler() {
		super.quittingHandler();
		getViewController().displayMainMenu();
	}

}
