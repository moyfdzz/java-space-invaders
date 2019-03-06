/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author ErickFrank
 */
public class Ball extends Item {
    
    private int width;
    private int height;
    private Game game;
    private int velX;
    private int velY;
    private int maxVel;
    private boolean bottom;

    public Ball(int x, int y, int width, int height, Game game, int velX, int velY) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velX = velX;
        this.velY = velY;
        maxVel = 15;
        this.bottom = false;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getMaxVel() {
        return maxVel;
    }

    public void setMaxVel(int maxVel) {
        this.maxVel = maxVel;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }
    
    /**
     * To get the perimeter of the rectangle of the ball
     * @return Rectangle
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * To determine if the object is intersecting with the paddle
     * @param obj
     * @return Rectangle
     */
    public boolean intersecta(Paddle obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    /**
     * To determine if the object is intersecting with a brick
     * @param obj
     * @return Rectangle
     */
    public boolean intersecta(Brick obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    @Override
    public void tick() {
        setY(getY() + getVelY());  
        setX(getX() + getVelX()); 

        if (getX() + getWidth() >= game.getWidth()) { // Right margin of window
            setVelX(-getVelX());
            
        } else if (getX() <= 0) { // Left margin of window
            setVelX(-getVelX());
        }

        if (getY() <= 0) { // Top margin of window
            setVelY(-getVelY());
        } 
        if (getY() + getHeight() >= game.getHeight()) {
            setBottom(true);
        } 

        if (getVelX() > getMaxVel()) {
            setVelX(getMaxVel());
        }

        if (getVelY() > getMaxVel()) {
            setVelY(getMaxVel());
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public String toString() {
        return x + "," + y + "," + width + "," + height + "," + velX + "," + velY;
    }
    
}
