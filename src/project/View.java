package project;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;

import static project.Status.*;

/**
 *
 * 
 */
public class View extends JFrame {

    private Model map;
    private State state;
    private Status status;
    private ArrayList<RailGraphics> railGraphics = new ArrayList<>();
    private ArrayList<StationGraphics> stationGraphics = new ArrayList<>();
    private ArrayList<TunnelEntranceGraphics> tunnelEntranceGraphics = new ArrayList<>();
    private ArrayList<EngineGraphics> engineGraphics = new ArrayList<>();
    private ArrayList<CarGraphics> carGraphics = new ArrayList<>();
    private ArrayList<CoalCarGraphics> coalCarGraphics = new ArrayList<>();
    private JPanel panel = new JPanel(new BorderLayout());
    private String[] buttons;

    private int x, y;

    /**
     * Default constructor
     */
    public View() {
        super("Sheldon Terepasztal");
        setMinimumSize(new Dimension(1024, 680));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        //getContentPane().add(panel, BorderLayout.CENTER);

        getContentPane().addMouseListener(new MyMouseListener());
        getContentPane().addKeyListener(new MyKeyListener());
        getContentPane().setFocusable(true);

        setVisible(true);
    }

    public void updateScreen(){ repaint(); }

    private void updateMap(){
        getContentPane().removeAll();

        refreshEngines();
        refreshCars();
        refreshCoalCars();
        refreshTunnelEntrances();
        refreshStations();

        for(StationGraphics s: stationGraphics)
            s.draw(getGraphics());

        for(TunnelEntranceGraphics t: tunnelEntranceGraphics)
            t.draw(getGraphics());

        drawRails();

        for(EngineGraphics e: engineGraphics)
            e.draw(getGraphics());

        for(CarGraphics c: carGraphics)
            c.draw(getGraphics());

        for(CoalCarGraphics c: coalCarGraphics)
            c.draw(getGraphics());

        //for(RailGraphics r: railGraphics)
        //    r.draw(getGraphics());

    }
    
