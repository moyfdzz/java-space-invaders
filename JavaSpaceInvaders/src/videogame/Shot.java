package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author moisesfernandez
 */
public class Shot extends Item implements Constants {
    
    private int width;                  // the width of the player
    private int height;                 // the height of the player
    private Game game;
    private int velocity;               // variable for the velocity of the player
    /**
     * Constructor to initialize an object of the type Player with its attributes
     * @param width
     * @param height
     * @param game
     */

    public Shot(int x, int y,int width, int height, Game game, int velocity) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velocity = velocity;
    }

    public Shot(int x,int y, int width, int height, Game game) {
        super(x + H_SPACE , y - V_SPACE);
        this.width = width;
        this.height = height;
        this.game = game;
        
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
     * To get the velocity of the player
     * @return velocity
     */
    public int getVelocity() {
        return velocity;
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
     * To set the velocity of the player
     * @param velocity 
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }


    /**
     * To get the perimeter of the rectangle of the player's image
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
    public boolean intersecta(Shot obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    @Override
    public void tick() {    
        // The player moves to the left
        if (game.getKeyManager().isLEFT()) {
            setX(getX() - getVelocity());
        }
        
        // The player moves to the right
        if (game.getKeyManager().isRIGHT()) {
            setX(getX() + getVelocity());
        }
        
        if (getX() >= game.getWidth() -2 * getWidth()) { // right side of the player
            setX(game.getWidth() -  2 * getWidth());
        } else if (getX() <= 0) { // // left side of the player
            setX(0);
        }
    }
    /**
     * To render the image of the player
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shot, getX(), getY(), getWidth(), getHeight(), null);
    }

}
