package project;

import java.util.*;

/**
 * A mozdonyt megvalósító osztály
 */
public class Engine extends Train {

    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Default constructor
     */
    public Engine() {
        super(0,0,0,0,null, Color.ENGINE);
    }

    /**
     * @param x 
     * @param y 
     * @param xE 
     * @param yE 
     */
    public Engine(int x, int y, int xE, int yE, Node onNode) {
        super(x, y, xE, yE, onNode, Color.ENGINE);
    }


    //******************************//
    //          Metodusok           //
    //******************************//
    @Override
    public Status move(){

        if(onNode.getNextNode(this)==null){
            return Status.NOT_EMPTY_CAR;
        }
        List<Train> on = onNode.getNextNode(this).getTrains();
        if(!on.isEmpty()) return Status.CRASHED;

        Node next = onNode.getNextNode(this);
        prevNode.removeTrain(this);
        next.addTrain(this);
        x=next.getX();
        y=next.getY();
        try {
            ((Station)next).setGetOff();
        }
        catch (Exception e) {}
        
        return nextCar.move();
    }

}