package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Implementazione concreta di un cellRender attraverso una JTextArea.
 * 
 * @author Enrico
 *
 * @param <E>
 *            il tipo dell'elemento nella cella da rappresentare
 */
public class MultiLineRender<E> extends JTextArea implements
		ListCellRenderer<E> {

	private static final long serialVersionUID = -744690964267434006L;

	@Override
	public Component getListCellRendererComponent(
			final JList<? extends E> list, final E value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {

		if (isSelected) {
			this.setBackground(list.getSelectionBackground());
			this.setForeground(list.getSelectionForeground());
			this.setBorder(new LineBorder(Color.decode("0x3B5BFF")));
		} else {
			this.setBackground(list.getBackground());
			this.setForeground(list.getForeground());
			this.setBorder(new EmptyBorder(1, 1, 1, 1));
		}

		setTabSize(4);
		setText(value.toString());
		return this;
	}

}
