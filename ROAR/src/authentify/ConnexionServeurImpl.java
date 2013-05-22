package authentify;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import core.AccesPrive;

import remote.InterfacePrivee;


public class ConnexionServeurImpl extends UnicastRemoteObject implements ConnexionServeur, Serializable {
	private static final long serialVersionUID = 1L;

	public ConnexionServeurImpl() throws RemoteException {
		super();
	}

	private AccesPrive acces=new AccesPrive();
	@Override
	public InterfacePrivee logon(String username, String passwd) throws RemoteException, LoginException{
		// Verifier si l'utilisateur a bien donné un login et passwd egaux
		// Si non, renvoye une instance de LoginException 
		
		LoginContext lc = new LoginContext("MonServeur", new security.module.RemoteCallbackHandler(username, passwd));
		try{
			lc.login();
			acces = new AccesPrive(username);
		}
		catch (LoginException e){
			System.out.println("Recu "+ username + " et " + passwd + " mais, apr�s v�rif, ils sont incorrects");
			throw e;
		}
		return acces;
	}	
}
