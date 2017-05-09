package project;

import java.util.*;

/**
 * Prints the pause 'screen'. User either quits or continues
 * @author Kunkli Richárd
 */
public class Pause implements State {

    private View view;
    private volatile Status output;

    public Pause(View view) {
        this.view = view;
        view.setState(this);
        view.setStatus(Status.PAUSE);
        view.updatePanel(new String[]{"Continue", "Exit Game", "Taking a break"});
        view.updateScreen();
    }

    @Override
    public Status start() {
        while(output == null);
        return output == Status.START_GAME ? Status.CONTINUE : output;
    }

    @Override
    public void setOutput(Status s) {
        output = s;
    }

}