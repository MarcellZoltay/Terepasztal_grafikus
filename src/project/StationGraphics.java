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
public class StationGraphics extends Drawable{

    private Station station;

    public StationGraphics(Station s){
        super("STATION_" + s.getColor());
        station = s;
    }

    @Override
    public void draw(Graphics g){


        g.fillRect(station.getX(), station.getY(), 20, 20);
        g.setColor(Color.BLACK);
        g.drawRect(station.getX(), station.getY(), 20, 20);
    }

    public Station getStation() { return station; }

}
