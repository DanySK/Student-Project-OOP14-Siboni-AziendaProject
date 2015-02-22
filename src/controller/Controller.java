package controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import view.ViewController;
import model.Model;
import model.contatti.Contatto;
import model.conto.Conto;
import model.data.Data;
import model.operation.Operation;
import model.situazione.SituazioneEconomica;
import model.situazione.SituazionePatrimoniale;

/**
 * Descrive il comportamento del controllore dell'applicazione.
 * 
 * @author Enrico, Marco, Elia
 *
 */
public interface Controller { // TODO forse sarebbe meglio fattorizzare meglio
								// il comportamento del controller creando sotto
								// controller che controllano piccole parti del
								// programma (controller contatti,controller
								// operazioni,controller conti, ecc...)

	/**
	 * Mostra il menu.
	 */
	void showMenu();

	/**
	 * Mostra la finestra di primo avvio.
	 */
	void showFirstRunView();

	/**
	 * Mostra un messaggio di errore.
	 * 
	 * @param errorMessage
	 *            messaggio da mostrare
	 */
	void showErrorMessage(String errorMessage);

	/**
	 * Aggiunge una operazione al modello.
	 * 
	 * @param operation
	 *            l'operazione da aggiungere
	 */
	void aggiuntaOperazione(Operation operation);

	/**
	 * Aggiunge un conto al modello.
	 * 
	 * @param conto
	 *            il conto da aggiungere
	 */
	void aggiuntaConto(Conto conto);

	/**
	 * Aggiunge un contatto al modello.
	 * 
	 * @param contatto
	 *            il contatto da aggiungere
	 */
	void aggiuntaContatto(Contatto contatto);

	/**
	 * Cancella il conto passato dal modello.
	 * 
	 * @param conto
	 *            il conto da cancellare
	 */
	void cancellaConto(Conto conto);

	/**
	 * Cancella il contatto passato dal modello.
	 * 
	 * @param contatto
	 *            il contatto da cancellare.
	 */
	void cancellaContatto(Contatto contatto);

	/**
	 * 
	 * @return l'insieme dei conti
	 */
	Set<Conto> getInsiemeConti();

	/**
	 * 
	 * @return l'insieme dei contatti
	 */
	Set<Contatto> getInsiemeContatti();

	/**
	 * Carica i dati salvati e popola il modello.
	 * 
	 * @return lo stato di caricamento del modello
	 */
	Model.State load();

	/**
	 * Salva il modello.
	 */
	void save();

	/**
	 * Resetta il modello allo stato di partenza
	 */
	void reset();

	/**
	 * Aggiunge la View al controller.
	 * 
	 * @param v
	 *            la view da aggiungere
	 */
	void setView(ViewController v);

	/**
	 * Collega il modello al controller.
	 * 
	 * @param m
	 *            il modello da collegare
	 */
	void setModel(Model m);

	/**
	 * Setta il nostro contatto nel modello
	 * 
	 * @param ourContact
	 *            il contatto da impostare come nostro
	 */
	void setOurContact(Contatto ourContact);

	/**
	 * 
	 * @return il nostro contatto mantenuto nel modello
	 */
	Contatto getOurContact();

	/**
	 * Ritorna le operazioni fra le date indicate.
	 * 
	 * @param dataFrom
	 *            la data da cui partire
	 * @param dataTo
	 *            la data a cui arrivare
	 * @return la lista delle operazioni registrate tra le due date
	 */
	List<Operation> getOperations(Data dataFrom, Data dataTo);

	/**
	 * 
	 * @return l'ultima operazione inserita nel modello
	 */
	Optional<Operation> getLastOp();

	/**
	 * 
	 * @return la situazione economica dell'azienda
	 */
	SituazioneEconomica getSitEconomica();

	/**
	 * 
	 * @return la situazione patrimoniale dell'azienda
	 */
	SituazionePatrimoniale getSitPatrimoniale();

}
