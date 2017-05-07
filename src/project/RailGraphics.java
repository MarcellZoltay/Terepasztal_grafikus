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
public class RailGraphics extends Drawable {

    private Rail rail;

    public RailGraphics(Rail r){
        super("sin");
        rail = r;
    }

    public void draw(Graphics g){
        AffineTransform at = new AffineTransform();
        at.translate((double)rail.getX(), (double)rail.getY());
        int vecX = rail.getPrev().getX() - rail.getNext().getX();
        int vecY = rail.getPrev().getY() - rail.getNext().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
        
    }

    public Rail getRail() { return rail; }

}
