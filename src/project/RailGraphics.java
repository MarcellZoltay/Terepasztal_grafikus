package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class RailGraphics {

    private Rail rail;

    public RailGraphics(Rail r){
        rail = r;
    }

    /**
     * A sín csomópontok kirajzolásáért felel
     * @param g 
     */
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        //g.drawLine(x1, y1, x2, y2);
    }

    public Rail getRail() { return rail; }

}
