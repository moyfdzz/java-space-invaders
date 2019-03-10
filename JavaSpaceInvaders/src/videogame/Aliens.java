/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author ErickFrank
 */
public class Aliens implements Constants {

    private ArrayList<Alien> aliens;    // Linked List for the aliens
    private int direction;              // Direction of the aliens

    public Aliens() {
        aliens = new ArrayList<Alien>();
        direction = -1;

        // Loops to create the aliens and add them to the linked list
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
    }
    
    /**
     * To save the information of the aliens in a file
     * @param pw 
     */
    public void save(PrintWriter pw) {

        for (int i = 0; i < aliens.size(); i++) {
            
            pw.println(aliens.get(i).getX());
            pw.println(aliens.get(i).getY());
            pw.println(aliens.get(i).isDying() ? 1 : 0);
            pw.println(aliens.get(i).getBomb().isCreated() ? 1 : 0);
            pw.println(aliens.get(i).getBomb().getX());
            pw.println(aliens.get(i).getBomb().getY());
        }
        
    }
    
    /**
     * To load the information of the aliens from a file
     * @param br
     * @throws IOException 
     */
    public void load(BufferedReader br) throws IOException {

        for (int i = 0; i < aliens.size(); i++) {

            aliens.get(i).setX(Integer.parseInt(br.readLine()));
            aliens.get(i).setY(Integer.parseInt(br.readLine()));
            aliens.get(i).setDying(Integer.parseInt(br.readLine()) == 1);
            aliens.get(i).getBomb().setCreated(Integer.parseInt(br.readLine()) == 1);
            aliens.get(i).getBomb().setX(Integer.parseInt(br.readLine()));
            aliens.get(i).getBomb().setY(Integer.parseInt(br.readLine()));
        }
        
    }
    
    /**
     * To render the image of the alien
     * @param g
     */
    public void render(Graphics g) {

        for (int i = 0; i < aliens.size(); i++) {
            aliens.get(i).render(g);
        }
    }

    /**
     * To get the direction of the alien
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * To set the direction of the alien
     * @param direction 
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void tick() {

        // To move the aliens
        for (int i = 0; i < aliens.size(); i++) {
            int x = aliens.get(i).getX();
            
            if (x >= BOARD_WIDTH - BORDER_RIGHT && getDirection() != -1) {
                setDirection(-1);
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && getDirection() != 1) {
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
    
    /**
     * Method that returns if a bomb of the aliens hit the player
     * @param obj
     * @return true or false
     */
    public boolean intersectaBomb(Player obj) {
            
            for(int i = 0; i < aliens.size(); i++)
            {
                if(aliens.get(i).getBomb().intersecta(obj))
                    return true;
            }
            
            return false;
    }
    
    /**
     * Method that returns if an alien got hit by the player's shot
     * @param obj
     * @return 
     */
    public boolean intersectaShot(Shot obj) {
        for(int i = 0; i < aliens.size(); i++) {
            if(aliens.get(i).intersectaShot(obj) && !aliens.get(i).isDying()) {
                 aliens.get(i).setDying(true);
                 return true;
            }
                    
        }
            
        return false;
    }
        
    /**
     * Method that returns if an alien reaches the bottom
     * @return true or false
     */
    public boolean reachesBottom() { 
        for(int i = 0; i < aliens.size(); i++){
            if(aliens.get(i).getY() > GROUND - ALIEN_HEIGHT && !aliens.get(i).isDying()) {
                return true;
            }
                    
        }
            
        return false;
    }
    
    
    // Private class of the alien
    private class Alien extends Item {

        private int width;                  // the width of the player
        private int height;                 // the height of the player
        private Game game;                  // variable for the game
        private int velocity;               // variable for the velocity of the alien
        private Bomb bomb;                  // variable for the bomb
        private boolean dying;              // variable for the status of the alien

