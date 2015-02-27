package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import controller.Controller;

/**
 * Classe astratta per un generico frame dell'applicazione.
 * 
 * @author Enrico
 *
 */
public abstract class AbstractFrame extends JFrame implements
		IMyApplicationFrame {

	private static final long serialVersionUID = -8367366047326420881L;

	/**
	 * Stringa titolo per un messaggio di errore.
	 */
	protected static final String TITOLO_ERRORE = "Errore";

	private final transient ViewController v;
	private final transient Controller c;
	private final transient GUIFactory factory = new GUIFactory.Standard();
	private final Dimension screenDim = Toolkit.getDefaultToolkit()
			.getScreenSize();

	/**
	 * Costruttore di un generico frame dell'applicazione.
	 * 
	 * @param frameName
	 *            il nome nella barra del titolo
	 * @param v
	 *            il controllore della view
	 * @param c
	 *            il controllore generale dell'applicazione
	 */
	protected AbstractFrame(final String frameName, final ViewController v,
			final Controller c) {
		this.c = c;
		this.v = v;

		setTitle(frameName);

		setBounds(getScreen().width / 4, getScreen().height / 4,
				getScreen().width / 4, getScreen().height / 4);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());

		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(final WindowEvent arg0) {
			}

			@Override
			public void windowClosed(final WindowEvent arg0) {
			}

			@Override
			public void windowClosing(final WindowEvent arg0) {
				quittingHandler();
			}

			@Override
			public void windowDeactivated(final WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(final WindowEvent arg0) {
			}

			@Override
			public void windowIconified(final WindowEvent arg0) {
			}

			@Override
			public void windowOpened(final WindowEvent arg0) {
			}
		});
	}

	@Override
	public Controller getController() {
		return this.c;
	}

	@Override
	public ViewController getViewController() {
		return this.v;
	}

	/**
	 * 
	 * @return la factory per la gui
	 */
	protected GUIFactory getGUIFactory() {
		return this.factory;
	}

	/**
	 * 
	 * @return la dimensione dello schermo
	 */
	protected Dimension getScreen() {
		return screenDim;
	}

	/**
	 * Esegue le operazioni prima di chiudere la view corrente.
	 */
	protected abstract void quittingHandler();
}
