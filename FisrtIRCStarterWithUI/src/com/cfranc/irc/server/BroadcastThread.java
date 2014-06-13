package com.cfranc.irc.server;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.cfranc.irc.IfClientServerProtocol;
import com.cfranc.irc.ui.SimpleChatFrameServer;

public class BroadcastThread extends Thread implements IfClientServerProtocol{
	
	public static HashMap<User, ServerToClientThread> clientTreadsMap=new HashMap<User, ServerToClientThread>();
	static{
		Collections.synchronizedMap(clientTreadsMap);
	}
	
	public static boolean addClient(User user, ServerToClientThread serverToClientThread){
		boolean res=true;
		if(clientTreadsMap.containsKey(user)){
			res=false;
		}
		else{
			clientTreadsMap.put(user, serverToClientThread);
			Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
			Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();
			
			// recup�ration de la liste des clients depuis le Hash MAP
			Collection<User> users=clientTreadsMap.keySet();
			
			// Envoie � tous les utilisateurs de l'arriv�e d'un nouvel arrivant
			while (receiverClientThreadIterator.hasNext()) {
				ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
				clientThread.post(ADD+user.getPseudo());
			}
			
			// Envoie � l'arrivant de la liste des utilisateurs d�j� connect�s.
			for (User userExistant  : users) {
				if (userExistant != user) {
					serverToClientThread.post(ADD+userExistant.getPseudo());
				}
			}
		}
		return res;
	}

	public static void sendMessage(User sender, String msg){
		Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
		Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();
		while (receiverClientThreadIterator.hasNext()) {
			ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
			clientThread.post("#"+sender.getPseudo()+"#"+msg);
			System.out.println("sendMessage : "+"#"+sender.getLogin()+"#"+msg);
		}
	}
	
	public static void removeClient(User user){
		clientTreadsMap.remove(user);
		
		Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
		Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();

		// Envoie � tous les utilisateurs du d�part d'un client
		while (receiverClientThreadIterator.hasNext()) {
			ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
			clientThread.post(DEL+user.getPseudo());
		}
		
		// c'est ici qu'il faudra coder la suppression du noeud dans le mod�le
		ClientConnectThread.RemoveNode(user.getLogin());
		
	}
	
	
	public static boolean accept(User user){
		boolean res=true;
		if(clientTreadsMap.containsKey(user)){
			res= false;
		}
		return res;
	}
}
