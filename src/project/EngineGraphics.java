package project;

import java.awt.*;
import java.awt.Color;
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
        g.setColor(Color.BLACK);
        g.fillOval(engine.getX(), engine.getY(), 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(engine.getX(), engine.getY(), 15, 15);
    }

    public Engine getEngine() { return engine; }

}
