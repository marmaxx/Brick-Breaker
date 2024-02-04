# Casse brique groupe AD1a

# Casse Brique

Groupe AD1a : 
 - DELPECH	Maxime
 - FILAUDEAU Marius
 - LABBI Noaym
 - VERON Lucas
 - XU Nic

Informations pour les développeurs:

⚠️ Le projet utilise l'anglais américain par convention
pour des raisons de compréhension et pour plus de facilité,
le français est utilisé dans les fichiers .md et sur gitlab.

Le Casse-Brique est un jeu vidéo classique.
Contrôlez une plateforme afin de ne pas laissez le boulet tomber,
renvoyez le sur les briques afin de les casser.
Le but est de détruire l'entièreté des briques à un temps imparti

Ce projet a été réalisé en mode projet dans un contexte scolaire. Il a été développé en JAVA.

## Table des matières

- [Casse brique groupe AD1a](#casse-brique-groupe-ad1a)
- [Casse Brique](#casse-brique)
	- [Table des matières](#table-des-matières)
	- [Développer sur Casse Brique](#développer-sur-casse-brique)
	- [À faire](#à-faire)
		- [Optionnel](#optionnel)
	- [Documentation](#documentation)
		- [Comment l'application est structurée](#comment-lapplication-est-structurée)
			- [package1](#package1)
			- [package2](#package2)
			- [package3](#package3)
			- [package4](#package4)
		- [Conventions et nomenclature](#conventions-et-nomenclature)

## Développer sur Casse Brique

Requis:

- OpenJDK (version xx jusqu'à xx) version à déterminer
- VSCode (ou un autre IDE)

Étape 1: Cloner le projet (avec les droits requis)

```bash
git clone https://gaufre.informatique.univ-paris-diderot.fr/labbi/casse-brique-groupe-ad1a.git
```

(marche également dans le terminal de Visual Studio Code)

Étape 2: Aller dans le dossier du projet

Étape 3: à déterminer

## À faire

- Propreté du code:
  - Commenter le code
  - Encapsuler les variables et les méthodes au maximum
  - Changer les types de variables pour rendre le polymorphisme plus évident
- Jeu:
  - Régler le bug qui cache les objets à partir d'une certaine position relative à la taille de l'objet
  - Ajouter le système de vitesse, de direction de la balle (via calcul des angles, vecteurs et rebonds)
  - Ajouter le système de collisions avec les briques et les bords de l'écran

### Optionnel

- 4
- 0
- 4

![Optionnel]

## Documentation

### Comment l'application est structurée

#### package1

404

#### package2

404

#### package3

404

#### package4

404

### Conventions et nomenclature

```java
/**
 * What the function does
 * 
 * @param type arg1 the first argument
 * @param objectType arg2 the second argument
 * 
 * @return void
 */
public void functionName(Type arg1, ObjectType arg2){
{
    // Si la condition est vraie, [...] (expliquer le sens)
    if(condition){
        // Faire cette action
        doThisAction();
    }
    else{
        // Si la condition est fausse, [...] (expliquer le sens)
        // Faire cette autre action
        doThisOtherAction('arg1', 'arg2'); // (/!\ avec des guillemets simples pour les arguments)
    }

    // Éxécuter cette fonction
    executeFunction();
}
```
