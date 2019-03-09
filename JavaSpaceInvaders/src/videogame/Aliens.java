/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author ErickFrank
 */
public class Aliens implements Constants {

    private ArrayList<Alien> aliens;
    private int direction;

    public Aliens() {
        aliens = new ArrayList<Alien>();
        direction = 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

    }

    /**
     * To render the image of the player
     *
     * @param g
     */
    public void render(Graphics g) {

        for (int i = 0; i < aliens.size(); i++) {
            aliens.get(i).render(g);
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
        public void tick() {

        for (int i = 0; i < aliens.size(); i++) {
    
             if (aliens.get(aliens.size()-1).getX() >= BOARD_WIDTH - BORDER_RIGHT && getDirection() != -1) {
                  setDirection(-1);
                  
                  Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }

             }
             
             if (aliens.get(0).getX() <= BORDER_LEFT && getDirection() != 1) {
                   setDirection(1);
                    Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {
                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
               }
        }
        
        for (int i = 0; i < aliens.size(); i++) {
            
            aliens.get(i).tick();
        }
  
    }

    private class Alien extends Item {

        private int width;                  // the width of the player
        private int height;                 // the height of the player
        private Game game;
        private int velocity;               // variable for the velocity of the player
        private Bomb bomb;
        private boolean dying;

        public Alien(int x, int y) {
            super(x, y);
            this.width = ALIEN_WIDTH;
            this.height = ALIEN_HEIGHT;
            this.game = game;
            this.velocity = 1;
            this.bomb = new Bomb(x, y, BOMB_WIDTH, BOMB_HEIGHT,BOMB_SPEED);
        }


        /**
         * To get if the alien is dying
         *
         * @return dying
         */
        public boolean isDying() {
            return dying;
        }

        /**
         * To set if the the alien is dying
         *
         * @param dying
         */
        public void setDying(boolean dying) {
            this.dying = dying;
        }

        /**
         * To get the width of the window of the game
         *
         * @return an <code>int</code> value with the width
         */
        public int getWidth() {
            return width;
        }

        /**
         * To get the height of the window of the game
         *
         * @return an <code>int</code> value with the height
         */
        public int getHeight() {
            return height;
        }

        /**
         * To get the velocity of the player
         *
         * @return velocity
         */
        public int getVelocity() {
            return velocity;
        }

        /**
         * To set the width of the window of the game
         *
         * @param width
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * To set the height of the window of the game
         *
         * @param height
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * To set the velocity of the player
         *
         * @param velocity
         */
        public void setVelocity(int velocity) {
            this.velocity = velocity;
        }

        public Bomb getBomb() {
            return bomb;
        }

        public void setBomb(Bomb bomb) {
            this.bomb = bomb;
        }

        /**
         * To get the perimeter of the rectangle of the player's image
         *
         * @return Rectangle
         */
        public Rectangle getPerimetro() {
            return new Rectangle(getX(), getY(), getWidth(), getHeight());
        }

        /**
         * To determine if the object is intersecting with another object
         *
         * @param obj
         * @return Rectangle
         */
        public boolean intersecta(Shot obj) {
            return getPerimetro().intersects(obj.getPerimetro());
        }

        @Override
        public void tick() {
            setX(getX() + getDirection());
        }

        /**
         * To render the image of the player
         *
         * @param g
         */
        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
        }

        private class Bomb extends Item {

            private int width;
            private int height;
            private boolean destroyed;
            private int speed;
            private boolean dropped;

            public Bomb(int x, int y, int width, int height, int speed) {
                super(x, y);
                this.width = width;
                this.height = height;
                this.destroyed = true;
                this.speed = speed;
                this.dropped = false;
            }

            public boolean isDropped() {
                return dropped;
            }

            public void setDropped(boolean dropped) {
                this.dropped = dropped;
            }
            
            public int getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
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
             *
             * @return Rectangle
             */
            public Rectangle getPerimetro() {
                return new Rectangle(getX(), getY(), getWidth(), getHeight());
            }
            /**
             * To determine if the object is intersecting with the paddle
             *
             * @param obj
             * @return Rectangle
             */
            public boolean intersecta(Player obj) {
                return getPerimetro().intersects(obj.getPerimetro());
            }

            @Override
            public void tick() {
                setY(getY() + getSpeed());
                
                if (getY() >= GROUND - BOMB_HEIGHT) {
                    setDropped(false);
                }
                
            }

            @Override
            public void render(Graphics g) {
                if(isDropped())
                g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
            }

        }

    }
}