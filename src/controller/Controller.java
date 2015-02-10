package controller;

import java.util.Set;

import model.contatti.Contatto;

/**
 * Descrive il comportamento del controllore dell'applicazione.
 * 
 * @author Enrico
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
	 * Chiede l'inserimento del nostro contatto all'utente.
	 * 
	 * @return il nostro contatto
	 */
	Contatto askOurContact();

	/**
	 * Chiede all'utente quale contatto mantenere tra quelli equals presenti nel set passato.
	 * 
	 * @param set il set di contatti tra cui scegliere quale mantenere
	 * @return il contatto scelto dall'utente per essere mantenuto
	 */
	Contatto wichContattoToMantain(Set<Contatto> set);
}
