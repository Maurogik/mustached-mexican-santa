package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface clientInterface extends Remote{
	
	void pullFinished() throws RemoteException;
	int getNbMessageRead() throws RemoteException;
}
