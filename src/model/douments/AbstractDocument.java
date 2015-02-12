package model.douments;

import model.contatti.Contatto;
import model.data.Data;

/**
 * Implementazione astratta di un Documento.
 * 
 * @author Enrico
 *
 */
public abstract class AbstractDocument implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7283770252215004660L;
	
	private final Contatto mittente;
	private final Contatto benefiaciario;
	private final Contatto debitore;
	private final Data data;

	/**
	 * Costruttore di un documento qualunque.
	 * 
	 * @param mittente il mittente/traente del documento
	 * @param beneficiario il beneficiario del documento
	 * @param debitore il debitore/trattario del documento
	 * @param data la data del documento
	 */
	protected AbstractDocument(final Contatto mittente,
			final Contatto beneficiario, final Contatto debitore,
			final Data data) {
		
		this.mittente = mittente;
		this.debitore = debitore;
		this.benefiaciario = beneficiario;
		this.data = data;
	}

	@Override
	public Contatto getMittente() {
		return this.mittente;
	}

	@Override
	public Contatto getBeneficiario() {
		return this.benefiaciario;
	}

	@Override
	public Contatto getDebitore() {
		return this.debitore;
	}

	@Override
	public Data getData() {
		return this.data;
	}

	@Override
	public abstract double getTotale();

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((benefiaciario == null) ? 0 : benefiaciario.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((debitore == null) ? 0 : debitore.hashCode());
		result = prime * result
				+ ((mittente == null) ? 0 : mittente.hashCode());
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
		AbstractDocument other = (AbstractDocument) obj;
		if (benefiaciario == null) {
			if (other.benefiaciario != null)
				return false;
		} else if (!benefiaciario.equals(other.benefiaciario))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (debitore == null) {
			if (other.debitore != null)
				return false;
		} else if (!debitore.equals(other.debitore))
			return false;
		if (mittente == null) {
			if (other.mittente != null)
				return false;
		} else if (!mittente.equals(other.mittente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Document [Mittente=" + mittente + ", Benefiaciario="
				+ benefiaciario + ", Debitore=" + debitore + ", Data=" + data
				+ "]";
	}
	
}
