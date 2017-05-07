package project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Drawable {
    
    protected BufferedImage image;
    
    public Drawable(String s) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("res\\" + s + ".png"));       // Need to implement later
        } catch(IOException e) {
            e.printStackTrace();
        }
        image = img;
    }
    
    public abstract void draw(Graphics g);
    
}
