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
        g.setColor(Color.CYAN);
        g.fillRect(tunnelEntrance.getX(), tunnelEntrance.getY(), 20, 20);
        g.setColor(Color.BLACK);
        g.drawRect(tunnelEntrance.getX(), tunnelEntrance.getY(), 20, 20);
    }

    public TunnelEntrance getTunnelEntrance() { return tunnelEntrance; }
}
