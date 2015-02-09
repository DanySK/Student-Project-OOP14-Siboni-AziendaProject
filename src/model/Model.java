package model;

import java.io.Serializable;
import java.util.Set;

import model.contatti.Contatto;
import model.conto.Conto;
import model.douments.Document;
import model.operation.Operation;

/**
 * Descrive il comportamento del modello dell'applicazione
 * 
 * @author Enrico
 *
 */
public interface Model extends Serializable{

	/**
	 * Carica i dati salvati e popola il modello
	 * 
	 */
	void load();
	
	/**
	 * Aggiunge il conto passato come parametro, se questo non era già presente, al file dei conti; lancia IllegalArgumentException se il conto esisteva già
	 * 
	 * @param c
	 * 		il conto da aggiungere
	 */
	void addConto(Conto c);
	
	/**
	 * 
	 * @return il set dei conti utilizzabili nell'applicazione
	 */
	Set<Conto> getConti();
	
	/**
	 * Setta il contatto della nostra azienda
	 * @param c il contatto da settare come nostro
	 */
	void setOurContact(Contatto c);
	
	/**
	 * 
	 * @return il nostro contatto, se non presente lo crea
	 */
	Contatto getOurContact();
	
	/**
	 * Aggiunge l'operazione passata come parametro al modello, preoccupandosi di dargli un numero progressivo
	 * 
	 * @param op
	 */
	void addOperation(Operation op);
	
	/**
	 * Ottiene l'operazione attraverso il numero progressivo; lancia IllegalArgumentException se viene passato un numero negativo,
	 * oppure lancia NoSuchElementException se il numero non corrisponde ad un'operazione ancora
	 * 
	 * @param numOperation
	 * 			numero dell'operazione da trovare
	 * @return l'operazione trovata
	 */
	Operation getOperation(int numOperation);
	
	/**
	 * Ritorna il documento riferito all'operazione con quel numero; lancia IllegalargumentException se il numero passato è negativo,
	 * oppure NoSuchElementException se il numero ancora non corrisponde ad alcuna operazione
	 * 
	 * @param numOperation
	 * 			numero dell'operazione da trovare
	 * @return il documento relativo all'operazione con quel numero operazione
	 */
	Document getDocumentReferredTo(int numOperation);
	
	/**
	 * Salva il modello
	 */
	void save();
	
	/**
	 * Resetta il modello allo stato di partenza
	 * 
	 */
	void reset();
	
	/*
	 * 
	 * questi due metodi sotto sono da meglio definire...
	
	void getSituazioneEconomica();
	
	void getSituazionePatrimoniale();
	*/
	
}
