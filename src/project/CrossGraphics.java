/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.TexturePaint;
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
        Graphics2D g2d = (Graphics2D) g;
        Polygon p = new Polygon();
        p.addPoint(c.getX()+15, c.getY()+15);
        p.addPoint(c.getX()-15, c.getY()-15);
        p.addPoint(c.getNext().getX()-15, c.getNext().getY()-15);
        p.addPoint(c.getNext().getX()+15, c.getNext().getY()+15);
        TexturePaint tp = new TexturePaint(image, new Rectangle(c.getX(), c.getY(), 10, 20));
        g2d.setPaint(tp);
        g2d.fillPolygon(p);
        
        Polygon p2 = new Polygon();
        p2.addPoint(c.getX()+15, c.getY()+15);
        p2.addPoint(c.getX()-15, c.getY()-15);
        p2.addPoint(c.getNext2().getX()-15, c.getNext2().getY()-15);
        p2.addPoint(c.getNext2().getX()+15, c.getNext2().getY()+15);
        g2d.fillPolygon(p2);  
        
    }
    
}
