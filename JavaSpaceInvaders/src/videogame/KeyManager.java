/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author moisesfernandez
 */
public class KeyManager implements KeyListener{
    public boolean RIGHT;
    public boolean LEFT;
    public boolean SPACE;
    public boolean P;
    public boolean G;
    public boolean C;
    public boolean R;
    
    private boolean keys[];

    public void setRIGHT(boolean RIGHT) {
        keys[KeyEvent.VK_RIGHT] = RIGHT;
    }

    public void setLEFT(boolean LEFT) {
        keys[KeyEvent.VK_LEFT] = LEFT;
    }

    public void setSPACE(boolean SPACE) {
        keys[KeyEvent.VK_SPACE] =SPACE;
    }

    public void setP(boolean P) {
        keys[KeyEvent.VK_P] = P;
    }

    public void setG(boolean G) {
        keys[KeyEvent.VK_G] = G;
    }

    public void setC(boolean C) {
        keys[KeyEvent.VK_C] = C;
    }

    public void setR(boolean R) {
        keys[KeyEvent.VK_R] = R;
    }
    
    public boolean isR() {
        return keys[KeyEvent.VK_R];
    }
    
    public boolean isRIGHT() {
        return keys[KeyEvent.VK_RIGHT];
    }

    public boolean isLEFT() {
        return keys[KeyEvent.VK_LEFT];
    }

    public boolean isSPACE() {
        return keys[KeyEvent.VK_SPACE];
    }

    public boolean isP() {
        return keys[KeyEvent.VK_P];
    }

    public boolean isG() {
        return keys[KeyEvent.VK_G];
    }
    public boolean isC() {
        return keys[KeyEvent.VK_C];
    }
KeyManager() {
        keys = new boolean[256];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(keys[e.getKeyCode()] == RIGHT ||keys[e.getKeyCode()] == LEFT )
            keys[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if(keys[e.getKeyCode()] == RIGHT ||keys[e.getKeyCode()] == LEFT)
            keys[e.getKeyCode()] = false;
        
        else keys[e.getKeyCode()] = true;
    }
    
    public void tick() {
        RIGHT = keys[KeyEvent.VK_RIGHT];
        LEFT = keys[KeyEvent.VK_LEFT];
        SPACE = keys[KeyEvent.VK_SPACE];
        P = keys[KeyEvent.VK_P];
        G = keys[KeyEvent.VK_G];
        C = keys[KeyEvent.VK_C];
        R = keys[KeyEvent.VK_R];
    }
}
