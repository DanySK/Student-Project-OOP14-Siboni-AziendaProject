package model.conto;

import java.util.Arrays;

public class ContoImpl implements Conto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1960640700205838185L;

	private double importo;
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
	public Eccedenza getSegnoEccedenzaAttuale() {
		return this.importo >= 0 ? getSegnoEccedenzaSolito() : eccedenzaContrariaDi(getSegnoEccedenzaSolito());
	}

	@Override
	public Eccedenza getSegnoEccedenzaSolito() {
		if (Arrays.asList(AccesoA.COSTI_ES, AccesoA.COSTI_PLUR,
				AccesoA.COSTI_SOSP, AccesoA.CREDITI).contains(this.accesoA)) {

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
	
	private Eccedenza eccedenzaContrariaDi(final Eccedenza e){
		if(e.equals(Eccedenza.DARE)){
			return Eccedenza.AVERE;
		}else{
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
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conto [NomeConto= " + name + ", AccesoA= " + accesoA
				+ ", Saldo= " + importo + "]";
	}

}