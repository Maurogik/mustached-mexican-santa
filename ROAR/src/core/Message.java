package core;

import java.util.List;

public class Message {
	private List<String> authors;
	private List<String> hashtags;
	private String content;
	private List<String> recipient;
	
	
	
	public List<String> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<String> authors) {
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
