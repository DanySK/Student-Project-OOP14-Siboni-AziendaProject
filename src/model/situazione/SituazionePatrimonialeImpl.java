package model.situazione;

import java.util.Set;
import java.util.stream.Collectors;

import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.conto.Conto.Eccedenza;

/**
 * Costruisce una situazione patrimoniale.
 * 
 * @author Enrico
 *
 */
public class SituazionePatrimonialeImpl extends AbstractSituazione implements
		SituazionePatrimoniale {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5079134913714721237L;

	/**
	 * Costruisce la situazione patrimoniale a partire dai conti della
	 * situazione patrimoniale.
	 * 
	 * @param contiSitPatr
	 *            i conti da cui partire per costruire la situazione
	 *            patrimoniale
	 */
	public SituazionePatrimonialeImpl(final Set<Conto> contiSitPatr) {
		super(contiSitPatr);

	}

	@Override
	public double getTotCostiPlur() {
		return getCostiPlur().stream().mapToDouble(c -> c.getSaldo()).sum();
	}

	@Override
	public double getTotRicaviPlur() {
		return getRicaviPlur().stream().mapToDouble(c -> c.getSaldo()).sum();
	}

	@Override
	public double getTotLiquiditaDifferite() {
		return getLiquiditaDifferite().stream().mapToDouble(c -> c.getSaldo())
				.sum();
	}

	@Override
	public double getTotLiquiditaImmediate() {
		return getLiquiditaImmediate().stream().mapToDouble(c -> c.getSaldo())
				.sum();
	}

	@Override
	public double getTotCostiSospesi() {
		return getCostiSospesi().stream().mapToDouble(c -> c.getSaldo()).sum();
	}

	@Override
	public double getTotDebiti() {
		return getDebiti()
				.stream()
				.mapToDouble(
						c -> c.getEccedenzaSolita() == Eccedenza.DARE ? -c
								.getSaldo() : c.getSaldo()).sum();
	}

	@Override
	public double getTotPatrimonioNetto() {
		return getPatrimonioNetto().stream().mapToDouble(c -> c.getSaldo())
				.sum();
	}

	@Override
	public double getTotRicaviSospesi() {
		return getRicaviSospesi().stream().mapToDouble(c -> c.getSaldo()).sum();
	}

	@Override
	public double getTotAPareggio() {
		return getTotAvere() - getTotDare();
	}

	@Override
	public Set<Conto> getCostiPlur() {
		return getContiDare().stream()
				.filter(c -> c.getAccesoA() == AccesoA.COSTI_PLUR)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getRicaviPlur() {
		return getContiAvere().stream()
				.filter(c -> c.getAccesoA() == AccesoA.RICAVI_PLUR)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getLiquiditaDifferite() {
		return getContiDare().stream()
				.filter(c -> c.getAccesoA() == AccesoA.CREDITI)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getLiquiditaImmediate() {
		return getContiDare().stream()
				.filter(c -> c.getAccesoA() == AccesoA.DENARO)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getCostiSospesi() {
		return getContiDare().stream()
				.filter(c -> c.getAccesoA() == AccesoA.COSTI_SOSP)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getDebiti() {
		return getContiAvere()
				.stream()
				.filter(c -> c.getAccesoA() == AccesoA.DEBITI
						|| (c.getAccesoA() == AccesoA.CREDITI && c
								.getEccedenzaAttuale() == Eccedenza.AVERE))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getPatrimonioNetto() {
		return getContiAvere().stream()
				.filter(c -> c.getAccesoA() == AccesoA.PATRIMONIO_NETTO)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Conto> getRicaviSospesi() {
		return getContiAvere().stream()
				.filter(c -> c.getAccesoA() == AccesoA.RICAVI_SOSP)
				.collect(Collectors.toSet());
	}

}
