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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import remote.InterfacePrivee;
import remote.InterfacePublique;
import remote.Message;


import client.windows.MainWindow;

public class Client 
{
	
	public static InterfacePublique iPub;
	public static InterfacePrivee iPriv;
	public String login;
	
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

		switch(Integer.decode(part[0]))
		{
			case 1:
				return login();
			case 2:
				return inscription();
			case 3:
				return rechercher();
			default:
				System.exit(0);
				return "";
		}
	}

	public String menuDisconnect() {
		StringBuilder s = new StringBuilder();
		s.append("\t\t===== MENU ====\n");
		s.append("1. Connexion\n");
		s.append("2. S'inscrire\n");
		s.append("3. Rechercher (#RoarTag ou @Auteur)\n");
		s.append("4. Quitter\n");
		s.append(">> ");
		
		return s.toString();
	}
	
	public String recherche() {
		StringBuilder s = new StringBuilder();
		s.append("\t ===== RECHERCHE ====\n");
		s.append("1. Lister #RoarTag existants\n");
		s.append("2. Rechercher messages par RoarTag\n");
		s.append("3. Lister @Auteurs existants\n");
		s.append("4. Rechercher messages par auteurs\n");
		s.append("5. Retour\n");
		s.append(">> ");
		
		return s.toString();
	}
	
	public String menuConnect() {
		StringBuilder s = new StringBuilder();
		s.append("\t\t===== Bienvenue "+ this.login +" ====\n");
		s.append("1. Messages récents\n");
		s.append("2. Ecrire message\n");
		s.append("3. Suivre un utilisateur\n");
		s.append("4. Mes interets\n");
		s.append("5. Rechercher (RoarTag ou Auteur)\n");
		s.append("6. Quitter\n");
		s.append(">> ");
		
		return s.toString();
	}
	
	public String help() {
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
	
	public String login() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine = br.readLine();
		
		System.out.println("Entrez votre login : ");
		inputLine = br.readLine();
		String login = inputLine;
		System.out.println("Entrez votre mot de passe");
		inputLine = br.readLine();
		String password = inputLine;
		
		System.out.println("En attente d'authentification...");
		setIPriv(iPub.login(login, password));
		this.login = login;
		if(iPriv != null)
			return "Connect� !";
		else
			return "Erreur lors du login";
	}
	
	private String inscription() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine = br.readLine();
		
		System.out.println("Entrez votre login : ");
		inputLine = br.readLine();
		String login = inputLine;
		System.out.println("Entrez votre mot de passe");
		inputLine = br.readLine();
		String password = inputLine;
		
		System.out.println("En attente d'inscription...");
		if(iPub.register(login, password))
			return "Inscription valid�e !";
		else
			return "Erreur lors de l'inscription";
	}
	
	public String rechercher() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine = br.readLine();
		StringBuilder s = new StringBuilder();
		
		System.out.println(recherche());
		inputLine = br.readLine();
		String[] part = inputLine.split(" ");
		switch(Integer.decode(part[0]))
		{
			case 1:
				for(String str : iPub.getExistingHashtags()) {
					System.out.println(str = "\n");
				}
				return rechercher();
			case 2:
				System.out.println("Entrez le roartag � rechercher >> ");
				inputLine = br.readLine();
				break;
			case 3:
				for(String str : iPub.getRegisteredusers()) {
					System.out.println(str = "\n");
				}
				return rechercher();
			case 4:
				System.out.println("Entrez l'auteur � rechercher >> ");
				inputLine = br.readLine();
				break;
		}
		
		System.out.println("Recherche en cours...");
		Pattern p = Pattern .compile("@([a-z]|[A-Z])+");
		  
		Matcher m = p.matcher(inputLine);
	   
		if (m.find()){
			return pullAuteur(20, inputLine.substring(m.start(), m.end()));
		}
		p = Pattern .compile("#([a-z]|[A-Z])+");
		m = p.matcher(inputLine);
		if (m.find()){
		  return pullHastag(20, inputLine.substring(m.start(), m.end()));
	   }
		
		return "Erreur";
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
		System.setProperty("java.security.policy", "./src/client/policy");
		Client cl = new Client();
		try 
		{
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("-- Demarrage du Client --");
			System.out.println("En attente du serveur...");
			Remote r = Naming.lookup("rmi://157.169.103.58:2001/roar");
			cl.setIPub((InterfacePublique)r);
			System.out.println("Debut");
			cl.getIPub().echo();
			while(true) {
				if(cl.getIPriv() == null) {
					System.out.println(cl.menuDisconnect());
				}
				else {
					System.out.println(cl.menuConnect());
				}
				System.out.println(cl.processInput());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
