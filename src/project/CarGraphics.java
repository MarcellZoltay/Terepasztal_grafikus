package project;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
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
        AffineTransform at = new AffineTransform();
        at.translate((double)car.getX(), (double)car.getY());
        int vecX = car.getOnNode().getX() - car.getPrevNode().getX();
        int vecY = car.getOnNode().getY() - car.getPrevNode().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
    }

    public Car getCar() { return car; }

}
