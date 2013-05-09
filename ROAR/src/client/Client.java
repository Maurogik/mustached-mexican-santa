package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;

import remote.InterfacePublique;

public class Client 
{
	private enum Commande{
		PUSH, PULL, H, ERREUR;

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

		switch(Commande.val(part[0])){
		case PUSH:
			if(part.length != 1) return "ERREUR : PUSH mal formé";
			return push();
		case PULL:
			if(part.length >= 3) return "ERREUR:PULL mal formé";
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
					return pull(20, part[1]);
				}
			}
			case 3 : 
				return pull(Integer.parseInt(part[1]), part[2]);
			}
		case H:
			if(part.length != 1) return "ERREUR: H mal formé";
			return help();
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
	private String pull(int n){
		return "le pull avec "+n+" lignes";
	}
	private String pull(int n, String auteur){
		return "le pull avec "+n+" lignes pour l'auteur "+auteur;
	}
	private String push() {
		return "le push ";
	}
	
	public static void main(String[] args) 
	{
		Client cl = new Client();
		try 
		{
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("-- Demarrage du Client --");
			InterfacePublique ip = (InterfacePublique)Naming.lookup("rmi://localhost:2001/roar");
			System.out.println("En attente du serveur...");
			while(true){
				System.out.println(cl.processInput());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
