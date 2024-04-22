## Semaine 1 (30/04/2024)

Tout le monde est là.

À faire :

- mettre en place projet gradle
- projet exécutable minimal dans fenêtre graphique swing

## Semaine 2 (06/02/2024)

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

## Semaine 3 (13/02/2024)

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

## Semaine 4 (20/02/2024)

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
- Amélioration de la fenêtre de jeu
- Gérer les collisions briques/balle

## Semaine 5 (27/02/2024)

.Ce qui a été fait:

- 25-implementer-collision-balle-brique (Noaym):
    .Il nous manquait lacollision entre la balle et les briques, la voici.
    .La vie d'une brique se met bien à jour si la balle la touche.
    .Il reste des petits détails à regler pour que cela soit parfait (gérer les coins par exemple).

- 24-Restructuration_Et_Refactorisation_collision (Nic):
    .Un travail de restructuration a été réalisé et les collisions ont été déplacées dans un autre ficher.
    .C'est censé être le dernier "gros" travail de restructuration

- 30-27-physicsLaws (Lucas):
    .Migration du projet "physiques" au sein du projet "cassebrique".
    .Implémentation de concepts/formules scientifiques.

- 29_Start_Menu (Maxime):
    .Implémentation d'un menu quand le programme se lance.
    .On dispose maintenant d'une zone de jeu qui ne fait plus toute la fenêtre.
    .Il y a l'affichage des vies et des points du joueur ainsi que le nombre de briques restantes (maj de l'affichage).

- fusion_26_27: (Marius)
    .Implémentation de murs en haut, à droite et à gauche pour pouvoir gérer les collisions que entre objets.
    .On a désormais un rebond assez basique mais qui fonctionne très bien.
    .La balle réapparait à sa position initiale si elle sort de la fenêtre par le bas.

Ce qu'il y a à faire:

- GRADLE 
- mise en mouvement de la balle que si on touche sur une touche 
- continuation de la physique en parallèle
- win condition et gameover
- amélioration menu?

### Absent

Lucas

### À faire

