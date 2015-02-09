package model.douments;

import java.util.Optional;

import model.contatti.Contatto;
import model.operation.Operation;

/**
 * Classe utilizzata come strategia per la generazione dei documenti nelle operazioni
 * 
 * @author Enrico
 *
 */
public interface DocumentGenerationStrategy {

	/**
	 * Genera un documento da un'operazione
	 * 
	 * @param op operazione in ingresso
	 * @param other l'altro soggetto presente nel documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 */
	Optional<Document> generate(Operation op, Contatto other);
	
	/**
	 * Genera un documento a partire da un'operazione (questo è specifico per cambiali tratte)
	 * 
	 * @param op operazione in ingresso
	 * @param traente colui che spicca il documento
	 * @param trattario colui che è tenuto ad onorare il documento
	 * @param beneficiario colui che riceve i benefici dal documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 */
	Optional<Document> generate(Operation op, Contatto traente, Contatto trattario, Contatto beneficiario);
	
	/**
	 * Dice se l'operazione passata può generare un documento
	 * 
	 * @param op l'operaione da cui generare
	 * @return true se si può generare un documento, false altrimenti
	 */
	boolean canGenerateFrom(Operation op);
}
