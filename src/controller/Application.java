package controller;

import view.ViewImpl;
import model.ModelImpl;

/**
 * Classe da cui parte l'applicazione.
 * 
 * @author Enrico
 *
 */
public class Application {

	private static final String SAVE_PATH = System.getProperty("user.dir");
	private static final String APP_NAME = "Azienda Project";
	
	public static void main(String... args) {
		final Controller c = new ControllerImpl(SAVE_PATH);
		c.setModel(new ModelImpl(c));
		c.setView(new ViewImpl(c));
		c.showMenu(APP_NAME);
	}

}
