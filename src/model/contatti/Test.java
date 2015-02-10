package model.contatti;

import static org.junit.Assert.*;

import java.util.Optional;

/**
 * Classe di testing per ContattoImpl.
 * 
 * @author Enrico
 *
 */
public class Test {

	@org.junit.Test
	public void testConstruction() {

		System.out.println("1--> Test Exceptions on construction");

		try {
			new ContattoImpl.Builder().build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}

		try {
			new ContattoImpl.Builder().setCF("asderf51c10324dsX").build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}

		try {
			new ContattoImpl.Builder().setCF("asderf51c10324ds").build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}

		try {
			new ContattoImpl.Builder().setCF("asderf51c10324ds")
					.setPIVA("11111111111X").build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}

		try {
			new ContattoImpl.Builder().setCF("asderf51c10324ds")
					.setPIVA("11111111111").build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}

		try {
			new ContattoImpl.Builder().setCF("asderf51c10324ds")
					.setPIVA("11111111111").setNomeTitolare("ciccio").build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}

		try {
			new ContattoImpl.Builder().setCF("asderf51c10324ds")
					.setPIVA("11111111111").setNomeTitolare("ciccio")
					.setRagSoc("ciccio snc").setCAP("47122X").build();
			fail("No exception generated");
		} catch (IllegalStateException e) {
			System.out.println(e.toString());
		}
	}

	@org.junit.Test
	public void testContent() {
		System.out.println("\n2--> Test Content generated");

		try {
			final Contatto a = new ContattoImpl.Builder()
					.setNomeTitolare("ciccio").setRagSoc("ciccio snc")
					.setPIVA("11111111111").setCF("asderf51c10324ds")
					.setCAP("47122").build();

			assertEquals(a.getNomeCognomeTitolare(), "ciccio");
			assertEquals(a.getCAP().get(), "47122");
			assertEquals(a.getCitta(), Optional.empty());
			assertEquals(a.getCF(), "asderf51c10324ds");
			assertEquals(a.getPIVA(), "11111111111");
			assertEquals(a.getProvincia(), Optional.empty());
			assertEquals(a.getRagioneSociale(), "ciccio snc");
			assertEquals(a.getSedeLegale(), Optional.empty());
			assertEquals(a.getTelefono(), Optional.empty());

			System.out.println(a.toString());
		} catch (IllegalStateException e) {
			fail("No exception expected");
		}

	}

	@org.junit.Test
	public void testEquals() {

		final Contatto a = new ContattoImpl.Builder().setNomeTitolare("ciccio")
				.setRagSoc("ciccio snc").setPIVA("11111111111")
				.setCF("asderf51c10324ds").setCAP("47122").setProvincia("FC")
				.build();

		final Contatto b = new ContattoImpl.Builder().setNomeTitolare("CICCIO")
				.setRagSoc("CICCIO SNC").setPIVA("11111111111")
				.setCF("ASDERF51C10324DS").setSedeLeg("via Ciccio, 50").build();

		final Contatto c = new ContattoImpl.Builder().setNomeTitolare("io")
				.setRagSoc("cio").setCF("eeeeeeeeeeeeeeee")
				.setPIVA("22222222222").build();

		assertEquals(a, b);

		assertEquals(b, a);

		assertNotEquals(c, b);

		assertNotEquals(b, c);
	}
}
