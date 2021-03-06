package model.conto;

/**
 * Implementazione concreta della classe Conto.
 * 
 * @author Enrico
 *
 */
public class ContoImpl implements Conto, Comparable<Conto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1960640700205838185L;

	private double importo;
	private final AccesoA accesoA;
	private final String name;

	/**
	 * Ritorna un nuovo conto.
	 * 
	 * @param name
	 *            il nome del nuovo conto
	 * @param acc
	 *            a cosa è acceso il nuovo conto
	 */
	public ContoImpl(final String name, final AccesoA acc) {
		this.accesoA = acc;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void addMovimento(final double importo) {
		this.importo += importo;

	}

	@Override
	public double getSaldo() {
		return this.importo;
	}

	@Override
	public Eccedenza getEccedenzaAttuale() {
		return this.importo >= 0 ? getEccedenzaSolita()
				: eccedenzaContrariaDi(getEccedenzaSolita());
	}

	@Override
	public Eccedenza getEccedenzaSolita() {
		if (Eccedenza.DARE.getContiAccesiQui().contains(this.accesoA)) {

			return Eccedenza.DARE;
		} else {
			return Eccedenza.AVERE;
		}
	}

	@Override
	public Tipo getTipo() {
		return this.accesoA.getTipoConto();
	}

	@Override
	public AccesoA getAccesoA() {
		return this.accesoA;
	}

	@Override
	public void reset() {
		this.importo = 0;
	}

	private Eccedenza eccedenzaContrariaDi(final Eccedenza e) {
		if (e.equals(Eccedenza.DARE)) {
			return Eccedenza.AVERE;
		} else {
			return Eccedenza.DARE;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accesoA == null) ? 0 : accesoA.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ContoImpl other = (ContoImpl) obj;
		if (accesoA != other.accesoA)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * 
	 * @return stringa completa di tutti i campi
	 */
	public String longToString() {
		return "Conto [NomeConto= " + name + ", AccesoA= " + accesoA
				+ ", Saldo= " + importo + "]";
	}

	@Override
	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 */
	public int compareTo(final Conto other) {
		return this.name.toLowerCase().compareTo(other.getName().toLowerCase());
	}

}