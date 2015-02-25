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
	 * @param errorMessage il messaggio di errore da mostrare
	 */
	void displayError(String errorMessage);

	/**
	 * Mostra la vista dei contatti.
	 */
	void displayContatti();

	/**
	 * Mostra la vista delle operazioni.
	 */
	void displayOperations();

	/**
	 * Mostra la vista di inserimento delle operazioni.
	 */
	void displayInserimentoOp();

	/*
	 * Mostra la vista del documento.
	 * 
	 * void displayDocument();
	 */

	/**
	 * Mostra la situazione economica.
	 */
	void displaySitEconomica();

	/**
	 * Mostra la situazione patrimoniale.
	 */
	void displaySitPatrimoniale();

	/**
	 * Mostra la vista dei conti.
	 */
	void displayConti();

	/**
	 * Mostra la vista di inserimento di un conto.
	 */
	void displayInserimentoConto();

	/**
	 * Mostra la vista di inserimento di un conttato.
	 */
	void displayInserminetoContatto();

	/**
	 * Mostra la vista di modifica di un caonttato.
	 * 
	 * @param contatto
	 *            il contatto da modificare
	 */
	void displayModificaContatto(Contatto contatto);

	/**
	 * Mostra la vista del nostro contatto.
	 */
	void displayNostroContatto();

}
