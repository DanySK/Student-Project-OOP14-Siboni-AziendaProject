package model.situazione;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import model.conto.Conto;
import model.conto.Conto.Eccedenza;

/**
 * Classe astratta per una generica situazione.
 * 
 * @author Enrico
 *
 */
public abstract class AbstractSituazione implements Situazione {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1474335757768346975L;

	private static final Comparator<Conto> NAME_SORTER = (c1, c2) -> c1
			.getName().compareTo(c2.getName());

	private final Set<Conto> contiDare = new TreeSet<>(NAME_SORTER);
	private final Set<Conto> contiAvere = new TreeSet<>(NAME_SORTER);

	/**
	 * Costruisce una situazione a partire da tutti i conti passati
	 * distinguendoli in quelli con eccedenza dare e avere.
	 * 
	 * @param tuttiIConti
	 *            i conti da cui costruire la situazione
	 */
	protected AbstractSituazione(final Set<Conto> tuttiIConti) {

		for (final Conto c : tuttiIConti) {

			if (c.getEccedenzaAttuale() == Eccedenza.DARE) {
				this.contiDare.add(c);
			} else {
				this.contiAvere.add(c);
			}
		}
	}

	@Override
	public Set<Conto> getContiDare() {
		return new TreeSet<>(this.contiDare);
	}

	@Override
	public Set<Conto> getContiAvere() {
		return new TreeSet<>(this.contiAvere);
	}

	@Override
	public double getTotDare() {
		return this.contiDare
				.stream()
				.mapToDouble(
						c -> c.getEccedenzaAttuale() == c.getEccedenzaSolita() ? c
								.getSaldo() : -c.getSaldo()).sum();
	}

	@Override
	public double getTotAvere() {
		return this.contiAvere
				.stream()
				.mapToDouble(
						c -> c.getEccedenzaAttuale() == c.getEccedenzaSolita() ? c
								.getSaldo() : -c.getSaldo()).sum();
	}

}
