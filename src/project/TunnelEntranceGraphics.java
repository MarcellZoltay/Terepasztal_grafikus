package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class TunnelEntranceGraphics {

    private TunnelEntrance tunnelEntrance;

    public TunnelEntranceGraphics(TunnelEntrance te){
        tunnelEntrance = te;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        Polygon p = new Polygon();
        int pointX = tunnelEntrance.getX() - tunnelEntrance.getPrev().getX();
        int pointY = tunnelEntrance.getY() - tunnelEntrance.getPrev().getY();
        p.addPoint(tunnelEntrance.getX(), tunnelEntrance.getY());
        p.addPoint(-pointY + tunnelEntrance.getPrev().getX(), pointX + tunnelEntrance.getPrev().getY());
        p.addPoint(pointY + tunnelEntrance.getPrev().getX(), -pointX + tunnelEntrance.getPrev().getY());
        g.fillPolygon(p);
    }

    public TunnelEntrance getTunnelEntrance() { return tunnelEntrance; }
}
