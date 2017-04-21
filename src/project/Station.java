package project;

import java.util.*;

/**
 * osztaly ami megvalositja a
 */
public class Station extends Node {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
    /**
     * Az állomás színe
     */
    private Color color;
    /**
     * Logikai érték, hogy leszállíthat-e a vonatról utasokat
     */
    private boolean canGetOff;

    /**
     * Az első nem üres kocsi, amiről le kell szállítani az utasokat
     */
    private Car firstEmptyCar;

    /**
     * Az azonos színű üres kocsi, amibe utasok szállnak fel
     */
    private Car getOnCar;


    //******************************//
    //         Konstruktorok        //
    //******************************//

    /**
     * Default constructor
     */
    public Station(){
        super(0, 0, null, null);
        this.color = null;
        this.canGetOff = false;
        this.firstEmptyCar = null;
    }

    /**
     * @param x Az állomás x koordinátája
     * @param y Az állomás y koordinátája
     * @param n A következő csomópont
     * @param p Az előző csomópont
     * @param c Az állomás színe
     */
    public Station(int x, int y, Node n, Node p, Color c) {
        super(x, y, n, p);
        this.color = c;
        this.canGetOff = false;
        this.firstEmptyCar = null;
    }


    //******************************//
    //          Metodusok           //
    //******************************//
    /**
     * Hozzáadja a paraméterül kapott változót a vonatrészeket tároló tömbhöz.
     * Az első azonos színű nem üres kocsiról leszállítja az utasokat.
     * Véletlenszerűen felszállít utasokat egy azonos színű üres kocsiba.
     * @param t A listához adandó vonatrész.
     */
    @Override
    public void addTrain(Train t) {
        trainsOn.add(t);
        t.setOnNode(this);
        if (canGetOff && t.getColor().equals(color))
            if (((Car)t).getOffPassengers())
                canGetOff = false;
        
        // Felszállás
        if (t.getColor().isEmpty() && !t.getColor().equals(Color.ENGINE) && !t.getColor().equals(Color.COAL_CAR)) {
            int r = new Random().nextInt(10);
            if ( r < 2 ) ((Car)t).getOnPassengers();
        }
    }


    //******************************//
    //       Getterek/Setterek      //
    //******************************//
    /**
     * Visszaadja az állomás színét
     * @return Az állomás színével
     */
    public Color getColor() {
        return color;
    }

    /**
     * Beállítja az állomás színét a paraméterül kapott értékkel
     * @param c A beállítandó szín
     */
    public void setColor(Color c){
        this.color = c;
    }
    
    public void setGetOff() {
        canGetOff = true;
    }
    
    public boolean getGetOff() {
        return canGetOff;
    }

}