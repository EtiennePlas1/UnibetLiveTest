# UnibetLiveTest
Petite application web de gestion d'événements sportifs en direct

-----

Installation : 

- Clonez le repository en ligne de commande : git clone https://github.com/EtiennePlas1/UnibetLiveTest.git
- Installer le JDK 16 ou version plus récente 
- Installer Maven verion 3.X ou supérieure
- A la racine du projet, lancer en ligne de commande : mvn spring-boot:run

Se référer à la documentation API : http://localhost:8887/swagger-ui/index.html#/ pour le modèle de données et les requêtes disponibles.

Un batch de paris automatique est implémenté mais désactivé. Pour l'activer mettez la propriété betBatch = true dans le fichier application.properties.

