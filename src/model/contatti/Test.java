package model.contatti;

import static org.junit.Assert.*;

/**
 * Classe di testing per ContattoImpl
 * @author Enrico
 *
 */
public class Test {

	@org.junit.Test
	void testConstruction(){
		
		try{
			new ContattoImpl.ContattoBuilder().build();
			fail("No exception generated");
		}catch(IllegalStateException e){}
		
		try{
			new ContattoImpl.ContattoBuilder().setNomeTitolare("ciccio").build();
			fail("No exception generated");
		}catch(IllegalStateException e){}
		
		
		assertTrue(true);
		
	}
}
