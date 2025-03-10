package com.cyberflapper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.cyberflapper.game.screens.MenuScreen;

public class CyberFlapperGame extends Game {
    public static final String TITLE = "Cyber Flapper";
    public static final float WIDTH = 480;
    public static final float HEIGHT = 800;
    
    public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager assets;
    
    public int highScore = 0;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new AssetManager();
        
        // Chargement des assets
        loadAssets();
        
        // Création de la police cyberpunk
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/cyber.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 2;
        parameter.color.set(0, 1, 1, 1); // Cyan néon
        parameter.borderColor.set(1, 0, 1, 1); // Rose néon
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor.set(0, 0, 0.5f, 0.75f);
        font = generator.generateFont(parameter);
        generator.dispose();
        
        // Lancer l'écran de menu
        this.setScreen(new MenuScreen(this));
    }
    
    private void loadAssets() {
        // Textures
        assets.load("textures/player.png", com.badlogic.gdx.graphics.Texture.class);
        assets.load("textures/obstacle.png", com.badlogic.gdx.graphics.Texture.class);
        assets.load("textures/background.png", com.badlogic.gdx.graphics.Texture.class);
        assets.load("textures/ui/play_button.png", com.badlogic.gdx.graphics.Texture.class);
        assets.load("textures/ui/game_over.png", com.badlogic.gdx.graphics.Texture.class);
        
        // Sons
        assets.load("sounds/jump.wav", Sound.class);
        assets.load("sounds/die.wav", Sound.class);
        assets.load("sounds/score.wav", Sound.class);
        assets.load("sounds/cyberpunk_loop.mp3", Music.class);
        
        // Attendre que tout soit chargé
        assets.finishLoading();
        
        // Démarrer la musique de fond
        Music music = assets.get("sounds/cyberpunk_loop.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.7f);
        music.play();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
    }
    
    public void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
        }
    }
}