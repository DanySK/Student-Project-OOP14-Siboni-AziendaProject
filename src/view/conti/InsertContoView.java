package view.conti;

import javax.swing.JOptionPane;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;
import javax.swing.JComboBox;

import view.AbstractInsertFrame;
import view.ViewController;
import controller.Controller;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.conto.ContoImpl;

/**
 * Classe concreta che realizza la vista di inserimento di un conto.
 * 
 * @author Enrico
 *
 */
public class InsertContoView extends AbstractInsertFrame {

	private static final long serialVersionUID = 582686582062429282L;

	private static final String EMPTY_NAME_ERROR = "Non hai inserito alcun nome per il conto!!";
	private static final String EMPTY_ACCESOA_ERROR = "Non hai inserito a cosa e' acceso questo conto!!";
	private static final String ALREADY_EXISTS_ERROR = "Hai inserito il nome di un conto gia esistente!!";

	private static final String BTN_ADD_TEXT = "Aggiungi";
	private static final String LABEL_CONTO_NAME_TEXT = "Nome Conto";
	private static final String LABEL_ACCESO_A_TEXT = "Acceso A";

	private final JTextField nameField;
	private final JComboBox<Conto.AccesoA> comboBox;

	/**
	 * Crea il frame di inserimento di un conto.
	 * 
	 * @param frameName
	 *            il nome del frame
	 * @param view
	 *            il controler della view
	 * @param controller
	 *            il controller dell'applicazione
	 */
	public InsertContoView(final String frameName, final ViewController view,
			final Controller controller) {
		super(frameName, view, controller);

		getAddButton().setText(BTN_ADD_TEXT);

		final GridBagLayout centerPanelLayout = new GridBagLayout();
		centerPanelLayout.columnWidths = new int[] { 0, 0, 0 };
		centerPanelLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		centerPanelLayout.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		centerPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		getCenterPanel().setLayout(centerPanelLayout);

		final JLabel lblNomeConto = getGUIFactory().createLabel(
				LABEL_CONTO_NAME_TEXT);
		final GridBagConstraints gbcLabels = new GridBagConstraints();
		gbcLabels.insets = new Insets(5, 5, 5, 5);
		gbcLabels.anchor = GridBagConstraints.EAST;
		gbcLabels.gridx = 0;
		gbcLabels.gridy = 0;
		getCenterPanel().add(lblNomeConto, gbcLabels);

		nameField = new JTextField();
		final GridBagConstraints gbcFields = new GridBagConstraints();
		gbcFields.insets = new Insets(5, 5, 5, 5);
		gbcFields.fill = GridBagConstraints.HORIZONTAL;
		gbcFields.gridx = 1;
		gbcFields.gridy = 0;
		getCenterPanel().add(nameField, gbcFields);
		nameField.setColumns(10);

		final JLabel lblAccesoA = getGUIFactory().createLabel(
				LABEL_ACCESO_A_TEXT);
		gbcLabels.gridy += 2;
		getCenterPanel().add(lblAccesoA, gbcLabels);

		comboBox = new JComboBox<>(Conto.AccesoA.values());
		gbcFields.gridy += 2;
		getCenterPanel().add(comboBox, gbcFields);
		comboBox.insertItemAt(null, 0);
		comboBox.setSelectedIndex(0);

		this.pack();
	}

	/**
	 * Metodo che setta il campo contoGenerated se l'input è ok.
	 * 
	 * @return true se passa i controlli per la validità del conto
	 */
	private boolean controlloInputPassed() {
		if (someFieldsFull()) {
			if (nameField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, EMPTY_NAME_ERROR,
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			} else if (comboBox.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, EMPTY_ACCESOA_ERROR,
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void addingHandler() {
		if (controlloInputPassed()) {
			final Conto resultConto = new ContoImpl(nameField.getText().trim(),
					(AccesoA) comboBox.getSelectedItem());
			if (getController().getInsiemeConti().contains(resultConto)) {
				JOptionPane.showMessageDialog(this, ALREADY_EXISTS_ERROR,
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			} else {
				getController().aggiuntaConto(resultConto);
				this.dispose();
				getViewController().displayConti();
			}
		}
	}

	@Override
	protected void whenQuittingReturnHere() {
		getViewController().displayConti();
	}

	@Override
	protected boolean someFieldsFull() {
		return !nameField.getText().trim().isEmpty()
				|| comboBox.getSelectedItem() != null;
	}
}
