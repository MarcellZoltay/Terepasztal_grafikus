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

        if(station.getColor()==project.Color.RED)
            g.setColor(Color.RED);
        else if(station.getColor()==project.Color.GREEN)
            g.setColor(Color.GREEN);
        else if(station.getColor()==project.Color.BLUE)
            g.setColor(Color.BLUE);
        else if(station.getColor()==project.Color.YELLOW)
            g.setColor(Color.YELLOW);
        else if(station.getColor()==project.Color.PINK)
            g.setColor(Color.PINK);

        g.fillRect(station.getX(), station.getY(), 20, 20);
        g.setColor(Color.BLACK);
        g.drawRect(station.getX(), station.getY(), 20, 20);
    }

    public Station getStation() { return station; }

}
