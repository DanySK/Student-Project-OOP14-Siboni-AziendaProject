package model.contatti;

import java.io.Serializable;

/**
 * Descrive i dati relativi alle persone che vengono a contatto con l'azienda
 * 
 * @author Enrico
 *
 */
public interface Contatto extends Serializable{

	/**
	 * 
	 * @return la stringa rappresentante a ragione sociale
	 */
	String getRagioneSociale();
	
	/**
	 * 
	 * @return la Partita Iva
	 */
	String getPIVA();
	
	/**
	 * 
	 * @return il codice fiscale
	 */
	String getCF();
	
	/**
	 * 
	 * @return "Nome Cognome" del titolare
	 */
	String getNomeCognomeTitolare();
	
	/**
	 * 
	 * @return "prefisso-numero" del contatto
	 */
	String getTelefono();
	
	/**
	 * 
	 * @return la stringa rappresentante la sede legale
	 */
	String getSedeLegale();
	
	/**
	 * 
	 * @return la città
	 */
	String getCitta();
	
	/**
	 * 
	 * @return il CAP
	 */
	String getCAP();
	
	/**
	 * 
	 * @return la provincia
	 */
	String getProvincia();
}
