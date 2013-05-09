package core;

import java.util.ArrayList;
import java.util.List;

import remote.Message;
import remote.User;

public class DataServer {
	
	
	private static DataServer server = null;
	
	private List<Message> recentMessages;
	private List<User> registeredUsers;
	private List<String> logs; // ancien messages sérializés
	
	private DataServer(){
		recentMessages = new ArrayList<Message>();
		registeredUsers = new ArrayList<User>();
		logs = new ArrayList<String>();
	}
	
	private List<Message> loadLog(String logRef){
		
		return null;
	}
	
	private void saveNewLog(){
		
	}
	
	private void loadLogList(){
		
	}
	
	private void saveLogList(){
		
	}
	
	
	
	public static DataServer getServer(){
		
		if(server == null){		
			server = new DataServer();
		}
		
		return server;
	}
	
	public boolean isValidLogin(String pseudo, String passw){
		
		User us = retrieveUser(pseudo);
		return us.getPassword().equals(passw);
		
	}
	
	public List<String> getRegisteredUsers(){
		
		List<String> regUsers = new ArrayList<String>();
		
		for(User us : registeredUsers){
			regUsers.add(us.getName());
		}
		
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
		
		return hashTags;
	}
	
	public void postMessage(String content, String author, List<String> recipient, List<String> hashTags){
		
		Message mes = new Message(author, content);
		
		for(String rec : recipient){
			mes.addRecipient(rec);
		}
		
		for(String hash : hashTags){
			mes.addHashtags(hash);
		}
	}
	
	public void relayerMessage(String user, Message mes){

		int index = recentMessages.indexOf(mes);
		if(index != -1 ){
			recentMessages.get(index).addAuthors(user);
		} else {
			//check if message in older logs
			System.out.println("ERROR : message not found in database");
		}
		
		
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
		
	}
	
	public void addInterest(String user, String interest){
		User usr = retrieveUser(user);
		if(usr == null){
			System.out.println("Error, unknown user");
			return;
		}
		usr.addInterest(interest);
	}
	
	public List<Message> getMessagesFrom(String user){
		
		List<Message> res = new ArrayList<Message>();
		
		for(Message msg : recentMessages){
			if(msg.getAuthors().contains(user)){
				res.add(msg);
			}
		}
		
		return res;
	}
	
	public List<Message> getMessagesAbout(String hashtag){
		
		List<Message> res = new ArrayList<Message>();
		
		for(Message msg : recentMessages){
			if(msg.getHashtags().contains(hashtag)){
				res.add(msg);
			}
		}
		
		return res;
	}
	
	public List<Message> getMessagesTo(String recipient){
		
		List<Message> res = new ArrayList<Message>();
		
		for(Message msg : recentMessages){
			if(msg.getRecipient().contains(recipient)){
				res.add(msg);
			}
		}
		
		return res;
	}
	
	public List<Message> getUserMessages(String user, int nbMessages){
		List<Message> res = new ArrayList<Message>();
		
		User usr = retrieveUser(user);
		
		if(usr == null){
			System.out.println("Error, unknown user");
			return null;
		}
		
		for (User followed : usr.getFollowed()) {
			//Ajoute les messages des personnes suivie
			res.addAll(getMessagesFrom(followed.getName()));
		}
		
		for(String interest : usr.getInterest()){
			//Ajoute les messages convernant les hashtags suivis
			res.addAll(getMessagesAbout(interest));
		}
		
		res.addAll(getMessagesTo(usr.getName()));
		
		//Tri par date
		//TODO 
		//garder seulement nbMessages
		
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
