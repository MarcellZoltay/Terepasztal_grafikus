package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class StationGraphics{

    private Station station;

    public StationGraphics(Station s){
        station = s;
    }

    public void draw(Graphics g){

        if(station.getColor()==project.Color.RED)
            g.setColor(Color.RED);
        else if(station.getColor()==project.Color.GREEN)
            g.setColor(Color.GREEN);
        else if(station.getColor()==project.Color.BLUE)
            g.setColor(Color.BLUE);
        else if(station.getColor()==project.Color.PINK)
            g.setColor(new Color(180,0,140));

        g.fillRect(station.getX(), station.getY(), 20, 20);
        g.setColor(Color.BLACK);
        g.drawRect(station.getX(), station.getY(), 20, 20);
    }

    public Station getStation() { return station; }

}
