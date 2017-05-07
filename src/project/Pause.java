package project;

import java.util.*;

/**
 * Prints the pause 'screen'. User either quits or continues
 * @author Kunkli Richárd
 */
public class Pause implements State {

    private View view;

    public Pause(View view) {
        this.view = view;
    }

    @Override
    public Status start() {
        return Status.CONTINUE;
    }

    @Override
    public void mouseEventHandler(int x1, int y1, int x2, int y2) {

    }

}