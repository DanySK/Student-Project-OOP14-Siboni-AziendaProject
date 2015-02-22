package view.operations;

import view.AbstractSearchListView;
import view.ViewController;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Insets;
import java.util.List;

import model.data.Data;
import model.data.DataImpl;
import model.operation.Operation;
import controller.Controller;

public class OperationsView extends AbstractSearchListView<Operation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6677986727299372138L;

	private static final String WRONG_DATA_ERROR = "Il formato della data deve essere GG/MM/AAAA, controllare inoltre di non aver inserito una data incongruente!!";
	private static final String WRONG_DATA_ORDER_ERROR = "Hai inserito le date nell'ordine sbagliato!!";
	private static final String NO_OPERATION_FOUND = "Non sono state trovate operazioni inserite tra le date indicate";
	private static final String BTN_NEW_OPERATION_TEXT = "Nuova Operazione";
	private static final String BTN_VIEW_DOCUMENT_TEXT = "Visualizza Documento";

	private static final String LABEL_TO_TEXT = "Al";
	private static final String LABEL_FROM_TEXT = "Dal";
	private static final String LABEL_DATA_TEXT = "Data";
	private static final String LABEL_OPERATION_TEXT = "Operazione";

	private final JTextField dateTo;
	private final JTextField dateFrom;

	/**
	 * Create the frame.
	 */
	public OperationsView(final String frameName, final ViewController view,
			final Controller controller) {
		super(frameName, view, controller);

		getNewElemButton().setText(BTN_NEW_OPERATION_TEXT);
		getActionButton().setText(BTN_VIEW_DOCUMENT_TEXT);

		getNorthPanel().remove(0);

		dateTo = getGUIFactory().createTextField();
		getNorthPanel().add(dateTo, 0);

		final JLabel lblTo = getGUIFactory().createLabel(LABEL_TO_TEXT);
		getNorthPanel().add(lblTo, 0);

		dateFrom = getGUIFactory().createTextField();
		getNorthPanel().add(dateFrom, 0);

		final JLabel lblFrom = getGUIFactory().createLabel(LABEL_FROM_TEXT);
		getNorthPanel().add(lblFrom, 0);

		final GridBagLayout headerPanelLayout = new GridBagLayout();
		headerPanelLayout.columnWidths = new int[] { 200, 0 };
		headerPanelLayout.rowHeights = new int[] { 0, 0 };
		headerPanelLayout.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		headerPanelLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		getScrollHeaderPanel().setLayout(headerPanelLayout);

		final GridBagConstraints headerPanelCnst = new GridBagConstraints();
		headerPanelCnst.fill = GridBagConstraints.HORIZONTAL;
		headerPanelCnst.insets = new Insets(2, 2, 2, 2);
		headerPanelCnst.gridx = 0;
		headerPanelCnst.gridy = 0;

		final JLabel lblData = getGUIFactory().createLabel(LABEL_DATA_TEXT);
		getScrollHeaderPanel().add(lblData, headerPanelCnst);

		final JLabel lblOperation = getGUIFactory().createLabel(
				LABEL_OPERATION_TEXT);
		headerPanelCnst.gridx++;
		getScrollHeaderPanel().add(lblOperation, headerPanelCnst);

		getCenterScroll().setViewportView(getList());

		if(controller.getLastOp().isPresent()){
			getListModel().addElement(controller.getLastOp().get());
		}
		
	}

	@Override
	protected void searchHandler() {
		if (dateFrom.getText().trim().isEmpty()
				|| dateTo.getText().trim().isEmpty()) {
			refresh();
		} else {
			Data from = new DataImpl();
			Data to = new DataImpl();
			boolean dataControlPassed = false;
			try {
				from = new DataImpl(dateFrom.getText().trim());
				to = new DataImpl(dateTo.getText().trim());
				dataControlPassed = true;
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, WRONG_DATA_ERROR,
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			}

			if (dataControlPassed) {
				if (from.compareTo(to) > 0) {
					JOptionPane.showMessageDialog(this, WRONG_DATA_ORDER_ERROR,
							TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
				} else if (!loadOperations(from, to)) {
					JOptionPane.showMessageDialog(this, NO_OPERATION_FOUND,
							"Operazioni non trovate",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

	}

	/**
	 * Carica le operazioni tra dataFrom e dataTo nella vista.
	 * 
	 * @param dataFrom
	 *            la data da cui caricare le operazioni
	 * @param dataTo
	 *            la data fino a cui caricare le operazioni
	 * @return true se ci sono operazioni da caricare, false altrimenti
	 */
	private boolean loadOperations(final Data dataFrom, final Data dataTo) {
		final List<Operation> list = getController().getOperations(dataFrom,
				dataTo);

		if (list.isEmpty()) {
			return false;
		}

		getListModel().clear();
		for (final Operation op : list) {
			getListModel().addElement(op);
		}

		return true;
	}

	@Override
	public void refresh() {
		getListModel().clear();
		if(getController().getLastOp().isPresent()){
			getListModel().addElement(getController().getLastOp().get());
		}
	}

	@Override
	protected void addHandler() {
		getViewController().displayInserimentoOp();
	}

	@Override
	protected void quittingHandler() {
		super.quittingHandler();
		dateTo.setText(null);
		dateFrom.setText(null);
		getViewController().displayMainMenu();
	}

	@Override
	protected void actionHandler() {
		System.out
				.println("Ancora da implementare la visualizzazione documenti :)");
	}
}
