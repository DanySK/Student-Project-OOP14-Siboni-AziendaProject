package view;

import controller.Controller;

/**
 * Classe che da il comando di salvataggio al controller.
 * 
 * @author Enrico
 *
 */
public class Saver extends Thread {

	private final Controller controller;

	/**
	 * Crea un nuovo saver.
	 * 
	 * @param c
	 *            il controller a cui dare il comando di salvataggio
	 */
	public Saver(final Controller c) {
		this.controller = c;
	}

	@Override
	public void run() {
		this.controller.save();
	}

}
