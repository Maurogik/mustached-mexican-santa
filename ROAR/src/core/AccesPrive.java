package core;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import remote.InterfacePrivee;
import remote.Message;

public class AccesPrive extends AccesPublic implements InterfacePrivee, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 0xDEADFACE;
	private String userName;
	
	public AccesPrive(String user) throws RemoteException{
		super();
		userName = user;
	}
	
	@Override
	public List<Message> getUserMessages() throws RemoteException {
		
		return server.getUserMessages(userName, 10);
	}

	@Override
	public void postMessage(String meg) throws RemoteException {
		
		
		
		
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
