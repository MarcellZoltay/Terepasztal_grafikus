package project;

import java.util.*;

/**
 * Loads two end game 'scenes' depending on the outcome
 * @author Kunkli Richárd
 */
public class End implements State {

    /**
     * output: A kirajzolt menü visszatérési értéke
     * ending: 2 érték állítódhat be, vagy játék nyerése, vagy vesztése, ez alapján rajzolja ki a megfelelő ending screen-t
     * view: Tárolja a view-t
     */
    private volatile Status output;
    private Status ending;
    private View view;

    /**
     * Beállítja a view attribútumait, hogy megfelelő menüt rajzoljon ki.
     * @param e Az ending értékét szabja meg, mely alapján lehet meghatározni, hogy mi írodjon ki a képernyőre
     * @param view Paraméterül kapott view
     */
    public End(Status e, View view) {
        this.ending = e;
        this.view = view;
        view.setState(this);
        view.setStatus(Status.END);
        view.updatePanel(new String[]{ending == Status.CRASHED ? "New Game" : "Next Level", "Exit Game", ending == Status.CRASHED ? "You Lost" : "You Won"});
        view.updateScreen();
    }

    /**
     * ending getter
     * @return 
     */
    public Status getOutput() { return ending; }

    /**
     * Várakozik míg meg nem változik az output értéke. majd visszatér vele
     * @return 
     */
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