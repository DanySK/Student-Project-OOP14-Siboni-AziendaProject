package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

import model.contatti.Contatto;
import model.contatti.ContattoImpl;
import model.conto.Conto;
import model.conto.Conto.AccesoA;
import model.conto.ContoImpl;
import model.douments.DataImpl;
import model.douments.Document;
import model.douments.fattura.SimpleFattura;
import model.operation.Operation;
import model.operation.OperationImpl;

public class Test {

	@org.junit.Test
	public void testOk() {

		final Model a = new ModelImpl();

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

		assertEquals(a.getContatti(), new HashSet<>(Arrays.asList(ciccio, mio)));

		try {
			a.deleteContatto(other);
			fail("Exception Expected");
		} catch (NoSuchElementException e) {
		}

		a.deleteContatto(ciccio);

		assertEquals(a.getContatti(), new HashSet<>(Arrays.asList(mio)));
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

		assertEquals(a.getConti(), new HashSet<>(Arrays.asList(c2)));
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

		assertEquals(a.getOperation(1), op1);
		assertEquals(a.getOperation(2), op2);
		op2.applicaMovimenti();

		final Document d = new SimpleFattura.Builder().setAliqIva(20)
				.setData(new DataImpl()).setImportoMerce(200).setMittente(mio)
				.setDebitore(ciccio).setNumFattura("88").build();

		try {
			a.addDocumentToOperation(0, d);
			fail("Exception Expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			a.addDocumentToOperation(1, null);
			fail("Exception Expected");
		} catch (NullPointerException e) {
		}

		assertTrue(a.addDocumentToOperation(1, d));
		assertFalse(a.addDocumentToOperation(1, d));

		final Document d2 = a.getDocumentReferredTo(1);
		assertEquals(d, d2);

		a.deleteDocumentReferredTo(1);

		try {
			a.deleteDocumentReferredTo(1);
		} catch (NoSuchElementException e) {
		}

		try {
			a.getDocumentReferredTo(1);
		} catch (NoSuchElementException e) {
		}

	}

	@org.junit.Test
	public void testExceptions() {

		final Model a = new ModelImpl();

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
			a.getOperation(0);
			fail("Exception Expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			a.getOperation(-20);
			fail("Exception Expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			a.getDocumentReferredTo(-1);
			fail("Exception Expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			a.deleteDocumentReferredTo(-1);
			fail("Exception Expected");
		} catch (IllegalArgumentException e) {
		}

		try {
			a.getOperation(3);
			fail("Exception Expected");
		} catch (NoSuchElementException e) {
		}

		try {
			a.getDocumentReferredTo(1);
			fail("Exception Expected");
		} catch (NoSuchElementException e) {
		}

		try {
			a.deleteDocumentReferredTo(1);
			fail("Exception Expected");
		} catch (NoSuchElementException e) {
		}

	}
}
