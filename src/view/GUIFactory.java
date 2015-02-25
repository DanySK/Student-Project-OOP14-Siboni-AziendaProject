package view;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Interfaccia che descrive una factory per componenti della GUI.
 * 
 * @author Enrico
 *
 */
public interface GUIFactory {

	/**
	 * Crea un JButton inizioalizzato con la stringa passata.
	 * 
	 * @param name
	 *            il nome del bottone
	 * @return il bottone creato
	 */
	default JButton createButton(final String name) {
		return new JButton(name);
	}

	/**
	 * Crea una JLabel inizializzata con la stringa passata.
	 * 
	 * @param text
	 *            il testo da immettere nella label
	 * @return la label cereata
	 */
	default JLabel createLabel(final String text) {
		return new JLabel(text);
	}

	/**
	 * Crea un JPanel inizializzato con i componenti passati, o vuoto se non
	 * viene passato nulla.
	 * 
	 * @param components
	 *            i componenti da aggiungere uno dopo l'altro al pannello
	 * @return il pannello creato con i componenti passati al suo intero
	 */
	default JPanel createPanel(Component... components) {
		final JPanel toReturn = new JPanel();
		for (final Component c : components) {
			toReturn.add(c);
		}
		return toReturn;
	}

	/**
	 * Crea un JTextField inizializzato con 10 spazi.
	 * 
	 * @return il field creato
	 */
	default JTextField createTextField() {
		return new JTextField(10);
	}

	/**
	 * Crea un JTextArea inizializzato con una riga di dieci caratteri.
	 * 
	 * @return la textArea creata
	 */
	default JTextArea createTextArea() {
		return new JTextArea(1, 10);
	}

	/**
	 * Crea un JScrollPane vuoto.
	 * 
	 * @return lo scrollPane creato
	 */
	default JScrollPane createScrollPane() {
		return new JScrollPane();
	}

	/**
	 * Crea uno JSplitPane vuoto.
	 * 
	 * @return lo splitPane creato
	 */
	default JSplitPane createSplitPane() {
		return new JSplitPane();
	}

	/**
	 * Crea una JList vuota.
	 * 
	 * @param <E>
	 *            il tipo degli elementi della lista
	 * @return la JList creata
	 */
	default <E> JList<E> createJList() {
		return new JList<>();
	}

	/**
	 * Crea un JComboBox inizializzato con l'array di elementi passato.
	 * 
	 * @param comboBoxElements
	 *            gli elementi del comboBox
	 * @param <E>
	 *            il tipo degli elementi del comboBox
	 * @return il comboBox creato
	 */
	default <E> JComboBox<E> createComboBox(E[] comboBoxElements) {
		return new JComboBox<>(comboBoxElements);
	}

	/**
	 * Classe standard formata dalle sole implementazioni di default dei metodi.
	 * 
	 * @author Enrico
	 *
	 */
	class Standard implements GUIFactory {

	}
}
