package model.situazione;

import java.io.Serializable;
import java.util.Set;

import model.conto.Conto;

/**
 * Descrive il comportamento di una Situazione generale (patromoniale/economica).
 * 
 * @author Enrico
 *
 */
public interface Situazione extends Serializable{

	/**
	 * 
	 * @return il set dei conti con eccedenza in dare
	 */
	Set<Conto> getContiDare();
	
	/**
	 * 
	 * @return il set dei conti con eccedenza avere
	 */
	Set<Conto> getContiAvere();
	
	/**
	 * 
	 * @return il totale Dare di questa situazione
	 */
	double getTotDare();
	
	/**
	 * 
	 * @return il totale avere di questa situazione
	 */
	double getTotAvere();
}
