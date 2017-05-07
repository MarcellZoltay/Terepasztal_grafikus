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
public class CarGraphics extends Drawable{

    private Car car;

    public CarGraphics(Car c){
        super("CAR_" + c.getColor());
        car = c;
    }

    @Override
    public void draw(Graphics g){

            g.setColor(Color.RED);


        g.fillOval(car.getX(), car.getY(), 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(car.getX(), car.getY(), 15, 15);
    }

    public Car getCar() { return car; }

}
