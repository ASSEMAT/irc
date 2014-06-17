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
		
		System.out.println("addClient : " + user.getLogin());
		System.out.println("addClient : " + user.getPseudo());
		System.out.println("addClient : " + user.getPrenom());
		System.out.println("addClient : " + user.getPwd());
		System.out.println("addClient : " + user.getPic());
		if(clientTreadsMap.containsKey(user)){
			res=false;
		}
		else{
			clientTreadsMap.put(user, serverToClientThread);
			Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
			Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();
			
			// recupération de la liste des clients depuis le Hash MAP
			Collection<User> users=clientTreadsMap.keySet();
			
			// Envoie à tous les utilisateurs de l'arrivée d'un nouvel arrivant
			while (receiverClientThreadIterator.hasNext()) {
				ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
				clientThread.post(ADD+user.getPseudo()+ SEPARATOR + user.getPic());
			}
			
			// Envoie à l'arrivant de la liste des utilisateurs déjà connectés.
			for (User userExistant  : users) {
				if (userExistant != user) {
					serverToClientThread.post(ADD+userExistant.getPseudo()+ SEPARATOR + userExistant.getPic());
				}
			}
		}
		return res;
	}

	public static void sendMessage(User sender, String msg, String usr, String ico){
		Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
		Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();
		
		if (usr.equals(""))
		{
			System.out.println("message pour tous");
			while (receiverClientThreadIterator.hasNext()) {
				ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
				clientThread.post("#"+sender.getPseudo()+"#"+msg+"#"+usr+"#"+ico);
				System.out.println("sendMessage : "+"#"+sender.getLogin()+"#"+msg+"#"+usr+"#"+ico);
			}
		} else
		{
			System.out.println("message privé pour " + usr);
			Collection<User> users=clientTreadsMap.keySet();
			for (User user : users) {
				if (user.getPseudo().equals(usr)){
					ServerToClientThread clientThread = clientTreadsMap.get(user);
					clientThread.post("#"+sender.getPseudo()+"#private message : "+msg+"#"+usr+"#"+ico);
				}
				if (user.getPseudo().equals(sender.getPseudo())){
					ServerToClientThread clientThread = clientTreadsMap.get(user);
					clientThread.post("#"+sender.getPseudo()+"#private message : "+msg+"#"+usr+"#"+ico);
				}
				
			}
			
		}
		
		
	}
	
	public static void removeClient(User user){
		clientTreadsMap.remove(user);
		
		Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
		Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();

		// Envoie à tous les utilisateurs du départ d'un client
		while (receiverClientThreadIterator.hasNext()) {
			ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
			clientThread.post(DEL+user.getPseudo());
		}
		
		// c'est ici qu'il faudra coder la suppression du noeud dans le modèle
		ClientConnectThread.RemoveNode(user.getPseudo());
		
	}
	
	
	public static boolean accept(User user){
		boolean res=true;
		if(clientTreadsMap.containsKey(user)){
			res= false;
		}
		return res;
	}
}
