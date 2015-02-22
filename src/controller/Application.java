package controller;

import view.ViewControllerImpl;
import model.Model;
import model.ModelImpl;

/**
 * Classe da cui parte l'applicazione.
 * 
 * @author Enrico
 *
 */
public final class Application {

	private static final String APP_NAME = "Azienda Project";
	private static final String SAVE_PATH = System.getProperty("user.dir")
			+ System.getProperty("file.separator");
	
	private Application(){
	}
	
	/**
	 * 
	 * @param args
	 *            ingnorato
	 */
	public static void main(final String... args) {
		final Controller c = new ControllerImpl(SAVE_PATH);
		c.setModel(new ModelImpl(c));
		c.setView(new ViewControllerImpl(c, APP_NAME));
		final Model.State stato = c.load();
		if (stato == Model.State.FIRST_RUN) {
			c.showFirstRunView();
		} else if (stato == Model.State.LOADING_SUCCESS) {
			c.showMenu();
		}
	}

}
