package project;
import java.util.*;

//Zahorán László 2017.04.06
/**
 * A CoalCar osztály, Szenesvagon megvalósítása.
 */
public class CoalCar extends Train {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
    /**
     * A Train elemet, a szerevénybe megelöző Train elem.
     */
    private Train prevTrain;


    //******************************//
    //         Konstruktorok        //
    //******************************//

    /**
     * Default constructor
     */
    public CoalCar(){
        super();
        prevTrain=null;
    }

    /**
     * CoalCar osztály konstruktora.
     *@param x Train x koordináta.
     *@param y Train y koordináta.
     *@param xEnd  A Train vége, x koordináta.
     *@param yEnd A Train vége, y koordináta.
     *@param onNode Az aktuális csomópont amin tartózkodik a Train.
     *@param nextCar A Train elemet követő Train elem a szerelvényben, utolsó elem esetén null.
     */
    public CoalCar(int x, int y,int xEnd, int yEnd, Node onNode, Train nextCar) {
        super(x, y, xEnd, yEnd, onNode, Color.COAL_CAR, nextCar);
    }

    /**
     * CoalCar osztály konstruktora.
     *@param x Train x koordináta.
     *@param y Train y koordináta.
     *@param xEnd  A Train vége, x koordináta.
     *@param yEnd A Train vége, y koordináta.
     *@param onNode Az aktuális csomópont amin tartózkodik a Train.
     */
    public CoalCar(int x, int y,int xEnd, int yEnd, Node onNode) {
        super(x, y, xEnd, yEnd, onNode, Color.COAL_CAR);
    }


    //******************************//
    //          Metodusok           //
    //******************************//

    /**
     * A szeneskocsi mozgatása
     * @return Status, A mozgatás során játék kimenetére ható események visszajelzése.
     */
    @Override
    public Status move(){
        Node next = onNode.getNextNode(this);

        prevNode.removeTrain(this);
        next.addTrain(this);

        x=next.getX();
        y=next.getY();

        if(nextCar != null)
            return nextCar.move();

        return Status.DELETE_TRAIN;
    }


    //******************************//
    //       Getterek/Setterek      //
    //******************************//
    /**
     * Getter metódus
     * @return A Train elemet, a szerevénybe megelöző Train elem lekérdezése.
     */
    public Train getPrevTrain() {
        return prevTrain;
    }

    /**
     * Setter metódus
     * @param t A Train elemet, a szerevénybe megelöző Train elem beállítása.
     */
    public void setPrevTrain(Train t) {
        prevTrain=t;
    }

}