package model.operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.conto.Conto;
import model.conto.Conto.Eccedenza;
import model.data.Data;
import model.data.DataImpl;

/**
 * Implementazione concreta di una Operazione.
 * 
 * @author Enrico
 *
 */
public class OperationImpl implements Operation, Comparable<Operation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1445965986132678879L;
	private static final double TOLLERANZA = 0.001;
	private static final int MINIMUM_LENGTH = 21;

	private final Map<Conto, Double> map;
	private final Data data;
	private boolean movementsApplied;
	private transient Optional<String> description;

	/**
	 * Restituisce una nuova operazione.
	 */
	public OperationImpl() {
		this.map = new HashMap<>();
		this.data = new DataImpl();
		this.description = Optional.empty();
	}

	@Override
	public void setContoMovimentato(final Conto c, final double importo) {
		if (!this.movementsApplied) {
			if (importo == 0) {
				throw new IllegalArgumentException(
						"L'importo inserito è uguale a zero!");
			}
			this.map.compute(c, (k, v) -> v == null ? (v = round(importo))
					: (v = round(v + importo)));
		}

	}

	@Override
	public void setDescription(final String description) {
		this.description = Optional.ofNullable(description);
	}

	@Override
	public Data getData() {
		return this.data;
	}

	@Override
	public boolean isBalanced() {
		final double result = this.map
				.entrySet()
				.stream()
				.filter(e -> e.getKey().getEccedenzaSolita()
						.equals(Eccedenza.DARE)).mapToDouble(e -> e.getValue())
				.sum()
				- this.map
						.entrySet()
						.stream()
						.filter(e -> e.getKey().getEccedenzaSolita()
								.equals(Eccedenza.AVERE))
						.mapToDouble(e -> e.getValue()).sum();

		return result > -TOLLERANZA && result < TOLLERANZA;
	}

	@Override
	public boolean haveMovementsBeenApplied() {
		return this.movementsApplied;
	}

	@Override
	public void applicaMovimenti() {
		if (!this.movementsApplied) {
			if (this.isBalanced()) {
				this.map.entrySet().stream()
						.forEach(e -> e.getKey().addMovimento(e.getValue()));
				this.movementsApplied = true;
			} else {
				throw new IllegalStateException(
						"L'operazione non è bilanciata!");
			}
		}

	}

	@Override
	public Set<Conto> getContiMovimentati() {
		return this.map.keySet();
	}

	@Override
	public Map<Conto, Double> getContiMovimentatiEImporto() {
		return this.map.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}

	@Override
	public String getDescription() {
		return this.description.orElse("");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationImpl other = (OperationImpl) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder(this.map.size()
				* MINIMUM_LENGTH);
		final int nameMaxLength = Integer.max(
				this.map.entrySet().stream()
						.mapToInt(e -> e.getKey().getName().length()).max()
						.orElse(MINIMUM_LENGTH), MINIMUM_LENGTH);
		str.append('\n');
		this.map.entrySet()
				.stream()
				.forEach(
						e -> {
							str.append("\t\t\t").append(e.getKey().getName());

							appendSpaces(
									str,
									nameMaxLength
											- e.getKey().getName().length())
									.append("\t\t");

							if (e.getKey().getEccedenzaSolita() == Eccedenza.DARE) {
								if (e.getValue() < 0) {
									str.append('\t');
									str.append(-e.getValue());
								} else {
									str.append(e.getValue());
								}

							} else {
								if (e.getValue() < 0) {
									str.append(-e.getValue());
								} else {
									str.append('\t');
									str.append(e.getValue());
								}
							}
							str.append('\n');

						});
		if (description.isPresent()) {
			str.append('\n').append("\t\t\t").append(description.get());
		}
		str.replace(1, 2, this.data.toString());
		return str.toString();
	}

	/**
	 * Metodo chiamato da un output stream che scrive; Indica come serailizzare
	 * una Operation.
	 * 
	 * @param out
	 *            l'outputstream su cui scrivere
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();

		out.writeObject(this.description.orElse(null));
	}

	/**
	 * Metodo chiamato da un input stream che legge; indica come leggere una
	 * Operation serializzata.
	 * 
	 * @param in
	 *            l'input stream da cui si legge
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(final ObjectInputStream in)
			throws ClassNotFoundException, IOException {
		in.defaultReadObject();

		this.description = Optional.ofNullable((String) in.readObject());
	}

	/**
	 * Arrotondamento a due cifre decimali.
	 * 
	 * @param toRound
	 * @return
	 */
	private double round(final double toRound) {
		return (Math.ceil(toRound * 100 - TOLLERANZA)) / 100;
	}

	/**
	 * Aggiunge spazi in base alla lunghezza data.
	 * 
	 * @param strB
	 *            lo StringBuilder a cui aggiungere gli spazi
	 * @param numSpaces
	 *            la lunghezza della parola da scrivere
	 * @return lo string builder utilizzato per appendere gli spazi, in modo ca
	 *         continuare dopo averchiamato il metodo
	 */
	private StringBuilder appendSpaces(final StringBuilder strB,
			final int numSpaces) {
		for (int i = 0; i < numSpaces; i++) {
			strB.append(' ');
		}
		return strB;
	}

	@Override
	public int compareTo(final Operation other) {
		return this.data.compareTo(other.getData());
	}
}
