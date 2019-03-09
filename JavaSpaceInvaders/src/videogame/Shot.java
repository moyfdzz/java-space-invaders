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
    private boolean created;                 // the height of the player
    private Game game;
    /**
     * Constructor to initialize an object of the type Player with its attributes
     * @param width
     * @param height
     * @param game
     */

    public Shot(int x, int y,int width, int height, Game game, boolean created) {
        super(x + H_SPACE , y - V_SPACE);
        this.width = width;
        this.height = height;
        this.game = game;
        this.created = created;
        
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
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
     * To get the perimeter of the rectangle of the player's image
     * @return Rectangle
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    
    @Override
    public void tick() {   
        if(isCreated())
            setY(getY() - SHOT_SPEED);
        
        if(getY() <= 0)
        {
            setCreated(false);
        }

    }
    /**
     * To render the image of the player
     * @param g 
     */
    @Override
    public void render(Graphics g) {
            if(isCreated())
            g.drawImage(Assets.shot, getX(), getY(), getWidth(), getHeight(), null);
    }

}
