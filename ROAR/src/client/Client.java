package client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import client.windows.MainWindow;

import remote.InterfacePrivee;
import remote.InterfacePublique;
import remote.Message;

public class Client
{

	public static InterfacePublique iPub;
	public static InterfacePrivee iPriv;

	private enum Commande{
		PUSH, PULL, H, ERREUR, LOGIN;

		public static Commande val(String s) {
			try
			{
				return valueOf(s);
			}
			catch(IllegalArgumentException e)
			{
				return ERREUR;
			}
		}
	}

	public String processInput() throws IOException
	{
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		String inputLine = br.readLine();

		String[] part = inputLine.split(" ");

		switch(Commande.val(part[0]))
		{
		case PUSH:
			if(part.length < 1) return "ERREUR : PUSH mal formé";
			String s = new String();
			for (int i=1; i < part.length; ++i){
				s += part[i];
				s += " ";
			}
			return push(s);
		case PULL:
			if(part.length > 3) return "ERREUR:PULL mal formé";
			switch(part.length) {
			case 1 :
				return pull(20);
			case 2 :
			{
				int n = 0;
				try{
					n = Integer.parseInt(part[1]);
					if(n>0)
						return pull(n);
					return "ERREUR: le nombre de ligne demandé doit être positif";
				}
				catch (Exception NumberFormatException){
					return pull(20);
				}
			}
			case 3 :
				int n = 0;
				try{
					n = Integer.parseInt(part[1]);
					if(n < 0)
						return "ERREUR: le nombre de ligne demandé doit être positif";
					return pullHastag(n, part[2]);
				}
				catch (Exception NumberFormatException){
					return pull(20);
				}
			}
		case H:
			if(part.length != 1) return "ERREUR: H mal formé";
			return help();
		case LOGIN:
			if(part.length > 3) return "ERREUR : LOGIN mal formé";

			switch(part.length)
			{
			case 3 :
			{
				String login;
				String password;
				login = part[1];
				password = part[2];
				System.out.println("En attente d'authentification...");
				setIPriv(iPub.login(login, password));
				return "Connecté !";
			}

			default :
				return "ERREUR A LA CONNEXION";
			}

		default:
			if(part[0].matches("(i?)exit")) return "exit";
			return "ERREUR : commande invalide";
		}
	}

	private String help() {
		return ("Commande possible : \n" +
				"PUSH <- permet de rentrer un message\n" +
				"PULL <- permet de lire les 20 derniers messages sur son mur\n" +
				"PULL n <- permet de lire les n derniers messages sur son mur\n" +
				"PULL auteur <- permet de lire les 20 derniers messages de cet auteur\n" +
				"PULL n auteur <- permet de lire les n derniers messages de cet auteur\n"+
				"H <- renvoit le menu\n" +
				"EXIT <- permet au client de se déconnecter du serveur\n" +
				"\n\n" );
	}

	/*juste les méthodes à récupérer par rmi*/
	private String pull(int n) throws RemoteException{
		if(iPriv != null){
			ArrayList<Message> messages = (ArrayList<Message>) iPriv.getUserMessages();
			StringBuilder s = new StringBuilder();
			for(int i=0; i < messages.size() && i < n; ++i){
				s.append(messages.get(i).toString());
				s.append('\n');
			}
			return s.toString();
		}
		else {
			return "Vous n'avez pas les privilége pour effectuer cette action, " +
					"\nveuillez vous authentifier";
		}
	}

	private String pullAuteur(int n, String auteur) throws RemoteException{
		ArrayList<Message> messages = (ArrayList<Message>) iPub.getMessagesFrom(auteur);
		StringBuilder s = new StringBuilder();
		for(int i=0; i < messages.size() && i < n; ++i){
			s.append(messages.get(i).toString());
			s.append('\n');
		}
		return s.toString();
	}

	private String pullHastag(int n, String tag) throws RemoteException{
		ArrayList<Message> messages = (ArrayList<Message>) iPub.getMessagesAbout(tag);
		StringBuilder s = new StringBuilder();
		for(int i=0; i < messages.size() && i < n; ++i){
			s.append(messages.get(i).toString());
			s.append('\n');
		}
		return s.toString();
	}

	private String push(String message) throws RemoteException {
		if(iPriv != null){
			iPriv.postMessage(message);
			return "Message posté !";
		}
		else {
			return "Vous n'avez pas les privilége pour effectuer cette action, " +
					"\nveuillez vous authentifier";
		}
	}

	public InterfacePublique getIPub(){
		return iPub;
	}

	public InterfacePrivee getIPriv(){
		return iPriv;
	}

	public void setIPub(InterfacePublique ip){
		this.iPub = ip;
	}

	public void setIPriv(InterfacePrivee ip){
		this.iPriv = ip;
	}

	public static void main(String[] args)
	{
		Client cl = new Client();
		try
		{
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("-- Demarrage du Client --");
			System.out.println("En attente du serveur...");
			Remote r = Naming.lookup("rmi://localhost:2001/roar");
			cl.setIPub((InterfacePublique)r);
			System.out.println("Debut");
			cl.getIPub().echo();
			/*EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainWindow frame = new MainWindow();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});*/
			
			while(true){
				cl.processInput();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}