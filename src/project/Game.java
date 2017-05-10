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
    public Timer timer;

    /**
     * Megfelelő időegységenként, Vonatokat ad hozzá a pályához ameddig vannak.
     * Beállítja a view attribútumait, hogy megfelelően működjenek
     * @param view 
     */
    public Game(View view) {
        map = new Model();
        numberOfTrains = 5;
        waitingTime = 8000;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(numberOfTrains>0) {
                    map.addTrainToMap();
                    numberOfTrains--;
                }
                else if(numberOfTrains==0){
                    timer.cancel();
                }
            }
        },0,waitingTime);
        this.view = view;
        view.setState(this);
        view.setStatus(Status.GAME);
        view.setMap(map);
        view.updateScreen();
    }
    
    private Model map;              // The model in which the elements are stored
    private int numberOfTrains;     // The limit of how many trains can be added to the map
    private long waitingTime;     // The waiting time between adding trains

    /**
     * A vonatokat mozgatja figyeli hogy ütköztek-e
     * Frissíti a kijelzőt
     * Figyeli, hogy a játékos nem Szünetelteti-e a játékot
     * @return A státusz mely alapján az állapot kezelő ösztály meghatározza azok sorsát
     */
    @Override
    public Status start() {
        Status st = Status.CONTINUE;
        view.setMap(map);
        while (true) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {}
            if(output != Status.PAUSE) {
                st = map.moveEngines();
                view.updateScreen();
            }
            if(output == Status.PAUSE) {
                output = Status.CONTINUE;
                return Status.PAUSE;
            }
            else if (st != Status.CONTINUE)
                return st;
        }
    }

    @Override
    public void setOutput(Status s) {
        output = s;
    }

}