- passer à un modèle de collision avec "biais" (calculer la normale plutôt que d'essayer de déterminer si on est dans le cas vertical ou horizontal -> une seule façon de traiter tous les cas)
- collision avec transmission de mouvement (prendre en compte coefficient de frottement de la surface et de la balle), ajouter propriété vitesse angulaire à la balle
- considérer objets-collision (contient: point d'impact, normale, mouvement transmis = coefficient de frottement * vitesse relative des objets en collision)
- gradle
- bonus
- physique (continuer étude biblio [https://femto-physique.fr/mecanique/physique-des-collisions.php])
- point sur l'architecture (préparer documents, on fera le point lors de la séance)

## Semaine 6 (05/03/2024)

### Ce qui a été fait

- start_ball (Marius):
    .Au début de la partie, la balle est immobile sur le paddle.
    .On peut déplacer le paddle et la balle reste sur le paddle.
    .Dès que la touche ESPACE est pressée, la balle se met en mouvement et la partie commence.
    .Si l'on fait tomber la balle, elle réapparait sur le paddle et redevient immobile.

- 30-27-physicsLaws (Lucas et Marius):
    .Pas mal de choses ont été implémenté pour la physique.
    .Cependant, on aimerait vraiment comprendre comment s'y prendre (utiliser la physique
    seulement pour gérer les collisions ou aussi pour le mouvement de la balle avec 
    une position, vitesse, accelération).
    .La physique n'est pas encore testable dans le jeu malgré les améliorations.


- 36-win-lose (Maxime)
    .Création d'un GameOver et Win panel. 
    .Win condition et lose condition en place. 
    .Correction de bug graphique.

- 33-fixer-le-probleme-de-collision-sur-les-bords (Noaym)
    .Debut de l'implementation de collision basee sur la phyisque (collision basee sur des angles)
    .refactoring du mouvement des entites en fonction de forceX et forceY
    .implementation de methode qui rendent la position au next frame update des entites
    .implementer dans GraphicalObject des methodes permettant de checkCollisions on next frame update

- Nic :
    .Branche 34, implémentation du squelette pour les bonus
    .Reconstruction d'une branche dû à un merge qui a inversé target et source branche
    .Diagramme UML

### À faire...

## Semaine 7 (12/03/2024)

### Ce qui a été fait

- Nic :
    .Branche 34 :
        - Réecriture de branche 34, les bonus sont maintenant dans une arrayList.
        - Restructuration minimale (qui sera utile pour la physique également):
            .Ajout de l'attribut speed pour les graphicalObject + get et setter correspondant
            .Réecriture des constructeurs des entitées pour qu'ils prennent en compte la vitesse  
            .misc
    .Ajout d'images
    .misc

- Maxime 
    - Création d'un menu in game.
    - Mise en pause de jeu quand on est dans le menu.
    - restart du jeu après une win ou un lose. 

- Lucas et Marius:
    - Calcul du point d'impact
    - Detremination de la vitesse du paddle en cours
    - rebond classique quasi pres a etre implémentésur develop (rebond rélaisé uniquement avec la physique)

- Noaym :
    .Fin de l'implementation de la gestion balle paddle
    .styling a un petit boutton du menu
    .debut d'etude de la physique

## À faire

Focus sur :

- refactoring objet
- physique (commencer vérifier les corner cases, pour réduire les bugs dans ce qui est déjà géré)

## Semaine 8 (19/3/2024)

### Ce qui a été fait:

- Marius: 
    .collision entre la balle et les murs parfait avec la physique
    .quelques cas critiques pour la collision balle paddle mais bien en général
    .réussi grace au calcul d'angle de reflexion, de vecteur normal etc...

- Maxime: 
    .Menu pour choisir le niveau.
    .Création d'un fichier level pour créer les niveaux.
    .Changement de la méthode start dans Breakout.
- Noaym:
    .Amélioration de la méthode getImpactPoint (elle est maintenant précise grace a l'utilisation des angles)
    .Début de la réparation de la collision balle brique (fix d'un bug qui causait les briques de continuer de collisioner même après disparition)
- Lucas:
  - prise en compte vitesse du paddle pour effet

### Absent

Nic JDC

### À faire

- plus de niveaux
- rebonds sur surfaces en biais
- merge branche physique
- quelques bugs de rebonds à corriger (entre 2 briques) -> penser à écrire des tests exécutables
- rotation (vitesse angulaire) de la balle (effets)
- 

## Semaine 9 (26/3/2024)

### Ce qui a été fait:

- Noaym, Lucas, Marius:
    .Implémentation du mode marathon

- Maxime
    .Création du niveau 2 avec une rotation de briques.
    .Fusion de la structure de sélection de niveau et de création de partie.
    .Adaptation du mode Marathon à cette nouvelle structure.

- Nic :
    .Légère restructuration du bonus 
    .misc


### À faire

- plus de niveaux
- rebonds sur surfaces en biais
- merge branche physique (URGENT)
- quelques bugs de rebonds à corriger (entre 2 briques) -> penser à écrire des tests exécutables
- rotation (vitesse angulaire) de la balle (effets)
- 


## Semaine 10 (2/4/2024)

### Ce qui a été fait:

- Le merge de physics et de develop etait un travaille conséquent, cette branche divergeait de develop depuis +4 semaines

- Marius:
    -   Merge de physics et de develop 

- Noaym :
    -   (enfin) fix de la collision ball et tout objets 
    -   grande amelioration de IsGoingToCollide avec la physique (maintenant tres precise et consistente)
    -   Merge de physics et de develop

- Lucas :
    -   Merge de physics et de develop

- Nic :
    - Merge manuelle de certaines anciennes branches finies en cours pour éviter tout conflit depuis l'ajout de la physique
    (La structuraction s'approche plus ou moins de sa forme finale, quelques modifications à faire suite au merge de la physique)


### À faire
- refactoring du code (faire que Entity extend PhysicalObject, permettra de clean up une grande partie du code)
- plus de niveaux
- rebonds sur surfaces en biais
- penser à écrire des tests exécutables
- rotation (vitesse angulaire) de la balle (effets)
- implémenter le respawn de la balle et l'actualisation du nopmbre de vies
- Pour Nic : Essayer de mettre chaque update de breakout des entités dans les classes respectives (En cours mais compliqué, et arrêté entre temps pour pas empiéter sur le merge de la physique) 
- Finir certains TODO (oubliés car ils n'étaient pas des tâches essentielles) 
- 

## Semaine 11 (23/4/2024)

### Ce qui a été fait:


- Noaym :
    -   Refactoring important du code comme mentionné dans le "A faire" précedent
    -   Implementé le bonus qui donne plusieurs balles
    -   added a basic ball trail
    -   another code refactor to remove the speed attribute from shapes 
    -   Implementation de champs gravitationels. L'implementation semble correcte mais ne fonctionne comme prevu pour l'instant

- Marius :
    - implémentation du respawn de la balle qui avait été perdu lors du merge de la physique
    - ajout d'une force de propulsion lors de la collision balle/paddle
    - ajout d'une vitesse maximale que la balle peut atteindre
    - graphismes de la balle, du fond du jeu 
    - touche M pour accéder au menu et R pour reprendre la game à partir du menu


### À faire
- plus de niveaux
- penser à écrire des tests exécutables
- rotation (vitesse angulaire) de la balle (effets)
- Pour Nic : Essayer de mettre chaque update de breakout des entités dans les classes respectives (En cours mais compliqué, et arrêté entre temps pour pas empiéter sur le merge de la physique) 
- Finir certains TODO (oubliés car ils n'étaient pas des tâches essentielles) 
- Améliorer le mode marathon (mouvement verticale semblerait plus approprié)
- Améliorer le visuel des briques en biais
- créer un bonus de vie maybe ?