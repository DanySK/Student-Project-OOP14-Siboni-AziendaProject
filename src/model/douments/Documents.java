package model.douments;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import model.contatti.Contatto;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.conto.ContoImpl;
import model.douments.fattura.SimpleFattura;
import model.operation.Operation;

/**
 * Classe che implementa la strategia di generazione dei documenti.
 * 
 * @author Enrico
 *
 */

// TODO
public final class Documents {

	private Documents() {
	}

	/**
	 * Enumerazione che contiene i conti che possono generare dei documenti
	 * 
	 * @author Enrico
	 *
	 */
	private enum ContiGeneratori {
		CREDITI_V_CLIENTI(new ContoImpl("Crediti v/clienti", AccesoA.CREDITI)), DEBITI_V_FORNITORI(
				new ContoImpl("Debiti v/fornitori", AccesoA.DEBITI)), VENDITA_MERCI(
				new ContoImpl("Vendita Merci", AccesoA.RICAVI_ES)), ACQUISTO_MERCI(
				new ContoImpl("Acquisto Merci", AccesoA.COSTI_ES)), IVA_A_CREDITO(
				new ContoImpl("Iva a Credito", AccesoA.CREDITI)), IVA_A_DEBITO(
				new ContoImpl("Iva a Debito", AccesoA.DEBITI));

		private final Conto conto;

		ContiGeneratori(final Conto c) {
			this.conto = c;
		}

		/**
		 * 
		 * @return il conto wrappato
		 */
		Conto get() {
			return this.conto;
		}

	}

	/**
	 * Genera un documento da un'operazione.
	 * 
	 * @param op
	 *            operazione in ingresso
	 * @param we
	 *            il nostro contatto
	 * @param other
	 *            l'altro soggetto presente nel documento
	 * @param dataDoc
	 *            la data del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti
	 *         Optional.empty
	 */
	public static Optional<Document> generate(final Operation op,
			final Contatto we, final Contatto other, final Data dataDoc) {
		// TODO Potrebbe generare assegni circolari
		return Optional.empty();
	}

	/**
	 * Genera un documento da un'operazione.
	 * 
	 * @param op
	 *            operazione in ingresso
	 * @param we
	 *            il nostro contatto
	 * @param other
	 *            l'altro soggetto presente nel documento
	 * @param dataDoc
	 *            la data del documento
	 * @param dataTermine
	 *            la data del termine di validità del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti
	 *         Optional.empty
	 */
	public static Optional<Document> generate(final Operation op,
			final Contatto we, final Contatto other, final Data dataDoc,
			final Data dataTermine) {
		// TODO Potrebbe generare cambiali pagherò
		return Optional.empty();
	}

	/**
	 * Genera un documento da un'operazione.
	 * 
	 * @param op
	 *            operazione in ingresso
	 * @param we
	 *            il nostro contatto
	 * @param other
	 *            l'altro soggetto presente nel documento
	 * @param dataDoc
	 *            la data del documento
	 * @param numDoc
	 *            il numero del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti
	 *         Optional.empty
	 */
	public static Optional<Document> generate(final Operation op,
			final Contatto we, final Contatto other, final Data dataDoc,
			final String numDoc) {
		// TODO creare altri metodi con altri parametri per fatture con
		// interessi ecc...
		Document resultDocument = null;

		final Map<Conto, Double> tempMap = op.getContiMovimentatiEImporto();

		if (op.getContiMovimentati().equals(
				new HashSet<>(Arrays.asList(
						ContiGeneratori.CREDITI_V_CLIENTI.get(),
						ContiGeneratori.IVA_A_DEBITO.get(),
						ContiGeneratori.VENDITA_MERCI.get())))) {

			resultDocument = new SimpleFattura.Builder()
					.setMittente(we)
					.setDebitore(other)
					.setData(dataDoc)
					.setAliqIva(
							calculateIvaFrom(tempMap
									.get(ContiGeneratori.VENDITA_MERCI.get()),
									tempMap.get(ContiGeneratori.IVA_A_DEBITO
											.get())))
					.setImportoMerce(
							tempMap.get(ContiGeneratori.VENDITA_MERCI.get()))
					.setNumFattura(numDoc).build();

		}
		return Optional.ofNullable(resultDocument);
	}

	/**
	 * Genera un documento a partire da un'operazione.
	 * 
	 * @param op
	 *            operazione in ingresso
	 * @param traente
	 *            colui che spicca il documento
	 * @param trattario
	 *            colui che è tenuto ad onorare il documento
	 * @param beneficiario
	 *            colui che riceve i benefici dal documento
	 * @param dataDoc
	 *            la data del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti
	 *         Optional.empty
	 */
	public static Optional<Document> generate(final Operation op,
			final Contatto traente, final Contatto trattario,
			final Contatto beneficiario, final Data dataDoc) {
		// TODO Potrebbe generare Assegni bancari
		return Optional.empty();
	}

	/**
	 * Genera un documento a partire da un'operazione.
	 * 
	 * @param op
	 *            operazione in ingresso
	 * @param traente
	 *            colui che spicca il documento
	 * @param trattario
	 *            colui che è tenuto ad onorare il documento
	 * @param beneficiario
	 *            colui che riceve i benefici dal documento
	 * @param dataDoc
	 *            la data del documento
	 * @param dataTermine
	 *            la data del termine di validità del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti
	 *         Optional.empty
	 */
	public static Optional<Document> generate(final Operation op,
			final Contatto traente, final Contatto trattario,
			final Contatto beneficiario, final Data dataDoc,
			final Data dataTermine) {
		// TODO Potrebbe generare cambiali tratte
		return Optional.empty();
	}

	/**
	 * Dice se l'operazione passata può generare un documento.
	 * 
	 * @param op
	 *            l'operaione da cui generare
	 * @return true se si può generare un documento, false altrimenti
	 */
	public static boolean canGenerateFrom(final Operation op) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Calcola l'aliquota iva a partire dall'importo imponibile e tot Iva
	 * 
	 * @param imponibile
	 *            importo imponibile su cui è stata calcolata l'IVA
	 * @param importoIva
	 *            importo dell'iva risultante
	 * @return l'intero che corrisponde alle cifre intere dell'aliquota IVA
	 */
	private static int calculateIvaFrom(final double imponibile,
			final double importoIva) {
		return (int) (Math.round((importoIva * 100) / imponibile));
	}
}
