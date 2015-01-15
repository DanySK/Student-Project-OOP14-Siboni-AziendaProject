package model.operation;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import model.conto.Conto;
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
	 * Aggiunge un conto movimentato a questa operazione; lancia IllegalArgumentException se l'importo è uguale a zero, o se il conto non esiste
	 * 
	 * @param c il conto da movimentare
	 * @param importo l'importo di cui il conto dovrà essere movimentato
	 */
	void addContoMovimentato(Conto c, double importo);

	/**
	 * 
	 * @return la data di registrazione dell'operazione
	 */
	Date getDate();
	
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
	 * 
	 * @return il documento generato dall'operazione corrente; lancia UnsupportedOperationException se l'operazione non è bilanciata,
	 * 			oppure una NotRecognisedException se non si riesce a riconoscere il tipo di documento da generare
	 */
	Document generateDocument();
	
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
