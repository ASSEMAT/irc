package com.cfranc.irc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cfranc.irc.IfClientServerProtocol;
import com.sun.security.ntlm.Client;

public class ClientConnectThread extends Thread implements IfClientServerProtocol {
	StyledDocument model=null;
	
	static DefaultTreeModel clientListModel;
	
	private boolean canStop=false;
	private ServerSocket server = null;
	
	private void printMsg(String msg){
		try {
			if(model!=null){
				model.insertString(model.getLength(), msg+"\n", null);
			}
			System.out.println(msg);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void RemoveNode (String nodeName){
	
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)clientListModel.getRoot();
		for (int i = 0; i < root.getChildCount(); i++) {
			if (root.getChildAt(i).toString().equals(nodeName)) {
				root.remove(i);
				break;
			}
		}
		clientListModel.reload();
	}

	
	public ClientConnectThread(int port, StyledDocument model, DefaultTreeModel clientListModel) {
		try {
			this.model=model;
			this.clientListModel=clientListModel;
			printMsg("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
			printMsg("Server started: " + server);
		} 
		catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
	
	@Override
	public void run() {
		while(!canStop){
			printMsg("Waiting for a client ...");
			Socket socket;
			try {
				socket = server.accept();
				printMsg("Client accepted: " + socket);
				
				// Accept new client or close the socket
				acceptClient(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void acceptClient(Socket socket) throws IOException, InterruptedException {
		// Read user login and pwd
		DataInputStream dis=new DataInputStream(socket.getInputStream());
		DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
		dos.writeUTF(LOGIN_PWD);
		
		while(dis.available()<=0){
			Thread.sleep(100);
		}
		String reponse=dis.readUTF();
		String[] userPwd=reponse.split(SEPARATOR);
		System.out.println("acceptClient " + userPwd);
		System.out.println("accept client reponse " + reponse);
		System.out.println("accept client userPwd " + userPwd);
		String login=userPwd[1];
		String pseudo=userPwd[2];
		String pwd=userPwd[3];
		String picture=userPwd[4];
		System.out.println(login);
		System.out.println(pseudo);
		System.out.println(pwd);
		System.out.println(picture);
		
		User newUser=new User(login, pseudo, pwd, picture);
		System.out.println(newUser.getLogin());
		System.out.println(newUser.getPseudo());
		System.out.println(newUser.getPwd());
		System.out.println(newUser.getPic());
		boolean isUserOK=authentication(newUser);
		if(isUserOK){
			
			ServerToClientThread client=new ServerToClientThread(newUser, socket);
			dos.writeUTF(OK);

			// Add user
			if(BroadcastThread.addClient(newUser, client)){
				client.start();		
								
				DefaultMutableTreeNode nom = new DefaultMutableTreeNode(newUser.getLogin());
				DefaultMutableTreeNode pseudoNode = new DefaultMutableTreeNode(newUser.getPseudo());
				((DefaultMutableTreeNode)clientListModel.getRoot()).add(nom);
				nom.add(pseudoNode);
				clientListModel.reload();
			}
		}
		else{
			System.out.println("socket.close()");
			dos.writeUTF(KO);
			dos.close();
			socket.close();
		}
	}
	
	private boolean authentication(User newUser){
		return BroadcastThread.accept(newUser);
	}

	
	public void open() throws IOException {
	}
	
	public void close() throws IOException {
		System.err.println("server:close()");
		if (server != null)
			server.close();
	}
}
