package view.contatti;

import javax.swing.JOptionPane;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import view.Saver;
import view.ViewController;
import controller.Controller;

public class InsertOurContactView extends InsertContattiView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6649085106603895203L;

	private static final String BTN_ADD_TEXT = "Imposta";
	private static final String CONTACT_NECESSARY_ERROR = "E necessario inserire il nostro contatto !!";

	public InsertOurContactView(final String frameName,
			final ViewController view, final Controller controller) {
		super(frameName, view, controller);

		final Contatto existingContact = controller.getOurContact();
		if (existingContact != null) {
			this.capField.setText(existingContact.getCAP().orElse(null));
			this.cfField.setText(existingContact.getCF());
			this.cittaField.setText(existingContact.getCitta().orElse(null));
			this.nomeTitField.setText(existingContact.getNomeCognomeTitolare());
			this.pivaField.setText(existingContact.getPIVA());
			this.ragSocField.setText(existingContact.getRagioneSociale());
			this.telField.setText(existingContact.getTelefono().orElse(null));
			this.viaField.setText(existingContact.getSedeLegale().orElse(null));
			this.provField.setText(existingContact.getProvincia().orElse(null));
		}

		getAddButton().setText(BTN_ADD_TEXT);

		pack();
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

				getController().setOurContact(toInsert);
				quittingHandler();
			} catch (IllegalStateException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(),
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	protected void quittingHandler() {
		if (getController().getOurContact() == null) {
			JOptionPane.showMessageDialog(this, CONTACT_NECESSARY_ERROR,
					TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
		} else {
			final Saver s = new Saver(getController());
			s.start();
			this.dispose();
			getViewController().displayMainMenu();
		}

	}
}
