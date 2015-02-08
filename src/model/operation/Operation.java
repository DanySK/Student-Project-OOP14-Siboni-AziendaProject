package model.operation;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.contatti.Contatto;
import model.conto.Conto;
import model.douments.Data;
import model.douments.Document;

/**
 * Descrive un'operazione di gestione
 * 
 * @author Enrico
 *
 */
public interface Operation extends Serializable {

	/**
	 * 
	 * @return il numero progressivo dell'operazione
	 */
	int getNum();
	
	/**
	 * Aggiunge un conto movimentato a questa operazione; lancia IllegalArgumentException se l'importo è uguale a zero, o se il conto era già stato movimentato
	 * 
	 * @param c il conto da movimentare
	 * @param importo l'importo di cui il conto dovrà essere movimentato
	 */
	void addContoMovimentato(Conto c, double importo);

	/**
	 * Aggiunge una descrizione all'operazione
	 * 
	 * @param descr la descrizione da aggiungere
	 */
	void setDescription(String descr);
	
	/**
	 * Setta il numero dell'operazione; può essere chiamato solo una volta sull'operazione
	 * @param numOp il numero operazione da settare
	 */
	void setNumOperation(int numOp);
	
	/**
	 * 
	 * @return la data di registrazione dell'operazione
	 */
	Data getData();
	
	/**
	 * 
	 * @return true se l'operazione bilancia, false altrimenti
	 */
	boolean isBalanced();
	
	/**
	 * 
	 * @return il set dei conti movimentati da questa operazione
	 */
	Set<Conto> getContiMovimentati();
	
	/**
	 * 
	 * @return la mappa conto -> importo di cui è stato movimentato
	 */
	Map<Conto,Double> getContiMovimentatiEImporto();
	
	/**
	 * 
	 * @return la descrizione dell'operazione
	 */
	String getDescription();
	
	/**
	 * @param contatti i contatti su cui generare il documento
	 * 
	 * @return il documento generato dall'operazione corrente; lancia IllegalStateException se l'operazione non è bilanciata;
	 */
	Optional<Document> generateDocument(Contatto... contatti);
	
	/**
	 * 
	 * @return se l'operazione è registrazione di un documento, oppure no
	 */
	boolean canGenerateDocument();
	
	
	/*
	 * Queta funzione sarebbe un po avanzata... non so riusciremo a implementarla...
	 * 
	 * Genera l'operazione a partire dal documento che la rappresenta; lancia NullPointerException se il documento passato è null
	 * @param doc
	 * 			il documento da cui prendere i dati per la generazione dell'operazione
	 *
	static Operation generateOperationFromDocument(Document doc);*/
	
}
