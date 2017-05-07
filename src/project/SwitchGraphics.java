/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Kunkli Richárd
 */
public class SwitchGraphics extends Drawable{
    private Switch s;
    
    public SwitchGraphics(Switch tmp) {
        super("RAIL");
        s = tmp;
    }

    @Override
    public void draw(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate((double)s.getX(), (double)s.getY());
        int vecX = s.getPrev().getX() - s.getNext().getX();
        int vecY = s.getPrev().getY() - s.getNext().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
        
    }
    
}
