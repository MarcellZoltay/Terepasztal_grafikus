package project;
import java.util.*;

//Zahorán László 2017.04.06
/**
 * A Car osztály, Vagon megvalósítása.
 */
public class Car extends Train{

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
    public Car(){
        super();
        prevTrain=null;
    }

    /**
     * Car osztály konstruktora.
     *@param x Train x koordináta.
     *@param y Train y koordináta.
     *@param xEnd  A Train vége, x koordináta.
     *@param yEnd A Train vége, y koordináta.
     *@param onNode Az aktuális csomópont amin tartózkodik a Train.
     *@param color A Train elem színe, Mozdony/ures -Gray, Szenes vagon -###
     *@param nextCar A Train elemet követő Train elem a szerelvényben, utolsó elem esetén null.
     */
    public Car(int x, int y,int xEnd,int yEnd, Node onNode, Color color, Train nextCar) {
        super(x, y, xEnd, yEnd, onNode, color, nextCar);
    }
    /**
     * Car osztály konstruktora.
     *@param x Train x koordináta.
     *@param y Train y koordináta.
     *@param xEnd  A Train vége, x koordináta.
     *@param yEnd A Train vége, y koordináta.
     *@param onNode Az aktuális csomópont amin tartózkodik a Train.
     *@param color A Train elem színe, Mozdony/ures -Gray, Szenes vagon -###
     */
    public Car(int x, int y,int xEnd,int yEnd, Node onNode, Color color) {
        super(x, y, xEnd, yEnd, onNode, color);
    }


    //******************************//
    //          Metodusok           //
    //******************************//
    /**
     *A vagonban utazó utasok leszállítása, azonos színű állomáson
     * üres vagon színe-->Gray
     */
    public boolean getOffPassengers() {
        Train t = this;
        do {
            if (t.getColor().equals(Color.COAL_CAR))
                t = ((CoalCar)t).getPrevTrain();
            else t = ((Car)t).getPrevTrain();
            if (!t.getColor().isEmpty()) return false;
        } while(!t.getColor().equals(Color.ENGINE));
        
        
        color=color.opposit();
        return true;
    }
    /**
     *A vagonban utazó utasok felszállítása, azonos színű állomáson
     * üres vagon színe-->Gray
     */
    public void getOnPassengers() {
        color=color.opposit();
    }

    @Override
    public Status move(){
        Node next = onNode.getNextNode(this);
        prevNode.removeTrain(this);
        next.addTrain(this);

        x=next.getX();
        y=next.getY();

        if (nextCar != null && nextCar.move().equals(Status.DELETE_TRAIN)) {
            if (color.isEmpty()) return Status.DELETE_TRAIN;
        }
        return Status.NOT_EMPTY_CAR;
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