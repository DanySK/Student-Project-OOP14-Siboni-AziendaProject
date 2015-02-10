package model.conto;

import java.io.Serializable;

/**
 * Descrive un conto, e il suo comportamento.
 * 
 * @author Enrico
 *
 */
public interface Conto extends Serializable {

	/**
	 * 
	 * @return il nome del conto
	 */
	String getName();

	/**
	 * Aggiunge una movimentazione al conto.
	 * 
	 * @param importo
	 *            l'importo della movimentazione
	 */
	void addMovimento(double importo);

	/**
	 * 
	 * @return il saldo di questo conto
	 */
	double getSaldo();

	/**
	 * 
	 * @return il segno dell'eccedenza attuale di questo conto
	 */
	Eccedenza getEccedenzaAttuale();

	/**
	 * 
	 * @return il segno dell'eccedenza che dovrebbe avere questo conto in
	 *         condizioni normali
	 */
	Eccedenza getEccedenzaSolita();

	/**
	 * 
	 * @return il tipo di questo conto
	 */
	Tipo getTipo();

	/**
	 * 
	 * @return a cosa è accesso questo conto
	 */
	AccesoA getAccesoA();

	/**
	 * Descrive l'eccedenza che può avere un conto.
	 * 
	 * @author Enrico
	 *
	 */
	static enum Eccedenza {
		DARE("Dare"), AVERE("Avere");

		private final String nome;

		private Eccedenza(final String name) {
			this.nome = name;
		}

		@Override
		public String toString() {
			return this.nome;
		}
	}

	/**
	 * Descrive il tipo del conto.
	 * 
	 * @author Enrico
	 *
	 */
	static enum Tipo {
		FINANZIARIO("Finanziario"), ECONOMICO_DI_REDDITO("Economico Di Reddito"), ECONOMICO_DI_CAPITALE(
				"Economico Di Capitale");

		private final String nome;

		private Tipo(final String name) {
			this.nome = name;
		}

		@Override
		public String toString() {
			return this.nome;
		}
	}

	/**
	 * Desccrive a cosa può essere acceso un conto.
	 * 
	 * @author Enrico
	 *
	 */
	static enum AccesoA {
		CREDITI("Crediti", Tipo.FINANZIARIO), DEBITI("Debiti", Tipo.FINANZIARIO), DENARO(
				"Denaro", Tipo.FINANZIARIO), COSTI_ES("Costi d'Esercizio",
				Tipo.ECONOMICO_DI_REDDITO), COSTI_SOSP("Costi Sospesi",
				Tipo.ECONOMICO_DI_REDDITO), COSTI_PLUR("Costi Pluriennali",
				Tipo.ECONOMICO_DI_REDDITO), RICAVI_ES("Ricavi d'Esercizio",
				Tipo.ECONOMICO_DI_REDDITO), RICAVI_SOSP("Ricavi Sospesi",
				Tipo.ECONOMICO_DI_REDDITO), RICAVI_PLUR("Ricavi Pluriennali",
				Tipo.ECONOMICO_DI_REDDITO), PATRIMONIO_NETTO(
				"Patrimonio Netto", Tipo.ECONOMICO_DI_CAPITALE);

		private final String nome;
		private final Tipo tipo;

		private AccesoA(final String name, final Tipo type) {
			this.nome = name;
			this.tipo = type;
		}

		public Tipo getTipoConto() {
			return this.tipo;
		}

		@Override
		public String toString() {
			return this.nome;
		}
	}
}
