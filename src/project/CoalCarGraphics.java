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
public class CoalCarGraphics extends Drawable{

    private CoalCar coalCar;

    public CoalCarGraphics(CoalCar c){
        super("COALCAR");
        coalCar = c;
    }

    @Override
    public void draw(Graphics g){
        AffineTransform at = new AffineTransform();
        at.translate((double)coalCar.getX(), (double)coalCar.getY());
        int vecX = coalCar.getOnNode().getX() - coalCar.getPrevNode().getX();
        int vecY = coalCar.getOnNode().getY() - coalCar.getPrevNode().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
    }

    public CoalCar getCoalCar() { return coalCar; }

}
