package view;

import java.awt.Component;

import javax.swing.*;

/**
 * Interfaccia che descrive una factory per componenti della GUI.
 * 
 * @author Enrico
 *
 */
public interface GUIFactory {

	default JButton createButton(final String name) {
		return new JButton(name);
	}

	default JLabel createLabel(final String text) {
		return new JLabel(text);
	}

	default JPanel createPanel(Component... components) {
		final JPanel toReturn = new JPanel();
		for (final Component c : components) {
			toReturn.add(c);
		}
		return toReturn;
	}

	default JTextField createTextField() {
		return new JTextField(10);
	}

	default JTextArea createTextArea() {
		return new JTextArea(1, 10);
	}

	default JScrollPane createScrollPane() {
		return new JScrollPane();
	}

	default JSplitPane createSplitPane() {
		return new JSplitPane();
	}

	default <E> JList<E> createJList() {
		return new JList<>();
	}
	
	default <E> JComboBox<E> createComboBox(E[] comboBoxElements){
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
