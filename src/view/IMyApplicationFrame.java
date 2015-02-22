package view;

import controller.Controller;

/**
 * Interfaccia che descrive i metodi per accedere al controller e al controller
 * della view.
 * 
 * @author Enrico
 *
 */
public interface IMyApplicationFrame {

	/**
	 * 
	 * @return il controler dell'applicazione
	 */
	Controller getController();

	/**
	 * 
	 * @return il controller della View
	 */
	ViewController getViewController();
}
