package com.cfranc.irc.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.cfranc.irc.Smiley;
import com.cfranc.irc.client.ClientToServerThread;
import com.cfranc.irc.server.User;
import com.cfranc.irc.server.UserGestion;

public class SimpleChatClientApp implements ActionListener, MouseListener {
	static String[] ConnectOptionNames = { "Connect", "Sign in" };
	static String ConnectTitle = "Connection Information";
	Socket socketClientServer;
	int serverPort;
	String serverName;
	String clientName;
	String clientPwd;
	String clientPseudo;
	String clientPicture;
	private connectionDialog frameDialog;
	private SimpleChatFrameClient frame;
	private FrameSignUser frameSignUser;
	private boolean connexionOk = false;

	public StyledDocument documentModel = new DefaultStyledDocument();
	DefaultListModel<User> clientListModel = new DefaultListModel<User>();

	public static final String BOLD_ITALIC = "BoldItalic";
	public static final String GRAY_PLAIN = "Gray";

	public static DefaultStyledDocument defaultDocumentModel() {
		DefaultStyledDocument res = new DefaultStyledDocument();

		Style styleDefault = (Style) res.getStyle(StyleContext.DEFAULT_STYLE);

		res.addStyle(BOLD_ITALIC, styleDefault);
		Style styleBI = res.getStyle(BOLD_ITALIC);
		StyleConstants.setBold(styleBI, true);
		StyleConstants.setItalic(styleBI, true);
		StyleConstants.setForeground(styleBI, Color.black);

		res.addStyle(GRAY_PLAIN, styleDefault);
		Style styleGP = res.getStyle(GRAY_PLAIN);
		StyleConstants.setBold(styleGP, false);
		StyleConstants.setItalic(styleGP, false);
		StyleConstants.setForeground(styleGP, Color.lightGray);

		return res;
	}

	private static ClientToServerThread clientToServerThread;

	public SimpleChatClientApp() {

	}

	public void displayClient() {

		// Init GUI
		frame = new SimpleChatFrameClient(clientToServerThread,
				clientListModel, documentModel, clientPseudo, this);
		frame.setTitle(this.frame.getTitle() + " : " + clientPseudo
				+ " connected to " + serverName + ":" + serverPort);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		((JFrame) this.frame).setVisible(true);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				quitApp(SimpleChatClientApp.this);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void hideClient() {

		// Init GUI
		((JFrame) this.frame).setVisible(false);
	}

	void displayConnectionDialog() {
		// ConnectionPanel connectionPanel=new ConnectionPanel();
		frameDialog = new connectionDialog(this);
		frameDialog.setModal(true);
		frameDialog.setVisible(true);

	}

	private void connectClient() {
		System.out.println("Establishing connection. Please wait ...");
		try {
			socketClientServer = new Socket(this.serverName, this.serverPort);
			// Start connection services
			clientToServerThread = new ClientToServerThread(documentModel,
					clientListModel, socketClientServer, clientName, clientPwd,
					clientPseudo, clientPicture);
			clientToServerThread.start();

			System.out.println("Connected: " + socketClientServer);
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final SimpleChatClientApp app = new SimpleChatClientApp();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app.displayConnectionDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		Scanner sc = new Scanner(System.in);
		String line = "";
		while (!line.equals(".bye")) {
			line = sc.nextLine();
		}
		quitApp(app);
	}

	private static void quitApp(final SimpleChatClientApp app) {
		try {
			app.clientToServerThread.quitServer();
			app.socketClientServer.close();
			app.hideClient();
			System.out.println("SimpleChatClientApp : ferm�e");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (frameDialog.creatCompte.equals(e.getSource())) {
			serverPort = Integer.parseInt(frameDialog.cp.getServerPortField()
					.getText());
			serverName = frameDialog.cp.getServerField().getText();
			frameDialog.setVisible(false);

			frameSignUser = new FrameSignUser(this);
			frameSignUser.setModal(true);
			frameSignUser.setVisible(true);
			if (connexionOk) {
				frameDialog.dispose();
				clientName = frameSignUser.signLogin.getText();
				clientPwd = frameSignUser.signPassword.getText();
				clientPseudo = frameSignUser.signLogin.getText();
				clientPicture = frameSignUser.chemin.getText();
				frameSignUser.dispose();

				connectClient();
				displayClient();
			} else {
				frameDialog.setVisible(true);
			}
		}
		if (frameDialog.ConnOK.equals(e.getSource())) {
			UserGestion usr = new UserGestion(frameDialog.cp.getUserNameField()
					.getText(), frameDialog.cp.getPasswordField().getText());
			if (usr.existeUser()) {

				clientName = frameDialog.cp.getUserNameField().getText();
				clientPwd = frameDialog.cp.getPasswordField().getText();
				clientPseudo = frameDialog.cp.getUserNameField().getText();
				;
				clientPicture = usr.getPicture();
				serverPort = Integer.parseInt(frameDialog.cp
						.getServerPortField().getText());
				serverName = frameDialog.cp.getServerField().getText();

				frameDialog.dispose();
				connectClient();
				displayClient();
			} else {
				JOptionPane
						.showMessageDialog(null,
								"cet utilisateur n'existe pas ou le mot de passe n'est pas correct.");
			}
		}

		if (frameSignUser != null) {
			if (frameSignUser.signOk.equals(e.getSource())) {
				if ((frameSignUser.signLogin.getText().equals(""))
						|| (frameSignUser.signPrenom.getText().equals(""))
						|| (frameSignUser.signPseudo.getText().equals(""))
						|| (frameSignUser.signPassword.getText().equals(""))
						|| (frameSignUser.chemin.getText().equals(""))) {
					JOptionPane.showMessageDialog(null,
							"Merci de compl�ter votre saisie.");
				} else {
					UserGestion u = new UserGestion(
							frameSignUser.signLogin.getText(),
							frameSignUser.signPseudo.getText(),
							frameSignUser.signPassword.getText(),
							frameSignUser.signPrenom.getText(),
							frameSignUser.chemin.getText());
					System.out.println("chemin : "
							+ frameSignUser.chemin.getText());
					if (u.addUser()) {
						frameSignUser.dispose();
						connexionOk = true;
					}
				}
			}
		}

		if (frameSignUser != null) {
			if (frameSignUser.signCancel.equals(e.getSource())) {
				frameSignUser.dispose();
				connexionOk = false;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (frame != null) {
			// TODO Auto-generated method stub
			System.out.println(e.getSource());
			System.out.println(frame.getEmoticon1());

			if (frame.getEmoticon1().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.BADDAY);
			}

			if (frame.getEmoticon1().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.BADDAY);
			}
			if (frame.getEmoticon2().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.CRY);
			}
			if (frame.getEmoticon3().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.HAPPY);
			}
			if (frame.getEmoticon4().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.LOL);
			}
			if (frame.getEmoticon5().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.SAD);
			}
			if (frame.getEmoticon6().equals(e.getSource())) {
				frame.setTextField(frame.getTextField().getText() + " "
						+ Smiley.HELLO);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
