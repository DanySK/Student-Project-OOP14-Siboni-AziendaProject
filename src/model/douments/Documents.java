package model.douments;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.ModelImpl;
import model.contatti.Contatto;
import model.douments.fattura.SimpleFattura;
import model.operation.Operation;

/**
 * Clase che implementa la strategia di generazione delle operazioni
 * 
 * @author Enrico
 *
 */
public class Documents{

	/**
	 * Genera un documento da un'operazione
	 * 
	 * @param op operazione in ingresso
	 * @param other l'altro soggetto presente nel documento
	 * @param dataDoc la data del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 */
	public static Optional<Document> generate(final Operation op, final Contatto other,
			final Data dataDoc) {
		// TODO Potrebbe generare assegni circolari
		return Optional.empty();
	}

	/**
	 * Genera un documento da un'operazione
	 * 
	 * @param op operazione in ingresso
	 * @param other l'altro soggetto presente nel documento
	 * @param dataDoc la data del documento
	 * @param numDoc il numero del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 */
	public Optional<Document> generate(final Operation op, final  Contatto other,
			final Data dataDoc, final String numDoc) {
		Document resultDocument = null;

		if (operationHasExactly(
				op,
				new HashSet<>(Arrays.asList("Crediti v/clienti",
						"IVA a Debito", "Vendita Merci")))) {

			resultDocument = new SimpleFattura.Builder()
					.setMittente(ModelImpl.getInstance().getOurContact())
					.setDebitore(other).build();

		}
		return Optional.ofNullable(resultDocument);
	}

	/**
	 * Genera un documento a partire da un'operazione
	 * 
	 * @param op operazione in ingresso
	 * @param traente colui che spicca il documento
	 * @param trattario colui che è tenuto ad onorare il documento
	 * @param beneficiario colui che riceve i benefici dal documento
	 * @param dataDoc la data del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 */
	public Optional<Document> generate(final Operation op, final Contatto traente,
			final Contatto trattario, final Contatto beneficiario, final Data dataDoc) {
		// TODO Potrebbe generare Assegni bancari
		return Optional.empty();
	}

	/**
	 * Genera un documento a partire da un'operazione
	 * 
	 * @param op operazione in ingresso
	 * @param traente colui che spicca il documento
	 * @param trattario colui che è tenuto ad onorare il documento
	 * @param beneficiario colui che riceve i benefici dal documento
	 * @param dataDoc la data del documento
	 * @param dataTermine la data del termine di validità del documento
	 * @return il documento se l'operazione poteva generarlo, altrimenti Optional.empty
	 */
	public Optional<Document> generate(final Operation op, final Contatto traente,
			final Contatto trattario, final Contatto beneficiario, final Data dataDoc,
			final Data dataTermine) {
		// TODO Potrebbe generare cambiali tratte
		return Optional.empty();
	}

	/**
	 * Dice se l'operazione passata può generare un documento
	 * 
	 * @param op l'operaione da cui generare
	 * @return true se si può generare un documento, false altrimenti
	 */
	public boolean canGenerateFrom(Operation op) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Controlla se l'operazione passata ha esattamente i conti presenti nel Set
	 * 
	 * @param op
	 *            operazione su cui controllare
	 * @param nomiConti
	 *            set con nomi dei conti di cui verificare la presenza
	 * @return true se l'operazione ha esattamente quei conti, false altrimenti
	 */
	private static boolean operationHasExactly(final Operation op,
			final Set<String> nomiConti) {

		return op.getContiMovimentati().stream().map(c -> c.getName())
				.collect(Collectors.toSet()).equals(nomiConti);

	}
}
