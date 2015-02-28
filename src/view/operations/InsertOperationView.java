package view.operations;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.conto.Conto;
import model.conto.Conto.Eccedenza;
import model.operation.Operation;
import model.operation.OperationImpl;
import view.AbstractInsertFrame;
import view.ViewController;
import controller.Controller;

/**
 * Classe concreta che realizza la vista di inserimento delle operazioni.
 * 
 * @author Enrico
 *
 */
public class InsertOperationView extends AbstractInsertFrame {

	private static final long serialVersionUID = 2998504799503573659L;

	private static final String CONTO_NULL_ERROR = "Il campo Conto non puo essere vuoto, se hai inserito un importo affianco!!";
	private static final String BOTH_FIELDS_FULL_ERROR = "I campi Dare e Avere non possono essere compilati contemporaneamente!!";
	private static final String BOTH_FIELDS_EMPTY_ERROR = "I campi Dare e Avere non possono essere entrambi vuoti!!";
	private static final String NOT_ENOUGH_LINES_COMPLETED_ERROR = "Un'operazione deve essere formata da almeno due movimentazioni di due conti diversi!!";
	private static final String NEGATIVE_OR_NULL_NUMBER_ERROR = "Gli importi iscritti non devono essere negativi o nulli";
	private static final String NOT_BALANCED_ERROR = "L'operazione non bilancia!! Ricontrollare gli importi!!";
	private static final String NOT_A_NUMBER_ERROR = "Non hai inserito un numero!!";
	private static final int MAX_LINES_BEFORE_SCROLL = 10;
	private static final int INITIAL_LINES = 2;

	private static final String BTN_ADD_LINE_TEXT = "Nuova Riga";
	private static final String BTN_END_OF_INPUT_TEXT = "Fine";
	private static final String LABEL_CONTO_TEXT = "Conto";
	private static final String LABEL_DARE_TEXT = "Dare";
	private static final String LABEL_AVERE_TEXT = "Avere";
	private static final String LABEL_DESCRIPTION_TEXT = "Descrizione";

	private final List<JComboBox<Conto>> listComboBox = new ArrayList<>();
	private final List<JTextField> listDare = new ArrayList<>();
	private final List<JTextField> listAvere = new ArrayList<>();
	private final Conto[] arrayConti;
	private final JTextArea descriptionArea;
	private Operation opGenerated;

	/**
	 * Crea il frame di inseriemento delle operazioni.
	 * 
	 * @param frameName
	 *            il nome del frame
	 * @param view
	 *            il controller della viwe
	 * @param c
	 *            il controller dell'applicazione
	 */
	public InsertOperationView(final String frameName,
			final ViewController view, final Controller c) {
		super(frameName, view, c);
		this.arrayConti = c.getInsiemeConti().toArray(
				new Conto[c.getInsiemeConti().size()]);

		getContentPane().removeAll();

		getSouthPanel().removeAll();
		getSouthPanel().setLayout(new BorderLayout());
		getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);

		final JLabel lblDescrizione = getGUIFactory().createLabel(
				LABEL_DESCRIPTION_TEXT);
		getSouthPanel().add(lblDescrizione, BorderLayout.WEST);

		descriptionArea = getGUIFactory().createTextArea();
		descriptionArea.setRows(3);
		descriptionArea.setLineWrap(true);
		final JScrollPane scroll = getGUIFactory().createScrollPane();
		scroll.setViewportView(descriptionArea);
		getSouthPanel().add(scroll, BorderLayout.CENTER);

		final JPanel centerPanelInternal = getGUIFactory().createPanel();

		getCenterPanel().add(centerPanelInternal);

		final JScrollPane centerScroll = getGUIFactory().createScrollPane();
		centerScroll.setViewportView(getCenterPanel());
		getContentPane().add(centerScroll, BorderLayout.CENTER);

		final GridBagLayout centerPanelInternalLayout = new GridBagLayout();
		centerPanelInternalLayout.columnWidths = new int[] { 170, 150, 150, 0 };
		centerPanelInternalLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		centerPanelInternalLayout.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		centerPanelInternalLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		centerPanelInternal.setLayout(centerPanelInternalLayout);

		final JPanel scrollHeaderPanel = getGUIFactory().createPanel();
		scrollHeaderPanel.setLayout(centerPanelInternalLayout);
		centerScroll.setColumnHeaderView(getGUIFactory().createPanel(
				scrollHeaderPanel));

