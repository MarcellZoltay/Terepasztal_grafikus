/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Graphics;

/**
 *
 * @author Kunkli Richárd
 */
public class SwitchGraphics extends Drawable{
    private Switch s;
    
    public SwitchGraphics(Switch tmp) {
        super("");
        s = tmp;
    }

    @Override
    public void draw(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
