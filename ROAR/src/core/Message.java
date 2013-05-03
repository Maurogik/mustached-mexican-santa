package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{
	

	private static final long serialVersionUID = 0xDEADBEEFL;
	
	private List<String> authors;
	private List<String> hashtags;
	private String content;
	private List<String> recipient;
	
	
	public Message(String autho, String cont){
		
		
		hashtags = new ArrayList<String>();
		authors = new ArrayList<String>();
		authors.add(autho);
		this.content = cont;
		recipient = new ArrayList<String>();
	}
		
	
	public List<String> getAuthors() {
		return authors;
	}
	
	public void addAuthors(String auth) {
		this.authors.add(auth);
	}
	
	public List<String> getRecipient() {
		return recipient;
	}
	
	public void addRecipient(String recipient) {
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
