package model.conto;

import java.util.Arrays;

public class ContoImpl implements Conto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1960640700205838185L;
	
	private int importo;
	private final AccesoA accesoA;
	private final String name;

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
	public double getEccedenza() {
		return this.importo;
	}

	@Override
	public Eccedenza getSegnoEccedenza() {
		if (Arrays.asList(AccesoA.COSTI_ES, AccesoA.COSTI_PLUR,
				AccesoA.COSTI_SOSP, AccesoA.CREDITI).contains(this.accesoA)) {

			return this.importo > 0 ? Eccedenza.DARE : Eccedenza.AVERE;
		} else {
			return this.importo > 0 ? Eccedenza.AVERE : Eccedenza.DARE;
		}
	}

	@Override
	public Eccedenza getSegnoEccedenzaSeAggiunto(final double importo) {
		final Eccedenza ecc;
		this.addMovimento(importo);
		ecc = this.getSegnoEccedenza();
		this.addMovimento(-importo);
		return ecc;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accesoA == null) ? 0 : accesoA.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
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
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conto [Nome Conto= " + name + ", Acceso a= " + accesoA
				+ ", Saldo= " + importo + "]";
	}

}