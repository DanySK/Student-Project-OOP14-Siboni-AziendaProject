package model.operation;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import model.conto.Conto;
import model.douments.Data;

/**
 * Descrive un'operazione di gestione
 * 
 * @author Enrico
 *
 */
public interface Operation extends Serializable {

	/**
	 * 
	 * @return il numero dell'operazione
	 */
	int getNum();
	
	/**
	 * Aggiunge un conto movimentato a questa operazione; lancia IllegalArgumentException se l'importo è uguale a zero
	 * 
	 * se l'operazione aveva già un movimento su quel conto questo lo sovrascrive
	 * se i movimenti sono già stati applicati questo metodo non ha più effetti sull'operazione
	 * 
	 * @param c il conto da movimentare
	 * @param importo l'importo di cui il conto dovrà essere movimentato
	 * @throws IllgalArgumentException se il valore passato come importo è 0
	 */
	void setContoMovimentato(Conto c, double importo);

	/**
	 * Applica i movimenti di questa Operazione ai conti, se l'operazione bilancia... altrimenti lancia IllegalStateException
	 * 
	 * se i movimenti erano già stati applicati non fa nulla
	 * se il numeo operazione non era stato settato lancia IllegalStateException
	 * 
	 * @throws IllegalStateException se l'operazione non bilancia oppure se il numero operazione non è stato settato
	 */
	void applicaMovimenti();
	
	/**
	 * 
	 * @return true se i movimenti sono già stati applicati ai conti, false altrimenti
	 */
	boolean haveMovementsBeenApplied();
	
	/**
	 * Aggiunge una descrizione all'operazione
	 * 
	 * @param descr la descrizione da aggiungere
	 */
	void setDescription(String descr);
	
	/**
	 * Setta il numero dell'operazione; può essere chiamato solo una volta sull'operazione, dopodichè lancia UnsupportedOperationException
	 * @param numOp il numero operazione da settare
	 * @throws UnsupportedOperationException se è già stato settato il numero
	 * @throws IllegalArgumentException se il numero operazione passato è negativo o 0
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
	Map<Conto, Double> getContiMovimentatiEImporto();
	
	/**
	 * 
	 * @return la descrizione dell'operazione
	 */
	String getDescription();
	
}