		final GridBagConstraints gbcLabels = new GridBagConstraints();
		gbcLabels.insets = new Insets(0, 0, 5, 5);
		gbcLabels.gridx = 0;
		gbcLabels.gridy = 0;

		final JLabel lblConto = getGUIFactory().createLabel(LABEL_CONTO_TEXT);
		scrollHeaderPanel.add(lblConto, gbcLabels);

		final JLabel lblDare = getGUIFactory().createLabel(LABEL_DARE_TEXT);
		gbcLabels.gridx++;
		scrollHeaderPanel.add(lblDare, gbcLabels);

		final JLabel lblAvere = getGUIFactory().createLabel(LABEL_AVERE_TEXT);
		gbcLabels.gridx++;
		scrollHeaderPanel.add(lblAvere, gbcLabels);

		final GridBagConstraints gbcFields = new GridBagConstraints();
		gbcFields.insets = new Insets(0, 0, 5, 5);
		gbcFields.gridx = 0;
		gbcFields.gridy = 0;
		gbcFields.insets = new Insets(0, 0, 5, 5);
		gbcFields.fill = GridBagConstraints.HORIZONTAL;

		for (int i = 0; i < INITIAL_LINES; i++) {
			gbcFields.gridx = 0;
			gbcFields.gridy++;

			final JComboBox<Conto> tmpComboBox = getGUIFactory()
					.createComboBox(this.arrayConti);
			tmpComboBox.insertItemAt(null, 0);
			tmpComboBox.setSelectedIndex(0);
			listComboBox.add(tmpComboBox);
			centerPanelInternal.add(listComboBox.get(listComboBox.size() - 1),
					gbcFields);

			final JTextField tmpTextField1 = getGUIFactory().createTextField();
			listDare.add(tmpTextField1);
			gbcFields.gridx++;
			centerPanelInternal.add(listDare.get(listDare.size() - 1),
					gbcFields);

			final JTextField tmpTextField2 = getGUIFactory().createTextField();
			listAvere.add(tmpTextField2);
			gbcFields.gridx++;
			centerPanelInternal.add(listAvere.get(listAvere.size() - 1),
					gbcFields);
		}

		final JPanel eastPanel = getGUIFactory().createPanel();
		eastPanel.setLayout(new BorderLayout());
		getContentPane().add(eastPanel, BorderLayout.EAST);

		final JButton btnNuovaRiga = getGUIFactory().createButton(
				BTN_ADD_LINE_TEXT);
		getAddButton().setText(BTN_END_OF_INPUT_TEXT);

		eastPanel.add(btnNuovaRiga, BorderLayout.NORTH);
		eastPanel.add(getAddButton(), BorderLayout.SOUTH);
		this.pack();

		final Dimension d = this.getSize();
		this.setMinimumSize(d);

