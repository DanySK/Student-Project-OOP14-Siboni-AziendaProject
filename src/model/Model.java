package model;

import java.util.Set;
import java.util.SortedSet;

import model.contatti.Contatto;
import model.conto.Conto;
import model.douments.Document;
import model.operation.Operation;

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
	 * già.
	 * 
	 * @param c
	 *            il conto da aggiungere
	 */
	void addConto(Conto c);

	/**
	 * Cancella il conto passato come parametro; lancia NoSuchElementException
	 * se il conto non era presente.
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
	 * Setta il contatto della nostra azienda.
	 * 
	 * @param c
	 *            il contatto da settare come nostro
	 */
	void setOurContact(Contatto c);

	/**
	 * 
	 * @return il nostro contatto, oppure null se non presente
	 */
	Contatto getOurContact();

	/**
	 * Aggiunge l'operazione passata come parametro al modello, preoccupandosi
	 * di dargli un numero progressivo.
	 * 
	 * @param op l'operazione da aggiungere
	 */
	void addOperation(Operation op);

	/**
	 * 
	 * @return l'insieme delle operazioni salvate
	 */
	SortedSet<Operation> getAllOperations();

	/**
	 * Aggiunge un documento all'operazione indicata.
	 * 
	 * @param op
	 *            l'operazione a cui collegare il documento
	 * @param doc
	 *            il documento da collegare
	 * @return true se il documento viene aggiunto, false se l'operazione aveva
	 *         già un documento correlato
	 */
	boolean addDocumentToOperation(Operation op, Document doc);

	/**
	 * Elimina il documento riferito all'operazione indicata; se l'operazione
	 * indicata non ha un documento allegato non fa nulla.
	 * 
	 * @param op
	 *            operazione di cui eliminare il documento
	 */
	void deleteDocumentReferredTo(Operation op);

	/**
	 * Aggiunge un conttatto all'insieme degli altri contatti.
	 * 
	 * @param contatto
	 *            da aggiungere
	 */
	void addContatto(Contatto contatto);

	/**
	 * Cancella il conttato da quelli salvati; lancia NoSuchElementException se
	 * il contatto passato non è presente in memoria.
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
	 * Salva il modello nel path passato.
	 * 
	 * @param path
	 *            dove salvare il modello
	 */
	void save(String path);

	/**
	 * Carica il modello dal path passato.
	 * 
	 * @param path
	 *            da cui caricare il modello
	 * @return lo stato del modello
	 */
	State load(String path);

	/**
	 * Resetta il modello allo stato di partenza.
	 * 
	 */
	void reset();

	/**
	 * Enumerazione che indica i tre valori di stato che può avere il modello.
	 * 
	 * @author Enrico
	 *
	 */
	static enum State {
		FIRST_RUN, ERROR_LOADING, LOADING_SUCCESS;
	}

}
