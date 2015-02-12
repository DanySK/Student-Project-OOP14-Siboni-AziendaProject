package model;

import java.util.Set;

import model.contatti.Contatto;
import model.conto.Conto;
import model.douments.Document;
import model.operation.Operation;
import model.situazione.SituazioneEconomica;
import model.situazione.SituazionePatrimoniale;

/**
 * Descrive il comportamento del modello dell'applicazione.
 * 
 * @author Enrico
 *
 */
public interface Model {

	/**
	 * Aggiunge il conto passato come parametro, se questo non era già presente,
	 * al file dei conti; lancia IllegalArgumentException se il conto esisteva
	 * già
	 * 
	 * @param c
	 *            il conto da aggiungere
	 */
	void addConto(Conto c);

	/**
	 * Cancella il conto passato come parametro; lancia NoSuchElementException
	 * se il conto non era presente
	 * 
	 * @param c
	 *            il conto da cancellare
	 */
	void deleteConto(Conto c);

	/**
	 * 
	 * @return il set dei conti utilizzabili nell'applicazione
	 */
	Set<Conto> getConti();

	/**
	 * Setta il contatto della nostra azienda
	 * 
	 * @param c
	 *            il contatto da settare come nostro
	 */
	void setOurContact(Contatto c);

	/**
	 * 
	 * @return il nostro contatto, se non presente lo genera
	 */
	Contatto getOurContact();

	/**
	 * Aggiunge l'operazione passata come parametro al modello, preoccupandosi
	 * di dargli un numero progressivo
	 * 
	 * @param op
	 */
	void addOperation(Operation op);

	/**
	 * Ottiene l'operazione attraverso il numero progressivo; lancia
	 * IllegalArgumentException se viene passato un numero negativo o 0, oppure
	 * lancia NoSuchElementException se il numero non corrisponde ad
	 * un'operazione ancora
	 * 
	 * @param numOperation
	 *            numero dell'operazione da trovare
	 * @return l'operazione trovata
	 */
	Operation getOperation(int numOperation);

	/**
	 * Aggiunge un documento all'operazione indicata dal numero passato; lancia
	 * IllegalArgumentException se il numero passato è negativo o 0,
	 * NoSuchElementException se il numero dell'operazione passato ancora non
	 * corrisponde a nessuna
	 * 
	 * @param numOperation
	 *            il numero dell'operazione a cui collegare il documento
	 * @param doc
	 *            il documento da collegare
	 * @return true se il documento viene aggiunto, false se l'operazione aveva
	 *         già un documento correlato
	 */
	boolean addDocumentToOperation(int numOperation, Document doc);

	/**
	 * Ritorna il documento riferito all'operazione con quel numero; lancia
	 * IllegalArgumentException se il numero passato è negativo o 0, lancia
	 * NoSuchElementException se il numero ancora non corrisponde ad alcuna
	 * operazione oppure se non c'è un documento correlato all'operazione
	 * 
	 * @param numOperation
	 *            numero dell'operazione da trovare
	 * @return il documento relativo all'operazione con quel numero operazione
	 */
	Document getDocumentReferredTo(int numOperation);

	/**
	 * Elimina il documento riferito all'operazione indicata; lancia
	 * IllegalArgumentException se il numero passato è negativo o 0, lancia
	 * NoSuchElementException se il numero ancora non corrisponde ad alcuna
	 * operazione; se l'operazione indicata non ha un documento allegato non fa
	 * nulla
	 * 
	 * @param numOperation
	 *            numero operazione di cui eliminare il documento
	 */
	void deleteDocumentReferredTo(int numOperation);

	/**
	 * Aggiunge un conttatto all'insieme degli altri contatti; se uno equals è
	 * già presente, si chiede quale mantenere.
	 * 
	 * @param contatto
	 *            da aggiungere
	 */
	void addContatto(Contatto contatto);

	/**
	 * Cancella il conttato da quelli salvati; lancia NoSuchElementException se
	 * il contatto passato non è presente in memoria
	 * 
	 * @param contatto
	 *            il contatto da eliminare
	 */
	void deleteContatto(Contatto contatto);

	/**
	 * 
	 * @return il set dei contatti salvati
	 */
	Set<Contatto> getContatti();

	/**
	 * 
	 * @return la situazione economica dell'azienda
	 */
	SituazioneEconomica getSituazioneEconomica();

	/**
	 * 
	 * @return la situazione patrimoniale dell'azienda
	 */
	SituazionePatrimoniale getSituazionePatrimoniale();

	/**
	 * Salva il modello nel path passato.
	 * 
	 * @param path dove salvare il modello
	 */
	void save(String path);
	
	/**
	 * Carica il modello dal path passato.
	 * 
	 * @param path da cui caricare il modello
	 */
	void load(String path);
	
	/**
	 * Resetta il modello allo stato di partenza
	 * 
	 */
	void reset();

	/*
	 * Queta funzione sarebbe un po avanzata... non so riusciremo a
	 * implementarla... dipende se rimane tempo
	 * 
	 * Genera l'operazione a partire dal documento che la rappresenta; lancia
	 * NullPointerException se il documento passato è null
	 * 
	 * @param doc il documento da cui prendere i dati per la generazione
	 * dell'operazione
	 * 
	 * Operation generateOperationFromDocument(Document doc);
	 */

}
