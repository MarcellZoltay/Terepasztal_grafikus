package project;

import java.util.Scanner;

/**
 * Loads the game starting menu
 * user can choose to start or quit
 * @author Kunkli Richárd
 */
public class Menu implements State {

    private View view;

    public Menu(View view) {
        this.view = view;
    }

    @Override
    public Status start() {
        
        return null;
    }

    @Override
    public void mouseEventHandler(int x1, int y1, int x2, int y2) {

    }

}