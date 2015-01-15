package model.douments;

import java.io.Serializable;
import java.util.Date;

import model.contatti.Contatto;

/**
 * Descrive un generico documento di contabilità
 *  
 * @author Enrico
 *
 */
public interface Document extends Serializable{
	
	/**
	 * 
	 * @return il contatto di chi che emette il documento
	 */
	Contatto getMittente();
	
	/**
	 * 
	 * @return il contatto di chi riceverà il denaro
	 */
	Contatto getBeneficiario();
	
	/**
	 * 
	 * @return il contatto di colui che dovrà sborsare denaro
	 */
	Contatto getDebitore();
	
	/**
	 * 
	 * @return la data di emissione del documeto
	 */
	Date getData();
	
	/**
	 * 
	 * @return il totale del documento
	 */
	double getTotale();
	
}
