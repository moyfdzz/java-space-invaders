/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moisesfernandez
 */
public class Game implements Runnable, Constants {

    private BufferStrategy bs;          // to have several buffers when	displaying
    private Graphics g;                 // to paint objects
    private Display display;            // to display in the game
    String title;			// the title of the window												//	title	of	the	window
    private final int width;		// width of the	window
    private final int height;		// height of the window
    private Thread thread;              // thread to create the	game
    private boolean running;            // to set the game
    private KeyManager keyManager;      // variable for the key manager
    
    
    private Player player;              // variable for the paddle
    private LinkedList<Alien> aliens;   // linked list of the aliens of the game
    private Shot shot;
    
    
    private boolean gameOver;           // to determine if the game is over
    private boolean paused;             // to determine if the game is paused
    private boolean start;             // to determine if the game is paused
        
    private String lastSave;    //Nombre del archivo.
    private String message;    //Nombre del archivo.
        
    /**
     * To create game with title, width, height and status of running
     * @param title
     * @param width
     * @param height 
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        this.gameOver = false;
        this.paused = false;
        this.start = false;
        this.lastSave = "lastSave.txt";
        this.message = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Returns a boolean value to know whether the game is paused or not
     * @return paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the status of the game either paused or not paused
     * @param paused 
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    /**
     * Returns the width of the window
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the window
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a boolean value to know whether the game is over or not
     * @return 
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the status of the game either over or not over
     * @param gameOver 
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
    
    /**
     * Returns the buffer strategy
     * @return bs
     */
    public BufferStrategy getBs() {
        return this.bs;
    }

    /**
     * Sets the buffer strategy
     * @param bs 
     */
    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    /**
     * Returns the graphics
     * @return g
     */
    public Graphics getG() {
        return this.g;
    }

    /**
     * Sets the graphics
     * @param g 
     */
    public void setG(Graphics g) {
        this.g = g;
    }

    /**
     * Returns the display
     * @return display
     */
    public Display getDisplay() {
        return this.display;
    }

    /**
     * Sets the display
     * @param display 
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * Returns the title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the thread
     * @return 
     */
    public Thread getThread() {
        return this.thread;
    }

    /**
     * Sets the thread
     * @param thread 
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    /**
     * Returns the status of running
     * @return running
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Sets the status of running
     * @param running 
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Returns the paddle
     * @return paddle
     */
    public Player getpaddle() {
        return this.paddle;
    }

    /**
     * Sets the paddle
     * @param paddle 
     */
    public void setpaddle(Player paddle) {
        this.paddle = paddle;
    }
    
