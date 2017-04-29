package project;

import java.awt.*;
import java.awt.Color;

/**
 * Created by Zoltay Marcell on 2017. 04. 22..
 */
public class CarGraphics {

    private Car car;

    public CarGraphics(Car c){
        car = c;
    }

    public void draw(Graphics g){

        if(car.getColor()==project.Color.RED)
            g.setColor(Color.RED);
        else if(car.getColor()==project.Color.GREEN)
            g.setColor(Color.GREEN);
        else if(car.getColor()==project.Color.BLUE)
            g.setColor(Color.BLUE);
        else if(car.getColor()==project.Color.YELLOW)
            g.setColor(Color.YELLOW);
        else if(car.getColor()==project.Color.PINK)
            g.setColor(Color.PINK);
        else if(car.getColor()==project.Color.RED_GRAY)
            g.setColor(new Color(100,0,0));
        else if(car.getColor()==project.Color.GREEN_GRAY)
            g.setColor(new Color(0,100,0));
        else if(car.getColor()==project.Color.BLUE_GRAY)
            g.setColor(new Color(0,0,100));
        else if(car.getColor()==project.Color.YELLOW_GRAY)
            g.setColor(new Color(160,180,0));
        else if(car.getColor()==project.Color.PINK_GRAY)
            g.setColor(new Color(180,0,140));

        g.fillOval(car.getX(), car.getY(), 15, 15);
        g.setColor(Color.BLACK);
        g.drawOval(car.getX(), car.getY(), 15, 15);
    }

    public Car getCar() { return car; }

}
