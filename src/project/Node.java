package project;

import java.util.*;

/**
 * absztrakt osztaly ami a mapitembol szarmazik es egy csomopontot valosit meg
 */
public abstract class Node extends MapItem {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
    /**
     * lecsereltem a tombot listara hogy konzisztens legyen a doksival
     */
    protected ArrayList<Train> trainsOn = new ArrayList<>();

    /**
     * a kovetkezo csomopont
     */
    protected Node nextNode;

    /**
     * az elozo csomopont
     */
    protected Node prevNode;


    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Konstruktor
     */
    public Node(int x, int y, Node n, Node p){
        super(x, y);
        this.nextNode = n;
        this.prevNode = p;
    }


    //******************************//
    //          Metodusok           //
    //******************************//
    /**
     * @param t Traint kap és hozzaadja a listahoz
     */
    public void addTrain(Train t) {
        trainsOn.add(t);
        t.setOnNode(this);
    }

    /**
     * @param t Traint kap és ezt ha megtalálta a listában akkor eltávolítja
     */
    public void removeTrain(Train t) {
        trainsOn.remove(t);
    }

    /**
     * @param t vonatot kap parameterkent aminek lekerdezi az elozo Nodejat és ez alapjan eldonti merre kell mennie tovabb
     * @return visszater azzal a node-al amerre a vonat haladni fog
     */
    public Node getNextNode(Train t) {
        Node temp = t.prevNode;
        
        if (temp == t.onNode) {
            return nextNode == null ? prevNode : nextNode; 
        }
        if (temp == nextNode){
            return prevNode;
        }
        else if(temp == prevNode){
            return nextNode;
        }

        return null;
    }


    //******************************//
    //       Getterek/Setterek      //
    //******************************//
    /**
     * @return visszatér a csomoponton levo vonatok listajaval
     */
    public ArrayList<Train> getTrains() {
        return trainsOn;
    }

    /**
     * @return visszater a kovetkezo Node-al
     */
    public Node getNext() {
        return nextNode;
    }

    /**
     * @param n beallitja a parameterkent kapott Nodeot kovetkezonek
     */
    public void setNext(Node n) {
        this.nextNode = n;
    }

    /**
     * @return visszater az elozo csomoponttal
     */
    public Node getPrev() {
        return prevNode;
    }

    /**
     * @param p beallitja elozo csopomontkent a parameterkent átadottat
     */
    public void setPrev(Node p) {
        this.prevNode = p;
    }

}