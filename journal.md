## Semaine 1

Tout le monde est là.

À faire :

- mettre en place projet gradle
- projet exécutable minimal dans fenêtre graphique swing
- 

## Semaine 2 (29/01/2024)

### (Récupéré depuis google docs et remanié)

Objectif donné par le professeur : faire une version casse brique avec déjà la balle le paddle et les bricks fonctionnant avec gradle

Ce qui a été fait : 

Une partie du groupe s’est concentré sur l’objectif donné par le professeur,
une autre partie s’est chargé de la partie conception et développement de l’application

Branche 6-rebond

- La fenêtre a été faite (Maxime)
- La raquette a été créée  (Noaym)
- Les collisions entre balle et raquette on été gérées (Marius, Lucas)
- la balle a été faite (Marius)
- la partie physique de la balle a été commencée (Lucas)
- gradle (Noaym)

Branche develop : (Nic) 
Grosse restructuration et adaptation des pushes sur la nouvelle structure, l’objectif étant de faire une base fonctionnelle sur lequel gradle marche, avec un structure modulaire et extendable 

### Commentaires

- La branche 6-rebond fait trop de choses. De facto elle devrait s'appeler develop.
- Utiliser directement journal.md et mettre les noms
- Ajouter objectifs (planification semaine suivante)
- L'affichage avec raquette contrôlée et balle qui rebondit fonctionne.
- Ne pas oublier de signaler les objectifs principaux pour la semaine qui vient.

### Objectifs semaine suivante 

## Semaine 3

Ce qui a été fait:

L'objectif principal de cette semaine était de fusionner tout le contenu fonctionnel de la branche 6-rebond et la bonne structure de projet de la branche develop.

Develop

- La balle a bien implémentée dans la nouvelle structure. La librairie contenant les outils physiques nécessaires a été débutée mais aucun résultat n'apparaît sur le gitlab pour le moment. (Marius)
- Le paddle a bien été implémenté dans la nouvelle structure et son mouvement
est maintenant beaucoup plus fluide/un bug d'affichage de la balle a été reglé.(Noaym)
- Le mouvement ainsi que les collisions de la balle ont bien été implémentés dans la nouvelle structure (implémenté comme sur 6-rebond et on s'aperçoit qu'il y a quelques soucis). (Lucas)
- Quelques briques sont maintenant affichées mais leur collision n'est pas encore implémentée (soucis avec le rectangle donc pour le
moment c'est affiché comme un paddle). (Maxime)
- La brique ainsi que ses différents types ont été implémentés/long travail sur des bugs graphiques. (Nic)

=> Conclusion: Le plus gros du travail a été fait en ce qui concerne la fusion des deux versions de la semaine dernière. On fait face à quelques soucis
qui seront la priorité du prochain sprint.

Ce qu'il faut faire:

- Gérer les soucis rencontrés cette semaine (PRIORITAIRE)
- Régler le problème des FPS chez Maxime
- Gérer la collision balle/brique balle/paddle
- Améliorer la fenêtre de jeu
- Continuer à avancer sur la physique (rebond amélioré)
- Peut-être un respawn de la balle si elle sort de la fenêtre en bas.
- Mettre en place GRADLE (débuté mais pas convaincant)

## Semaine 4

.Ce qui a été fait:

- 20-updating_ball (Marius):
    . La balle a une nouvelle position initiale (plus centrée et surtout démarre au niveau du paddle)
    . Il n'y a plus de rebond sur le mur du bas. Si la balle arrive à ce niveau là, elle réapparait en haut et
    redescend.
    . Le paddle est position plus bas.

- 18_implementation_bricks (Maxime):
    . Une méthode a été écrite pour initialiser les briques du jeu. Elle sera améliorée par la suite en fonction
    des niveaux qu'on créera.
    . Modification logique de la fonction update pour enlever les briques qui seront détruites (pas fait pour
    le moment).

- 19-implementer-la-collision-entre-la-balle-et-le-paddle (Noaym):
    . Création d'une interface du nom de Collisions qui gère toutes les collisions entre les différents objets.

- Lucas:
    . Début du "projet" physiques dans un autre git.
    . Première structuration (dossiers, fichiers).
    . Implémentation des classes, méthodes...

- Nic:
    . Une limite pour Les FPS a été fixée (< 45).
    . Restructuration de l'engine.

.Ce qu'il faut faire:

- Implémenter la logique des briques (modification de la vie, destruction, bonus...)
- Mettre en place Gradle
- Continuer le projet physiques en parallèle (TODOs)
- Améliration de la fenêtre de jeu
- Gérer les collisions briques/balle