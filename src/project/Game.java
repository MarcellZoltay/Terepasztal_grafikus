package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The game itself
 * @author Kunkli Richárd
 */
public class Game implements State {

    public Game() {
        map = new Model();
        numberOfTrains = 0;
        waitingTime = 1;
    }
    
    private Model map;              // The model in which the elements are stored
    private int numberOfTrains;     // The limit of how many trains can be added to the map
    private double waitingTime;     // The waiting time between adding trains

    /**
     * The 'load' command got implemented here, because it just loads more of the commands it would already use
     * @return 
     */
    private Status read() {
        Scanner sc = new Scanner(System.in);
        List<String> loadedFile = new ArrayList<>();
        Status s = null;
        while (true) {
            String command = sc.nextLine();
            if (command.contentEquals("pause")) return Status.PAUSE;
            if (command.contains("load ")) {
                if (!command.contains(" -l ")) System.out.println("> load command does not contain parameters");
                else {
                    String load[];
                    load = command.split(" ", 2);
                    load = load[1].split("-l ");
                    try { 
                        File f = new File(System.getProperty("user.dir") + "\\maps\\" + load[1]);
                        Scanner sf = new Scanner(f);
                        while (sf.hasNextLine()) {
                            loadedFile.add(sf.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("> file does not exist");
                    }
                }
            }
            try {
                if(loadedFile.size() > 0) {
                    for(int i = 0; i < loadedFile.size(); i++) {
                        s = map.decideActions(loadedFile.get(i));
                        if (s == Status.CRASHED || s == Status.GAME_WON) return s;
                    }
                    loadedFile = new ArrayList<>();
                }
                else {
                    s = map.decideActions(command);
                    if (s == Status.CRASHED || s == Status.GAME_WON) return s;  
                }
            } 
            catch(Exception e) {
                System.out.println("> " + e.getMessage());
                e.printStackTrace();
            }
        } 
    }

    /* Azért van itt külön függvény, mert a végleges programban, a start fog új vonatokat hozzáadni a Modellhez
     * Itt ez is parancssorból történik
     */
    @Override
    public Status start() {
        return read();
    }

}