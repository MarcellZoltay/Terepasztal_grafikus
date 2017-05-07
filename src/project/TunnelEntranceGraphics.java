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
    }

    public TunnelEntrance getTunnelEntrance() { return tunnelEntrance; }
}