        /**
         * Constructor of the alien
         * @param x
         * @param y 
         */
        public Alien(int x, int y) {
            super(x, y);
            this.width = ALIEN_WIDTH;
            this.height = ALIEN_HEIGHT;
            this.game = game;
            this.velocity = 1;
            this.bomb = new Bomb(x, y, BOMB_WIDTH, BOMB_HEIGHT);
            this.dying = false;
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
         * To get the velocity of the alien
         *
         * @return velocity
         */
        public int getVelocity() {
            return velocity;
        }

        /**
         * To set the width of the window of the alien
         *
         * @param width
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * To set the height of the window of the alien
         *
         * @param height
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * To set the velocity of the alien
         *
         * @param velocity
         */
        public void setVelocity(int velocity) {
            this.velocity = velocity;
        }

        /**
         * To get the bomb
         * @return return
         */
        public Bomb getBomb() {
            return bomb;
        }

        /**
         * To set the bomb
         * @param bomb 
         */
        public void setBomb(Bomb bomb) {
            this.bomb = bomb;
        }

        /**
         * To get the perimeter of the rectangle of the alien's image
         *
         * @return Rectangle
         */
        public Rectangle getPerimetro() {
            return new Rectangle(getX(), getY(), getWidth(), getHeight());
        }

        /**
         * To determine if the alien intersects with the shot
         *
         * @param obj
         * @return Rectangle
         */
        public boolean intersectaShot(Shot obj) {
            return getPerimetro().intersects(obj.getPerimetro());
        }
        
        @Override
        public void tick() {
            if(!isDying()) {
                setX(getX() + getDirection());

                if (!bomb.isCreated()) {
                    int chance = (int) (Math.random() * 15);

                    if (chance == CHANCE) {
                        bomb.setCreated(true);
                        bomb.setX(getX());
                        bomb.setY(getY());
                    }
                }
            bomb.tick();
            }
            
        }

        /**
         * To render the image of the alien
         *
         * @param g
         */
        @Override
        public void render(Graphics g) {
            if(!isDying())
            {
            g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
            bomb.render(g);
            }
        }

        // Class for the bomb
        private class Bomb extends Item {

            private int width;
            private int height;
            private boolean created;
            private int speed;

            /**
             * Constructor of the bomb
             * @param x
             * @param y
             * @param width
             * @param height 
             */
            public Bomb(int x, int y, int width, int height) {
                super(x, y);
                this.width = width;
                this.height = height;
                this.created = false;
                this.speed = BOMB_SPEED;
            }

            /**
             * To get the speed of the bomb
             * @return speed
             */
            public int getSpeed() {
                return speed;
            }

            /**
             * To set the speed of the bomb
             * @param speed 
             */
            public void setSpeed(int speed) {
                this.speed = speed;
            }

            /**
             * To get the width of the bomb
             * @return width
             */
            public int getWidth() {
                return width;
            }

            /**
             * To set the width of the bomb
             * @param width 
             */
            public void setWidth(int width) {
                this.width = width;
            }

            /**
             * To get the height of the bomb
             * @return 
             */
            public int getHeight() {
                return height;
            }

            /**
             * To set the height of the bomb
             * @param height 
             */
            public void setHeight(int height) {
                this.height = height;
            }

            /**
             * To get the status of the bomb whether it is created or not
             * @return 
             */
            public boolean isCreated() {
                return created;
            }

            /**
             * To set the status of the bomb whether it is created or not
             * @param created 
             */
            public void setCreated(boolean created) {
                this.created = created;
            }

            /**
             * To get the perimeter of the rectangle of the bomb
             *
             * @return Rectangle
             */
            public Rectangle getPerimetro() {
                return new Rectangle(getX(), getY(), getWidth(), getHeight());
            }

            /**
             * To determine if the bomb is intersecting with the player
             *
             * @param obj
             * @return Rectangle
             */
            public boolean intersecta(Player obj) {
                return getPerimetro().intersects(obj.getPerimetro());
            }

            @Override
            public void tick() {

                // For the bomb to move down
                if (isCreated()) {
                    
                    setY(getY() + BOMB_SPEED);
                        
                    if (getY() >= GROUND - BOMB_HEIGHT) {
                        
                        setCreated(false);
                    }
                }
                

            }

            @Override
            public void render(Graphics g) {

                if (isCreated()) {
                    g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
                }
            }

        }

    }
}
