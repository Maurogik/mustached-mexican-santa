
Mini-projet d'application répartie

						Roarer
				(twitter-like application)


                      ,.
                    ,_> `.   ,';
                ,-`'      `'   '`'._
             ,,-) ---._   |   .---''`-),.
           ,'      `.  \  ;  /   _,'     `,
        ,--' ____       \   '  ,'    ___  `-,
       _>   /--. `-.              .-'.--\   \__
      '-,  (    `.  `.,`~ \~'-. ,' ,'    )    _\
      _>    \     \ ,'  ') )   `. /     /    <,.
   ,-'   _,  \    ,'    ( /      `.    /        `-,
   `-.,-'     `.,'       `         `.,'  `\    ,-'
    ,'       _  /   ,,,      ,,,     \     `-. `-._
   /-,     ,'  ;   ' _ \    / _ `     ; `.     `(`-\
    /-,        ;    (o)      (o)      ;          `'`,
  ,~-'  ,-'    \     '        `      /     \      <_
  /-. ,'        \                   /       \     ,-'
    '`,     ,'   `-/             \-' `.      `-. <
     /_    /      /   (_     _)   \    \          `,
       `-._;  ,' |  .::.`-.-' :..  |       `-.    _\
         _/       \  `:: ,^. :.:' / `.        \,-'
       '`.   ,-'  /`-..-'-.-`-..-'\            `-.
         >_ /     ;  (\/( ' )\/)  ;     `-.    _<
         ,-'      `.  \`-^^^-'/  ,'        \ _<
          `-,  ,'   `. `"""""' ,'   `-.   <`'
            ')        `._.,,_.'        \ ,-'
             '._        '`'`'   \       <
                >   ,'       ,   `-.   <`'
                 `,/          \      ,-`
                  `,   ,' |   /     /
                   '; /   ;        (
                    _)|   `       (
                    `')         .-'
                      <_   \   /   
                        \   /\(
                         `;/  `


Description du projet

	Le but de ce projet est de mettre en place une application ressemblant a Twitter, basée sur la technologie RMI. L'application offrira la possibilité de lire et d'écrire et de lire des messages (et plein d'autres choses !).

Fonctionnalités offertes

	Voici la liste des fonctionnalités qui ont était implémentées dans le projet :
		- Pour l'utilisateur non connecté :
			- Incription
			- Connexion
			- Fonctionnalités de recherches (listage des utilisateurs, des roarTags, recherche par utilisateur, par roarTags)
		- Pour l'utilisateur connecté :
			- Ecriture de message, avec destinataire et topic (roarTag) optionnels
			- Récupération des messages récents concernant l'utilisateur
			- Afficher les utilisateurs suivis
			- Afficher les interêts (roarTags) suivis
			- Suivre/Renier un utilisateur
			- Ajouter/Supprimer un interêt
			- Fonctionnalités de recherches (comme pour le mode non connecté)
		- Sécurisation de la connexion via JAAS
		- Ajout d'un fichier ascii art entre les balises ::fichier:: dans le message et affichage de l'acsii art lors de la lecture
		- Persistence du serveur d'une utilisation sur l'autre
		- Sauvegarde des messages dans des logs si ils sont trop nombreux (pour ne pas tout garder en mémoire)


Difficultés rencontrées

	Au cours de ce projet, le problème qui nous as le plus ralentit fut la configuration des propriétés de lancement. Nous avons ainsi perdu plusieurs heures à cause d'un server.hostname oublié.
	A part cela, nous n'avons pas rencontré de problèmes vraiment génants.
	Pour le client, une utilisation judicieuse des expressions régulières permet de traiter les messages.
	La mise en place de l'authentifaction JAAS c'est faite sans accroc.
	C'est probablement le fait que nous ayons eut, dès le départ, une architecture clairement définie qui nous a permis de réaliser le projet aisément tout en séparant les tâches à effectuer.

Fichiers fournis

	ROAR =>	
			src =>	
					authentify => package JAAS
					classserver => package pour le téléchargement dynamique de classes
					client => package contenant les sources du client
					core => package contenant les sources du serveur et des implémentations distantes
					remote => package contenant les classes en commun entre le serveur et le client
					security => package pour la sécurité JAAS

			bin =>	
					authentify
					classserver
					client
					core
					remote
					security

			logs => Contiens les logs de messages du serveur

			server.save : serveur sérialisé
			acdc.art : fichier ascii art
			dead.art : fichier ascii art
			kangoroo.art : fichier ascii art
			rhinoceros.art : fichier ascii art
			login.conf : fichier pour la configuration JAAS
			policy : fichier policy pour la sécurité Java


Comment lancer le projet

	Pour utiliser le projet, rendez vous dans le dossier ROAR et exécuter les commandes suivantes :

		Pour le serveur : java -cp bin/ core.Serveur
		Pour le client : java -cp bin/ client.Client

	Le projet est actuellement configuré pour fonctionner avec un client et un serveur en local sur la même machine.