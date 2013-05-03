package core;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{
	
	private List<User> authors;
	private List<String> hashtags;
	private String content;
	private List<String> recipient;
	
	
	public Message(String autho, String content){
		
		
		
	}
	
	public Message(List<User>p_authors, String content){
		
		
		
	}
	
	
	
	public List<User> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<User> authors) {
		this.authors = authors;
	}
	
	public List<String> getRecipient() {
		return recipient;
	}
	
	public void setRecipient(List<String> recipient) {
		this.recipient = recipient;
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
	
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
}
