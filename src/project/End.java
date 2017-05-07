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
    }

    public Status getOutput() { return ending; }

    public End(Status e) {
        this.ending = e;
    }

    @Override
    public Status start() {
        String[] buttons = {"New Game", "Exit Game", ending == Status.GAME_WON ? "Congrats! You won" : "You lost"};
        view.updatePanel(buttons, this);
        while(output == null);
        return output;
    }

    @Override
    public void setOutput(Status s) {
        output = s;
    }

}