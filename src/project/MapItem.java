package project;

/**
 * absztrakt osztaly a palyaelemekre
 */
public abstract class MapItem {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
    /**
     * x koordinata
     */
    protected int x;

    /**
     * y koordinata
     */
    protected int y;


    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Konstruktor
     */
    public MapItem(int x, int y){
        this.x = x;
        this.y = y;
    }


    //******************************//
    //       Getterek/Setterek      //
    //******************************//
    /**
     * @return visszater az x koordinataval
     */
    public int getX() {
        return x;
    }

    /**
     * @return isszater az y koordinataval
     */
    public int getY() {
        return y;
    }

    /**
     * beallitja az x koordinatat arra a parameterre amit a fuggveny kap
     * @param x a beallitando x koordinata
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * beallitja az y koordinatat arra a parameterre amit a fuggveny kap
     * @param y a beallitando y koordinata
     */
    public void setY(int y){
        this.y = y;
    }

}