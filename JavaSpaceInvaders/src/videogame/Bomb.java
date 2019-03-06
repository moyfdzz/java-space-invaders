
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author ErickFrank
 */
public class Bomb extends Item {
    
    private int width;
    private int height;
    private boolean destroyed;

    public Bomb(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.destroyed = true;
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

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
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
    public boolean intersecta(Player obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    
    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
    }

    
}
