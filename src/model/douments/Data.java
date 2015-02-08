package model.douments;

/**
 * Descrive il concetto di una data nel calendario
 * @author Enrico
 *
 */
public interface Data extends Comparable<Data>{

	/**
	 * 
	 * @return un numero da 1 a 31 che corrisponde al giorno di questa data
	 */
	int getGiorno();
	
	/**
	 * 
	 * @return un numero da 1 a 12 che corrisponde al mese di questa data
	 */
	int getMese();

	/**
	 * 
	 * @return un numero maggiore di 1900 che corrisponde all'anno di questa data
	 */
	int getAnno();
}
