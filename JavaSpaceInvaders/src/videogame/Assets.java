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
    public static BufferedImage paddle;
    public static BufferedImage enemy;
    public static BufferedImage intro;
    public static BufferedImage lives;
    public static BufferedImage gameOver;
    public static BufferedImage ball;
    public static SoundClip death;
    public static SoundClip theme;
    public static BufferedImage brickSkins[];
    
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpeg");
        paddle = ImageLoader.loadImage("/images/bar.png");
        intro = ImageLoader.loadImage("/images/intro.jpg");
        ball = ImageLoader.loadImage("/images/ball.png");
        gameOver = ImageLoader.loadImage("/images/game_over.jpg");
        death = new SoundClip("/sounds/death.wav"); 
        theme = new SoundClip("/sounds/theme_song.wav");
        brickSkins = new BufferedImage[3];
        brickSkins[0] = ImageLoader.loadImage("/images/brick_hp_1.png");
        brickSkins[1] = ImageLoader.loadImage("/images/brick_hp_2.png");
        brickSkins[2] = ImageLoader.loadImage("/images/brick_hp_3.png");
    }
    
}
