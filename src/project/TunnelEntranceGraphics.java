package project;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class TunnelEntranceGraphics extends Drawable{

    private TunnelEntrance tunnelEntrance;

    public TunnelEntranceGraphics(TunnelEntrance te){
        super("TUNNELENTRANCE");
        tunnelEntrance = te;
    }

    @Override
    public void draw(Graphics g){
        AffineTransform at = new AffineTransform();
        at.translate((double)tunnelEntrance.getX(), (double)tunnelEntrance.getY());
        int vecX = tunnelEntrance.getPrev().getX() - tunnelEntrance.getNext().getX();
        int vecY = tunnelEntrance.getPrev().getY() - tunnelEntrance.getNext().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
        
        Polygon p = new Polygon();
        p.addPoint(tunnelEntrance.getX()+15, tunnelEntrance.getY()+15);
        p.addPoint(tunnelEntrance.getX()-15, tunnelEntrance.getY()-15);
        p.addPoint(tunnelEntrance.getNext().getX()-15, tunnelEntrance.getNext().getY()-15);
        p.addPoint(tunnelEntrance.getNext().getX()+15, tunnelEntrance.getNext().getY()+15);
        TexturePaint tp = new TexturePaint(image, new Rectangle(tunnelEntrance.getX(), tunnelEntrance.getY(), 10, 20));
        g2d.setPaint(tp);
        g2d.fillPolygon(p);

        Polygon p2 = new Polygon();
        p2.addPoint(tunnelEntrance.getX()+15, tunnelEntrance.getY()+15);
        p2.addPoint(tunnelEntrance.getX()-15, tunnelEntrance.getY()-15);
        p2.addPoint(tunnelEntrance.getSecond().getX()-15, tunnelEntrance.getNext().getY()-15);
        p2.addPoint(tunnelEntrance.getSecond().getX()+15, tunnelEntrance.getNext().getY()+15);
        g2d.fillPolygon(p2);
        
        Polygon p3 = new Polygon();
        p3.addPoint(tunnelEntrance.getX()+15, tunnelEntrance.getY()+15);
        p3.addPoint(tunnelEntrance.getX()-15, tunnelEntrance.getY()-15);
        p3.addPoint(tunnelEntrance.getPrev().getX()-15, tunnelEntrance.getNext().getY()-15);
        p3.addPoint(tunnelEntrance.getPrev().getX()+15, tunnelEntrance.getNext().getY()+15);
        g2d.fillPolygon(p3);
        
    }

    public TunnelEntrance getTunnelEntrance() { return tunnelEntrance; }
}
