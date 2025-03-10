package com.cyberflapper.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.cyberflapper.game.CyberFlapperGame;

public class GameOverScreen implements Screen {
    private final CyberFlapperGame game;
    private final OrthographicCamera camera;
    private final Texture background;
    private final Texture gameOver;
    private final Rectangle retryBounds;
    private final int score;
    
    public GameOverScreen(CyberFlapperGame game, int score) {
        this.game = game;
        this.score = score;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CyberFlapperGame.WIDTH, CyberFlapperGame.HEIGHT);
        
        background = game.assets.get("textures/background.png", Texture.class);
        gameOver = game.assets.get("textures/ui/game_over.png", Texture.class);
        
        // Zone cliquable pour rejouer
        float buttonWidth = 300;
        float buttonHeight = 80;
        float x = (CyberFlapperGame.WIDTH / 2) - (buttonWidth / 2);
        float y = (CyberFlapperGame.HEIGHT / 2) - (buttonHeight / 2) - 100;
        retryBounds = new Rectangle(x, y, buttonWidth, buttonHeight);
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
        
        // Gérer le clic pour rejouer
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            
            if (retryBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
        
        // Afficher les éléments de l'écran de fin
        game.batch.begin();
        
        // Afficher le fond
        game.batch.draw(background, 0, 0, CyberFlapperGame.WIDTH, CyberFlapperGame.HEIGHT);
        
        // Afficher le game over
        float gameOverX = (CyberFlapperGame.WIDTH / 2) - (gameOver.getWidth() / 2);
        float gameOverY = (CyberFlapperGame.HEIGHT / 2) - (gameOver.getHeight() / 2) + 100;
        game.batch.draw(gameOver, gameOverX, gameOverY);
        
        // Afficher le score
        game.font.draw(game.batch, "SCORE: " + score, 
                      CyberFlapperGame.WIDTH / 2 - 50, 
                      gameOverY - 30);
                      
        // Afficher le high score
        game.font.draw(game.batch, "HIGH SCORE: " + game.highScore, 
                      CyberFlapperGame.WIDTH / 2 - 80, 
                      gameOverY - 60);
        
        // Afficher le bouton pour rejouer
        game.font.draw(game.batch, "TAP TO RETRY", 
                      CyberFlapperGame.WIDTH / 2 - 80, 
                      retryBounds.y + retryBounds.height / 2 + 5);
        
        game.batch.end();
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
        // Libérer les ressources
    }
}