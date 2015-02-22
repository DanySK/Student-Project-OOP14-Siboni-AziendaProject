package view.situazioni;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.conto.Conto;
import controller.Controller;
import view.AbstractViewFrame;
import view.ViewController;

public abstract class AbstractSituazioneView extends AbstractViewFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4576397769543108072L;
	private static final double TOLLERANZA = 0.001;

	protected static final int NUM_MEDIUM_LENGTH = 8;

	private final JScrollPane centerScroll = getGUIFactory().createScrollPane();
	private final JPanel scrollInternalPanel = getGUIFactory().createPanel();

	protected final int nameMaxLength = getController().getInsiemeConti()
			.stream().mapToInt(c -> c.getName().length()).max().orElse(0);

	protected AbstractSituazioneView(final String frameName,
			final ViewController v, final Controller c) {
		super(frameName, v, c);
		getContentPane().add(centerScroll, BorderLayout.CENTER);

		centerScroll.setViewportView(scrollInternalPanel);

	}

	/**
	 * 
	 * @return lo scroll pane centrale
	 */
	protected JScrollPane getCenterScroll() {
		return this.centerScroll;
	}

	/**
	 * 
	 * @return il pennello interno allo scrollPane
	 */
	protected JPanel getScrollInternalPanel() {
		return this.scrollInternalPanel;
	}

	/**
	 * Aggiunge un carattere allo stringBuilder numTimes volte.
	 * 
	 * @param strB
	 *            lo StringBuilder a cui aggiungere gli spazi
	 * @param numSpaces
	 *            la lunghezza della parola da scrivere
	 * @param numTimes
	 *            il numero di volte da inserire il carattere
	 * @return lo string builder utilizzato per appendere gli spazi, in modo ca
	 *         continuare dopo averchiamato il metodo
	 */
	protected StringBuilder appendCharacters(final StringBuilder strB,
			final char character, final int numTimes) {
		for (int i = 0; i < numTimes; i++) {
			strB.append(character);
		}
		return strB;
	}

	/**
	 * Aggiunge i conti del set allo StringBuilder passato.
	 * 
	 * @param strB
	 *            lo string builder da utilizzare
	 * @param set
	 *            il set di conti da aggiungere
	 * @return lo string builder modificato
	 */
	protected StringBuilder appendConti(final StringBuilder strB,
			final Set<Conto> set) {
		for (final Conto c : set) {
			strB.append(c);
			appendCharacters(strB, ' ', nameMaxLength - c.getName().length())
					.append('\t').append(c.getSaldo()).append('\n');
		}
		return strB;
	}

	/**
	 * Arrotondamento a due cifre decimali.
	 * 
	 * @param toRound
	 *            il numero da arrotondare
	 * @return il numero arrotondato
	 */
	protected double round(final double toRound) {
		return toRound == 0 ? 0.0
				: (Math.ceil(toRound * 100 - TOLLERANZA)) / 100;
	}

	@Override
	protected void quittingHandler() {
		this.dispose();
		getViewController().displayMainMenu();
	}
}
