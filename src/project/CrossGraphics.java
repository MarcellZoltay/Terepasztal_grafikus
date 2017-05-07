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
public class CrossGraphics extends Drawable{
    private Cross c;
    
    public CrossGraphics(Cross tmp) {
        super("RAIL");
        c = tmp;
    }
    @Override
    public void draw(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate((double)c.getX(), (double)c.getY());
        int vecX = c.getPrev().getX() - c.getNext().getX();
        int vecY = c.getPrev().getY() - c.getNext().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
        
    }
    
}
