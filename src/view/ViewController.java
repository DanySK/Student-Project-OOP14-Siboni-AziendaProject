package view;

import model.contatti.Contatto;

/**
 * Descrive il comportamento della vista dell'applicazione.
 * 
 * @author Enrico
 *
 */
public interface ViewController {

	/**
	 * Mostra il menu pricipale.
	 */
	void displayMainMenu();

	/**
	 * Mostra un messaggio di errore.
	 * 
	 * @param errorMessage
	 */
	void displayError(String errorMessage);

	void displayContatti();

	void displayOperations();

	void displayInserimentoOp();

	void displayDocument();

	void displaySitEconomica();

	void displaySitPatrimoniale();

	void displayConti();

	void displayInserimentoConto();
	
	void displayInserminetoContatto();
	
	void displayModificaContatto(Contatto contatto);

	void displayNostroContatto();
	
}
