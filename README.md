# Partie 1 - ACDC FIL 2018 - 2021

## Prérequis

### Versions des logiciels 

* Java 1.8 
* MySQL : ( Version utilisée pour le développement du projet : 5.7.23 )

### Base de données

* Avoir MySQL
* Avoir sa base avec le moteur FEDERATED [(Activer le moteur FEDERATED dans sa base de données)](#db_federated)
* Avoir créé trois bases de données : acdc, acdc\_server\_one, acdc\_server\_two. 
* Executer les scripts SQL dans mysql présents dans le dossier scripts/ après avoir lu les instructions dans scripts/script\_acdc\_local.sql

### Activer le moteur federated dans sa base de données
<a name="db_federated"></a>
[OPTION 1]

Pour activer le moteur federated, il faut editer le fichier de configuration de mysql nommé my.cnf une fois que le serveur mysql est éteint. Voici les differents exemples de chemins où doit se trouver le fichier en fonction de l'OS. Si il n'est pas present il faut le créer.

* Linux : /etc/my.cnf
* MacOS avec MAMP : /Applications/MAMP/conf/my.cnf
* Windows : C:\ProgramData\MySQL\MySQL Server 5.7\my.ini

Pour activer le moteur federated, il faut maintenant ajouter ceci dans le fichier my.cnf : 

```
[mysqld]
federated
```
et redémarer le serveur MySQL

[OPTION 2] 

Vous pouvez aussi lancer le serveur MySQL avec l'option --federated. 



© Created by Mathis DELAUNAY - 2018.
