package parser;

public class Parser {

	private enum Commande{
		PUSH, PULL, H, ERREUR;

		public static Commande val(String s) {
			try {
				return valueOf(s);
			} catch(IllegalArgumentException e) {
				return ERREUR;
			}
		}

		public String processInput(String inputLine) {
			String[] part = inputLine.split(" ");

			switch(Commande.val(part[0])){
			case PUSH:
				if(part.length != 1) return "ERREUR : PUSH mal formé";
				return push();
			case PULL:
				if(part.length >= 3) return "ERREUR:GET mal formé";
				switch(part.length) {
				case 1 : 
					return pull(20);
				case 2 :
				{
					int n = 0;
					try{
						n = Integer.parseInt(part[1]);
						return pull(n);
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
				return menu();
			default:
				if(part[0].matches("(i?)exit")) return "exit";
				return "ERREUR : commande invalide";
			}
		}

		private String menu() {
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
			return "";
		}
		private String pull(int n, String auteur){
			return "";
		}
		private String push() {
			return "";
		}
	}
}