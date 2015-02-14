package model.contatti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

/**
 * Implementazione della classe Contatto.
 * 
 * @author Enrico
 *
 */
public class ContattoImpl implements Contatto, Comparable<Contatto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8608243775362205040L;

	private final String nomeTit;
	private final String ragSoc;
	private final String cf;
	private final String piva;
	private transient Optional<String> telefono;
	private transient Optional<String> sedeLeg;
	private transient Optional<String> citta;
	private transient Optional<String> cap;
	private transient Optional<String> provincia;

	private ContattoImpl(final String nome, final String ragSoc,
			final String cf, final String piva,
			final Optional<String> telefono, final Optional<String> sedeLeg,
			final Optional<String> citta, final Optional<String> cap,
			final Optional<String> provincia) {

		this.nomeTit = nome;
		this.ragSoc = ragSoc;
		this.cf = cf;
		this.piva = piva;
		this.telefono = telefono;
		this.sedeLeg = sedeLeg;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
	}

	/**
	 * Costruisce un nuovo contatto a partire da uno presente.
	 * 
	 * @param toCopy
	 *            il contatto da copiare
	 */
	public ContattoImpl(final Contatto toCopy) {
		this(toCopy.getNomeCognomeTitolare(), toCopy.getRagioneSociale(),
				toCopy.getCF(), toCopy.getPIVA(), toCopy.getTelefono(), toCopy
						.getSedeLegale(), toCopy.getCitta(), toCopy.getCAP(),
				toCopy.getProvincia());
	}

	@Override
	public String getRagioneSociale() {
		return this.ragSoc;
	}

	@Override
	public String getPIVA() {
		return this.piva;
	}

	@Override
	public String getCF() {
		return this.cf;
	}

	@Override
	public String getNomeCognomeTitolare() {
		return this.nomeTit;
	}

	@Override
	public Optional<String> getTelefono() {
		return this.telefono;
	}

	@Override
	public Optional<String> getSedeLegale() {
		return this.sedeLeg;
	}

	@Override
	public Optional<String> getCitta() {
		return this.citta;
	}

	@Override
	public Optional<String> getCAP() {
		return this.cap;
	}

	@Override
	public Optional<String> getProvincia() {
		return this.provincia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cf == null) ? 0 : cf.hashCode());
		result = prime * result + ((piva == null) ? 0 : piva.hashCode());
		result = prime * result + ((nomeTit == null) ? 0 : nomeTit.hashCode());
		result = prime * result + ((ragSoc == null) ? 0 : ragSoc.hashCode());
		result = prime * result + ((sedeLeg == null) ? 0 : sedeLeg.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ContattoImpl other = (ContattoImpl) obj;

		/*
		 * Questa equals ritorna vero se due campi sono uguali (senza
		 * considerare le maiuscole), ma anche quando un campo non è presente in
		 * un contatto e nell'altro si, e viceversa;
		 * 
		 * Cioè un contatto si considera uguale ad un'altro se i campi RIEMPITI
		 * coincidono, se un campo è pieno in uno, ma non nell'altro, allora
		 * sono comunque uguali.
		 */

		return nomeTit.equalsIgnoreCase(other.nomeTit)
				&& ragSoc.equalsIgnoreCase(other.ragSoc)
				&& piva.equalsIgnoreCase(other.piva)
				&& cf.equalsIgnoreCase(other.cf)
				&& (sedeLeg.isPresent() ? sedeLeg.get().equalsIgnoreCase(
						other.sedeLeg.orElse(sedeLeg.get())) : true)
				&& (citta.isPresent() ? citta.get().equalsIgnoreCase(
						other.citta.orElse(citta.get())) : true)
				&& (provincia.isPresent() ? provincia.get().equalsIgnoreCase(
						other.provincia.orElse(provincia.get())) : true)
				&& (cap.isPresent() ? cap.get().equalsIgnoreCase(
						other.cap.orElse(cap.get())) : true)
				&& (telefono.isPresent() ? telefono.get().equalsIgnoreCase(
						other.telefono.orElse(telefono.get())) : true);
	}

	@Override
	public String toString() {
		return this.ragSoc + ", " + this.piva;
	}

	/**
	 * 
	 * @return la stringa completa con tutti i campi
	 */
	public String longToString() {
		final StringBuilder s = new StringBuilder(100);
		s.append("RagioneSociale= ").append(ragSoc).append(", NomeTitolare= ")
				.append(nomeTit).append(", P.IVA= ").append(piva)
				.append(", C.F.= ").append(cf);
		if (telefono.isPresent()) {
			s.append(", Telefono= ").append(telefono.get());
		}
		if (sedeLeg.isPresent()) {
			s.append(", SedeLeg.= ").append(sedeLeg.get());
		}
		if (citta.isPresent()) {
			s.append(", Citta'= ").append(citta.get());
		}
		if (cap.isPresent()) {
			s.append(", CAP= ").append(cap.get());
		}
		if (provincia.isPresent()) {
			s.append(", Provincia= ").append(provincia.get());
		}
		return s.toString();
	}

	@Override
	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 */
	public int compareTo(final Contatto other) {
		return this.ragSoc.toLowerCase().compareTo(
				other.getRagioneSociale().toLowerCase());
	}

	/**
	 * Metodo chiamato da un output stream che scrive; Indica come serailizzare
	 * un contatto.
	 * 
	 * @param out
	 *            l'outputstream su cui scrivere
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();

		out.writeObject(telefono.orElse(null));
		out.writeObject(sedeLeg.orElse(null));
		out.writeObject(citta.orElse(null));
		out.writeObject(cap.orElse(null));
		out.writeObject(provincia.orElse(null));
	}

	/**
	 * Metodo chiamato da un input stream che legge; indica come leggere un
	 * contatto serializzato.
	 * 
	 * @param in
	 *            l'input stream da cui si legge
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(final ObjectInputStream in)
			throws ClassNotFoundException, IOException {
		in.defaultReadObject();

		telefono = Optional.ofNullable((String) in.readObject());

		sedeLeg = Optional.ofNullable((String) in.readObject());

		citta = Optional.ofNullable((String) in.readObject());

		cap = Optional.ofNullable((String) in.readObject());

		provincia = Optional.ofNullable((String) in.readObject());
	}

	/**
	 * Builder per la classe Contatto.
	 * 
	 * @author Enrico
	 *
	 */
	public static class Builder {

		private static final int CF_LENGTH = 16;
		private static final int PIVA_LENGTH = 11;
		private static final int CAP_LENGTH = 5;

		private Optional<String> nomeTit;
		private Optional<String> ragSoc;
		private Optional<String> cf;
		private Optional<String> piva;
		private Optional<String> tel;
		private Optional<String> sedeLeg;
		private Optional<String> citta;
		private Optional<String> cap;
		private Optional<String> prov;

		/**
		 * Restituisce un Builder per la classe Contatto.
		 */
		public Builder() {
			this.nomeTit = Optional.empty();
			this.ragSoc = Optional.empty();
			this.cf = Optional.empty();
			this.piva = Optional.empty();
			this.tel = Optional.empty();
			this.sedeLeg = Optional.empty();
			this.citta = Optional.empty();
			this.cap = Optional.empty();
			this.prov = Optional.empty();
		}

		public Builder setNomeTitolare(final String name) {
			this.nomeTit = Optional.ofNullable(name);
			return this;
		}

		public Builder setRagSoc(final String rag) {
			this.ragSoc = Optional.ofNullable(rag);
			return this;
		}

		public Builder setCF(final String cf) {
			this.cf = Optional.ofNullable(cf);
			return this;
		}

		public Builder setPIVA(final String piva) {
			this.piva = Optional.ofNullable(piva);
			return this;
		}

		public Builder setTelefono(final String tel) {
			this.tel = Optional.ofNullable(tel);
			return this;
		}

		public Builder setSedeLeg(final String sede) {
			this.sedeLeg = Optional.ofNullable(sede);
			return this;
		}

		public Builder setCitta(final String citta) {
			this.citta = Optional.ofNullable(citta);
			return this;
		}

		public Builder setCAP(final String cap) {
			this.cap = Optional.ofNullable(cap);
			return this;
		}

		public Builder setProvincia(final String prov) {
			this.prov = Optional.ofNullable(prov);
			return this;
		}

		public Contatto build() {
			if (cf.orElseThrow(
					() -> new IllegalStateException(
							"Il campo C.F. non è stato compilato")).length() != CF_LENGTH) {
				throw new IllegalStateException("Il campo C.F. deve contenere "
						+ CF_LENGTH + " caratteri");
			}

			if (piva.orElseThrow(
					() -> new IllegalStateException(
							"Il campo P.IVA non è stato compilato")).length() != PIVA_LENGTH) {
				throw new IllegalStateException(
						"Il campo P.IVA deve contenere " + PIVA_LENGTH
								+ " caratteri");
			}

			if (cap.isPresent() && cap.get().length() != CAP_LENGTH) {
				throw new IllegalStateException("Il campo CAP deve contenere "
						+ CAP_LENGTH + " caratteri");
			}

			if (nomeTit.isPresent() && ragSoc.isPresent()) {
				return new ContattoImpl(nomeTit.get(), ragSoc.get(), cf.get(),
						piva.get(), tel, sedeLeg, citta, cap, prov);
			} else {
				throw new IllegalStateException(
						"I campi Nome Titolare e/o Rag. Sociale non sono stati compilati");
			}
		}
	}

}
