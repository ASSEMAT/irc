package com.cfranc.irc.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cfranc.irc.server.UserGestion;

public class connectionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private UserGestion usergestion ;
	public ConnectionPanel cp;
	public JButton ConnOK;
	public JButton creatCompte;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			connectionDialog dialog = new connectionDialog(new SimpleChatClientApp());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public connectionDialog(SimpleChatClientApp listener) {
		
		setBounds(100, 100, 262, 300);
		getContentPane().setLayout(new BorderLayout());
		
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				ConnOK = new JButton("Connexion");
				ConnOK.addActionListener(listener);
				buttonPane.add(ConnOK);
				getRootPane().setDefaultButton(ConnOK);

			}
			{
				creatCompte = new JButton("creation compte");
				creatCompte.addActionListener(listener);
				buttonPane.add(creatCompte);
			}
		}
		cp = new ConnectionPanel();
		getContentPane().add(cp,BorderLayout.CENTER);
	}

}
