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
        /*g.drawLine(rail.getX(), rail.getY(), rail.getNext().getX(), rail.getNext().getY());
        AffineTransform at = new AffineTransform();
        at.translate((double)rail.getX(), (double)rail.getY());
        int vecX = rail.getNext().getX() - rail.getX();
        int vecY = rail.getNext().getY() - rail.getY();
        double angle = Math.toRadians(Math.tan(vecY / vecX ));
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        Polygon p = new Polygon();
        p.addPoint( rail.getX()+20*(int)Math.sin(angle), rail.getY()+20*(int)Math.cos(angle));
        p.addPoint( 200, 100);
        p.addPoint( 200, 120);
        p.addPoint( 100, 120);
        TexturePaint tp = new TexturePaint(image, new Rectangle(rail.getX(), rail.getY(), 10, 20));
        g2d.setPaint(tp);
        g2d.fillPolygon(p);*/
        
       /* boolean done = false;
        double movingX = (double)rail.getX();
        double movingY = (double)rail.getY();
        while(!done) {
            movingX += 5 * Math.sin(angle);
            movingY += 5 * Math.cos(angle);
            at.translate(movingX, movingY);
            g2d.drawImage(image, at, null);
            if ((int)movingX == rail.getNext().getX() || (int)movingY == rail.getNext().getY()) done = true;
        }*/
        
    }

    public Rail getRail() { return rail; }

}
