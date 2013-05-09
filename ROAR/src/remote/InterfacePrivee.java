package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfacePrivee extends Remote
{
	
	//ATTENTION VIEUX COPIER/COLLER D INTERFACE PUBLIQUE

	public List<Message> getMessagesAbout(String hashtag) throws RemoteException;
	
	public List<Message> getMessagesFrom(String user) throws RemoteException;
	
	public List<String> getRegisteredusers() throws RemoteException;
	
	public List<String> getExistingHashtags() throws RemoteException;
	
	//PARTIE PRIVEE
	
	public List<Message> getUserMessages() throws RemoteException;
	
	public void postMessage(String msg) throws RemoteException;
	
	public void relayerMessage(Message mes) throws RemoteException;
	
	public void follow(String userToFollow) throws RemoteException;
	
	public void addInterest(String interest) throws RemoteException;
	
	public String getName() throws RemoteException;
}
