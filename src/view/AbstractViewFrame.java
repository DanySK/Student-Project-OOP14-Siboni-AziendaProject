package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.Controller;

/**
 * Classe astratta che definisce il conportamento generico di una finestra
 * dell'applicazione, con pulsante di ritorno indietro.
 * 
 * @author Enrico
 *
 */
public abstract class AbstractViewFrame extends AbstractFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5185631268279454969L;
	private static final String BTN_BACK_TEXT = "Indietro";

	private final JButton btnBack = getGUIFactory().createButton(BTN_BACK_TEXT);
	private final JPanel southPanel = getGUIFactory().createPanel(btnBack);

	/**
	 * Costruttore di un generico frame con pulsante di ritorno indietro.
	 * 
	 * @param frameName
	 *            il nome nella barra del titolo
	 * @param v
	 *            il controllore della view
	 * @param c
	 *            il controllore generale dell'applicazione
	 */
	protected AbstractViewFrame(final String frameName, final ViewController v,
			final Controller c) {
		super(frameName, v, c);

		setBounds(getScreen().width / 4, getScreen().height / 4,
				getScreen().width / 2, getScreen().height / 2);

		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		btnBack.addActionListener(e -> quittingHandler());

	}

	/**
	 * 
	 * @return il pannello sud del frame
	 */
	protected JPanel getSouthPanel() {
		return this.southPanel;
	}

	/**
	 * 
	 * @return il bottone indietro del frame
	 */
	protected JButton getBackButton() {
		return this.btnBack;
	}

}
