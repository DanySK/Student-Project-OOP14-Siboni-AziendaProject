package model.douments.fattura;

import model.contatti.Contatto;

/**
 * Implementazione astratta di una Fattura
 * @author Enrico
 *
 */
public abstract class AbstractFattura implements Fattura {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9085600052663813184L;

	@Override
	public Contatto getMittente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contatto getBeneficiario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contatto getDebitore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTotale() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAliquotaIva() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getIVA() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getImponibile() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getImportoSconto() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAliquotaSconto() {
		// TODO Auto-generated method stub
		return 0;
	}

}
