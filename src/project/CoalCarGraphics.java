package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class CoalCarGraphics{

    private CoalCar coalCar;

    public CoalCarGraphics(CoalCar c){
        coalCar = c;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(coalCar.getX(), coalCar.getY(), 15,15);
        g.setColor(Color.BLACK);
        g.drawOval(coalCar.getX(), coalCar.getY(), 15,15);
    }

    public CoalCar getCoalCar() { return coalCar; }

}
