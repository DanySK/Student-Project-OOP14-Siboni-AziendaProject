package model.operation;

import static org.junit.Assert.*;

import java.util.Map;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.conto.ContoImpl;
import model.data.DataImpl;
import model.douments.Document;
import model.douments.fattura.SimpleFattura;

/**
 * Classe di testing per le operazioni.
 * 
 * @author Enrico
 *
 */
public class Test {

	@org.junit.Test
	public void testOK() {

		Operation o = new OperationImpl();

		Conto creditivclienti = new ContoImpl("Crediti v/Clienti",
				AccesoA.CREDITI);
		Conto venditamerci = new ContoImpl("Vendita merci", AccesoA.RICAVI_ES);
		Conto ivadebito = new ContoImpl("Iva a debito", AccesoA.DEBITI);

		o.setContoMovimentato(creditivclienti, 133.64);

		try {
			o.setContoMovimentato(venditamerci, 0);
			fail("Deve generare eccezione");
		} catch (IllegalArgumentException e) {
		}

		o.setContoMovimentato(venditamerci, 111.37);

		assertFalse(o.isBalanced());

		try {
			o.applicaMovimenti();
			fail("Deve generare eccezione");
		} catch (IllegalStateException e) {
		}

		o.setContoMovimentato(ivadebito, 22.27);

		assertFalse(o.haveMovementsBeenApplied());

		try {
			o.applicaMovimenti();
		} catch (IllegalStateException e) {
			fail("Non deve generare eccezioni");
		}

		assertTrue(o.haveMovementsBeenApplied());
		assertEquals(creditivclienti.getSaldo(), 133.64, 0.001);

		try {
			o.applicaMovimenti();
		} catch (IllegalStateException e) {
			fail("Non deve generare eccezioni");
		}

		assertTrue(o.isBalanced());

		assertEquals(creditivclienti.getSaldo(), 133.64, 0.001);

		System.out.println(o);

		Operation o2 = new OperationImpl();

		Conto assegni = new ContoImpl("Assegni", AccesoA.DENARO);

		o2.setContoMovimentato(assegni, 133.64);
		o2.setContoMovimentato(creditivclienti, -101.63);
		System.out.println(o2);

		assertFalse(o2.isBalanced());

		o2.setContoMovimentato(creditivclienti, -32.01);

		System.out.println(o2);

		try {
			o2.applicaMovimenti();
		} catch (IllegalStateException e) {
			fail("Non deve generare eccezioni");
		}

		assertEquals(creditivclienti.getSaldo(), 0, 0.001);

		final Map<Conto, Double> first = o2.getContiMovimentatiEImporto();
		o2.setContoMovimentato(creditivclienti, 10);
		assertEquals(first, o2.getContiMovimentatiEImporto());

		final Contatto a = new ContattoImpl.Builder().setNomeTitolare("ciccio")
				.setRagSoc("ciccio snc").setPIVA("11111111111")
				.setCF("asderf51c10324ds").build();

		final Document fatt = new SimpleFattura.Builder().setAliqIva(20)
				.setData(new DataImpl()).setDebitore(a).setMittente(a)
				.setImportoMerce(1000).setNumFattura("10/bis").build();
		try{
			o2.setDocument(null);
		}catch (IllegalStateException e){
			fail("No exception expected");
		}
		
		try{
			o2.setDocument(fatt);
		}catch (IllegalStateException e){
			fail("No exception expected");
		}
		
		try{
			o2.setDocument(fatt);
			fail("Exception expected");
		}catch (IllegalStateException e){
		}

		try{
			o2.setDocument(null);
			fail("Exception expected");
		}catch (IllegalStateException e){
		}
	}
}
