package core;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import remote.InterfacePrivee;
import remote.InterfacePublique;
import remote.Message;

public class AccesPublic extends UnicastRemoteObject implements InterfacePublique, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0xDEADBEEF;
	protected transient DataServer server;
	
	public AccesPublic() throws RemoteException{
		super();
		server = DataServer.getServer();
	}

	@Override
	public InterfacePrivee login(String login, String mdp)
			throws RemoteException {
		System.out.println("tentative de login");
		if(server.isValidLogin(login, mdp)){
			return new AccesPrive(login);
		}
		return null;
	}

	@Override
	public List<Message> getMessagesAbout(String hashtag)
			throws RemoteException {
		
		return server.getMessagesAbout(hashtag);
	}

	@Override
	public List<Message> getMessagesFrom(String user) throws RemoteException {
		
		return server.getMessagesFrom(user);
	}

	@Override
	public List<String> getRegisteredusers() throws RemoteException {
		
		return server.getRegisteredUsers();
	}

	@Override
	public List<String> getExistingHashtags() throws RemoteException {

		return server.getHastags();
	}

	@Override
	public void echo() throws RemoteException {
		System.out.println("Hey !");
	}

}
