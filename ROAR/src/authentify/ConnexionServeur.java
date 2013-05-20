package authentify;

import java.rmi.*;

import javax.security.auth.login.LoginException;

import remote.InterfacePrivee;
public interface ConnexionServeur extends Remote {
	public InterfacePrivee logon(String uname, String passwd) throws RemoteException, LoginException;
}
