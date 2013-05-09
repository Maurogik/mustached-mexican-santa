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

import classserver.ClassFileServer;

public class Serveur 
{
	public static void main(String[] args) 
	{
		
		System.setProperty("java.rmi.server.codebase", "http://localhost:2007/");
		System.setProperty("java.rmi.server.hostname", "82.236.41.99");

		try {
			AccesPublic ap = new AccesPublic();
			String url;
			LocateRegistry.createRegistry(2001);

			url = "rmi://82.236.41.99:2001/roar";

			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, ap);
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
