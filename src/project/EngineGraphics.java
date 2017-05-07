package project;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by Zoltay Marcell on 2017. 04. 21..
 */
public class EngineGraphics extends Drawable{

    private Engine engine;

    public EngineGraphics(Engine eng){
        super("ENGINE");
        engine = eng;
    }

    @Override
    public void draw(Graphics g){
        AffineTransform at = new AffineTransform();
        at.translate((double)engine.getX(), (double)engine.getY());
        int vecX = engine.getOnNode().getX() - engine.getPrevNode().getX();
        int vecY = engine.getOnNode().getY() - engine.getPrevNode().getY();
        at.rotate(vecX, vecY);
        at.translate(-image.getWidth()/2, -image.getWidth()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
    }

    public Engine getEngine() { return engine; }

}
