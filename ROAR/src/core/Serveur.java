package core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import classserver.ClassFileServer;

public class Serveur 
{
	public static void main(String[] args) 
	{
		try {
			AccesPublic ap = new AccesPublic();
			String url;
			LocateRegistry.createRegistry(2001);
			url = "rmi://localhost:2001/roar";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, ap);
			new ClassFileServer(2007, "bin/");
		} 
		catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}