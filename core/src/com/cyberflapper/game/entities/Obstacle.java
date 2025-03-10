package com.cyberflapper.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cyberflapper.game.CyberFlapperGame;

import java.util.Random;

public class Obstacle {
    private static final float SCROLL_SPEED = 200;
    private static final int OBSTACLE_GAP = 200;
    private static final int OBSTACLE_WIDTH = 70;
    
    private final Vector2 posTop;
    private final Vector2 posBottom;
    private final Rectangle boundsTop;
    private final Rectangle boundsBottom;
    private final Random random;
    private final Texture texture;
    private boolean counted = false;
    
    public Obstacle(float x, Texture texture) {
        this.texture = texture;
        random = new Random();
        
        // Calculer les positions des obstacles haut et bas
        int gap = random.nextInt(250) + 100;
        int height = random.nextInt(150) + 100;
        
        posTop = new Vector2(x, gap + OBSTACLE_GAP);
        posBottom = new Vector2(x, gap - OBSTACLE_GAP - height);
        
        boundsTop = new Rectangle(posTop.x, posTop.y, OBSTACLE_WIDTH, texture.getHeight());
        boundsBottom = new Rectangle(posBottom.x, posBottom.y, OBSTACLE_WIDTH, texture.getHeight());
    }
    
    public void update(float delta) {
        // Déplacer les obstacles vers la gauche
        posTop.x -= SCROLL_SPEED * delta;
        posBottom.x -= SCROLL_SPEED * delta;
        
        // Mettre à jour les hitbox
        boundsTop.setPosition(posTop.x, posTop.y);
        boundsBottom.setPosition(posBottom.x, posBottom.y);
    }
    
    public void render(SpriteBatch batch) {
        // Dessiner l'obstacle du haut
        batch.draw(texture, posTop.x, posTop.y, OBSTACLE_WIDTH, texture.getHeight());
        // Dessiner l'obstacle du bas (à l'envers)
        batch.draw(texture, posBottom.x, posBottom.x, OBSTACLE_WIDTH, texture.getHeight(), 
                  0, 0, texture.getWidth(), texture.getHeight(), false, true);
    }
    
    public boolean isOffScreen() {
        return posTop.x + OBSTACLE_WIDTH < 0;
    }
    
    public boolean collides(Player player) {
        return boundsTop.overlaps(player.getBounds().getBoundingRectangle()) || 
               boundsBottom.overlaps(player.getBounds().getBoundingRectangle());
    }
    
    public boolean isCounted() {
        return counted;
    }
    
    public void setCounted(boolean counted) {
        this.counted = counted;
    }
    
    public float getX() {
        return posTop.x;
    }
    
    public void dispose() {
        texture.dispose();
    }
}