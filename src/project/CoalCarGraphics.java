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
public class CoalCarGraphics extends Drawable{

    private CoalCar coalCar;

    public CoalCarGraphics(CoalCar c){
        super("COALCAR");
        coalCar = c;
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.fillOval(coalCar.getX(), coalCar.getY(), 15,15);
        g.setColor(Color.BLACK);
        g.drawOval(coalCar.getX(), coalCar.getY(), 15,15);
    }

    public CoalCar getCoalCar() { return coalCar; }

}
