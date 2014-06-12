package com.cfranc.irc.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class SimpleChatFrameServer extends JFrame{

	public StyledDocument model=null;
	public DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Root");
	public DefaultTreeModel clientListModel = new DefaultTreeModel(racine);


	public SimpleChatFrameServer(int port, StyledDocument model,  DefaultTreeModel clientListModel) {
		super("ISM - IRC Server Manager");
		this.model=model;
		this.clientListModel=clientListModel;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 702, 339);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JTextPane textPane = new JTextPane(model);
		scrollPane.setViewportView(textPane);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());

			}
		});

		JScrollPane scrollPaneList = new JScrollPane();
		getContentPane().add(scrollPaneList, BorderLayout.WEST);

		final JLabel statusBar=new JLabel("");
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		final JTree list = new JTree(clientListModel);
	    list.setRootVisible(false);
		list.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {

				DefaultMutableTreeNode node = (DefaultMutableTreeNode)list.getLastSelectedPathComponent();
				if (node == null)
				    //Nothing is selected.  
				    return;
				String clientSelected=node.getUserObject().toString();
				statusBar.setText(clientSelected);
			}

		});
		list.setMinimumSize(new Dimension(200,0));
		scrollPaneList.setViewportView(list);
		scrollPaneList.setPreferredSize(new Dimension(300,300));
	}	
	
	public DefaultMutableTreeNode getRoot(){
		return racine;
	}
}