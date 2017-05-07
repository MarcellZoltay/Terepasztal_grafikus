package project;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import static jdk.nashorn.internal.objects.NativeArray.map;
import static project.Status.END;
import static project.Status.GAME;
import static project.Status.MENU;
import static project.Status.PAUSE;

/**
 *
 * 
 */
public class View extends JFrame {

    private ArrayList<Drawable> drawables;
    private JPanel panel;

    private int x1, y1, x2, y2;

    /**
     * Default constructor
     */
    public View() {
        super("Sheldon Terepasztal");
        setMinimumSize(new Dimension(1499, 1013));
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //getContentPane().add(panel, BorderLayout.CENTER);

        getContentPane().addMouseListener(new MyMouseListener());
        //getContentPane().addKeyListener(new MyKeyListener());

        setVisible(true);
    }
    
    public void updateScreen(ArrayList<Drawable> items){
        drawables = items;
        repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        drawables.forEach((item) -> {
            item.draw(g);
        });
        
    }

    private class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    private class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            x2 = e.getX();
            y2 = e.getY();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

}