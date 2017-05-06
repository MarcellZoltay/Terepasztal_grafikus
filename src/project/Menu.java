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
        view.setState(this);
        view.setStatus(Status.MENU);
        view.updateScreen();
    }

    @Override
    public Status start() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.print("1. Start Game\n");
            System.out.print("2. Exit\n");
            choice = sc.nextInt();
            sc.nextLine();
        } while(choice < 1 || choice > 2);
        if (choice == 1) return Status.START_GAME;
        else if (choice == 2) return Status.EXIT_GAME;
        return null;
    }

    @Override
    public void mouseEventHandler(int x1, int y1, int x2, int y2) {

    }

}