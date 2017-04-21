package project;

import java.util.*;

/**
 * A sínt megvalósító osztály
 */
public class Rail extends Node {

    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Default constructor
     */
    public Rail() {
        super(0,0,null,null);
    }

    /**
     * @param x A sín x koordinátája
     * @param y A sín y koordinátája
     * @param n A következő csomópont
     * @param p Az előző csomópont
     */
    public Rail(int x, int y, Node n, Node p) {
        super(x, y, n, p);
    }

}