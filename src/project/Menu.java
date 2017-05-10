package project;

import java.util.Scanner;

/**
 * Loads the game starting menu
 * user can choose to start or quit
 * @author Kunkli Richárd
 */
public class Menu implements State {

    private View view;
    private volatile Status output;

    /**
     * Beállítja a view attribútumait
     * @param view Paraméterül kapott view
     */
    public Menu(View view) {
        this.view = view;
        view.setState(this);
        view.setStatus(Status.MENU);
        view.updatePanel(new String[]{"Start Game", "Exit Game", "Sheldon's Board"});
        view.updateScreen();
    }

    /**
     * Várakozik a felhasználó választására
     * @return visszaadja a választást
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