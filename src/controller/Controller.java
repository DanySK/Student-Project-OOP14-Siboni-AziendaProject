package controller;

import java.util.Set;

import view.View;
import model.contatti.Contatto;

/**
 * Descrive il comportamento del controllore dell'applicazione.
 * 
 * @author Enrico, Marco, Elia
 *
 */
public interface Controller {

	/**
	 * Carica i dati salvati e popola il modello.
	 * 
	 */
	void load();

	/**
	 * Salva il modello.
	 */
	void save();

	/**
	 * Aggiunge una View al controller.
	 * 
	 * @param v
	 *            la view da aggiungere
	 */
	void attachView(View v);
	
	/**
	 * Rimuove la view passata, se non corrisponde a nessuna view non fa nulla
	 * @param v la view da rimuovere
	 */
	void removeView(View v);

	/**
	 * Chiede l'inserimento del nostro contatto all'utente.
	 * 
	 * @return il nostro contatto
	 */
	Contatto askOurContact();

	/**
	 * Richiede quale contatto mantenere tra quelli equals presenti nel set
	 * passato.
	 * 
	 * @param set
	 *            il set di contatti tra cui scegliere quale mantenere
	 * @return il contatto scelto per essere mantenuto
	 */
	Contatto wichContattoToMantain(Set<Contatto> set);
}
