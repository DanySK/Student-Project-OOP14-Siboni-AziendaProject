package view.contatti;

import java.util.Optional;

import javax.swing.JOptionPane;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import view.Saver;
import view.ViewController;
import controller.Controller;

/**
 * Classe concreta che realizza la vista di modifica e consultazione del nostro
 * contatto.
 * 
 * @author Enrico
 *
 */
public class InsertOurContactView extends InsertContattoView {

	private static final long serialVersionUID = -6649085106603895203L;

	private static final String BTN_ADD_TEXT = "Imposta";
	private static final String CONTACT_NECESSARY_ERROR = "E necessario inserire il nostro contatto !!";

	/**
	 * Crea il frame di inserimento/visualizzaqzione del nostro contatto.
	 * 
	 * @param frameName
	 *            il nome del frame
	 * @param view
	 *            il controller della view
	 * @param controller
	 *            il controller dell'applicazione
	 * @param existingContact
	 *            l'eventuale nostro contatto già esistente
	 */
	public InsertOurContactView(final String frameName,
			final ViewController view, final Controller controller,
			final Contatto existingContact) {
		super(frameName, view, controller, Optional.ofNullable(existingContact));

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
