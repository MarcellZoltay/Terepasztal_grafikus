package project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Drawable {
    
    protected BufferedImage image;
    
    public abstract void draw(Graphics g);
    
}
