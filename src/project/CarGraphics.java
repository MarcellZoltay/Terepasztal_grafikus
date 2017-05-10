package project;

import java.awt.*;
import java.awt.Color;

public class CarGraphics {

    /**
     * A tárolt vagon aminek a kirajzolásáért felelős
     */
    private Car car;

    public CarGraphics(Car c){
        car = c;
    }

    /**
     * A Vagonok kirajzolásáért felel, A megfelelő szín alapján beállítja a vagonok színét
     * @param g 
     */
    public void draw(Graphics g){

        if(car.getColor()==project.Color.RED)
            g.setColor(Color.RED);
        else if(car.getColor()==project.Color.GREEN)
            g.setColor(Color.GREEN);
        else if(car.getColor()==project.Color.BLUE)
            g.setColor(Color.BLUE);
        else if(car.getColor()==project.Color.PINK)
            g.setColor(new Color(180,0,140));
        else if(car.getColor()==project.Color.RED_GRAY)
            g.setColor(new Color(100,0,0));
        else if(car.getColor()==project.Color.GREEN_GRAY)
            g.setColor(new Color(0,100,0));
        else if(car.getColor()==project.Color.BLUE_GRAY)
            g.setColor(new Color(0,0,100));
        else if(car.getColor()==project.Color.PINK_GRAY)
            g.setColor(Color.PINK);

        g.fillOval(car.getX(), car.getY(), 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(car.getX(), car.getY(), 15, 15);
    }

    /**
     * Visszadja a tárolt vagont
     * @return 
     */
    public Car getCar() { return car; }

}
