package project;

import java.util.*;

/**
 * Loads two end game 'scenes' depending on the outcome
 * @author Kunkli Richárd
 */
public class End implements State {

    private Status output;
    private View view;

    public End(Status e, View view) {
        this.output = e;
        this.view = view;
    }

    public Status getOutput() { return output; }

    public End(Status e) {
        this.output = e;
    }

    @Override
    public Status start() {
        if (output == Status.GAME_WON) System.out.println("> Congrats! You won");
        if (output == Status.CRASHED) System.out.println("> Trains crashed! You lost");
        return Status.EXIT_GAME;
    }

    @Override
    public void mouseEventHandler(int x1, int y1, int x2, int y2) {

    }

}