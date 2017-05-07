package project;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Loads the game starting menu
 * user can choose to start or quit
 * @author Kunkli Richárd
 */
public class Menu implements State {

    private View view;
    private volatile Status output;

    public Menu(View view) {
        this.view = view;
        output = null;
    }

    @Override
    public Status start() {
        String[] buttons = {"Start Game", "Exit Game"};
        view.updatePanel(buttons, this);
        while(output == null);
        return output;
    }
    
    @Override
    public void setOutput(Status s) {
        output = s;
    }
}