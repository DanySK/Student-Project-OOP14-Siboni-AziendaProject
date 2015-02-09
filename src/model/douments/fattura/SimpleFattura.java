package model.douments.fattura;

import java.util.Optional;

import model.contatti.Contatto;
import model.douments.AbstractDocument;
import model.douments.Data;

/**
 * Implementazione astratta di una Fattura
 * 
 * @author Enrico
 *
 */
public class SimpleFattura extends AbstractDocument implements Fattura {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9085600052663813184L;

	private final double importoMerce;
	private final int aliqIva;
	private final String numFattura;
	private final Optional<Integer> aliqSconto;
	private Optional<Double> importoSconto = Optional.empty();
	private final Optional<Double> speseDoc;
	private final Optional<Double> interessi;
	private transient Optional<Double> totCached;

	private SimpleFattura(final Contatto mittente, final Contatto debitore,
			final Data data, final double importo, final int aliqIva,
			final String num, final Optional<Integer> aliqSconto,
			final Optional<Double> importoSconto,
			final Optional<Double> speseDoc, final Optional<Double> interessi) {

		super(mittente, mittente, debitore, data);

		this.importoMerce = importo;
		this.aliqIva = aliqIva;
		this.numFattura = num;
		this.aliqSconto = aliqSconto;
		this.importoSconto = importoSconto;
		this.speseDoc = speseDoc;
		this.interessi = interessi;
	}

	@Override
	public int getAliquotaIva() {
		return this.aliqIva;
	}

	@Override
	public double getImportoIVA() {
		return this.getImponibile() * this.getAliquotaIva() / 100;
	}

	@Override
	public double getImportoMerce() {
		return this.importoMerce;
	}

	@Override
	public double getImponibile() {
		if (this.getImportoSconto().isPresent()) {
			return this.getImportoMerce() - this.getImportoSconto().get();
		}
		return this.getImportoMerce();
	}

	@Override
	public Optional<Double> getImportoSconto() {
		if (!this.importoSconto.isPresent()
				&& this.getAliquotaSconto().isPresent()) {
			this.importoSconto = Optional.of(this.getImportoMerce()
					* this.getAliquotaSconto().get() / 100);
		}
		return this.importoSconto;
	}

	public Optional<Integer> getAliquotaSconto() {
		return this.aliqSconto;
	}

	@Override
	public Optional<Double> getSpeseDocumentate() {
		return this.speseDoc;
	}

	@Override
	public Optional<Double> getInteressi() {
		return this.interessi;
	}

	@Override
	public double getTotale() {
		if (!this.totCached.isPresent()) {
			this.totCached = Optional.of(this.getImponibile()
					+ this.getImportoIVA()
					+ this.getSpeseDocumentate().orElse(0.0)
					+ this.getInteressi().orElse(0.0));
		}
		return totCached.get();
	}

	@Override
	public String getNumFattura() {
		return this.numFattura;
	}

	/**
	 * Builder per la classe SimpleFattura
	 * 
	 * @author Enrico
	 *
	 */
	public static class Builder {

		private Optional<Contatto> mittente = Optional.empty();
		private Optional<Contatto> debitore = Optional.empty();
		private Optional<Data> data = Optional.empty();
		private Optional<Double> importoMerce = Optional.empty();
		private Optional<Integer> aliqIva = Optional.empty();
		private Optional<String> numFattura = Optional.empty();
		private Optional<Integer> aliqSconto = Optional.empty();
		private Optional<Double> importoSconto = Optional.empty();
		private Optional<Double> speseDoc = Optional.empty();
		private Optional<Double> interessi = Optional.empty();

		public Builder setMittente(final Contatto mitt) {
			this.mittente = Optional.ofNullable(mitt);
			return this;
		}

		public Builder setDebitore(final Contatto deb) {
			this.debitore = Optional.ofNullable(deb);
			return this;
		}

		public Builder setData(final Data data) {
			this.data = Optional.ofNullable(data);
			return this;
		}

		public Builder setImportoMerce(final double imp) {
			this.importoMerce = Optional.of(imp);
			return this;
		}

		public Builder setAliqIva(final int aliqIva) {
			this.aliqIva = Optional.of(aliqIva);
			return this;
		}

		public Builder setNumFattura(final String num) {
			this.numFattura = Optional.of(num);
			return this;
		}

		public Builder setAliqSconto(final int aliqSconto) {
			this.aliqSconto = Optional.of(aliqSconto);
			return this;
		}

		public Builder setImportoSconto(final double importoSconto) {
			this.importoSconto = Optional.of(importoSconto);
			return this;
		}

		public Builder setSpeseDoc(final double speseDoc) {
			this.speseDoc = Optional.of(speseDoc);
			return this;
		}

		public Builder setInteressi(final double i) {
			this.interessi = Optional.of(i);
			return this;
		}

		public SimpleFattura build() {

			if (mittente.isPresent() && debitore.isPresent()
					&& data.isPresent() && importoMerce.isPresent()
					&& aliqIva.isPresent() && numFattura.isPresent()) {

				if (importoMerce.get() < 0) {
					throw new IllegalArgumentException(
							"L'importo della merce non può essere negativo");
				}

				if (aliqIva.get() <= 0 || aliqIva.get() >= 100) {
					throw new IllegalArgumentException(
							"L'aliquota iva deve essere compresa tra 0 e 100");
				}

				if (aliqSconto.isPresent()
						&& (aliqSconto.get() <= 0 || aliqSconto.get() > 100)) {
					throw new IllegalArgumentException(
							"L'aliquota dello sconto deve essere compresa tra 0 e 100");
				}

				if (importoSconto.isPresent() && importoSconto.get() < 0) {
					throw new IllegalArgumentException(
							"L'importo dello sconto non può essere negativo");
				}

				if (speseDoc.isPresent() && speseDoc.get() < 0) {
					throw new IllegalArgumentException(
							"L'importo delle Spese Documentate non può essere negativo");
				}

				if (interessi.isPresent() && interessi.get() < 0) {
					throw new IllegalArgumentException(
							"L'importo degli interessi non può essere negativo");
				}

				return new SimpleFattura(this.mittente.get(),
						this.debitore.get(), this.data.get(),
						this.importoMerce.get(), this.aliqIva.get(),
						this.numFattura.get(), this.aliqSconto,
						this.importoSconto, this.speseDoc, this.interessi);
			} else {
				throw new IllegalArgumentException(
						"Uno o piu' campi tra: Mittente, Debitore, Data, ImportoMerce, AliqiotaIva, NumeroFattura non sono stati compilati");
			}

		}
	}

}
