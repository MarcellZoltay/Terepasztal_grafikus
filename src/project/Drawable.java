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
            img = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\" + s + ".png"));       // Need to implement later
        } catch(IOException e) { }
        image = img;
    }
    
    public abstract void draw(Graphics g);
    
}
