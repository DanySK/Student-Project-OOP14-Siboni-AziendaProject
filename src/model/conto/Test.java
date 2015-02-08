package model.conto;

import static org.junit.Assert.*;
import model.conto.Conto.AccesoA;
import model.conto.Conto.Eccedenza;
import model.conto.Conto.Tipo;

/**
 * Test per la Classe ContoImpl 
 * @author Enrico
 *
 */
public class Test {
	
	@org.junit.Test
	public void testOK(){
		
		final Conto c = new ContoImpl("Crediti v/clienti",AccesoA.CREDITI);
		
		assertEquals(0, c.getEccedenza(), 0.001);
		
		assertEquals(c.getSegnoEccedenza(),Eccedenza.DARE);
		
		assertEquals(c.getTipo(),Tipo.FINANZIARIO);
		
		System.out.println(c);
		
		c.addMovimento(0);
		
		System.out.println(c);
		
		assertEquals(0, c.getEccedenza(), 0.001);
		
		assertEquals(c.getSegnoEccedenzaSeAggiunto(10),Eccedenza.DARE);
		
		assertEquals(c.getSegnoEccedenzaSeAggiunto(-0.9),Eccedenza.AVERE);
		
		System.out.println(c);
		
		assertEquals(c.getSegnoEccedenza(),Eccedenza.DARE);
		
		c.addMovimento(-10.05);
		
		System.out.println(c);
		
		assertEquals(c.getSegnoEccedenza(),Eccedenza.AVERE);
		
		assertEquals(c.getSegnoEccedenzaSeAggiunto(10.06),Eccedenza.DARE);
		
	}

}