    private void drawPanel(){
        panel.removeAll();
        getContentPane().removeAll();
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
                    state.setOutput(START_GAME);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setFont(new Font("Verdana", Font.BOLD, 42));
                //repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setFont(new Font("Verdana", Font.PLAIN, 42));
                //repaint();
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
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setFont(new Font("Verdana", Font.BOLD, 42));
                //repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setFont(new Font("Verdana", Font.PLAIN, 42));
                //repaint();
            }
        });
        JLabel text = new JLabel(buttons[2]);
        text.setFont(new Font("Verdana", Font.PLAIN, 84));
        text.setOpaque(false);
        text.setForeground(java.awt.Color.orange);
        JPanel options = new JPanel();
        options.setLayout(new GridLayout(3,1));
        options.add(text, BorderLayout.NORTH);
        options.add(start, BorderLayout.PAGE_END);
        options.add(end, BorderLayout.PAGE_END);
        panel.add(options, BorderLayout.PAGE_START);
        getContentPane().add(panel);
        setVisible(true);
    }
     public void updatePanel(String[] buttons) {
         this.buttons = buttons;
    }

    public void setState(State s) { state = s; }
    public void setStatus(Status s) { status = s; }
    public void setMap(Model map) { this.map = map; }

    private void refreshStations(){
        //stations = map.getStations();
//
        //for(Station s: stations){
        //    StationGraphics tmp = new StationGraphics(s);
        //    stationGraphics.add(tmp);
        //    getContentPane().add(tmp);
        //}

        ArrayList<Station> tmp = map.getStations();

        // Újak hozzáadása
        ArrayList<StationGraphics> ujak = new ArrayList<>();
        boolean uj = true;
        for(Station t: tmp){
            for(StationGraphics s: stationGraphics){
                if(s.getStation().equals(t)){
                    uj=false;
                    break;
                }
            }
            if(uj)
                ujak.add(new StationGraphics(t));
            uj=true;
        }

        stationGraphics.addAll(ujak);

    }
    private void refreshEngines(){
        // Model lekérdezése
        ArrayList<Engine> tmp = map.getEngines();

        // Törlés, amik már nincsenek a pályán
        if(!engineGraphics.isEmpty()) {
            ArrayList<EngineGraphics> torol = new ArrayList<>();
            boolean nincs = true;
            for (EngineGraphics e : engineGraphics) {
                for (Engine t : tmp) {
                    if (t.equals(e.getEngine())) {
                        nincs = false;
                        break;
                    }
                }
                if (nincs)
                    torol.add(e);
                nincs = true;
            }

            engineGraphics.removeAll(torol);
        }

        // Újak hozzáadása
        ArrayList<EngineGraphics> ujak = new ArrayList<>();
        boolean uj = true;
        for(Engine t: tmp){
            for(EngineGraphics e: engineGraphics){
                if(e.getEngine().equals(t)){
                    uj=false;
                    break;
                }
            }
            if(uj)
                ujak.add(new EngineGraphics(t));
            uj=true;
        }

        engineGraphics.addAll(ujak);

    }
    private void refreshCars(){
        // Model lekérdezése
        ArrayList<Car> tmp = map.getCars();

        // Törlés, amik már nincsenek a pályán
        if(!carGraphics.isEmpty()) {
            ArrayList<CarGraphics> torol = new ArrayList<>();
            boolean nincs = true;
            for (CarGraphics c : carGraphics) {
                for (Car t : tmp) {
                    if (t.equals(c.getCar())) {
                        nincs = false;
                        break;
                    }
                }
                if (nincs)
                    torol.add(c);
                nincs = true;
            }

            carGraphics.removeAll(torol);
        }

        // Újak hozzáadása
        ArrayList<CarGraphics> ujak = new ArrayList<>();
        boolean uj = true;
        for(Car t: tmp){
            for(CarGraphics c: carGraphics){
                if(c.getCar().equals(t)){
                    uj=false;
                    break;
                }
            }
            if(uj)
                ujak.add(new CarGraphics(t));
            uj=true;
        }

        carGraphics.addAll(ujak);

    }
    private void refreshCoalCars(){
        // Model lekérdezése
        ArrayList<CoalCar> tmp = map.getCoalCars();

        // Törlés, amik már nincsenek a pályán
        if(!coalCarGraphics.isEmpty()) {
            ArrayList<CoalCarGraphics> torol = new ArrayList<>();
            boolean nincs = true;
            for (CoalCarGraphics c : coalCarGraphics) {
                for (CoalCar t : tmp) {
                    if (t.equals(c.getCoalCar())) {
                        nincs = false;
                        break;
                    }
                }
                if (nincs)
                    torol.add(c);
                nincs = true;
            }

            coalCarGraphics.removeAll(torol);
        }

        // Újak hozzáadása
        ArrayList<CoalCarGraphics> ujak = new ArrayList<>();
        boolean uj = true;
        for(CoalCar t: tmp){
            for(CoalCarGraphics c: coalCarGraphics){
                if(c.getCoalCar().equals(t)){
                    uj=false;
                    break;
                }
            }
            if(uj)
                ujak.add(new CoalCarGraphics(t));
            uj=true;
        }

        coalCarGraphics.addAll(ujak);
    }
    private void refreshTunnelEntrances(){
        // Model lekérdezése
        ArrayList<TunnelEntrance> tmp = map.getTunnelEntrances();

        // Törlés, amik már nincsenek a pályán
        if(!tunnelEntranceGraphics.isEmpty()) {
            ArrayList<TunnelEntranceGraphics> torol = new ArrayList<>();
            boolean nincs = true;
            for (TunnelEntranceGraphics te : tunnelEntranceGraphics) {
                for (TunnelEntrance t : tmp) {
                    if (t.equals(te.getTunnelEntrance())) {
                        nincs = false;
                        break;
                    }
                }
                if (nincs)
                    torol.add(te);
                nincs = true;
            }

            tunnelEntranceGraphics.removeAll(torol);
        }

        // Újak hozzáadása
        ArrayList<TunnelEntranceGraphics> ujak = new ArrayList<>();
        boolean uj = true;
        for(TunnelEntrance t: tmp){
            for(TunnelEntranceGraphics te: tunnelEntranceGraphics){
                if(te.getTunnelEntrance().equals(t)){
                    uj=false;
                    break;
                }
            }
            if(uj)
                ujak.add(new TunnelEntranceGraphics(t));
            uj=true;
        }

        tunnelEntranceGraphics.addAll(ujak);
    }

    private void drawRails(){

        Graphics g = getGraphics();



        ArrayList<Rail> rails = map.getRails();
        for(Rail r: rails){
            if(r.getNext()!=null)
                g.drawLine(r.getX(),r.getY(), r.getNext().getX(), r.getNext().getY());
            if(r.getPrev()!=null)
                g.drawLine(r.getX(),r.getY(), r.getPrev().getX(), r.getPrev().getY());
        }

        ArrayList<Switch> switches = map.getSwitches();
        for(Switch s: switches){
            if(s.getNext()!=null) {
                g.drawLine(s.getX(), s.getY(), s.getNext().getX(), s.getNext().getY());

                // Aktív kimenet jelzése
                g.setColor(Color.GREEN);
                g.fillOval((s.getNext().getX()+s.getX())/2, (s.getNext().getY()+s.getY())/2, 10, 10);
                g.setColor(Color.BLACK);
                g.drawOval((s.getNext().getX()+s.getX())/2, (s.getNext().getY()+s.getY())/2, 10, 10);
            }
            if(s.getSecond()!=null) {
                g.drawLine(s.getX(), s.getY(), s.getSecond().getX(), s.getSecond().getY());

                // Passzív kimenet jelzése
                g.setColor(Color.RED);
                g.fillOval((s.getSecond().getX()+s.getX())/2, (s.getSecond().getY()+s.getY())/2, 10, 10);
                g.setColor(Color.BLACK);
                g.drawOval((s.getSecond().getX()+s.getX())/2, (s.getSecond().getY()+s.getY())/2, 10, 10);
            }
            if(s.getPrev()!=null)
                g.drawLine(s.getX(),s.getY(), s.getPrev().getX(), s.getPrev().getY());
        }

        for(StationGraphics s: stationGraphics){
            if(s.getStation().getNext()!=null)
                g.drawLine(s.getStation().getX(),s.getStation().getY(), s.getStation().getNext().getX(), s.getStation().getNext().getY());
            if(s.getStation().getPrev()!=null)
                g.drawLine(s.getStation().getX(),s.getStation().getY(), s.getStation().getPrev().getX(), s.getStation().getPrev().getY());

        }

        ArrayList<Cross> crosses = map.getCrosses();
        for(Cross c: crosses){
            if(c.getNext()!=null)
                g.drawLine(c.getX(), c.getY(), c.getNext().getX(), c.getNext().getY());
            if(c.getNext2()!=null)
                g.drawLine(c.getX(), c.getY(), c.getNext2().getX(), c.getNext2().getY());
            if(c.getPrev()!=null)
                g.drawLine(c.getX(), c.getY(), c.getPrev().getX(), c.getPrev().getY());
            if(c.getPrev2()!=null)
                g.drawLine(c.getX(), c.getY(), c.getPrev2().getX(), c.getPrev2().getY());
        }
//
        //for(TunnelEntranceGraphics te: tunnelEntranceGraphics){
        //    if(te.getTunnelEntrance().getSecond()!=null)
        //        g.drawLine(te.getTunnelEntrance().getX(), te.getTunnelEntrance().getY(), te.getTunnelEntrance().getSecond().getX(), te.getTunnelEntrance().getSecond().getY());
        //}

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        if (status == GAME) updateMap();
        else drawPanel();
    }

    private class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (status == GAME && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                status = Status.PAUSE;
                state.setOutput(PAUSE);
                //repaint();
            }
            else if (status == PAUSE && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                status = Status.GAME;
                state.setOutput(CONTINUE);
                //repaint();
            }
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
            if(status == GAME) {
                x = e.getX();
                y = e.getY();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(status == GAME) {
                map.decideActions(x, y + 30, e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //e.getComponent().setFont(new Font("Verdana", Font.BOLD, 42));
            //repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //e.getComponent().setFont(new Font("Verdana", Font.PLAIN, 42));
            //repaint();
        }

    }

}