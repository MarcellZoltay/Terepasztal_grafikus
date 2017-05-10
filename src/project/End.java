package project;

import java.util.*;

/**
 * Loads two end game 'scenes' depending on the outcome
 * @author Kunkli Richárd
 */
public class End implements State {

    private volatile Status output;
    private Status ending;
    private View view;

    public End(Status e, View view) {
        this.ending = e;
        this.view = view;
        view.setState(this);
        view.setStatus(Status.END);
        view.updatePanel(new String[]{ending == Status.CRASHED ? "New Game" : "Next Level", "Exit Game", ending == Status.CRASHED ? "You Lost" : "You Won"});
        view.updateScreen();
    }

    public Status getOutput() { return ending; }

    @Override
    public Status start() {
        while(output == null);
        return output;
    }

    @Override
    public void setOutput(Status s) {
        output = s;
    }

}