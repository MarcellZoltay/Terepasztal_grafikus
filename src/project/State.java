package project;

import java.util.*;

/**
 * 
 */
public interface State {

    Status start();

    void mouseEventHandler(int x1, int y1, int x2, int y2);
    //void keyEventHandler(int x1, int y1, int x2, int y2);

}