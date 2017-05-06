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
public class RailGraphics extends Drawable {

    private Rail rail;

    public RailGraphics(Rail r){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(""));       // Need to implement later
        } catch(IOException e) {
            e.printStackTrace();
        }
        this.image = img;
        rail = r;
    }

    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        //g.drawLine(x1, y1, x2, y2);
    }

    public Rail getRail() { return rail; }

}
