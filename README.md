# Version

- **1.0-SNAPSHOT** :

# Objectif
API permettant la gestion de plusieurs tondeuses automatisées à gazon pour une surface rectangulaire.

# Lancement de l'application
L'application MowItNow Tondeuse se lance de 3 manières :
### Avec Docker
A la racine du projet lancer les commandes suivantes :
```sh
$ mvn clean install
$ docker build -t tondeuse .
$ docker run -p 8080:8080 tondeuse
```

### Avec docker-compose
L'image docker est sauvegardée dans AWS ECR.
A la racine du projet lancer la commande suivante :
```sh
$ docker-compose up
```

### Avec Java
Aller dans le répertoire ms-launcher puis lancer la commande
```sh
$ mvn clean install
$ cd target
$ java -jar ms-launcher-1.0-SNAPSHOT.jar 
```

# Utilisation de l'application
L'ensemble de l'api peut être testé avec l'interface fournie par swagger-ui
http://localhost:8080/api/swagger-ui.html

Pour tester l'application avec un fichier plat, dérouler **api-controller**, 
puis cliquer sur **POST** puis sur **Try it out**

Vous pouvez maintenant uploader le fichier et cliquer sur **Exécuter**

Le résultat apparaîtra plus bas au format Json.

Pour relancer un test il faut cliquer sur le bouton rouge **Cancel**

# Architecture
Application codée et compilée en Java 8, utilise les frameworks et dépendances suivants :
- Spring boot 2
- Lombok
- JUnit 4
- Mockito
- Mapstruct
- H2
- Spring-JPA
- Spring-Web
- SpringFox

Application entièrement dévelopée avec Spring Boot mais avec une architecture hexagonale.
Il y a donc 4 modules :
- Domaine

Contient le modèle, ce module ne possède aucune dépendance vers les autres modules et n'utilise aucun framework. Ce module minimaliste permet une isolation du modèle du reste de l'application.
Des ports sont utilisés pour permettre aux autres modules de manipuler le modèle

- Infrastructure

Contient les adaptateurs pour persister les données du modèle

- Application

Module contenant l'interface REST. 

- Ms-launcher

Contient le lanceur SpringBoot ainsi que la création des beans pour le domaine. Contient également la configuration swagger.

>Le domaine contient 2 répertoires **api** et **spi**. **api** est le port qui sera appelé par l'application. **spi** contient les ports utilisés pour la persistance des données.

# Persistance des données
Une base de données in memory H2 est utilisée pour la persistance des données.

Les données persistées sont :
- La position est l'orientation de la tondeuse
- La grille

# Swagger
Utilisation du modèle de spécification Swagger pour documenter l'api.

http://localhost:8080/swagger-ui.html

Toutes les ressources et Api sont générées grâce à la dépendance maven maven **springfox-swagger2**

Dans le projet lancer la commande suivante pour générer les ressources.

```sh
$ mvn clean install
```

# Test d'intégrations
Les tests d'intégration doivent être lancés avec postman. Les sources sont présentes dans le fichier **itests/MowItNow.postman_collection.json**

Il suffit d'importer les collections dans postman et lancers les tests.

# Contributeur
Cyril Marchive (cyril.marchive@gmail.com)

[github ressource]: https://github.com/langston8182/tondeuse
