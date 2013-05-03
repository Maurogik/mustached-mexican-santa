package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{
	

	private static final long serialVersionUID = 0xDEADBEEFL;
	
	private List<User> authors;
	private List<String> hashtags;
	private String content;
	private List<User> recipient;
	
	
	public Message(User autho, String cont){
		
		
		hashtags = new ArrayList<String>();
		authors = new ArrayList<User>();
		authors.add(autho);
		this.content = cont;
		recipient = new ArrayList<User>();
	}
		
	
	public List<User> getAuthors() {
		return authors;
	}
	
	public void addAuthors(User auth) {
		this.authors.add(auth);
	}
	
	public List<User> getRecipient() {
		return recipient;
	}
	
	public void addRecipient(User recipient) {
		this.recipient.add(recipient);
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getHashtags() {
		return hashtags;
	}
	
	public void addHashtags(String hasht) {
		this.hashtags.add(hasht);
	}
	
}
