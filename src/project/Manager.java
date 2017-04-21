package project;

import java.util.*;

/** Basically it manages different menus in the game
 * checks for one's return type, which is the users input, winning or losing
 * then acts upon these scenarios, loads the next state(menu)
 * @author Kunkli Richárd
 */
public class Manager {

    private Stack<State> states;
    
    public Manager() {
        states = new Stack<>();
        Menu menu = new Menu();
        states.add(menu);
    }

    public void run() {
        while(true) {
            switch(states.peek().start()) {
                case START_GAME: 
                    states.push(new Game());
                    break;
                case EXIT_GAME:
                    System.exit(0);
                case CRASHED:
                    states.push(new End(Status.CRASHED));
                    break;
                case GAME_WON:
                    states.push(new End(Status.GAME_WON));
                case CONTINUE:
                    states.pop();
                    break;
                case PAUSE:
                    states.push(new Pause());
                    break;         
            }  
        }
    }
    
    /**
     * Don't really need it since util.Stack has its own function
     */
    private void addState(State s) {
        
    }

    /**
     * Don't really need it since util.Stack has its own function
     */
    private void removeState(State s) {
        
    }

}