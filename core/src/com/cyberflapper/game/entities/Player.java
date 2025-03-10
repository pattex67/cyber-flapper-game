package com.cyberflapper.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private static final float GRAVITY = -15;
    private static final float JUMP_VELOCITY = 350;
    private static final float ANIMATION_SPEED = 0.1f;
    
    private final Vector2 position;
    private final Vector2 velocity;
    private final Circle bounds;
    
    private final Texture texture;
    private final Animation<TextureRegion> animation;
    private float stateTime;
    
    private final Sound jumpSound;
    
    public Player(float x, float y, Texture texture, Sound jumpSound) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.bounds = new Circle(x, y, 25);
        
        this.texture = texture;
        
        // Créer l'animation à partir de la spritesheet
        TextureRegion[][] regions = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight());
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            frames[i] = regions[0][i];
        }
        this.animation = new Animation<>(ANIMATION_SPEED, frames);
        this.stateTime = 0;
        
        this.jumpSound = jumpSound;
    }
    
    public void update(float delta) {
        stateTime += delta;
        
        // Appliquer la gravité
        velocity.add(0, GRAVITY);
        
        // Limiter la vitesse de chute
        if (velocity.y < -400) {
            velocity.y = -400;
        }
        
        // Mettre à jour la position
        position.add(velocity.x * delta, velocity.y * delta);
        
        // Mettre à jour la hitbox
        bounds.setPosition(position.x + texture.getWidth() / 8, position.y + texture.getHeight() / 2);
        
        // Empêcher le joueur de tomber hors de l'écran
        if (position.y <= 0) {
            position.y = 0;
            velocity.y = 0;
        }
    }
    
    public void jump() {
        velocity.y = JUMP_VELOCITY;
        jumpSound.play(0.5f);
    }
    
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }
    
    public void dispose() {
        texture.dispose();
    }
    
    public Vector2 getPosition() {
        return position;
    }
    
    public Circle getBounds() {
        return bounds;
    }
}