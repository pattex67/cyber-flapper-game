package com.cyberflapper.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.cyberflapper.game.CyberFlapperGame;
import com.cyberflapper.game.entities.Obstacle;
import com.cyberflapper.game.entities.Player;

public class GameScreen implements Screen {
    private static final int OBSTACLE_SPACING = 300;
    private static final int OBSTACLE_COUNT = 4;
    
    private final CyberFlapperGame game;
    private final OrthographicCamera camera;
    private final Player player;
    private final Array<Obstacle> obstacles;
    private final Texture background;
    
    private final Sound scoreSound;
    private final Sound dieSound;
    
    private int score = 0;
    private float timeSinceObstacle = 0;
    
    public GameScreen(CyberFlapperGame game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CyberFlapperGame.WIDTH, CyberFlapperGame.HEIGHT);
        
        // Initialiser le joueur
        Texture playerTexture = game.assets.get("textures/player.png", Texture.class);
        Sound jumpSound = game.assets.get("sounds/jump.wav", Sound.class);
        player = new Player(CyberFlapperGame.WIDTH / 4, CyberFlapperGame.HEIGHT / 2, playerTexture, jumpSound);
        
        // Initialiser le fond
        background = game.assets.get("textures/background.png", Texture.class);
        
        // Récupérer les sons
        scoreSound = game.assets.get("sounds/score.wav", Sound.class);
        dieSound = game.assets.get("sounds/die.wav", Sound.class);
        
        // Initialiser les obstacles
        obstacles = new Array<>();
        Texture obstacleTexture = game.assets.get("textures/obstacle.png", Texture.class);
        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            obstacles.add(new Obstacle(i * OBSTACLE_SPACING + CyberFlapperGame.WIDTH, obstacleTexture));
        }
    }
    
    @Override
    public void show() {
        // Méthode appelée quand l'écran devient l'écran actif
    }
    
    @Override
    public void render(float delta) {
        // Effacer l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Mettre à jour la caméra
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        // Gérer les entrées utilisateur (tap/clic pour sauter)
        if (Gdx.input.justTouched()) {
            player.jump();
        }
        
        // Mettre à jour le joueur
        player.update(delta);
        
        // Mettre à jour et gérer les obstacles
        updateObstacles(delta);
        
        // Vérifier les collisions
        checkCollisions();
        
        // Afficher les éléments du jeu
        game.batch.begin();
        
        // Afficher le fond
        game.batch.draw(background, 0, 0, CyberFlapperGame.WIDTH, CyberFlapperGame.HEIGHT);
        
        // Afficher les obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.render(game.batch);
        }
        
        // Afficher le joueur
        player.render(game.batch);
        
        // Afficher le score
        game.font.draw(game.batch, "SCORE: " + score, 20, CyberFlapperGame.HEIGHT - 20);
        
        game.batch.end();
    }
    
    private void updateObstacles(float delta) {
        timeSinceObstacle += delta;
        
        // Mettre à jour tous les obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
            
            // Compter les obstacles passés
            if (!obstacle.isCounted() && obstacle.getX() + 70 < player.getPosition().x) {
                obstacle.setCounted(true);
                score++;
                scoreSound.play(0.5f);
            }
            
            // Recycler les obstacles sortis de l'écran
            if (obstacle.isOffScreen()) {
                float farthestX = 0;
                for (Obstacle o : obstacles) {
                    if (o.getX() > farthestX) {
                        farthestX = o.getX();
                    }
                }
                obstacle = new Obstacle(farthestX + OBSTACLE_SPACING, 
                                       game.assets.get("textures/obstacle.png", Texture.class));
            }
        }
    }
    
    private void checkCollisions() {
        // Vérifier les collisions avec les obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle.collides(player)) {
                dieSound.play();
                game.updateHighScore(score);
                game.setScreen(new GameOverScreen(game, score));
            }
        }
        
        // Vérifier si le joueur est sorti de l'écran par le haut
        if (player.getPosition().y + 50 > CyberFlapperGame.HEIGHT) {
            dieSound.play();
            game.updateHighScore(score);
            game.setScreen(new GameOverScreen(game, score));
        }
    }
    
    @Override
    public void resize(int width, int height) {
        // Adapter la vue en cas de redimensionnement
    }
    
    @Override
    public void pause() {
        // Mettre en pause
    }
    
    @Override
    public void resume() {
        // Reprendre
    }
    
    @Override
    public void hide() {
        // Quand l'écran n'est plus l'écran actif
    }
    
    @Override
    public void dispose() {
        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
        player.dispose();
    }
}