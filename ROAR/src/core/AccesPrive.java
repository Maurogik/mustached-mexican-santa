package core;

import java.rmi.RemoteException;
import java.util.List;

public class AccesPrive extends AccesPublic implements InterfacePrivee{

	private String userName;
	
	public AccesPrive(String user){
		super();
		userName = user;
	}
	
	@Override
	public List<Message> getUserMessages() throws RemoteException {
		
		return server.getUserMessages(userName, 10);
	}

	@Override
	public void postMessage(String content, List<String> recipient,
			List<String> hashTags) throws RemoteException {
		
		server.postMessage(content, userName, recipient, hashTags);
	}

	@Override
	public void relayerMessage(Message mes) throws RemoteException {
		
		server.relayerMessage(userName, mes);
	}

	@Override
	public void follow(String userToFollow) throws RemoteException {
		
		server.addFollowRelationship(userName, userToFollow);
	}

	@Override
	public void addInterest(String interest) throws RemoteException {
		
		server.addInterest(userName, interest);
	}

	@Override
	public String getName() throws RemoteException {
		
		return userName;
	}

}
