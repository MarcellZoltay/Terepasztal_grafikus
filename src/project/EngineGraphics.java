package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 21..
 */
public class EngineGraphics{

    private Engine engine;

    public EngineGraphics(Engine e){
        engine = e;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval(engine.getX(), engine.getY(), 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(engine.getX(), engine.getY(), 15, 15);
    }

    public Engine getEngine() { return engine; }

}
