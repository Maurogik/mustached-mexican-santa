package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import authentify.*;

import remote.InterfacePrivee;
import remote.InterfacePublique;
import remote.Message;
import remote.clientInterface;

public class Client extends UnicastRemoteObject implements clientInterface, Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Client() throws RemoteException {
		super();
	}

	public static String ip = "localhost";
	
	public InterfacePublique iPub;
	public InterfacePrivee iPriv;
	public String login;
	public int messageGet = 0;
	public boolean finished;
	
	public String processInputDisconnect() throws IOException 
	{
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine = br.readLine();
		
		String[] part = inputLine.split(" ");

		switch(Integer.decode(part[0]))
		{
			case 1:
				return loginBis();
			case 2:
				return inscription();
			case 3:
				return rechercher();
			case 4:
				iPub.saveServer();
				System.exit(0);
				return "quit";
			default:
				throw new IOException();
		}
	}
	
	public String processInputConnect() throws IOException 
	{
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine = br.readLine();
		
		String[] part = inputLine.split(" ");

		switch(Integer.decode(part[0]))
		{
			case 1:
				return list();
			case 2:
				return write();
			case 3:
				return myUsers();
			case 4:
				return follow();
			case 5:
				return unfollow();
			case 6:
				return myInteret();
			case 7:
				return addInteret();
			case 8:
				return delInteret();
			case 9:
				return rechercher();
			case 0:
				iPub.saveServer();
				System.exit(0);
				return "quit";
			default:
				throw new IOException();
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
		s.append("3. Afficher utilisateur suivis\n");
		s.append("4. Suivre un utilisateur\n");
		s.append("5. Renier un utilisateur\n");
		s.append("6. Mes interets\n");
		s.append("7. Ajouter un interet\n");
		s.append("8. Supprimer un interet\n");
		s.append("9. Rechercher (RoarTag ou Auteur)\n");
		s.append("0. Quitter\n");
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
		String inputLine; 
		
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
			return "Connecté !";
		else
			return "Erreur lors du login";
	}
	public String loginBis() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine;
		
		System.out.println("Entrez votre login : ");
		inputLine = br.readLine();
		String login = inputLine;
		System.out.println("Entrez votre mot de passe");
		inputLine = br.readLine();
		String password = inputLine;
		
		System.out.println("En attente d'authentification...");
		ConnexionServeur cs=null; // cs est un stub vers l objet remote, obtenu par le lookup
		try {
			cs = (ConnexionServeur)Naming.lookup("rmi://"+ip+":2001/roar2"); 
				// rechercher sur cette machine, localhost, un objet remote offrant un service de connexion 
		} catch (MalformedURLException e) {
		} catch (RemoteException e) {
		} catch (NotBoundException e) {
		}
		InterfacePrivee acces=null;
		try {
			acces=(InterfacePrivee) cs.logon(login, password); 
		setIPriv(acces);
		this.login = login;
		}catch (RemoteException e) {
		}catch (LoginException e) {
			System.out.println("Dommage, mauvais login et mot de passe, Recommencez !");
		}
		if(iPriv != null)
			return "Connecté !";
		else
			return "Erreur lors du login";
	}
	
	private String inscription() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine;
		
		System.out.println("Entrez votre login : ");
		inputLine = br.readLine();
		String login = inputLine;
		System.out.println("Entrez votre mot de passe");
		inputLine = br.readLine();
		String password = inputLine;
		
		System.out.println("En attente d'inscription...");
		if(iPub.register(login, password))
			return "Inscription validée !";
		else
			return "Erreur lors de l'inscription";
	}
	
	public String rechercher() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine;
		
		System.out.println(recherche());
		inputLine = br.readLine();
		String[] part = inputLine.split(" ");
		switch(Integer.decode(part[0]))
		{
			case 1:
				for(String str : iPub.getExistingHashtags()) {
					System.out.println(str + "\n");
				}
				return rechercher();
			case 2:
				System.out.println("Entrez le roartag à rechercher >> ");
				inputLine = br.readLine();
				break;
			case 3:
				for(String str : iPub.getRegisteredusers()) {
					System.out.println(str + "\n");
				}
				return rechercher();
			case 4:
				System.out.println("Entrez l'auteur à rechercher >> ");
				inputLine = br.readLine();
				break;
		}
		
		System.out.println("Recherche en cours...");
		if(Integer.decode(part[0]) == 4)
			return pullAuteur(20, inputLine);
		
		if(Integer.decode(part[0]) == 2)
			return pullHastag(20, inputLine);
		
		return "Erreur";
	}
	
	public String list() throws RemoteException {
		return pull(20);
	}
	
	private String write() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		System.out.println("Entrez votre message >> ");
		String inputLine = br.readLine();
		ArrayList<String> arts = parse(inputLine);
		if(arts == null)
			iPriv.postMessage(inputLine);
		else
			iPriv.postMessageAscii(inputLine, arts);
		return "Message posté !";
	}
	
	private ArrayList<String> parse(String inputLine) {
		ArrayList<String> chaine = new ArrayList<String>();
		String art = "";
		Pattern p = Pattern .compile("(::[^:]+::)");
	    Matcher m = p.matcher(inputLine);
	   
	    while (m.find()){
	    	//lecture du fichier texte	
			try{
				InputStream ips=new FileInputStream(inputLine.substring(m.start()+2, m.end()-2)); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
					art += ligne+"\n";
				}
				br.close();
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			chaine.add(art);
	    }
	    
		return chaine != null ? chaine : null;
	}

	private String follow() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		System.out.println("Entrez l'utilisateur a suivre >> ");
		String inputLine = br.readLine();
		iPriv.follow(inputLine);
		return "Utilisateur suivi !";
	}
	
	private String unfollow() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		System.out.println("Entrez l'utilisateur a renier >> ");
		String inputLine = br.readLine();
		iPriv.unfollow(inputLine);
		return "Utilisateur renié !";
	}

	/*juste les méthodes à récupérer par rmi*/
	private String pull(int n) throws RemoteException{
		ArrayList<Message> messages = new ArrayList<Message>();
		if(iPriv != null){
			while(!finished) {
				messages.addAll((ArrayList<Message>) iPriv.getUserMessages(this));
				messageGet = messages.size();
			}
			finished = false;
			messageGet = 0;
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
	
	private String addInteret() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine;
		System.out.println("Entrez ici le RoarTag que vous voulez suivre");
		inputLine = br.readLine();
		iPriv.addInterest(inputLine);
		return "RoarTag ajouté";
	}
	
	private String delInteret() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in); 
		BufferedReader br=new BufferedReader(isr); 
		String inputLine;
		System.out.println("Entrez ici le RoarTag que vous voulez renier");
		inputLine = br.readLine();
		iPriv.removeInterest(inputLine);
		return "RoarTag supprimé";
	}
	
	private String myInteret() throws IOException {
		System.out.println("Voici les RoarTag que vous suivez :\n");
		ArrayList<String> interet = (ArrayList<String>) iPriv.listInterest();
		StringBuilder s = new StringBuilder();
		for(int i=0; i < interet.size(); ++i){
			s.append(interet.get(i).toString());
			s.append('\n');
		}
		return s.toString();
	}
	
	private String myUsers() throws IOException {
		System.out.println("Voici les utilisateurs que vous suivez :\n");
		ArrayList<String> users = (ArrayList<String>) iPriv.listFollowed();
		StringBuilder s = new StringBuilder();
		for(int i=0; i < users.size(); ++i){
			s.append(users.get(i).toString());
			s.append('\n');
		}
		return s.toString();
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
		System.setProperty("java.security.policy", "./policy");



		try 
		{
			Client cl = new Client();
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("-- Demarrage du Client --");
			System.out.println("En attente du serveur...");
			Remote r = Naming.lookup("rmi://"+ip+":2001/roar");
			cl.setIPub((InterfacePublique)r);
			System.out.println("Debut");
			cl.getIPub().echo();
			while(true) {
				
				try{
					
					if(cl.getIPriv() == null) {
						System.out.println(cl.menuDisconnect());
						System.out.println(cl.processInputDisconnect());
					}
					else {
						System.out.println(cl.menuConnect());
						System.out.println(cl.processInputConnect());
					}
					
				} catch (Exception e){
					System.out.println("Commande incorrecte, veuillez réessayer");
					e.printStackTrace();
				}
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void pullFinished() {
		finished = true;
	}

	@Override
	public int getNbMessageRead() throws RemoteException {
		return messageGet;
	}
	
	
}
