package model.douments;

public interface Fattura extends Document {

	/**
	 * 
	 * @return l'aliquota iva in formato a due cifre intere
	 */
	int getAliquotaIva();
	
	/**
	 * 
	 * @return l'iva calcolata sull'imponibile
	 */
	double getIVA();
	
	/**
	 * 
	 * @return l'imponibile della fattura
	 */
	double getImponibile();
	
	/**
	 * 
	 * @return l'importo dello sconto eventuale
	 */
	double getImportoSconto();
	
	/**
	 * 
	 * @return l'aliquota dello sconto in formato a due cifre intere
	 */
	int getAliquotaSconto();
}
