package model.conto;

import static org.junit.Assert.*;
import model.conto.Conto.AccesoA;
import model.conto.Conto.Eccedenza;
import model.conto.Conto.Tipo;

/**
 * Test per la Classe ContoImpl.
 * 
 * @author Enrico
 *
 */
public class Test {

	@org.junit.Test
	public void testOK() {

		final Conto c = new ContoImpl("Crediti v/clienti", AccesoA.CREDITI);

		assertEquals(0, c.getSaldo(), 0.001);

		assertEquals(c.getEccedenzaAttuale(), Eccedenza.DARE);

		assertEquals(c.getEccedenzaSolita(), Eccedenza.DARE);

		assertEquals(c.getTipo(), Tipo.FINANZIARIO);

		System.out.println(c);

		c.addMovimento(0);

		System.out.println(c);

		assertEquals(0, c.getSaldo(), 0.001);

		System.out.println(c);

		assertEquals(c.getEccedenzaAttuale(), Eccedenza.DARE);

		c.addMovimento(-10.05);

		System.out.println(c);

		assertEquals(c.getEccedenzaAttuale(), Eccedenza.AVERE);

		assertEquals(c.getEccedenzaSolita(), Eccedenza.DARE);

	}

}
