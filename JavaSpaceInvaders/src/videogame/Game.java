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
import java.util.ArrayList;
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
    private Aliens aliens;   // linked list of the aliens of the game
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
        
        //Assets.theme.play();
        
        aliens = new Aliens();
        player =  new Player(START_X, START_Y,PLAYER_WIDTH,PLAYER_HEIGHT, this, 2);
        shot = new Shot(0,GROUND,SHOT_WIDTH,SHOT_HEIGHT, this,false);
        
        getDisplay().getJframe().addKeyListener(getKeyManager());
    }
    
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    private void tick() throws IOException {
        
        keyManager.tick();
        
        if(getKeyManager().isP() == true) {
            setMessage("Paused!");
            setPaused(!isPaused());
            getKeyManager().setP(false);
        }
        if(getKeyManager().isSPACE() == true){
            
           if(!shot.isCreated())
           {
               shot = new Shot(player.getX(),player.getY(),SHOT_HEIGHT,SHOT_WIDTH,this,true);
           }

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
        if (aliens.intersectaBomb(player)) {
            setMessage("Game lost!");
            setGameOver(true);
            //Assets.theme.stop();
        }
        
        if (aliens.intersectaShot(shot)) {
            shot.setCreated(false);
            shot.setX(player.getX());
            shot.setY(player.getY());
            player.setDeaths(player.getDeaths()+1);
            //Assets.theme.stop();
        }
        
        if (aliens.reachesBottom()) {
                    setGameOver(true);
                    message = "Invasion!";
        }

        
        if(player.getDeaths() == NUMBER_OF_ALIENS_TO_DESTROY)
        {
            setMessage("Game won!");
            setGameOver(true);   
        }
        
        
        //running
        if(!isGameOver() && !isPaused()){
    
            aliens.tick();
            shot.tick();
            player.tick();
        }
        
        if(getKeyManager().isR() == true) {
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
            g.clearRect(0, 0, width, height);
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            
            g.setColor(Color.GREEN);
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            
            
            if(!isGameOver()) {
                
                player.render(g);
                shot.render(g);
                aliens.render(g);

            }
          
            if(isPaused())
            {
                g.setColor(Color.black);
                 g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
                 g.setColor(new Color(0, 32, 48));
                 g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
                 g.setColor(Color.white);
                 g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
                 Font small = new Font("Helvetica", Font.BOLD, 14);
                 FontMetrics metr = display.getJframe().getFontMetrics(small);
                 g.setColor(Color.white);
                 g.setFont(small);
                 g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
            
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
                 FontMetrics metr = display.getJframe().getFontMetrics(small);
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
        int fps = 60;
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
        
        fileOut.println(player.getX());
        fileOut.println(player.getY());
        fileOut.println(player.getDeaths());
        fileOut.println(shot.getX());
        fileOut.println(shot.getY());
        fileOut.println(shot.isCreated() ? 1 : 0);
        fileOut.println(aliens.getDirection());
        
        aliens.save(fileOut);

        fileOut.close();
                
    }
    
    
    private void loadGame() throws IOException
    {
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

              player.setX(Integer.parseInt(fileIn.readLine()));
              player.setY(Integer.parseInt(fileIn.readLine()));
              player.setDeaths(Integer.parseInt(fileIn.readLine()));
              shot.setX(Integer.parseInt(fileIn.readLine()));
              shot.setY(Integer.parseInt(fileIn.readLine()));
              shot.setCreated(Integer.parseInt(fileIn.readLine()) == 1);
              aliens.setDirection(Integer.parseInt(fileIn.readLine()));
              
              aliens.load(fileIn);

              fileIn.close();
    }

    private void restartGame() {
        
        
        aliens = new Aliens();
        player =  new Player(START_X, START_Y,PLAYER_WIDTH,PLAYER_HEIGHT, this, 2);
        shot = new Shot(0,GROUND,SHOT_WIDTH,SHOT_HEIGHT, this,false);
        
        

    }

}

