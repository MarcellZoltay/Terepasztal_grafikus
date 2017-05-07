package project;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class StationGraphics extends Drawable{

    private Station station;

    public StationGraphics(Station s){
        super("STATION_" + s.getColor());
        station = s;
    }

    @Override
    public void draw(Graphics g){
        AffineTransform at = new AffineTransform();
        at.translate((double)station.getX(), (double)station.getY());
        int vecX = station.getPrev().getX() - station.getNext().getX();
        int vecY = station.getPrev().getY() - station.getNext().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
    }

    public Station getStation() { return station; }

}
