package project;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class RailGraphics extends Drawable {

    private Rail rail;

    public RailGraphics(Rail r){
        super("RAIL");
        rail = r;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Polygon p = new Polygon();
        p.addPoint(rail.getX()+15, rail.getY()+15);
        p.addPoint(rail.getX()-15, rail.getY()-15);
        p.addPoint(rail.getNext().getX()-15, rail.getNext().getY()-15);
        p.addPoint(rail.getNext().getX()+15, rail.getNext().getY()+15);
        TexturePaint tp = new TexturePaint(image, new Rectangle(rail.getX(), rail.getY(), 10, 20));
        g2d.setPaint(tp);
        g2d.fillPolygon(p);  
    }

    public Rail getRail() { return rail; }

}
