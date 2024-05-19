# Casse brique groupe AD1a

# Casse Brique

Groupe AD1a : 
 - DELPECH	Maxime
 - FILAUDEAU Marius
 - LABBI Noaym
 - VERON Lucas
 - XU Nic

Informations pour les développeurs:

⚠️ Le projet a été traduit en anglais par convention,
pour des raisons de compréhension, le README.md sera en français.

Le Casse-Brique est un jeu vidéo classique.
Contrôlez une plateforme afin de ne pas laissez le boulet tomber,
renvoyez le sur les briques afin de les casser.
Le but est de détruire l'entièreté des briques à un temps imparti

Ce projet a été réalisé en mode projet dans un contexte scolaire. Il a été développé en JAVA.

## Table des matières

- [Casse Brique](#casse-brique)
  - [Table of contents](#table-des-matières)
  - [Développer sur Convergence](#développer-sur-Casse-Brique)
  - [À faire](#à-faire)
    - [Optionnel (futur)](#optionnel)
  - [Documentation](#documentation)
    - [Comment l'application est structurée](#comment-lapplication-est-structurée)
      - [Configuration](#configuration)
      - [Géometrie](#géométrie)
      - [Interface Graphique utilisateur](#gui)
      - [Entitées](#modèle)
    - [Conventions et nomenclature](#conventions-et-nomenclature)
- [Rapports](#Rapports-des-réunions)



## Développer sur Casse Brique

Requis:

- OpenJDK version 17 ou plus
- VSCode (ou un autre IDE)

Étape 1: Cloner le projet (avec les droits requis)

```bash
git clone https://gaufre.informatique.univ-paris-diderot.fr/labbi/casse-brique-groupe-ad1a.git
```
(marche également dans le terminal de Visual Studio Code)

Étape 2: Aller dans le dossier du projet

Étape 3: Ouvrir n'importe quel fichier et appuyer sur F5 ou bien exécuter le main.java dans le dossier racine


## Documentation

### Comment l'application est structurée (voir Structure.pdf)

Assister à la présentation ou bien lire le rapport.

### Conventions et nomenclature

```java
/**
 * javadoc
 *
 */
functionName(type: arg1, objectType|null: arg2) : returnType1 | returnType2 // (en camelCase)
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

# Rapports des Réunions

Le rapport des réunions a été décallé dans journal.md