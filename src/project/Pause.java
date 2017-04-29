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
        view.setState(this);
        view.setStatus(Status.PAUSE);
        view.updateScreen();
    }

    @Override
    public Status start() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.print("1. Continue\n");
            System.out.print("2. Exit\n");
            choice = sc.nextInt();
            sc.nextLine();
        } while(choice < 1 || choice > 2);
        if (choice == 1) return Status.CONTINUE;
        else if (choice == 2) return Status.EXIT_GAME;
        return null;
    }

    @Override
    public void mouseEventHandler(int x1, int y1, int x2, int y2) {

    }

}