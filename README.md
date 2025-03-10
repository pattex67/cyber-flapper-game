# Cyber Flapper

Un jeu inspiré de Flappy Bird dans un monde cyberpunk pour Android, développé avec libGDX.

## Description

Cyber Flapper est un jeu d'arcade de type "tap-to-fly" où vous contrôlez un personnage qui doit naviguer à travers des obstacles dans un monde cyberpunk. Le jeu utilise une esthétique néon et une ambiance futuriste pour offrir une expérience visuelle unique.

## Fonctionnalités

- Gameplay simple et addictif : tapez sur l'écran pour faire sauter votre personnage
- Esthétique cyberpunk avec effets visuels néon
- Système de score et high score
- Effets sonores et musique immersifs
- Interface utilisateur futuriste

## Comment compiler

1. Clonez ce dépôt
2. Ouvrez le projet dans Android Studio
3. Assurez-vous d'avoir les SDK Android nécessaires installés
4. Compilez et exécutez le projet sur votre appareil Android ou émulateur

## Structure du projet

Le projet utilise le framework libGDX et suit une architecture MVC :

- `android/` : Fichiers spécifiques à Android
- `core/` : Code principal du jeu, indépendant de la plateforme
  - `CyberFlapperGame.java` : Classe principale du jeu
  - `screens/` : Différents écrans du jeu (menu, jeu, game over)
  - `entities/` : Entités du jeu (joueur, obstacles)

## Ressources requises

Pour que le jeu fonctionne correctement, vous devez ajouter les ressources suivantes dans le dossier `android/assets` :

- `fonts/cyber.ttf` : Police d'écriture cyberpunk
- `sounds/` : Fichiers audio (jump.wav, die.wav, score.wav, cyberpunk_loop.mp3)
- `textures/` : Images du jeu (player.png, obstacle.png, background.png)
- `textures/ui/` : Images de l'interface (play_button.png, game_over.png)

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.