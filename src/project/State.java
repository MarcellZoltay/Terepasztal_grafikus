package project;

import java.util.*;

/**
 * 
 */
public interface State {

    Status start();

    /**
     * Ezt hívja meg a view, hogy a kirajzolt képernyőn, megkapja, hogy a felhasználó
     * melyik választ adta
     * @param s Válaszul kapott opció
     */
    void setOutput(Status s);

}