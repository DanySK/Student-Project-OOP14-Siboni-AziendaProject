package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;

public abstract class AbstractInsertFrame extends AbstractFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8173351693637167153L;

	private static final String DATA_UNSAVED = "Ci sono dati inseriti non salvati, tornando indietro li perderai...\nVuoi tornare indietro?";
	private static final String BTN_CONFIRM_TEXT = "Inserisci";

	private final JButton btnConfirm = getGUIFactory().createButton(
			BTN_CONFIRM_TEXT);
	private final JPanel southPanel = getGUIFactory().createPanel(btnConfirm);
	private final JPanel centerPanel = getGUIFactory().createPanel();

	public AbstractInsertFrame(final String frameName, final ViewController v,
			final Controller c) {
		super(frameName, v, c);

		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout());

		getContentPane().add(centerPanel, BorderLayout.CENTER);

		btnConfirm.addActionListener(e -> addingHandler());
	}

	/**
	 * 
	 * @return il bottone di conferma inserimento
	 */
	protected JButton getAddButton() {
		return this.btnConfirm;
	}

	/**
	 * 
	 * @return il pannello sud
	 */
	protected JPanel getSouthPanel() {
		return this.southPanel;
	}

	/**
	 * 
	 * @return il pannello centrale
	 */
	protected JPanel getCenterPanel() {
		return this.centerPanel;
	}

	@Override
	protected void quittingHandler() {
		int answer = JOptionPane.YES_OPTION;
		if (someFieldsFull()) {
			answer = JOptionPane.showConfirmDialog(this, DATA_UNSAVED, null,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		if (answer == JOptionPane.YES_OPTION) {
			this.dispose();
			whenQuittingReturnHere();
		}
	}

	/**
	 * Handler bottone aggiunta
	 */
	protected abstract void addingHandler();

	/**
	 * Specifica dove tornare, dopo aver chiuso la finestra corrente.
	 */
	protected abstract void whenQuittingReturnHere();

	/**
	 * 
	 * @return true se almeno un campo è pieno, false se sono tutti vuoti
	 */
	protected abstract boolean someFieldsFull();
}
