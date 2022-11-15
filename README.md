# UnibetLiveTest
Petite application web de gestion d'événements sportifs en direct

-----

Installation : 

- Clonez le repository en ligne de commande :   ``` git clone https://github.com/EtiennePlas1/UnibetLiveTest.git ```
- Installer le JDK 16 ou version plus récente 
- Installer Maven verion 3.X ou supérieure

Lancer en ligne de commande :

- A la racine du projet, lancer en ligne de commande :
- Compiler l'application : ``` mvn install ```
- Compiler et lancer l'application : ``` mvn spring-boot:run ```

Via Eclipse :

- **File** -> **Import** -> **Existing Maven projects** -> **Browse** (ajouter le dossier racine du projet cloné)
- **Help** -> **install new software** -> copier coller l'url https://projectlombok.org/p2 dans le champ **work with** -> **Add...** -> cocher le projet **Lombok** -> **Finish**
- Compiler : clic droit sur le dossier du projet -> **Run as** -> **Maven install**
- Lancer l'application : clic droit sur le dossier du projet -> **Run as** -> **Java application**


Au lancement, se référer à la [documentation API](http://localhost:8887/swagger-ui/index.html#/) pour le modèle de données et les requêtes disponibles.

Un batch de paris automatique est implémenté mais désactivé. 
Pour l'activer mettez la propriété **betBatch = true** dans le fichier application.properties.



## Créer une springBoot app : l'archi

Le design pattern sur lequel est basé l'architecture d'une app Spring est le **MVC**

**Model** : structure de donnée interne (**DAO** = data access object, typiquement l'objet présent en bdd avec des champs qui n'ont pas de logique pour le client : _id mongo par exemple_), code métier (càd code qui manipule ces données selon la fonction de l'app) 

**View** : interface, ce que voit le client et ce avec quoi il peut interagir (site internet, bouton, formulaire) 

**Controller** : structure de donnée externe (**DTO** = data transfer object, un objet destiné à la view ou aux consommateurs des api de l'application, c'est généralement le DAO sans les champs inutiles pour le client, ou ceux qu'il ne doit pas voir), méthodes de transfert de données entre appli et consommateurs de l'appli. 

L'idée première du MVC est de décorreler ce que reçoivent les consommateurs des données internes de l'application.

Exemple pratique : Client A et Client B consomment l'application. Un modèle interne V2 de l'application sort. Seul client B met à jour sa consommation de l'application. Le MVC permet de mettre à disposition au client 2 l'ancienne version de l'app sans toucher au modèle interne.

2e exemple : Client A change sa manière de consommer les API. Il suffit de créer une nouvelle version du controller, donc du modèle externe, sans toucher le modèle interne.

C'est théorique mais en pratiquant ça devient simple.

---

## 1. Le modèle de données 

Voila l'archi globale de notre site internet : 1 module front dans une tech javascript qui appelle 1 module back via des WS REST. Ce module back prend et envoie les appels avec un controller REST et communique avec une BDD d'une tech au choix.

Ma suggestion : créer le modèle interne à l'ancienne via diagramme de classe et remplir en même temps un **contrat d'interface**. Le **contrat d'interface** c'est toutes les specs techniques des APIS que l'ont va proposer à la consommation.

Pour résumer : 

1. Diagramme de classe pour les **DAO**
2. **Contrat d'interface** pour les **DTO** et les verbes à dispo (GET, POST, PUT, DELETE).

Pour le diagramme de classe tu sais faire, pour le **contrat d'interface** je te suggère d'utiliser [l'editeur swagger en ligne](https://editor.swagger.io/) qui est un doc en YAML qui permet de générer ensuite automatiquement tous tes **DTO** via une librairie maven.

_La difficulté conceptuelle se situera dans la différence entre tes **DAO** et tes **DTO** quels champs veux-tu révéler au client, etc._

---

## 2. La structure du module backend

Dans une app Spring, voici les 3 principaux types de classe :

## Les **@Service** 

Ce sont les classes dans lesquelles tu trouveras le **code métier** _(giga chiante cette appellation ça veut tout et rien dire, en gros c'est la que se déroule la logique de l'application, si tu as une app qui gère des paris sportifs par exemple, une classe **@Service** aura une fonction qui multiplie la mise par la cote pour créditer le client, ça c'est du code métier.)_
On y manipule les **DAO** et les **DTO**. Ces classes instancient les classes de type **@Repository** et sont instanciées par les classes de type **@Controller**.


## Les **@Repository**

Ce sont les classes qui gèrent tout ce qui est query en BDD. Elles ont donc des fonctions qui retournent des **DAO** ou qui les mettent à jour en BDD. Elles sont instanciées pas les @Service mais **JAMAIS** par les controllers. Rappel : il faut décorreler le modèle du controller.
Les **@repository** ne gèrent pas la connexion a la BDD, seulement les query.


## les **@Controller**

Ce sont les classes qui contiennent toutes les apis de l'app, qu'elles fournissent via des fonctions annotées (@GetMapping, etc). Elles instancient les classes **@Service** et ne doivent pas contenir de code métier. En revanche elles doivent pouvoir contenir la logique nécessaire pour envoyer des codes retour HTTP informant des erreurs fonctionnelles ou techniques.


**Schématiquement : @Controller instancie @Service instancie @Repository** _Des **@Service** peuvent s'instancier entre eux_

Le **@** que tu vois devant chaque mot indique que ce mot est une annotation java. Si tu crées une classe et que tu l'annotes avec **@Service**, Spring va comprendre que cette classe a besoin d'être instanciée au lancement de l'application. Cette instance est ensuite utilisée lorsqu'un **@Controller** fait un appel d'une fonction de cette classe par exemple.
Cette instance d'application est appelée un **@bean** dans Spring. Elle permet notamment d'éviter de devoir instancier manuellement ces classes. Cela s'appelle **l'injection de dépendance** et permet de décorreler les classes entre elles.

il existe d'autres **@** qui peuvent s'appliquer aux classes : **@Configuration** une classe qui permet par exemple de paramétrer la connexion à la base de données, ce qui est nécessaire pour les **@bean** de type **@Repository**, **@Component** une annotation dont héritent les 3 principales décrites au dessus.

[Plus d'info sur les annotations](https://www.baeldung.com/spring-component-annotation) -> je te conseille de lire ça pour comprendre un peu mieux spring, c'est pas long.

Des annotations s'appliquent aussi aux méthodes et aux variables : la plus importante est **@Autowired** (**@Resource** marche aussi) qui permet d'indiquer à Spring que la variable en question est un **@bean** à injecter dans la classe la contenant. Si Spring ne trouve pas le **@Bean** au démarrage, l'app plante.

---

