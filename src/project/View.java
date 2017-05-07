package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * 
 */
public class View extends JFrame {

    private ArrayList<Drawable> drawables;
    private JPanel panel;
    private State state;
    private Model map;

    /**
     * Default constructor
     */
    public View() {
        super("Sheldon Terepasztal");
        setMinimumSize(new Dimension(800, 600));
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //getContentPane().add(panel, BorderLayout.CENTER);
        //getContentPane().addKeyListener(new MyKeyListener());

        setVisible(true);
        
        drawables = new ArrayList<>();
        panel = new JPanel();
    }
    
    private void refreshMap() {
        map.getRails().forEach((Rail n) -> {
            drawables.add(new RailGraphics(n));
        });
        map.getCrosses().forEach((Cross n) -> {
            drawables.add(new CrossGraphics(n));
        });
        map.getStations().forEach((Station n) -> {
            drawables.add(new StationGraphics(n));
        });
        map.getSwitches().forEach((Switch n) -> {
            drawables.add(new SwitchGraphics(n));
        });
        map.getTunnelEntrances().forEach((TunnelEntrance n) -> {
            drawables.add(new TunnelEntranceGraphics(n));
        });
        map.getCars().forEach((Car n) -> {
            drawables.add(new CarGraphics(n));
        });
        map.getEngines().forEach((Engine n) -> {
            drawables.add(new EngineGraphics(n));
        });
        map.getCoalCars().forEach((CoalCar n) -> {
            drawables.add(new CoalCarGraphics(n));
        });
    }
    
    public void setMap(Model newMap) {
        panel.removeAll();
        map = newMap;
    }
    
    public void updateScreen(){
        refreshMap();
        repaint();
    }
    
    public void updatePanel(String[] buttons, State newState) {
        drawables.clear();
        state = newState;
        panel.removeAll();
        JButton start = new JButton(buttons[0]);
        start.setFont(new Font("Verdana", Font.BOLD, 24));
        start.setBorderPainted(false);
        start.setFocusPainted(false);
        start.setContentAreaFilled(false);
        start.setForeground(java.awt.Color.DARK_GRAY);
        start.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state.setOutput(Status.START_GAME);
            }
        });
        JButton end = new JButton(buttons[1]);
        end.setFont(new Font("Verdana", Font.BOLD, 24));
        end.setBorderPainted(false);
        end.setFocusPainted(false);
        end.setContentAreaFilled(false);
        end.setForeground(java.awt.Color.DARK_GRAY);
        end.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                state.setOutput(Status.EXIT_GAME);
            }
        });
        JLabel bg = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            bg = new JLabel(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\kep.jpg"))));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        //panel.add(bg);
        panel.add(start);
        panel.add(end);
        add(panel, BorderLayout.CENTER);
        
        pack();
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
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            e.getComponent().setForeground(java.awt.Color.LIGHT_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setForeground(java.awt.Color.DARK_GRAY);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }
}