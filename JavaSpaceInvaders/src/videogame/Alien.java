package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author moisesfernandez
 */
public class Alien extends Item implements Constants {
    
    private int width;                  // the width of the player
    private int height;                 // the height of the player
    private Game game;
    private int velocity;               // variable for the velocity of the player
    private int chance;                  // the lives of the player
    private Bomb bomb;
    private int direction;
    private boolean dying;
    

    public Alien(int x, int y)
    {
       super(x, y);
    }
    public Alien(int x, int y, int width, int height, int direction, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.velocity = 1;
        this.chance = CHANCE;
        this.bomb = new Bomb(x,y,BOMB_WIDTH,BOMB_HEIGHT);
        this.direction = direction;
    }
    /**
     * To get if the alien is dying
     * @return dying
     */
    public boolean isDying() {
        return dying;
    }
    /**
     * To set if the the alien is dying
     * @param dying 
     */
    public void setDying(boolean dying) {
        this.dying = dying;
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

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
        setX(getX()+getDirection());
    }
    
    /**
     * To render the image of the player
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
    }

}
