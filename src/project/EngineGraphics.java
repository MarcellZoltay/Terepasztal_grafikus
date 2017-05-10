package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 21..
 */
public class EngineGraphics{

    /**
     * A tárolt mozdony, mely kirajzolásáért felelős
     */
    private Engine engine;

    public EngineGraphics(Engine e){
        engine = e;
    }

    /**
     * A mozdony kirajzolásáért felelős függvény
     * @param g 
     */
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval(engine.getX(), engine.getY(), 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(engine.getX(), engine.getY(), 15, 15);
    }

    public Engine getEngine() { return engine; }

}
