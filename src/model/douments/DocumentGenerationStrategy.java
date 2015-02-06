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
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 * @throws IllegalArgumentException se il numero di contati passati è diverso da 1 o 2
	 */
	Optional<Document> generate(Operation op, Contatto... contatti );
	
	/**
	 * Dice se l'operazione passata può generare un documento
	 * 
	 * @param op l'operaione da cui generare
	 * @return true se si può generare un documento, false altrimenti
	 */
	boolean canGenerateFrom(Operation op);
}
