package remote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Message implements Serializable, Comparable<Message>{
	

	private static final long serialVersionUID = 0xDEADBEE;
	
	private List<String> authors;
	private List<String> hashtags;
	private String content;
	private List<String> recipient;
	private Date date;
	private long id;
	 
	
	public Message(long iD, String autho, String cont){
		
		id = iD;
		hashtags = new ArrayList<String>();
		authors = new ArrayList<String>();
		authors.add(autho);
		this.content = cont;
		recipient = new ArrayList<String>();
		date = new Date(); //date du jour...normalement
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


	public Date getDate() {
		return date;
	}
	
	public long getID(){
		return id;
	}
	
	public boolean equals(Message mes){
		return content.equals(mes.getContent()) && authors.equals(mes.getAuthors())
				&& hashtags.equals(mes.getHashtags()) && recipient.equals(mes.getRecipient()) || mes.id == id;
	}

	public String toString(){
		String res;
		
		res = "ID : " + id + "\nby : " + authors + "\n on : " + date.toString()+"\n\n" + content + "\n";
		
		return res;
	}

	@Override
	public int compareTo(Message o) {
		
		return o.getDate().compareTo(date);
	}
}
