package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import remote.Message;
import remote.User;
import remote.clientInterface;


public class DataServer implements Serializable{



	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0xC0FFEE;

	private static DataServer server = null;
	
	private List<Message> recentMessages;
	private List<User> registeredUsers;
	private List<String> logs; // ancien messages sérializés
	private static final String logPath= "./logs";
	private static final int logSize = 50;
	private static final int sizeToSave = 3;
	private static final String serverSave = "./server.save";
	
	private long lastMessageID = 0;
	
	private DataServer(){
		recentMessages = new ArrayList<Message>();
		registeredUsers = new ArrayList<User>();
		logs = new ArrayList<String>();
	}
	
	@SuppressWarnings("unchecked")
	private List<Message> loadLog(String logRef){
		
		String logName = logPath + logRef;
		List<Message> res = null;
		
		try {
			
			FileInputStream fis = new FileInputStream(logPath + logName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			res = (List<Message>)ois.readObject();
			
			System.out.println("log loaded : " + logRef);
			
			return res;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("log loading failed");
		
		return res;
	}
	
	private void saveNewLog(){
		
		String logName = recentMessages.get(recentMessages.size()-1).getDate().toString();
		
		try {
			
			FileOutputStream fos = new FileOutputStream(logPath + logName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(recentMessages);
			logs.add(logName);
			recentMessages.clear();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("log saved : "+logName);
		
		saveServer();
	}
	
	private static void loadServer(){
		
		try {
			
			FileInputStream fis = new FileInputStream(serverSave);
			ObjectInputStream ois = new ObjectInputStream(fis);
			DataServer temp = (DataServer)ois.readObject();
			if(temp != null){
				server = temp;
				
				System.out.println("server loaded");
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		server.saveServer();

	}
	
	private void saveServer(){
		
		try {
			
			FileOutputStream fos;
			fos = new FileOutputStream(serverSave);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			
			System.out.println("server saved : " + serverSave);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private int getMessageIndexFromID(List<Message> mesList, long id){
		
		for(int i = 0; i<mesList.size(); ++i){
			if(mesList.get(i).getID() == id){
				return i;
			}
		}
		
		return -1;
	}
	
	
	public static DataServer getServer(){
		
		if(server == null){	
			server = new DataServer();
			loadServer();
		}
		
		System.out.println("server started");
		
		return server;
	}
	
	public boolean isValidLogin(String pseudo, String passw){
		
		System.out.println("Login "+pseudo+" pass "+passw + " requested");
		User us = retrieveUser(pseudo);
		if(us == null){
			return false;
		}
		boolean res = us.getPassword().equals(passw);
		
		System.out.println("sucess : "+ res);
		
		return res;
		
	}
	
	public boolean registerUser(String pseudo, String passw){
		
		User us = retrieveUser(pseudo);
		if(us != null){
			return false;
		}
		
		us = new User(pseudo, passw);
		
		registeredUsers.add(us);
		
		saveServer();
		
		return true;
	}
	 
	public List<String> getRegisteredUsers(){
		
		List<String> regUsers = new ArrayList<String>();
		
		for(User us : registeredUsers){
			regUsers.add(us.getName());
		}
		
		System.out.println("get users requested");
		
		return regUsers;
	}
	
	public List<String> getHastags(){
		
		List<String> hashTags = new ArrayList<String>();
		
		for(Message mes : recentMessages){
			for(String hashT : mes.getHashtags()){
				if(!hashTags.contains(hashT)){
					hashTags.add(hashT);
				}
			}
		}
		
		System.out.println("get hashtags requested");
		
		return hashTags;
	}
	
	public void postMessage(String content, String author, List<String> recipient, List<String> hashTags){
		
		Message mes = new Message(++lastMessageID, author, content);
		
		for(String rec : recipient){
			mes.addRecipient(rec);
		}
		
		for(String hash : hashTags){
			mes.addHashtags(hash);
		}
		
		recentMessages.add(mes);
		
		if(recentMessages.size() >= logSize){
			saveNewLog();
		}
		
		if(recentMessages.size() >= sizeToSave){
			saveServer();
		}
		
		System.out.println("message from "+author+" posted");
	}
	
	public void relayerMessage(String user, long mesID){

		int index = getMessageIndexFromID(recentMessages, mesID);
		
		if(index != -1 ){
			recentMessages.get(index).addAuthors(user);
		} else {
			int i = 0;
			List<Message> old = null;
			while(index == -1 && i < logs.size()){
				old = loadLog(logs.get(i));
				index = getMessageIndexFromID(old, mesID);
			}
			
			if(i<logs.size() && old != null){
				old.get(index).addAuthors(user);
			} else {
				System.out.println("ERROR : message not found in database");
			}
			
		}
		
		System.out.println("message relayé");
		
	}
	
	public List<String> getInsterestOf(String user){
		
		User usr = retrieveUser(user);
		
		if(usr == null){
			System.out.println("Error, unknown user");
			return null;
		} 
		
		return usr.getInterest();
	}
	
	public void addFollowRelationship(String follower, String followed){
		User foler = retrieveUser(follower);
		User foled = retrieveUser(followed);
		
		if(foled != null && foler!= null){
			foler.addFollowed(foled);
			foled.addFollowers(foler);
		}else {
			System.out.println("Error, unknown user");
		}
		
		System.out.println("relashionship added");
	}
	
	public void addInterest(String user, String interest){
		User usr = retrieveUser(user);
		if(usr == null){
			System.out.println("Error, unknown user");
			return;
		}
		usr.addInterest(interest);
		
		System.out.println("interest added");
	}
	
	public List<Message> getMessagesFrom(String user, int nbMes){
		
		List<Message> res = new ArrayList<Message>();
		
		for(Message msg : recentMessages){
			if(msg.getAuthors().contains(user)){
				res.add(msg);
			}
		}
		
		int logInd = 0;
		while(res.size() < nbMes && logInd < logs.size()){
			
			List<Message> tempOld = loadLog(logs.get(logInd));
			
			for(Message msg : tempOld){
				if(msg.getAuthors().contains(user)){
					res.add(msg);
				}
			}
			
			++logInd;
		}
		
		System.out.println("get message from requested");
		
		return res;
	}
	
	public List<Message> getMessagesAbout(String hashtag, int nbMes){
		
		List<Message> res = new ArrayList<Message>();
		
		for(Message msg : recentMessages){
			if(msg.getHashtags().contains(hashtag)){
				res.add(msg);
			}
		}
		
		int logInd = 0;
		while(res.size() < nbMes && logInd < logs.size()){
			
			List<Message> tempOld = loadLog(logs.get(logInd));
			
			for(Message msg : tempOld){
				if(msg.getHashtags().contains(hashtag)){
					res.add(msg);
				}
			}
			
			++logInd;
		}
		
		System.out.println("get message about requested");
		
		return res;
	}
	
	public List<Message> getMessagesTo(String recipient, int nbMes){
		
		List<Message> res = new ArrayList<Message>();
		
		for(Message msg : recentMessages){
			if(msg.getRecipient().contains(recipient)){
				res.add(msg);
			}
		}
		
		int logInd = 0;
		while(res.size() < nbMes && logInd < logs.size()){
			
			List<Message> tempOld = loadLog(logs.get(logInd));
			
			for(Message msg : tempOld){
				if(msg.getRecipient().contains(recipient)){
					res.add(msg);
				}
			}
			
			++logInd;
		}
		
		
		System.out.println("get message to requested");
		
		return res;
	}
	
	public List<Message> getUserMessages(String user, int nbMessages, clientInterface cl){
		List<Message> res = new ArrayList<Message>();
		
		User usr = retrieveUser(user);
		
		if(usr == null){
			System.out.println("Error, unknown user");
			return null;
		}
		
		for (User followed : usr.getFollowed()) {
			//Ajoute les messages des personnes suivie
			res.addAll(getMessagesFrom(followed.getName(), nbMessages));
			res.addAll(getMessagesTo(followed.getName(), nbMessages));
		}
		
		for(String interest : usr.getInterest()){
			//Ajoute les messages convernant les hashtags suivis
			res.addAll(getMessagesAbout(interest, nbMessages));
		}
		
		res.addAll(getMessagesFrom(user, nbMessages));
		
		res.addAll(getMessagesTo(usr.getName(), nbMessages));
		
		
		Collections.sort(res);
		
		try{
			if(cl.getNbMessageRead() > nbMessages){
				res = res.subList(cl.getNbMessageRead(), cl.getNbMessageRead()+5);
			}
		} catch (Exception e) {
			System.out.println("error dnas get user message");
		}

		
		System.out.println("get user messages requested");
		
		return res;
	}
	
	private User retrieveUser(String user){
		
		for(User usr : registeredUsers){
			if( usr.getName().equals(user)){
				return usr;
			}
		}
		
		return null;
	}
}
