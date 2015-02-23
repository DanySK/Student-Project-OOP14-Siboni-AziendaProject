package view.contatti;

import controller.Controller;
import view.AbstractInsertFrame;
import view.ViewController;

import javax.swing.JOptionPane;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;

import java.awt.Insets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InsertContattoView extends AbstractInsertFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7888374192911595938L;

	private static final String LABEL_RAGIONE_SOCIALE_TEXT = "Ragione Sociale";
	private static final String LABEL_NOME_TIT_TEXT = "Nome Titolare";
	private static final String LABEL_CF_TEXT = "C.F.";
	private static final String LABEL_PIVA_TEXT = "P.IVA";
	private static final String LABEL_TEL_TEXT = "Telefono";
	private static final String LABEL_VIA_TEXT = "Via";
	private static final String LABEL_PROV_TEXT = "Provincia";
	private static final String LABEL_CAP_TEXT = "CAP";
	private static final String LABEL_CITY_TEXT = "Citta'";

	private static final int CF_CHARACTERS = 16;

	protected final JTextField ragSocField;
	protected final JTextField nomeTitField;
	protected final JTextField cfField;
	protected final JTextField pivaField;
	protected final JTextField viaField;
	protected final JTextField cittaField;
	protected final JTextField provField;
	protected final JTextField capField;
	protected final JTextField telField;
	private boolean modifyMode;

	/**
	 * Create the frame.
	 */
	public InsertContattoView(final String frameName,
			final ViewController view, final Controller controller,
			final Optional<Contatto> toModify) {
		super(frameName, view, controller);

		final GridBagLayout centerPanelLayout = new GridBagLayout();
		centerPanelLayout.columnWidths = new int[] { 0, 0, 0 };
		centerPanelLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		centerPanelLayout.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		centerPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getCenterPanel().setLayout(centerPanelLayout);

		final GridBagConstraints gbcLabels = new GridBagConstraints();
		gbcLabels.insets = new Insets(5, 5, 5, 5);
		gbcLabels.anchor = GridBagConstraints.EAST;
		gbcLabels.gridx = 0;
		gbcLabels.gridy = 0;

		final GridBagConstraints gbcFields = new GridBagConstraints();
		gbcFields.insets = new Insets(5, 5, 5, 5);
		gbcFields.fill = GridBagConstraints.HORIZONTAL;
		gbcFields.gridx = 1;
		gbcFields.gridy = 0;

		final JLabel lblRagioneSociale = getGUIFactory().createLabel(
				LABEL_RAGIONE_SOCIALE_TEXT);
		getCenterPanel().add(lblRagioneSociale, gbcLabels);

		ragSocField = getGUIFactory().createTextField();
		getCenterPanel().add(ragSocField, gbcFields);

		final JLabel lblNomeTitolare = getGUIFactory().createLabel(
				LABEL_NOME_TIT_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblNomeTitolare, gbcLabels);

		nomeTitField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(nomeTitField, gbcFields);

		final JLabel lblCf = getGUIFactory().createLabel(LABEL_CF_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblCf, gbcLabels);

		cfField = getGUIFactory().createTextField();
		cfField.setColumns(CF_CHARACTERS);
		gbcFields.gridy++;
		getCenterPanel().add(cfField, gbcFields);

		final JLabel lblPiva = getGUIFactory().createLabel(LABEL_PIVA_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblPiva, gbcLabels);

		pivaField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(pivaField, gbcFields);

		final JLabel lblVia = getGUIFactory().createLabel(LABEL_VIA_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblVia, gbcLabels);

		viaField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(viaField, gbcFields);

		final JLabel lblCitta = getGUIFactory().createLabel(LABEL_CITY_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblCitta, gbcLabels);

		cittaField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(cittaField, gbcFields);

		final JLabel lblProvincia = getGUIFactory()
				.createLabel(LABEL_PROV_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblProvincia, gbcLabels);

		provField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(provField, gbcFields);

		final JLabel lblCap = getGUIFactory().createLabel(LABEL_CAP_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblCap, gbcLabels);

		capField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(capField, gbcFields);

		final JLabel lblTel = getGUIFactory().createLabel(LABEL_TEL_TEXT);
		gbcLabels.gridy++;
		getCenterPanel().add(lblTel, gbcLabels);

		telField = getGUIFactory().createTextField();
		gbcFields.gridy++;
		getCenterPanel().add(telField, gbcFields);

		if (toModify != null && toModify.isPresent()) {
			this.capField.setText(toModify.get().getCAP().orElse(null));
			this.cfField.setText(toModify.get().getCF());
			this.cittaField.setText(toModify.get().getCitta().orElse(null));
			this.nomeTitField.setText(toModify.get().getNomeCognomeTitolare());
			this.pivaField.setText(toModify.get().getPIVA());
			this.ragSocField.setText(toModify.get().getRagioneSociale());
			this.telField.setText(toModify.get().getTelefono().orElse(null));
			this.viaField.setText(toModify.get().getSedeLegale().orElse(null));
			this.provField.setText(toModify.get().getProvincia().orElse(null));
			this.modifyMode = true;
		}

		pack();
	}

	/**
	 * @param field
	 *            da cui prendere il testo
	 * @return il testo di un textfield se c'è, altrimenti torna null
	 */
	protected String getTextInField(final JTextField field) {
		return field.getText().trim().isEmpty() ? null : field.getText().trim();
	}

	@Override
	protected void addingHandler() {
		if (someFieldsFull()) {

			try {
				final Contatto toInsert = new ContattoImpl.Builder()
						.setCAP(getTextInField(capField))
						.setCF(getTextInField(cfField))
						.setCitta(getTextInField(cittaField))
						.setNomeTitolare(getTextInField(nomeTitField))
						.setPIVA(getTextInField(pivaField))
						.setProvincia(getTextInField(provField))
						.setRagSoc(getTextInField(ragSocField))
						.setSedeLeg(getTextInField(viaField))
						.setTelefono(getTextInField(telField)).build();

				final List<Contatto> list = getController()
						.getInsiemeContatti().stream()
						.filter(c -> c.equals(toInsert))
						.collect(Collectors.toList());

				if (list.isEmpty() || this.modifyMode) {
					insertThisAndClose(toInsert);
				} else {
					list.add(toInsert);

					final Map<String, Contatto> tmpMap = list.stream().collect(
							Collectors.toMap(c -> c.toString(), c -> c));

					final String selected = (String) JOptionPane
							.showInputDialog(this,
									"Scegli quale contatto mantenere:",
									"Seleziona Contatto",
									JOptionPane.QUESTION_MESSAGE, null, tmpMap
											.keySet().toArray(), null);
					if (selected != null) {
						insertThisAndClose(tmpMap.get(selected));
					}
				}

			} catch (IllegalStateException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(),
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	/**
	 * Inserisce il contatto nel modello e chiude la finestra insermento
	 * tornando indietro
	 * 
	 * @param toInsert
	 *            il contatto da inserire
	 */
	private void insertThisAndClose(final Contatto toInsert) {
		getController().aggiuntaContatto(toInsert);
		this.dispose();
		whenQuittingReturnHere();
	}

	@Override
	protected void whenQuittingReturnHere() {
		getViewController().displayContatti();
	}

	@Override
	protected boolean someFieldsFull() {
		return !ragSocField.getText().trim().isEmpty()
				|| !nomeTitField.getText().trim().isEmpty()
				|| !pivaField.getText().trim().isEmpty()
				|| !cfField.getText().trim().isEmpty()
				|| !provField.getText().trim().isEmpty()
				|| !capField.getText().trim().isEmpty()
				|| !telField.getText().trim().isEmpty()
				|| !viaField.getText().trim().isEmpty()
				|| !cittaField.getText().trim().isEmpty();
	}
	

}
