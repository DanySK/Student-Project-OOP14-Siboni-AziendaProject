package model.contatti;

import java.util.Optional;

public class ContattoImpl implements Contatto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8608243775362205040L;

	private final String nomeTit;
	private final String ragSoc;
	private final String CF;
	private final String PIVA;
	private final Optional<String> Telefono;
	private final Optional<String> sedeLeg;
	private final Optional<String> citta;
	private final Optional<String> CAP;
	private final Optional<String> Prov;

	private ContattoImpl(final String nome, final String ragSoc,
			final String CF, final String PIVA,
			final Optional<String> Telefono, final Optional<String> sedeLeg,
			final Optional<String> citta, final Optional<String> CAP,
			final Optional<String> Prov) {

		this.nomeTit = nome;
		this.ragSoc = ragSoc;
		this.CF = CF;
		this.PIVA = PIVA;
		this.Telefono = Telefono;
		this.sedeLeg = sedeLeg;
		this.citta = citta;
		this.CAP = CAP;
		this.Prov = Prov;
	}

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
		return this.PIVA;
	}

	@Override
	public String getCF() {
		return this.CF;
	}

	@Override
	public String getNomeCognomeTitolare() {
		return this.nomeTit;
	}

	@Override
	public Optional<String> getTelefono() {
		return this.Telefono;
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
		return this.CAP;
	}

	@Override
	public Optional<String> getProvincia() {
		return this.Prov;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CF == null) ? 0 : CF.hashCode());
		result = prime * result + ((PIVA == null) ? 0 : PIVA.hashCode());
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
				&& PIVA.equalsIgnoreCase(other.PIVA)
				&& CF.equalsIgnoreCase(other.CF)
				&& (sedeLeg.isPresent() ? sedeLeg.get().equalsIgnoreCase(
						other.sedeLeg.orElse(sedeLeg.get())) : true)
				&& (citta.isPresent() ? citta.get().equalsIgnoreCase(
						other.citta.orElse(citta.get())) : true)
				&& (Prov.isPresent() ? Prov.get().equalsIgnoreCase(
						other.Prov.orElse(Prov.get())) : true)
				&& (CAP.isPresent() ? CAP.get().equalsIgnoreCase(
						other.CAP.orElse(CAP.get())) : true)
				&& (Telefono.isPresent() ? Telefono.get().equalsIgnoreCase(
						other.Telefono.orElse(Telefono.get())) : true);
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder(100);
		s.append("Contatto [RagioneSociale= ").append(ragSoc)
				.append(", NomeTitolare= ").append(nomeTit).append(", P.IVA= ")
				.append(PIVA).append(", C.F.= ").append(CF);
		if (Telefono.isPresent()) {
			s.append(", Telefono= ").append(Telefono.get());
		}
		if (sedeLeg.isPresent()) {
			s.append(", SedeLeg.= ").append(sedeLeg.get());
		}
		if (citta.isPresent()) {
			s.append(", Citta'= ").append(citta.get());
		}
		if (CAP.isPresent()) {
			s.append(", CAP= ").append(CAP.get());
		}
		if (Prov.isPresent()) {
			s.append(", Provincia= ").append(Prov.get());
		}
		return s.append("]").toString();
	}

	/**
	 * Builder per la classe Contatto
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
		private Optional<String> telefono;
		private Optional<String> sedeLeg;
		private Optional<String> citta;
		private Optional<String> cap;
		private Optional<String> prov;

		public Builder() {
			this.nomeTit = Optional.empty();
			this.ragSoc = Optional.empty();
			this.cf = Optional.empty();
			this.piva = Optional.empty();
			this.telefono = Optional.empty();
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
			this.telefono = Optional.ofNullable(tel);
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
						piva.get(), telefono, sedeLeg, citta, cap, prov);
			} else {
				throw new IllegalStateException(
						"I campi Nome Titolare e/o Rag. Sociale non sono stati compilati");
			}
		}
	}

}
