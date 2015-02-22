package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.conto.ContoImpl;
import model.data.DataImpl;
import model.douments.Document;
import model.douments.fattura.SimpleFattura;
import model.operation.Operation;
import model.operation.OperationImpl;

/**
 * Classe di testing per la classe ModelImpl.
 * 
 * @author Enrico
 *
 */
public class Test {

	@org.junit.Test
	public void testOk() {

		final Model a = new ModelImpl(null);

		final Contatto ciccio = new ContattoImpl.Builder()
				.setNomeTitolare("ciccio").setRagSoc("ciccio snc")
				.setCF("ssssssssssssssss").setPIVA("33333333333").build();
		final Contatto mio = new ContattoImpl.Builder()
				.setNomeTitolare("Enrico Siboni").setRagSoc("Siboni s.n.c.")
				.setCF("SBNNRC94M03D704F").setPIVA("16680464604").build();
		final Contatto other = new ContattoImpl.Builder()
				.setCF("wwwwwwwwwwwwwwww").setPIVA("22222222222")
				.setNomeTitolare("other").setRagSoc("other spa").build();

		a.addContatto(ciccio);
		a.addContatto(mio);

		assertEquals(a.getContatti(), new TreeSet<>(Arrays.asList(ciccio, mio)));

		try {
			a.deleteContatto(other);
			fail("Exception Expected");
		} catch (NoSuchElementException e) {
		}

		a.deleteContatto(ciccio);

		assertEquals(a.getContatti(), new TreeSet<>(Arrays.asList(mio)));
		a.addContatto(ciccio);
		a.addContatto(other);

		final Conto c1 = new ContoImpl("Crediti v/clienti", AccesoA.CREDITI);
		final Conto c2 = new ContoImpl("Denaro In Cassa", AccesoA.DENARO);

		a.addConto(c1);

		try {
			a.addConto(c1);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}

		a.addConto(c2);
		a.deleteConto(c1);

		try {
			a.deleteConto(c1);
			fail("Exception Expected");
		} catch (NoSuchElementException e) {
		}

		assertEquals(a.getConti(), new TreeSet<>(Arrays.asList(c2)));
		a.addConto(c1);

		final Operation op1 = new OperationImpl();
		op1.setContoMovimentato(c1, -100);
		op1.setContoMovimentato(c2, 100);
		op1.applicaMovimenti();
		a.addOperation(op1);

		final Operation op2 = new OperationImpl();
		op2.setContoMovimentato(c1, -200.50);
		op2.setContoMovimentato(c2, 200.50);
		a.addOperation(op2);

		op2.applicaMovimenti();

		final Document d = new SimpleFattura.Builder().setAliqIva(20)
				.setData(new DataImpl()).setImportoMerce(200).setMittente(mio)
				.setDebitore(ciccio).setNumFattura("88").build();


		assertTrue(a.addDocumentToOperation(op1, d));
		assertFalse(a.addDocumentToOperation(op1, d));

		final Document d2 = a.getDocumentReferredTo(op1);
		assertEquals(d, d2);

		a.deleteDocumentReferredTo(op1);

		try {
			a.deleteDocumentReferredTo(op1);
		} catch (NoSuchElementException e) {
		}

		try {
			a.getDocumentReferredTo(op1);
		} catch (NoSuchElementException e) {
		}

	}

	@org.junit.Test
	public void testExceptions() {

		final Model a = new ModelImpl(null);

		try {
			a.addContatto(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		try {
			a.deleteContatto(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		try {
			a.addConto(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		try {
			a.deleteConto(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		try {
			a.addOperation(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		try {
			a.setOurContact(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		try {
			a.getDocumentReferredTo(null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

	}

	@org.junit.Test
	public void testWriteAndRead() {
		final Model a = new ModelImpl(null);

		final Contatto ciccio = new ContattoImpl.Builder()
				.setNomeTitolare("ciccio").setRagSoc("ciccio snc")
				.setCF("ssssssssssssssss").setPIVA("33333333333").build();
		final Contatto mio = new ContattoImpl.Builder()
				.setNomeTitolare("Enrico Siboni").setRagSoc("Siboni s.n.c.")
				.setCF("SBNNRC94M03D704F").setPIVA("16680464604").build();
		final Contatto other = new ContattoImpl.Builder()
				.setCF("wwwwwwwwwwwwwwww").setPIVA("22222222222")
				.setNomeTitolare("other").setRagSoc("other spa").build();

		a.addContatto(ciccio);
		a.addContatto(mio);
		a.addContatto(other);

		final Conto c1 = new ContoImpl("Crediti v/clienti", AccesoA.CREDITI);
		final Conto c2 = new ContoImpl("Denaro In Cassa", AccesoA.DENARO);

		a.addConto(c1);
		a.addConto(c2);

		final Operation op1 = new OperationImpl();
		op1.setContoMovimentato(c1, -100);
		op1.setContoMovimentato(c2, 100);
		op1.applicaMovimenti();
		a.addOperation(op1);

		final Operation op2 = new OperationImpl();
		op2.setContoMovimentato(c1, -200.50);
		op2.setContoMovimentato(c2, 200.50);
		a.addOperation(op2);
		op2.applicaMovimenti();

		final Document d = new SimpleFattura.Builder().setAliqIva(20)
				.setData(new DataImpl()).setImportoMerce(200).setMittente(mio)
				.setDebitore(ciccio).setNumFattura("88").build();

		a.addDocumentToOperation(op1, d);
		
		a.setOurContact(mio);

		// System.out.println(System.getProperty("java.io.tmpdir"));

		a.save(System.getProperty("java.io.tmpdir"));

		final Model b = new ModelImpl(null);

		b.load(System.getProperty("java.io.tmpdir"));

		assertEquals(a.getContatti(), b.getContatti());
		// System.out.println(b.getContatti());
		assertEquals(a.getConti(), b.getConti());
		// System.out.println(b.getConti());
		assertEquals(a.getDocumentReferredTo(op1), b.getDocumentReferredTo(op1));
	}
}
