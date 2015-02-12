package controller;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import view.View;
import model.Model;
import model.contatti.Contatto;
import model.conto.Conto;

/**
 * Descrive il comportamento del controllore dell'applicazione.
 * 
 * @author Enrico, Marco, Elia
 *
 */
public interface Controller extends ActionListener{

	/**
	 * Mostra il menu.
	 * 
	 * @param appName il nome da dare al menu
	 */
	void showMenu(String appName);
	
	/**
	 * Mostra un messaggio di errore.
	 * 
	 * @param errorMessage messaggio da mostrare
	 */
	void showErrorMessage(String errorMessage);
	
	/**
	 * Aggiunge una operazione al modello.
	 * 
	 * @param nomeConti i conti movimentati
	 * @param importi gli importi di cui sono stati movimentati
	 * @return true se l'aggiunta va a buon fine, false altrimenti
	 */
	boolean aggiuntaOperazione(List<Conto> conti, List<Double> importi);
	
	/**
	 * 
	 * @return l'insieme dei conti
	 */
	Set<Conto> getInsiemeConti();
	
	/**
	 * 
	 * @return l'insieme dei contatti
	 */
	Set<Contatto> getInsiemeContatti();
	
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
	 * Aggiunge la View al controller.
	 * 
	 * @param v
	 *            la view da aggiungere
	 */
	void setView(View v);
	
	/**
	 * Collega il modello al controller.
	 * 
	 * @param m il modello da collegare
	 */
	void setModel(Model m);
	
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
