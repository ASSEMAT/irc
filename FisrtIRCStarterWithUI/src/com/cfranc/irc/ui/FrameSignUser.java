package com.cfranc.irc.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.JTextArea;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.cfranc.irc.server.UserGestion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameSignUser extends JFrame {

	private JPanel contentPane;
	private JTextField signLogin;
	private JTextField signPseudo;
	private JLabel lblNewLabel;
	private JLabel lblPseudo;
	private JLabel lblPrenom;
	private JTextField signPrenom;
	private JLabel lblMotDePasse;
	private JTextField signPassword;
	private JLabel lblAvatar_1;
	private JButton btnParcourir;
	private JPanel panelPicture;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameSignUser frame = new FrameSignUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameSignUser() {
		setPreferredSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(400, 500));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel1 = new JPanel();
		panel1.setSize(new Dimension(400, 500));
		panel1.setPreferredSize(new Dimension(400, 500));
		contentPane.add(panel1, BorderLayout.CENTER);
		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[] {34, 30, 159, 30};
		gbl_panel1.rowHeights = new int[] {30, 0, 0, 0, 0, 30, 30, 30, 30, 30};
		gbl_panel1.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		gbl_panel1.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		panel1.setLayout(gbl_panel1);
		
		lblNewLabel = new JLabel("Login (name)");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		panel1.add(lblNewLabel, gbc_lblNewLabel);
		
		signLogin = new JTextField();
		GridBagConstraints gbc_signLogin = new GridBagConstraints();
		gbc_signLogin.insets = new Insets(0, 0, 5, 5);
		gbc_signLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_signLogin.gridx = 2;
		gbc_signLogin.gridy = 4;
		panel1.add(signLogin, gbc_signLogin);
		signLogin.setColumns(10);
		
		lblPseudo = new JLabel("Pseudo");
		GridBagConstraints gbc_lblPseudo = new GridBagConstraints();
		gbc_lblPseudo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPseudo.anchor = GridBagConstraints.WEST;
		gbc_lblPseudo.gridx = 1;
		gbc_lblPseudo.gridy = 5;
		panel1.add(lblPseudo, gbc_lblPseudo);
		
		signPseudo = new JTextField();
		GridBagConstraints gbc_signPseudo = new GridBagConstraints();
		gbc_signPseudo.insets = new Insets(0, 0, 5, 5);
		gbc_signPseudo.fill = GridBagConstraints.HORIZONTAL;
		gbc_signPseudo.gridx = 2;
		gbc_signPseudo.gridy = 5;
		panel1.add(signPseudo, gbc_signPseudo);
		signPseudo.setColumns(10);
		
		lblPrenom = new JLabel("Prenom");
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.anchor = GridBagConstraints.WEST;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 1;
		gbc_lblPrenom.gridy = 6;
		panel1.add(lblPrenom, gbc_lblPrenom);
		
		signPrenom = new JTextField();
		GridBagConstraints gbc_signPrenom = new GridBagConstraints();
		gbc_signPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_signPrenom.fill = GridBagConstraints.HORIZONTAL;
		gbc_signPrenom.gridx = 2;
		gbc_signPrenom.gridy = 6;
		panel1.add(signPrenom, gbc_signPrenom);
		signPrenom.setColumns(10);
		
		lblMotDePasse = new JLabel("Mot de Passe");
		GridBagConstraints gbc_lblMotDePasse = new GridBagConstraints();
		gbc_lblMotDePasse.anchor = GridBagConstraints.WEST;
		gbc_lblMotDePasse.insets = new Insets(0, 0, 5, 5);
		gbc_lblMotDePasse.gridx = 1;
		gbc_lblMotDePasse.gridy = 7;
		panel1.add(lblMotDePasse, gbc_lblMotDePasse);
		
		signPassword = new JTextField();
		GridBagConstraints gbc_signPassword = new GridBagConstraints();
		gbc_signPassword.insets = new Insets(0, 0, 5, 5);
		gbc_signPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_signPassword.gridx = 2;
		gbc_signPassword.gridy = 7;
		panel1.add(signPassword, gbc_signPassword);
		signPassword.setColumns(10);
		
		lblAvatar_1 = new JLabel("Avatar");
		GridBagConstraints gbc_lblAvatar_1 = new GridBagConstraints();
		gbc_lblAvatar_1.anchor = GridBagConstraints.WEST;
		gbc_lblAvatar_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar_1.gridx = 1;
		gbc_lblAvatar_1.gridy = 9;
		panel1.add(lblAvatar_1, gbc_lblAvatar_1);
		
		panelPicture = new JPanel();
		panelPicture.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GridBagConstraints gbc_panelPicture = new GridBagConstraints();
		gbc_panelPicture.gridheight = 4;
		gbc_panelPicture.insets = new Insets(0, 0, 5, 5);
		gbc_panelPicture.fill = GridBagConstraints.BOTH;
		gbc_panelPicture.gridx = 2;
		gbc_panelPicture.gridy = 9;
		panel1.add(panelPicture, gbc_panelPicture);
		
		btnParcourir = new JButton("Parcourir");
		GridBagConstraints gbc_btnParcourir = new GridBagConstraints();
		gbc_btnParcourir.insets = new Insets(0, 0, 5, 0);
		gbc_btnParcourir.gridx = 3;
		gbc_btnParcourir.gridy = 9;
		panel1.add(btnParcourir, gbc_btnParcourir);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton signOk = new JButton("OK");
		signOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			if ((signLogin.getText().equals("")) || (signPrenom.getText().equals("")) || (signPseudo.getText().equals("")) || (signPassword.getText().equals("")))
			{
				JOptionPane.showMessageDialog(null,"Merci de compl�ter votre saisie.");
				return;
			}
			signUser();
			
			}
		});
		panel.add(signOk);
		
		JButton signCancel = new JButton("Annuler");
		signCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				closeSign();		
				
			}
		});
		panel.add(signCancel);
	}
	
	private void signUser() {
		
		String nom=signLogin.getText();
		String prenom=signPrenom.getText();
		String pseudo=signPseudo.getText();
		String pwd=signPassword.getText();
		String pic=panelPicture.getComponents().toString();
		
		UserGestion u=new UserGestion(nom, pseudo, pwd, prenom, pic);
		
		u.addUser();
		
	}
	
	private void closeSign()
	{
		this.dispose();
	}

}