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
public class SwitchGraphics extends Drawable{
    private Switch s;
    
    public SwitchGraphics(Switch tmp) {
        super("RAIL");
        s = tmp;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Polygon p = new Polygon();
        p.addPoint(s.getX()+15, s.getY()+15);
        p.addPoint(s.getX()-15, s.getY()-15);
        p.addPoint(s.getNext().getX()-15, s.getNext().getY()-15);
        p.addPoint(s.getNext().getX()+15, s.getNext().getY()+15);
        TexturePaint tp = new TexturePaint(image, new Rectangle(s.getX(), s.getY(), 10, 20));
        g2d.setPaint(tp);
        g2d.fillPolygon(p);

        Polygon p2 = new Polygon();
        p2.addPoint(s.getX()+15, s.getY()+15);
        p2.addPoint(s.getX()-15, s.getY()-15);
        p2.addPoint(s.getSecond().getX()-15, s.getNext().getY()-15);
        p2.addPoint(s.getSecond().getX()+15, s.getNext().getY()+15);
        g2d.fillPolygon(p2);  
        
    }
    
}
