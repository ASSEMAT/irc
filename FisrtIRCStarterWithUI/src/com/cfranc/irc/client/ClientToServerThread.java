package com.cfranc.irc.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import com.cfranc.irc.IfClientServerProtocol;
import com.cfranc.irc.server.User;
import com.cfranc.irc.ui.SimpleChatClientApp;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

public class ClientToServerThread extends Thread implements IfSenderModel{
	private Socket socket = null;
	private DataOutputStream streamOut = null;
	private DataInputStream streamIn = null;
	private BufferedReader console = null;
	public String login,pwd, pseudo, picture, usrToSend;

	static DefaultListModel<User> clientListModel;
	StyledDocument documentModel;
	
	public ClientToServerThread(StyledDocument documentModel, DefaultListModel<User> clientListModel, Socket socket, String login, String pwd, String pseudo, String picture) {
		super();
		this.documentModel=documentModel;
		this.clientListModel=clientListModel;
		this.socket = socket;
		this.login=login;
		this.pwd=pwd;
		this.pseudo=pseudo;
		this.picture=picture;
	}


	
	public void open() throws IOException {
		console = new BufferedReader(new InputStreamReader(System.in));
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
	}
	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
	
	public void receiveMessage(String user, String line){
		Style styleBI = ((StyledDocument)documentModel).getStyle(SimpleChatClientApp.BOLD_ITALIC);
        Style styleGP = ((StyledDocument)documentModel).getStyle(SimpleChatClientApp.GRAY_PLAIN);
        receiveMessage(user, line, styleBI, styleGP);
	}
	
	public void receiveMessage(String user, String line, Style styleBI,
			Style styleGP) {
        try {        	
			documentModel.insertString(documentModel.getLength(), user+" : ", styleBI);
			documentModel.insertString(documentModel.getLength(), line+"\n", styleGP);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				        	
	}
    
	void readMsg() throws IOException{
		String line = streamIn.readUTF();
		System.out.println("line " + line);
		
		if(line.startsWith(IfClientServerProtocol.ADD)){
			String newUser=line.substring(IfClientServerProtocol.ADD.length());
			String[] result =newUser.split(IfClientServerProtocol.SEPARATOR);
			String pseudo = result[0];
			System.out.println(result[0]);
			String pic = result[1];
			User user1=new User("","", pseudo,"",pic);
			
			if(!clientListModel.contains(user1.getPseudo())){
				clientListModel.addElement(user1);
				receiveMessage(pseudo, " entre dans le salon...");
			}
		}
		else if(line.startsWith(IfClientServerProtocol.DEL)){
			String delUser=line.substring(IfClientServerProtocol.DEL.length());
			
			for (int i=0; i < clientListModel.getSize() ; i++) {
				System.out.println(clientListModel.getElementAt(i).getPseudo() +  " " +  delUser);
				if (clientListModel.getElementAt(i).getPseudo().equals(delUser)) {
					System.out.println("je passe dans supprimer");
					clientListModel.removeElementAt(i);
					receiveMessage(delUser, " quitte le salon !");
					break;
				}
			}
		}
		else{
			String[] userMsg=line.split(IfClientServerProtocol.SEPARATOR);
			String user=userMsg[1];
			receiveMessage(user, userMsg[2]);
		}
	}
	
	String msgToSend=null;
	
	/* (non-Javadoc)
	 * @see com.cfranc.irc.client.IfSenderModel#setMsgToSend(java.lang.String)
	 */
	@Override
	public void setMsgToSend(String msgToSend, String usrToSend) {
		this.msgToSend = msgToSend;
		this.usrToSend = usrToSend;
	}

	private boolean sendMsg() throws IOException{
		boolean res=false;
		if(msgToSend!=null){
			streamOut.writeUTF("#"+pseudo+"#"+msgToSend+"#"+usrToSend+"#picture");
			msgToSend=null;
		    streamOut.flush();
		    res=true;
		}
		return res;
	}
	
	public void quitServer() throws IOException{
		streamOut.writeUTF(IfClientServerProtocol.DEL+pseudo);
		streamOut.flush();
		done=true;
	}
	
	boolean done;
	@Override
	public void run() {
		try {
			open();
			done = !authentification();
			while (!done) {
				try {
					if(streamIn.available()>0){
						readMsg();
					}

					if(!sendMsg()){
						Thread.sleep(100);
					}
				} 
				catch (IOException | InterruptedException ioe) {
					ioe.printStackTrace();
					done = true;
				}
			}
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean authentification() {
		boolean res=false;
		String loginPwdQ;
		try {
			while(streamIn.available()<=0){
				Thread.sleep(100);
			}
			loginPwdQ = streamIn.readUTF();
			if(loginPwdQ.equals(IfClientServerProtocol.LOGIN_PWD)){
				streamOut.writeUTF(IfClientServerProtocol.SEPARATOR+this.login+IfClientServerProtocol.SEPARATOR+this.pseudo+IfClientServerProtocol.SEPARATOR+this.pwd+IfClientServerProtocol.SEPARATOR+this.picture);
			}
			while(streamIn.available()<=0){
				Thread.sleep(100);
			}
			String acq=streamIn.readUTF();
			if(acq.equals(IfClientServerProtocol.OK)){
				res=true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res=false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;		
	}
	
}

