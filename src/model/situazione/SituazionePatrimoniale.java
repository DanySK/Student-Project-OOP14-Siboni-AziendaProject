package model.situazione;

import java.util.Set;

import model.conto.Conto;

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
	 * @return il totale dei ricavi pluriennali
	 */
	double getTotRicaviPlur();

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

	/**
	 * 
	 * @return il reddito dell'azienda
	 */
	double getTotAPareggio();

	/**
	 * 
	 * @return il set dei conti accesi a costi pluriennali
	 */
	Set<Conto> getCostiPlur();

	/**
	 * 
	 * @return il set dei conti accesi a ricavi pluriennali
	 */
	Set<Conto> getRicaviPlur();

	/**
	 * 
	 * @return il set dei conti accesi a crediti
	 */
	Set<Conto> getLiquiditaDifferite();

	/**
	 * 
	 * @return il set dei conti accesi a denaro
	 */
	Set<Conto> getLiquiditaImmediate();

	/**
	 * 
	 * @return il set dei conti accesi a costi sospesi
	 */
	Set<Conto> getCostiSospesi();

	/**
	 * 
	 * @return il set dei conti accesi a deviti
	 */
	Set<Conto> getDebiti();

	/**
	 * 
	 * @return il set dei conti accesi a patrimonio netto
	 */
	Set<Conto> getPatrimonioNetto();

	/**
	 * 
	 * @return il set dei conti accesi a ricavi sospesi
	 */
	Set<Conto> getRicaviSospesi();
}