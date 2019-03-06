package videogame;

/**
 *
 * @author moisesfernandez
 */
public class VideoGame implements Constants{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game g = new Game("Space Invaders", BOARD_WIDTH, BOARD_HEIGHT);
        g.start();
    }
    
    
    
}
