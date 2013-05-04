package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 0xDEADFACEL;
	private List<User> followed;
	private List<User> followers;
	private List<String> interest; //followed hashtag
	private String name;
	private String password;
	
	
	public User(String name, String passw){
		this.name = name;
		password = passw;
		followed = new ArrayList<User>();
		followers = new ArrayList<User>();
		interest = new ArrayList<String>();
	}


	public List<User> getFollowed() {
		return followed;
	}


	public void addFollowed(User fol) {
		this.followed.add(fol);
	}


	public List<User> getFollowers() {
		return followers;
	}


	public void addFollowers(User foll) {
		this.followers.add(foll);
	}


	public List<String> getInterest() {
		return interest;
	}


	public void addInterest(String inter) {
		this.interest.add(inter);
	}


	public String getName() {
		return name;
	}


	public String getPassword() {
		return password;
	}

	
}
