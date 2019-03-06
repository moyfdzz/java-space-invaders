/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;
import videogame.ImageLoader;

/**
 *
 * @author moisesfernandez
 */
public class Assets {
    public static BufferedImage background;
    public static BufferedImage alien;
    public static BufferedImage player;
    public static BufferedImage shot;
    public static BufferedImage bomb;
    public static SoundClip death;
    public static SoundClip explosion;
    public static SoundClip shoot;
    public static SoundClip theme;
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpeg");
        alien = ImageLoader.loadImage("/images/alien.png");
        death = new SoundClip("/sounds/death.wav"); 
        theme = new SoundClip("/sounds/theme_song.wav");
    }
    
}