		btnNuovaRiga.addActionListener((e) -> {
			gbcFields.gridy++;

			final JComboBox<Conto> comboBox = getGUIFactory().createComboBox(
					this.arrayConti);
			comboBox.insertItemAt(null, 0);
			comboBox.setSelectedIndex(0);
			listComboBox.add(comboBox);
			gbcFields.gridx = 0;
			centerPanelInternal.add(listComboBox.get(listComboBox.size() - 1),
					gbcFields);

			final JTextField tmpTextField1 = getGUIFactory().createTextField();
			listDare.add(tmpTextField1);
			gbcFields.gridx++;
			centerPanelInternal.add(listDare.get(listDare.size() - 1),
					gbcFields);

			final JTextField tmpTextField2 = getGUIFactory().createTextField();
			tmpTextField2.setColumns(10);
			listAvere.add(tmpTextField2);
			gbcFields.gridx++;
			centerPanelInternal.add(listAvere.get(listAvere.size() - 1),
					gbcFields);

			if (listComboBox.size() - 1 == MAX_LINES_BEFORE_SCROLL) {
				d.width += 15;
			}
			if (listComboBox.size() > MAX_LINES_BEFORE_SCROLL) {
				this.setMinimumSize(d);
				this.validate();
			} else {
				this.pack();
			}

		});
	}

	/**
	 * Metodo che controlla l'inserimento; controlla che i campi non siano in
	 * parte vuoti o compilati in maniera non corretta e che non ci siano numeri
	 * negativi;
	 * 
	 * @return true se i controlli passano, false altrimenti
	 */
	private boolean controlloInserimentoPassed() {
		int passed = 0;

		for (int i = 0; i < this.listComboBox.size() && passed >= 0; i++) {
			if (this.listComboBox.get(i).getSelectedItem() == null) {
				if (!this.listDare.get(i).getText().trim().isEmpty()
						|| !this.listAvere.get(i).getText().trim().isEmpty()) {
					showErrorDialog(i + 1, "\n" + CONTO_NULL_ERROR);
					passed = -1;
				}
			} else {
				try {
					if (!this.listDare.get(i).getText().trim().isEmpty()
							&& !this.listAvere.get(i).getText().trim()
									.isEmpty()) {
						showErrorDialog(i + 1, "\n" + BOTH_FIELDS_FULL_ERROR);
						passed = -1;
					} else if (this.listDare.get(i).getText().trim().isEmpty()
							&& this.listAvere.get(i).getText().trim().isEmpty()) {
						showErrorDialog(i + 1, "\n" + BOTH_FIELDS_EMPTY_ERROR);
						passed = -1;
					} else if (!this.listDare.get(i).getText().trim().isEmpty()
							&& Double.valueOf(this.listDare.get(i).getText()) <= 0
							|| !this.listAvere.get(i).getText().trim()
									.isEmpty()
							&& Double.valueOf(this.listAvere.get(i).getText()) <= 0) {
						showErrorDialog(i + 1, "\n"
								+ NEGATIVE_OR_NULL_NUMBER_ERROR);
						passed = -1;
					} else {
						passed++;
					}
				} catch (NumberFormatException e) {
					showErrorDialog(i + 1, "\n" + NOT_A_NUMBER_ERROR);
					passed = -1;
				}
			}

		}

		if ((passed != -1 && passed < 2)
				|| (passed == 2 && listComboBox.get(0).getSelectedItem() == listComboBox
						.get(1).getSelectedItem())) {
			JOptionPane.showMessageDialog(this,
					NOT_ENOUGH_LINES_COMPLETED_ERROR, TITOLO_ERRORE,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return passed == -1 ? false : true;
	}

	/**
	 * Metodo che ha il compito di creare l'operazione dalle selezioni
	 * dell'utente, e intercettare qualora l'operazione non bilanci, generando
	 * un messaggio d'errore.
	 * 
	 * @return true se l'operazione generata bilancia, false se ci sono problemi
	 */
	private boolean generateOperationPassed() {
		final Operation resultOperation = new OperationImpl();
		resultOperation.setDescription(this.descriptionArea.getText());
		for (int i = 0; i < this.listComboBox.size(); i++) {
			final Conto selected = (Conto) this.listComboBox.get(i)
					.getSelectedItem();
			if (selected != null) {
				if (!this.listDare.get(i).getText().trim().isEmpty()) {
					if (selected.getEccedenzaSolita() == Eccedenza.DARE) {
						resultOperation.setContoMovimentato(selected,
								Double.valueOf(this.listDare.get(i).getText()));
					} else {
						resultOperation
								.setContoMovimentato(selected,
										-Double.valueOf(this.listDare.get(i)
												.getText()));
					}
				} else {
					if (selected.getEccedenzaSolita() == Eccedenza.AVERE) {
						resultOperation
								.setContoMovimentato(selected, Double
										.valueOf(this.listAvere.get(i)
												.getText()));
					} else {
						resultOperation.setContoMovimentato(selected, -Double
								.valueOf(this.listAvere.get(i).getText()));
					}
				}
			}
		}

		if (resultOperation.isBalanced()) {
			this.opGenerated = resultOperation;
			return true;
		} else {
			JOptionPane.showMessageDialog(this, NOT_BALANCED_ERROR,
					TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Metodo che mostra un messaggio di errore con riga in cui è avvenuto.
	 * 
	 * @param rigaErrore
	 *            la riga in cui è avvenuto l'errore
	 * @param text
	 *            il testo da mostrare per l'errore
	 */
	private void showErrorDialog(final int rigaErrore, final String text) {
		JOptionPane.showMessageDialog(this, "Riga: " + rigaErrore + text,
				TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	protected void addingHandler() {
		if (controlloInserimentoPassed() && generateOperationPassed()) {
			getController().aggiuntaOperazione(opGenerated);
			this.dispose();
			getViewController().displayOperations();
		}
	}

	@Override
	protected void whenQuittingReturnHere() {
		getViewController().displayOperations();
	}

	@Override
	protected boolean someFieldsFull() {
		for (int i = 0; i < listComboBox.size(); i++) {
			if (listComboBox.get(i).getSelectedItem() != null
					|| !(listDare.get(i).getText().trim().isEmpty())
					|| !(listAvere.get(i).getText().trim().isEmpty())) {
				return true;
			}
		}
		return false;
	}
}
