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

    public void updateScreen(){ repaint(); }

    private void updateMap(){

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
    private void drawMenu(){
        setMinimumSize(new Dimension(1024,680));
        setPreferredSize(new Dimension(1024,680));

    }
    private void drawEndGameMenu(Status output){

    }
    private void drawPause(){


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
        for(StationGraphics u: ujak) {
            stationGraphics.add(u);
        }
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
            for (EngineGraphics t : torol) {
                engineGraphics.remove(t);
            }
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
        for(EngineGraphics u: ujak) {
            engineGraphics.add(u);
        }
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
            for (CarGraphics t : torol) {
                carGraphics.remove(t);
            }
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
        for(CarGraphics u: ujak) {
            carGraphics.add(u);
        }
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
            for (CoalCarGraphics t : torol) {
                coalCarGraphics.remove(t);
            }
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
        for(CoalCarGraphics u: ujak) {
            coalCarGraphics.add(u);
        }
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
            for (TunnelEntranceGraphics t : torol) {
                tunnelEntranceGraphics.remove(t);
            }
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
        for(TunnelEntranceGraphics u: ujak) {
            tunnelEntranceGraphics.add(u);
        }
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

            //if(s.getPrev()!=null)
            //    g.getGraphics().drawLine(s.getX(),s.getY(), s.getPrev().getX(), s.getPrev().getY());
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

        switch (status){
            case MENU: drawMenu();
                break;
            case GAME: updateMap();
                break;
            case END: drawEndGameMenu( ((End)state).getOutput() );
                break;
            case PAUSE: drawPause();
                break;
            default: break;
        }

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
            state.mouseEventHandler(x1, y1+30, x2, y2);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

}