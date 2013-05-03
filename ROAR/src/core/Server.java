package core;

import java.util.ArrayList;
import java.util.List;

public class Server {
	
	
	private static Server server = null;
	
	private List<Message> recentMessages;
	private List<User> registeredUsers;
	private List<String> logs; // ancien messages sérializés
	
	private Server(){
		recentMessages = new ArrayList<Message>();
		registeredUsers = new ArrayList<User>();
		logs = new ArrayList<String>();
	}
	
	private void loadServer(){
		
		
	}
	
	private void saveServer(){
		
	}
	
	public Server getServer(){
		
		if(server == null){
			
			server = new Server();
			
		}
		
		return server;
	}
	
}
