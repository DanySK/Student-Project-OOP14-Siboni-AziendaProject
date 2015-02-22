package view.situazioni;

import javax.swing.JTextArea;

import model.situazione.SituazioneEconomica;
import controller.Controller;
import view.ViewController;

public class SituazioneEconomicaView extends AbstractSituazioneView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5710910355902729426L;

	private static final int CHARACTERS_OF_SITUAZIONE_ECO = 100;
	private static final String TOT = "TOT ";
	private static final String COSTI = "COSTI";
	private static final String RICAVI = "RICAVI";
	private static final String REDDITO = "REDDITO";

	private final int lineLength = Integer.max(nameMaxLength
			+ NUM_MEDIUM_LENGTH, (TOT + RICAVI).length());

	/**
	 * Create the frame.
	 */
	public SituazioneEconomicaView(final String frameName,
			final ViewController view, final Controller controller) {
		super(frameName, view, controller);

		final JTextArea textArea = getGUIFactory().createTextArea();
		textArea.setEditable(false);
		textArea.setBackground(getBackground());

		getScrollInternalPanel().add(textArea);

		final SituazioneEconomica sitCached = controller.getSitEconomica();

		final StringBuilder s = new StringBuilder(CHARACTERS_OF_SITUAZIONE_ECO);
		s.append(COSTI).append('\n');
		appendConti(s, sitCached.getContiDare()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(COSTI);
		appendCharacters(s, ' ', nameMaxLength - (TOT + COSTI).length())
				.append('\t').append(round(sitCached.getTotDare()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append("\n\n").append(RICAVI)
				.append('\n');
		appendConti(s, sitCached.getContiAvere()).append('\n');
		appendCharacters(s, '=', lineLength).append('\n').append(TOT)
				.append(RICAVI);
		appendCharacters(s, ' ', nameMaxLength - (TOT + RICAVI).length())
				.append('\t').append(round(sitCached.getTotAvere()))
				.append('\n');
		appendCharacters(s, '=', lineLength).append("\n\n").append(REDDITO);
		appendCharacters(s, ' ', nameMaxLength - REDDITO.length()).append('\t')
				.append(round(sitCached.getReddito())).append('\n');
		appendCharacters(s, '=', lineLength);
		textArea.setText(s.toString());
		textArea.setCaretPosition(0);

	}
}
