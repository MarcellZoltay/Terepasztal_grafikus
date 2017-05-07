package project;

import java.util.*;

/**
 * Prints the pause 'screen'. User either quits or continues
 * @author Kunkli Richárd
 */
public class Pause implements State {

    private View view;
    private Status output;

    public Pause(View view) {
        this.view = view;
    }

    @Override
    public Status start() {
        String[] buttons = {"Continue Game", "Exit Game", "Game Paused"};
        view.updatePanel(buttons, this);
        while(output == null);
        return output == Status.START_GAME ? Status.CONTINUE : output;
    }

    @Override
    public void setOutput(Status s) {
        output = s;
    }

}