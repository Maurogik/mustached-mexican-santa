package core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfacePublique extends Remote
{
	public InterfacePrivee login(String login, String mdp);
	
	public List<Message> getMessagesAbout(String hashtag) throws RemoteException;
	
	public List<Message> getMessagesFrom(String user) throws RemoteException;
	
	public List<String> getRegisteredusers() throws RemoteException;
	
	public List<String> getExistingHashtags() throws RemoteException;
	
}
