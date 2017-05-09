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

    public Game(View view) {
        map = new Model();
        numberOfTrains = 5;
        waitingTime = 4000;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(numberOfTrains>0) {
                    //map.addTrainToMap();
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