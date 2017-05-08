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
    private volatile Status output;

    public Game(View view) {
        map = new Model();
        numberOfTrains = 5;
        waitingTime = 1;
        this.view = view;
        output = null;
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
        view.setMap(map);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            if (output == Status.PAUSE) return output;
            st = map.moveEngines();
            view.updateScreen();
            if (st != Status.CONTINUE) return st;
            
        }
    }

    @Override
    public void setOutput(Status s){
        output = s;
    }

}