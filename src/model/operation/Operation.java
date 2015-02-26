package model.operation;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.conto.Conto;
import model.data.Data;
import model.douments.Document;

/**
 * Descrive un'operazione di gestione.
 * 
 * @author Enrico
 *
 */
public interface Operation extends Serializable {

	/**
	 * Aggiunge un conto movimentato a questa operazione; lancia
	 * IllegalArgumentException se l'importo è uguale a zero;
	 * 
	 * se l'operazione aveva già un movimento su quel conto, questo lo
	 * aggiunge; se i movimenti sono già stati applicati questo metodo non ha
	 * più effetti sull'operazione.
	 * 
	 * @param c
	 *            il conto da movimentare
	 * @param importo
	 *            l'importo di cui il conto dovrà essere movimentato
	 * @throws IllegalArgumentException
	 *             se il valore passato come importo è 0
	 */
	void setContoMovimentato(Conto c, double importo);

	/**
	 * Applica i movimenti di questa Operazione ai conti, se l'operazione
	 * bilancia... altrimenti lancia IllegalStateException
	 * 
	 * se i movimenti erano già stati applicati non fa nulla
	 * 
	 * @throws IllegalStateException
	 *             se l'operazione non bilancia
	 */
	void applicaMovimenti();

	/**
	 * 
	 * @return true se i movimenti sono già stati applicati ai conti, false
	 *         altrimenti
	 */
	boolean haveMovementsBeenApplied();

	/**
	 * Aggiunge una descrizione all'operazione.
	 * 
	 * @param descr
	 *            la descrizione da aggiungere
	 */
	void setDescription(String descr);

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
	 * @return la mappa dal conto all'importo di cui è stato movimentato
	 */
	Map<Conto, Double> getContiMovimentatiEImporto();

	/**
	 * 
	 * @return la descrizione dell'operazione
	 */
	String getDescription();

	/**
	 * 
	 * @return quando è stata creata l'operazione attraverso un Date
	 */
	Date getTimeStamp();
	
	/**
	 * 
	 * @return il documento che ha generato l'operazione se c'è, altrimenti Optional.empty()
	 */
	Optional<Document> getDocument();

	/**
	 * Setta il documento che ha generato l'operazione.
	 * 
	 * @param documento il documento da settare
	 * @throws IllegalStateException se l'operazione già aveva un documento settato
	 */
	void setDocument(Document documento);
	
	/**
	 * Rimuove il documento settato in una operazione. Non fa nulla se il documento non c'era
	 */
	void removeDocument();

}
