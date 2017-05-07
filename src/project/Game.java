package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The game itself
 * @author Kunkli Richárd
 */
public class Game implements State {

    private View view;

    public Game(View view) {
        map = new Model();
        numberOfTrains = 5;
        waitingTime = 1;
        this.view = view;
    }
    
    private Model map;              // The model in which the elements are stored
    private int numberOfTrains;     // The limit of how many trains can be added to the map
    private double waitingTime;     // The waiting time between adding trains

    /**
     * The 'load' command got implemented here, because it just loads more of the commands it would already use
     * @return 
     */
    private void read() {
        
    }

    /* Azért van itt külön függvény, mert a végleges programban, a start fog új vonatokat hozzáadni a Modellhez
     * Itt ez is parancssorból történik
     */
    @Override
    public Status start() {
        Status st;
        ArrayList<Drawable> drawables = new ArrayList<>();
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
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            st = map.moveEngines();
            view.updateScreen(drawables);
            if (st != Status.CONTINUE) return st;
            
        }
    }

    @Override
    public void mouseEventHandler(int x1, int y1, int x2, int y2) {

        map.decideActions(x1, y1, x2, y2);

    }

}