package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.Controller;

/**
 * Classe astratta che definisce una view con barra di ricerca, tasto di
 * ricerca, tasto nuovo elemento.
 * 
 * @author Enrico
 *
 */
public abstract class AbstractSearchListView<E> extends AbstractViewFrame
		implements RefreshView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1419508065951576119L;
	private static final String BTN_SEARCH_TEXT = "Creca";
	private static final String BTN_NEW_ELEM_TEXT = "Nuovo";
	private static final String BTN_ACTION_TEXT = "Azione sulla selezione";

	private final JButton btnSearch = getGUIFactory().createButton(
			BTN_SEARCH_TEXT);
	private final JButton btnNewElement = getGUIFactory().createButton(
			BTN_NEW_ELEM_TEXT);
	private final GridBagConstraints eastPanelInternalCnst = new GridBagConstraints();
	private final JTextField toSearch = getGUIFactory().createTextField();
	private final JButton btnAction = getGUIFactory().createButton(
			BTN_ACTION_TEXT);
	private final JScrollPane centerScroll = getGUIFactory().createScrollPane();
	private final JPanel scrollHeaderPanel = getGUIFactory().createPanel();
	private final JPanel eastPanelInternal = getGUIFactory().createPanel();
	private final JPanel eastPanel = getGUIFactory().createPanel(
			eastPanelInternal);
	private final JPanel northPanel = getGUIFactory().createPanel(toSearch,
			btnSearch);

	private final JList<E> jList = getGUIFactory().createJList();
	private final DefaultListModel<E> model = new DefaultListModel<>();

	protected AbstractSearchListView(final String frameName,
			final ViewController v, final Controller c) {
		super(frameName, v, c);

		getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout());

		getContentPane().add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new FlowLayout());

		eastPanelInternal.setLayout(new GridBagLayout());

		eastPanelInternalCnst.fill = GridBagConstraints.HORIZONTAL;
		eastPanelInternalCnst.anchor = GridBagConstraints.PAGE_START;
		eastPanelInternalCnst.insets = new Insets(2, 2, 2, 2);
		eastPanelInternalCnst.gridx = 0;
		eastPanelInternalCnst.gridy = 0;
		eastPanelInternal.add(btnNewElement, eastPanelInternalCnst);

		eastPanelInternalCnst.gridy++;
		eastPanelInternal.add(btnAction, eastPanelInternalCnst);

		getContentPane().add(centerScroll);
		centerScroll.setColumnHeaderView(scrollHeaderPanel);
		scrollHeaderPanel.setLayout(new FlowLayout());
		scrollHeaderPanel.setBorder(new LineBorder(Color.BLACK));

		jList.setCellRenderer(new MultiLineRender<>());
		jList.setModel(model);
		jList.setBackground(getBackground());
		jList.addListSelectionListener(e -> listSelectionHandler());
		centerScroll.setViewportView(getGUIFactory().createPanel(jList));

		btnAction.setEnabled(false);
		btnAction.addActionListener(e -> actionHandler());
		btnSearch.addActionListener(e -> searchHandler());
		btnNewElement.addActionListener(e -> addHandler());
	}

	/**
	 * 
	 * @return il pannello nord
	 */
	protected JPanel getNorthPanel() {
		return this.northPanel;
	}

	/**
	 * 
	 * @return il pannello est
	 */
	protected JPanel getEastPanel() {
		return this.eastPanel;
	}

	/**
	 * 
	 * @return il pannello est interno, contenente i pulsanti
	 */
	protected JPanel getEastButtonPanel() {
		return this.eastPanelInternal;
	}

	/**
	 * 
	 * @return lo ScrollPane all'intero del centerPanel
	 */
	protected JScrollPane getCenterScroll() {
		return this.centerScroll;
	}

	/**
	 * 
	 * @return l'headerPanel dello scrollPane
	 */
	protected JPanel getScrollHeaderPanel() {
		return this.scrollHeaderPanel;
	}

	/**
	 * 
	 * @return il bottone di ricerca
	 */
	protected JButton getSearchButton() {
		return this.btnSearch;
	}

	/**
	 * 
	 * @return il bottone nuovo elemento
	 */
	protected JButton getNewElemButton() {
		return this.btnNewElement;
	}

	/**
	 * 
	 * @return GridBagConstraints del pannello est
	 */
	protected GridBagConstraints getEastPanelConstraints() {
		return this.eastPanelInternalCnst;
	}

	/**
	 * 
	 * @return il campo di testo per la ricerca
	 */
	protected JTextField getSearchField() {
		return this.toSearch;
	}

	/**
	 * 
	 * @return il bottone di azione sulla selezione nella lista
	 */
	protected JButton getActionButton() {
		return this.btnAction;
	}

	/**
	 * 
	 * @return la lista degli elementi situata nel pannello centrale
	 */
	protected JList<E> getList() {
		return this.jList;
	}

	/**
	 * 
	 * @return il modello da cui la lista prende i dati da mostrare
	 */
	protected DefaultListModel<E> getListModel() {
		return this.model;
	}

	/**
	 * Handler selezione elemento dalla lista.
	 */
	protected void listSelectionHandler() {
		if (jList.getSelectedValuesList().size() >= 1) {
			btnAction.setEnabled(true);
		} else {
			btnAction.setEnabled(false);
		}
	}

	@Override
	protected void quittingHandler() {
		final Saver s = new Saver(getController());
		s.start();
		this.toSearch.setText(null);
		this.dispose();
	}

	/**
	 * Handler per la ricerca.
	 */
	protected abstract void searchHandler();

	/**
	 * Handler per l'inserimento.
	 */
	protected abstract void addHandler();

	/**
	 * Handler pressione pulsante di azione.
	 */
	protected abstract void actionHandler();

	@Override
	public abstract void refresh();
}