    /**
     * Sets the key manager
     * @param keyManager 
     */
    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }
    
    /**
     * initializing	the	display	window	of	the	game
     */
    private void init() {
        setDisplay(new Display(getTitle(), getWidth(), getHeight())) ;
        
        Assets.init();
        //play the theme song of the game
        
        Assets.theme.play();
        aliens = new LinkedList<Alien>();
        
        getDisplay().getJframe().addKeyListener(getKeyManager());
                
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien( ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
        

    }
    
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void tick() throws IOException {
        keyManager.tick();
        
        if(getKeyManager().isP() == true) {
            setPaused(!isPaused());
            getKeyManager().setP(false);
        }
        if(getKeyManager().isSPACE() == true){
           setStart(true);
           getKeyManager().setSPACE(false);
        }
        
        if(getKeyManager().isG() == true)
        {
            saveGame();
            getKeyManager().setG(false);
        }
        if(getKeyManager().isC() == true)
        {
            loadGame();
            getKeyManager().setC(false);
        }
         
        //GameOver
        if (player.isDying()) {
            setMessage("Game lost!");
            setGameOver(true);
            Assets.theme.stop();
        }
        
        if(player.getDeaths() == NUMBER_OF_ALIENS_TO_DESTROY)
        {
            setMessage("Game won!");
            setGameOver(true);   
        }
        //running
        if(!isGameOver() && !isPaused()){
    
            for (int i = 0; i < aliens.size(); i++) {
                
                if(aliens.get(i).isDying()) {
                    aliens.remove(aliens.get(i));
                }
                else {
                    aliens.get(i).tick();
                }
            }
            shot.tick();
            player.tick();
        }

        if(isGameOver() && getKeyManager().isR() == true) {
            setGameOver(false);
            restartGame();
            getKeyManager().setR(false);   
        }
        
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one	with 3 buffers to display images of
           the game, if	not null, then we display every	image of the game but
           after clearing the Rectanlge, getting the graphic object from the	
           buffer strategy element. show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            
            g = bs.getDrawGraphics();
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            g.setColor(Color.WHITE);
            
            
            if(!isGameOver()) {
                
                player.render(g);
                shot.render(g);
                for (int i = 0; i < aliens.size(); i++) {
                        aliens.get(i).render(g);
                        aliens.get(i).getBomb().render(g);
                }

            }
            if(isPaused())
            {
                g.setFont(new Font("Serif", Font.BOLD, 120));
                g.drawString("Paused", getWidth()/2-200, getHeight()/2);
            
            }
            if(isGameOver())
            {
                 g.setColor(Color.black);
                 g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
                 g.setColor(new Color(0, 32, 48));
                 g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
                 g.setColor(Color.white);
                 g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
                 Font small = new Font("Helvetica", Font.BOLD, 14);
                 FontMetrics metr = g.getFontMetrics(small);
                 g.setColor(Color.white);
                 g.setFont(small);
                 g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);

            }
            bs.show();
            g.dispose();
        }
        
    }
    
    public synchronized void start() {
        if(!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public synchronized void stop() {
        if(running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        init();
        int fps = 30;
        double timeTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
	while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            
            if (delta >= 1) {
                try {
                    tick();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                render();
                delta--;
            }
        }
        stop();
    }

    private void saveGame() throws IOException {
                                                          
        PrintWriter fileOut = new PrintWriter(new FileWriter(lastSave));
        fileOut.println(bricks.size());
        for (int i = 0; i < bricks.size(); i++) {
            fileOut.println(bricks.get(i).getX());
            fileOut.println(bricks.get(i).getY());
            fileOut.println(bricks.get(i).getLives());
        }
        fileOut.println(ball.getX());
        fileOut.println(ball.getY());
        fileOut.println(ball.getVelX());
        fileOut.println(ball.getVelY());
        
        fileOut.println(paddle.getX());
        fileOut.println(paddle.getY());
        fileOut.println(paddle.getLives());
        fileOut.println(paddle.getScore());
       
        fileOut.close();
                
    }
    
    
    private void loadGame() throws IOException
    {
        setStart(false);
        bricks.clear();
        BufferedReader fileIn;
              try {
                      fileIn = new BufferedReader(new FileReader(lastSave));
              } catch (FileNotFoundException e){
                      File puntos = new File(lastSave);
                      PrintWriter fileOut = new PrintWriter(puntos);
                      fileOut.println("100,demo");
                      fileOut.close();
                      fileIn = new BufferedReader(new FileReader(lastSave));
              }
              String dato = fileIn.readLine();
              int num = Integer.parseInt(dato);
              int brickX, brickY,brickLives;
              
              for(int i = 0; i < num ; i++)   
              {
                         brickX = Integer.parseInt(fileIn.readLine());
                         brickY = Integer.parseInt(fileIn.readLine());
                         brickLives = Integer.parseInt(fileIn.readLine());
                         
                    bricks.add(new Brick(brickX,brickY,70,25,brickLives, this));
              }
              int bX,bY,bVelX, bVelY;
                bX = Integer.parseInt(fileIn.readLine());
                bY = Integer.parseInt(fileIn.readLine());
                bVelX = Integer.parseInt(fileIn.readLine());
                bVelY = Integer.parseInt(fileIn.readLine());
                ball = new Shot(bX,bY,50,50,this,bVelX,bVelY);
                
              int pX,pY,pScore, pLives;
                pX = Integer.parseInt(fileIn.readLine());
                pY = Integer.parseInt(fileIn.readLine());
                pLives = Integer.parseInt(fileIn.readLine());
                pScore = Integer.parseInt(fileIn.readLine());
                paddle = new Player(120,30,this,10, pLives, pScore, pX,pY);

              fileIn.close();
    }

    private void restartGame() {
       setStart(false);
       paddle.setScore(0);
       paddle.setLives(3);
       paddle.setX(getWidth()/2);
       paddle.setY(getHeight() - 100);
       ball.setX(this.getWidth()/2);
       ball.setY(this.getHeight()/2-50);
       for (int i = 0; i <= 10; i++) {
            bricks.add(new Brick(100*i+25, 25, 70, 25, this));
        }
        for (int i = 0; i <= 9; i++) {
            bricks.add(new Brick(100*i+75, 75, 70, 25, this));
        }
        for (int i = 0; i <= 10; i++) {
            bricks.add(new Brick(100*i+25, 125, 70, 25, this));
        }
        for (int i = 0; i <= 9; i++) {
            bricks.add(new Brick(100*i+75, 175, 70, 25, this));
        }
    }
   

}

