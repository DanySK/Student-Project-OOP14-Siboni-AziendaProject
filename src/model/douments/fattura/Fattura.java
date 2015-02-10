package model.douments.fattura;

import java.util.Optional;

import model.douments.Document;

/**
 * Interfaccia che descrive una Fattura.
 * 
 * @author Enrico
 *
 */
public interface Fattura extends Document {

	/**
	 * 
	 * @return il numero della fattura in una stringa (potrebbero esserci numeri
	 *         fattura come 8bis)
	 */
	String getNumFattura();

	/**
	 * 
	 * @return l'aliquota iva in formato a due cifre intere
	 */
	int getAliquotaIva();

	/**
	 * 
	 * @return l'iva calcolata sull'imponibile
	 */
	double getImportoIVA();

	/**
	 * 
	 * @return l'importo merci senza sconti
	 */
	double getImportoMerce();

	/**
	 * 
	 * @return l'imponibile della fattura (quindi detratti eventuali socnti)
	 */
	double getImponibile();

	/**
	 * 
	 * @return l'importo dello sconto eventuale
	 */
	Optional<Double> getImportoSconto();

	/**
	 * 
	 * @return l'aliquota dello sconto in formato a due cifre intere
	 */
	Optional<Integer> getAliquotaSconto();

	/**
	 * 
	 * @return le spese documentate eventuali
	 */
	Optional<Double> getSpeseDocumentate();

	/**
	 * 
	 * @return gli interessi eventuali
	 */
	Optional<Double> getInteressi();
}
