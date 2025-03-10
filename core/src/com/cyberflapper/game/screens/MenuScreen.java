package com.cyberflapper.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.cyberflapper.game.CyberFlapperGame;

public class MenuScreen implements Screen {
    private final CyberFlapperGame game;
    private final OrthographicCamera camera;
    private final Texture background;
    private final Texture playButton;
    private final Rectangle playBounds;
    
    public MenuScreen(CyberFlapperGame game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CyberFlapperGame.WIDTH, CyberFlapperGame.HEIGHT);
        
        background = game.assets.get("textures/background.png", Texture.class);
        playButton = game.assets.get("textures/ui/play_button.png", Texture.class);
        
        // Zone cliquable du bouton play
        float buttonWidth = playButton.getWidth();
        float buttonHeight = playButton.getHeight();
        float x = (CyberFlapperGame.WIDTH / 2) - (buttonWidth / 2);
        float y = (CyberFlapperGame.HEIGHT / 2) - (buttonHeight / 2);
        playBounds = new Rectangle(x, y, buttonWidth, buttonHeight);
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
        
        // Gérer le clic sur le bouton play
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            
            if (playBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
        
        // Afficher les éléments du menu
        game.batch.begin();
        
        // Afficher le fond
        game.batch.draw(background, 0, 0, CyberFlapperGame.WIDTH, CyberFlapperGame.HEIGHT);
        
        // Afficher le titre
        game.font.draw(game.batch, "CYBER FLAPPER", 
                      CyberFlapperGame.WIDTH / 2 - 100, 
                      CyberFlapperGame.HEIGHT - 100);
        
        // Afficher le bouton play
        game.batch.draw(playButton, playBounds.x, playBounds.y);
        
        // Afficher le high score
        game.font.draw(game.batch, "HIGH SCORE: " + game.highScore, 
                      CyberFlapperGame.WIDTH / 2 - 80, 
                      CyberFlapperGame.HEIGHT / 4);
        
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