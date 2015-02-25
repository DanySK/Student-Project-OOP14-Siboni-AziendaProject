package model.situazione;

/**
 * Interfaccia che descrive la situazione economica.
 * 
 * @author Enrico
 *
 */
public interface SituazioneEconomica extends Situazione {

	/**
	 * 
	 * @return il reddito dell'azienda; se nagativo si tratta di Perdita, se
	 *         positivo di Utile
	 */
	double getReddito();
}
