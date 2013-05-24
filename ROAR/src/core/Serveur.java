package core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import authentify.ConnexionServeurImpl;

import classserver.ClassFileServer;

public class Serveur
{
	public static void main(String[] args)
	{

		System.setProperty("java.rmi.server.codebase", "http://localhost:2007/");
		System.setProperty("java.rmi.server.hostname", "192.168.0.10");
		System.setProperty("java.security.auth.login.config", "login.conf");

		try {
			AccesPublic ap = new AccesPublic();
			String url;
			LocateRegistry.createRegistry(2001);

			url = "rmi://localhost:2001"; 
			ConnexionServeurImpl conServImpl = new ConnexionServeurImpl();
			System.out.println("Enregistrement de l'objet avec l'url : " + url+  "/roar");
			Naming.rebind(url+"/roar", ap);
			Naming.rebind(url+"/roar2", conServImpl); 
			System.out.println("rebind done");
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