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
 * @author moisesfernandez
 */
public class Brick extends Item {
    
    private int width;
    private int height;
    private Game game;
    private int lives;
    /**
     * Constructor to initialize an object of the type Brick with its attributes
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public Brick(int x, int y, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;      
        this.lives =  (int)(Math.random() * ((3 - 1) + 1)) + 1;
    }
    // to 
    public Brick(int x, int y, int width, int height, int lives, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;      
        this.lives =  lives;
    }

    /**
     * To get the number of lives of the brick
     * @return an <code>int</code> value with the number of lives of the brick
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * To set the number of lives of the brick
     * @param lives 
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    /**
     * To get the width of the window of the game
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * To get the height of the window of the game
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    /**
     * To set the width of the window of the game
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * To set the height of the window of the game
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * To get the perimeter of the rectangle of the enemy's image
     * @return Rectangle
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    /**
     * To determine if the object is intersecting with another object
     * @param obj
     * @return Rectangle
     */
    public boolean intersecta(Paddle obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    @Override
    public void tick() {    

    }
    
    /**
     * To render the image of the enemy
     * @param g 
     */
    @Override
    public void render(Graphics g) {

        g.drawImage(Assets.brickSkins[getLives()-1] , getX(), getY(), getWidth(), getHeight(), null);

    }

    @Override
    public String toString() {
        return  x + "," + y + "," + width + "," + height + "," + lives;
    }
    
}
