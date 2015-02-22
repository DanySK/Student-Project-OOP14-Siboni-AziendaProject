package model.situazione;

import java.util.Set;

import model.conto.Conto;

/**
 * Costruisce una situazione economica.
 * 
 * @author Enrico
 *
 */
public class SituazioneEconomicaImpl extends AbstractSituazione implements
		SituazioneEconomica {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5504257003011189534L;

	/**
	 * Costruisce la situazione economica a partire dai conti accesi a costi
	 * d'es e ricavi d'es
	 * 
	 * @param contiSitEconomica
	 *            i conti da cui partire per la costruzione
	 * 
	 */
	public SituazioneEconomicaImpl(final Set<Conto> contiSitEconomica) {
		super(contiSitEconomica);
	}

	@Override
	public double getReddito() {
		return getTotAvere()-getTotDare();
	}
}
