package model.situazione;

/**
 * Interfaccia che descrive la situazione patrimoniale.
 * 
 * @author Enrico
 *
 */
public interface SituazionePatrimoniale extends Situazione {
	
	/**
	 * 
	 * @return il totale dei costi pluriennali
	 */
	double getTotCostiPlur();
	
	/**
	 * 
	 * @return il totale dei conti accesi ai crediti
	 */
	double getTotLiquiditaDifferite();
	
	/**
	 * 
	 * @return il totale dei conti accesi a denaro
	 */
	double getTotLiquiditaImmediate();
	
	/**
	 * 
	 * @return il totale dei costi sopsesi
	 */
	double getTotCostiSospesi();
	
	/**
	 * 
	 * @return il totale dei conti accesi a debiti
	 */
	double getTotDebiti();
	
	/**
	 * 
	 * @return il totale dei conti accesi a Patrimonio Netto
	 */
	double getTotPatrimonioNetto();
	
	/**
	 * 
	 * @return il totale dei ricavi sospesi
	 */
	double getTotRicaviSospesi();
}


/*
Aggiunto un metodo all'eccedenza per farsi dare i conti che si accendono in dare o avere; aggiunte interfacce per situazioni patrimoniale e economica*/