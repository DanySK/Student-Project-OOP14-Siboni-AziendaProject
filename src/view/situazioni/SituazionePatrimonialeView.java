package view.situazioni;

import javax.swing.JTextArea;

import model.situazione.SituazionePatrimoniale;
import controller.Controller;
import view.ViewController;

import javax.swing.JSplitPane;

/**
 * Classe concreta che realizza la vista di una situazione patrimoniale.
 * 
 * @author Enrico
 *
 */
public class SituazionePatrimonialeView extends AbstractSituazioneView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6930398524142880639L;

	private static final int CHARACTERS_OF_SITUAZIONE_PATR = 200;

	private static final String TOT = "TOT ";
	private static final String ATTIVITA = "ATTIVITA'";
	private static final String PASSIVITA = "PASSIVITA' E NETTO";
	private static final String LIQUID_IMMEDIATE = "LIQUIDITA' IMMEDIATE";
	private static final String LIQUID_DIFFERITE = "LIQUIDITA' DIFFERITE";
	private static final String IMMOBILIZZAZIONI = "IMMOBILIZZAZZIONI";
	private static final String COSTI_SOSP = "COSTI SOSPESI";
	private static final String RICAVI_PLUR = "RICAVI PLURIENNALI";
	private static final String RICAVI_SOSP = "RICAVI SOSPESI";
	private static final String DEBITI = "DEBITI";
	private static final String PATRIM_NETTO = "PATRIMONIO NETTO";
	private static final String UTILE_ES = "UTILE DELL'ESERCIZIO";
	private static final String PERDITA_ES = "PERDITA DELL'ESERCIZIO";
	private static final String TOT_A_PAREGGIO = "TOT A PAREGGIO";

	private final int lineLength = Integer.max(getNameMaxLength()
			+ NUM_MEDIUM_LENGTH, PERDITA_ES.length());

	/**
	 * Crea il frame contenente la visualizzazione della situazione
	 * patrimoniale.
	 * 
	 * @param frameName
	 *            il nome del frame
	 * @param view
	 *            il controller della view
	 * @param controller
	 *            il controller dell'applicazione
	 */
	public SituazionePatrimonialeView(final String frameName,
			final ViewController view, final Controller controller) {
		super(frameName, view, controller);

		final JSplitPane splitPane = getGUIFactory().createSplitPane();
		getScrollInternalPanel().add(splitPane);

		final JTextArea textAreaLeft = new JTextArea();
		splitPane.setLeftComponent(textAreaLeft);
		textAreaLeft.setBackground(getBackground());

		final JTextArea textAreaRight = new JTextArea();
		splitPane.setRightComponent(textAreaRight);
		textAreaRight.setBackground(getBackground());

		final int nameMaxLength = Integer.max(PERDITA_ES.length(),
				getNameMaxLength());

		final SituazionePatrimoniale sitCached = controller
				.getSitPatrimoniale();

		final StringBuilder s = new StringBuilder(CHARACTERS_OF_SITUAZIONE_PATR);
		s.append(ATTIVITA).append("\n\n").append(IMMOBILIZZAZIONI).append('\n');
		appendConti(s, sitCached.getCostiPlur()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(IMMOBILIZZAZIONI);
		appendCharacters(s, ' ',
				nameMaxLength - (TOT + IMMOBILIZZAZIONI).length()).append('\t')
				.append(round(sitCached.getTotCostiPlur())).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append(LIQUID_DIFFERITE).append('\n');
		appendConti(s, sitCached.getLiquiditaDifferite()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(LIQUID_DIFFERITE);
		appendCharacters(s, ' ',
				nameMaxLength - (TOT + LIQUID_DIFFERITE).length()).append('\t')
				.append(round(sitCached.getTotLiquiditaDifferite()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append(LIQUID_IMMEDIATE).append('\n');
		appendConti(s, sitCached.getLiquiditaImmediate()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(LIQUID_IMMEDIATE);
		appendCharacters(s, ' ',
				nameMaxLength - (TOT + LIQUID_IMMEDIATE).length()).append('\t')
				.append(round(sitCached.getTotLiquiditaImmediate()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append(COSTI_SOSP).append('\n');
		appendConti(s, sitCached.getCostiSospesi()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(COSTI_SOSP);
		appendCharacters(s, ' ', nameMaxLength - (TOT + COSTI_SOSP).length())
				.append('\t').append(round(sitCached.getTotCostiSospesi()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append("\n\n").append(TOT)
				.append(ATTIVITA);
		appendCharacters(s, ' ', nameMaxLength - (TOT + ATTIVITA).length())
				.append('\t').append(round(sitCached.getTotDare()))
				.append('\n');
		appendCharacters(s, '=', lineLength);

		textAreaLeft.setText(s.toString());

		s.setLength(0);
		final double reddito = controller.getSitEconomica().getReddito();
		s.append(PASSIVITA).append("\n\n").append(PATRIM_NETTO).append('\n');
		appendConti(s, sitCached.getPatrimonioNetto()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(PATRIM_NETTO);
		appendCharacters(s, ' ', nameMaxLength - (TOT + PATRIM_NETTO).length())
				.append('\t').append(round(sitCached.getTotPatrimonioNetto()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append(DEBITI).append('\n');
		appendConti(s, sitCached.getDebiti()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(DEBITI);
		appendCharacters(s, ' ', nameMaxLength - (TOT + DEBITI).length())
				.append('\t').append(round(sitCached.getTotDebiti()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append(RICAVI_PLUR).append('\n');
		appendConti(s, sitCached.getRicaviPlur()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(RICAVI_PLUR);
		appendCharacters(s, ' ', nameMaxLength - (TOT + RICAVI_PLUR).length())
				.append('\t').append(round(sitCached.getTotRicaviPlur()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append(RICAVI_SOSP).append('\n');
		appendConti(s, sitCached.getRicaviSospesi()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(RICAVI_SOSP);
		appendCharacters(s, ' ', nameMaxLength - (TOT + COSTI_SOSP).length())
				.append('\t').append(round(sitCached.getTotRicaviSospesi()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append('\n')
				.append('\n').append(TOT).append(PASSIVITA);
		appendCharacters(s, ' ', nameMaxLength - (TOT + PASSIVITA).length())
				.append('\t').append(round(sitCached.getTotAvere()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append("\n\n").append(
				reddito >= 0 ? UTILE_ES : PERDITA_ES);
		appendCharacters(s, ' ',
				nameMaxLength - (reddito >= 0 ? UTILE_ES : PERDITA_ES).length())
				.append('\t').append(round(reddito)).append("\n\n")
				.append(TOT_A_PAREGGIO);
		appendCharacters(s, ' ', nameMaxLength - TOT_A_PAREGGIO.length())
				.append('\t').append(round(sitCached.getTotAvere() + reddito))
				.append('\n');
		appendCharacters(s, '=', lineLength);
		textAreaRight.setText(s.toString());

		textAreaLeft.setCaretPosition(0);
	}
}
