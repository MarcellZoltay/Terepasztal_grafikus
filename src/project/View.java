package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
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
    private int x,y;

    /**
     * Default constructor
     */
    public View() {
        super("Sheldon Tepepasztala");
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
        repaint();
        map = newMap;
        getContentPane().addMouseListener(new MouseAdapter() {
            public void MousePressed(MouseEvent e) {
                if (!drawables.isEmpty()) { 
                    x = e.getX();
                    y = e.getY();
                }
            }
            
            public void MouseReleased(MouseEvent e) {
                if (!drawables.isEmpty()) {
                    map.decideActions(x, y, e.getX(), e.getY());
                }
            }
        });
        getContentPane().addKeyListener(new MyKeyListener());
    }
    
    public void updateScreen(){
        refreshMap();
        repaint();
    }
    
    public void updatePanel(String[] buttons, State newState) {
        drawables.clear();
        state = newState;
        panel.removeAll();
        
        setPreferredSize(new Dimension(1500, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocation(getWidth() / 2 + 750, getHeight() / 2 + 400);
        setVisible(true);
        
        if (buttons != null) {
            try {
                setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\GRASS.png"))))); 
            } catch (IOException e) { }
            setLayout(new FlowLayout());

            JButton start = new JButton(buttons[0]);
            start.setFont(new Font("Verdana", Font.PLAIN, 42));
            start.setOpaque(false);
            start.setBorderPainted(false);
            start.setFocusPainted(false);
            start.setContentAreaFilled(false);
            start.setCursor(new Cursor(Cursor.HAND_CURSOR));
            start.setForeground(java.awt.Color.orange);
            start.addMouseListener(new MyMouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    state.setOutput(Status.START_GAME);
                }
            });
            JButton end = new JButton(buttons[1]);
            end.setFont(new Font("Verdana", Font.PLAIN, 42));
            end.setOpaque(false);
            end.setBorderPainted(false);
            end.setFocusPainted(false);
            end.setContentAreaFilled(false);
            end.setCursor(new Cursor(Cursor.HAND_CURSOR));
            end.setForeground(java.awt.Color.orange);
            end.addMouseListener(new MyMouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    state.setOutput(Status.EXIT_GAME);
                }
            });
            JLabel text = new JLabel(buttons[2]);
            text.setFont(new Font("Verdana", Font.PLAIN, 84));
            text.setOpaque(false);
            text.setForeground(java.awt.Color.orange);
            JPanel options = new JPanel();
            options.setLayout(new GridLayout(3,1));
            options.add(text, BorderLayout.PAGE_END);
            options.add(start, BorderLayout.PAGE_END);
            options.add(end, BorderLayout.PAGE_END);
            options.setBackground(new java.awt.Color(0, 0, 0, 1));
            panel.setBackground(new java.awt.Color(0, 0, 0, 1));
            panel.add(options, BorderLayout.PAGE_START);
            add(panel);
        }
        
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
            if (!drawables.isEmpty() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                state.setOutput(Status.PAUSE);
                System.out.println("asdf");
            }
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
            e.getComponent().setFont(new Font("Verdana", Font.BOLD, 42));
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setFont(new Font("Verdana", Font.PLAIN, 42));
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }
}