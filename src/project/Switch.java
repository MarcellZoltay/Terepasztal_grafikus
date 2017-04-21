package project;

import java.util.*;

/**
 * osztaly ami a valtot valositja meg
 */
public class Switch extends Node {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
    /**
     * A másik kimenet.
     */
    protected Node next2Node;


    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Default constructor
     */
    public Switch(){
        super(0, 0, null, null);
        this.next2Node = null;
    }

    /**
     * @param x koordinata
     * @param y koordinata
     * @param n egyik kimenetcsomopont
     * @param n2 masik kimenetcsomopont
     * @param p elozo csomopont
     */
    public Switch(int x, int y, Node n, Node n2, Node p) {
        super(x, y, n, p);
        this.next2Node = n2;
    }


    //******************************//
    //          Metodusok           //
    //******************************//
    /**
     * megcsereli a valto kimenetet
     */
    public void changeOutput() {
        Node temp = nextNode;
        nextNode = next2Node;
        next2Node = temp;
    }

    /**
     * Meghatarozza a kovetkezo csomopontot, amerre a vonatnak mennie kell
     * @param t vonatot kap parameterkent aminek lekerdezi az elozo Node-jat és ez alapjan eldonti merre kell tovabb mennie
     * @return A haladási iránynak megfelelő következő csomópont.
     */
    @Override
    public Node getNextNode(Train t){
        Node ret = null;

        if(t.getPrevNode().equals(nextNode))
            ret = prevNode;
        else if(t.getPrevNode().equals(prevNode))
            ret = nextNode;
        else if(t.getPrevNode().equals(next2Node))
            ret = prevNode;

        return ret;
    }

    //******************************//
    //       Getterek/Setterek      //
    //******************************//
    /**
     * @return visszater masik kimenetcsomoponttal
     */
    public Node getSecond() {
        return next2Node;
    }

    /**
     * @param n2 beallitja a masik kimenetcsomopontot
     */
    public void setSecond(Node n2) {
        this.next2Node = n2;
    }

}