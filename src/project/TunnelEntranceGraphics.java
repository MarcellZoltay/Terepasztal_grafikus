package project;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
        g.setColor(Color.CYAN);
        g.fillRect(tunnelEntrance.getX(), tunnelEntrance.getY(), 20, 20);
        g.setColor(Color.BLACK);
        g.drawRect(tunnelEntrance.getX(), tunnelEntrance.getY(), 20, 20);
    }

    public TunnelEntrance getTunnelEntrance() { return tunnelEntrance; }
}
