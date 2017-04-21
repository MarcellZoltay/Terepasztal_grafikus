package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Attributes are stored as a map hash, in order to be able to reference them by the user
 *  The final product wont have it like that, its just the purpose of the prototype
 */
public class Model {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
    /**
     * 
     */
    private View view;

    /**
     * A mozdonyokat tároló Map
     */
    private Map<String, Engine> engines;

    /**
     * A kocsikat tároló Map
     */
    private Map<String, Car> cars;

    /**
     * A szeneskocsikat tároló Map
     */
    private Map<String, CoalCar> coalCars;

    /**
     * Az állomásokat tároló Map
     */
    private Map<String, Station> stations;

    /**
     * A síneket tároló Map
     */
    private Map<String, Rail> rails;

    /**
     * A kereszteződéseket tároló Map
     */
    private Map<String, Cross> crosses;

    /**
     * A váltókat tároló Map
     */
    private Map<String, Switch> switches;

    /**
     * Az alagút bejáratokat tároló Map
     */
    private Map<String, TunnelEntrance> tunnelEntrances;


    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Konstruktor
     * Inicializálja a tagváltozókat
     */
    public Model() {
        //view = new View();
        engines = new TreeMap<>();
        cars = new TreeMap<>();
        coalCars = new TreeMap<>();
        stations = new TreeMap<>();
        rails = new TreeMap<>();
        crosses = new TreeMap<>();
        switches = new TreeMap<>();
        tunnelEntrances = new TreeMap<>();
    }


    //******************************//
    //          Metodusok           //
    //******************************//
    /**
     * Meghívja a mozdonyok move() metódusát.
     * Visszatér az aktuális mozdony állapotával.
     * @return ret Az aktuális mozdony állapota.
     */
    public Status moveEngines() {

        if(isMapEmpty())
            return Status.GAME_WON;

        Boolean moved[] = new Boolean[engines.size()];
        Arrays.fill(moved, false);
        Boolean movedLast[];
        List<Train> toRemove = new ArrayList<>();
        String keys[] = engines.keySet().toArray(new String[0]);
        do {
            movedLast = Arrays.copyOf(moved, moved.length);
            for (int i = 0; i < keys.length; i++ )
                if(!movedLast[i]) {
                    Status s = engines.get(keys[i]).move();
                    if (s == Status.DELETE_TRAIN) toRemove.add(engines.get(keys[i]));
                    if (s != Status.CRASHED) moved[i] = true;
                }
        } while(!Arrays.equals(moved, movedLast));
        
        toRemove.forEach((r) -> {
            removeTrain(r);
        });
        
        for (boolean n : moved){
            if (n == false) return Status.CRASHED;
        }
        return Status.CONTINUE;
    }

    /**
     * 
     */
    public void addTrainToMap() {
        // TODO implement here
    }
    
    /**
     * Checks if there exists a node with a specific name
     * @param name the node's name to find
     * @return if found returns the node, else returns null
     */
    private Node getNode(String name) {
        Node toReturn;
        toReturn = rails.get(name);
        if (toReturn != null) return toReturn;
        toReturn = stations.get(name);
        if (toReturn != null) return toReturn;
        toReturn = switches.get(name);
        if (toReturn != null) return toReturn;
        toReturn = crosses.get(name);
        if (toReturn != null) return toReturn;
        toReturn = tunnelEntrances.get(name);
        if (toReturn != null) return toReturn;
        return null;
    }
    
    /**
     * Called when a node was referred by another one, to set up the connection between them
     * To link them together from both sides
     * @param name the node's name to find
     * @param toSet the node to set up the connection with
     * @return returns true if it could set up the connection, false otherwise
     */
    private boolean setNext(String name, Node toSet) {
        Node n = getNode(name);
        boolean override = false;
        if (tunnelEntrances.get(getNodeName(toSet)) != null) override = true;
        if (crosses.get(name) != null) {
            if (n.getNext() == null) n.setNext(toSet);
            else if (((Cross)n).getNext2() == null) ((Cross)n).setNext2(toSet);
            else if (override) n.setNext(toSet);
            else if (n.getNext() != toSet && ((Cross)n).getNext2() != toSet) return false;
        }
        else if (switches.get(name) != null) {
            if (n.getNext() == null) n.setNext(toSet);
            else if (((Switch)n).getSecond() == null) ((Switch)n).setSecond(toSet);
            else if (override) n.setNext(toSet);
            else if (n.getNext() != toSet && ((Switch)n).getSecond() != toSet) return false;
        }
        else if (tunnelEntrances.get(name) != null) {
            if (n.getNext() == null) n.setNext(toSet);
            else if (((TunnelEntrance)n).getSecond() == null) ((TunnelEntrance)n).setSecond(toSet);
            else if (n.getNext() != toSet && ((TunnelEntrance)n).getSecond() != toSet) return false;
        }
        else if (n.getNext() == null || override) n.setNext(toSet);
        else if (n.getNext() != toSet) return false;
        return true;
    }
   
    /**
     * Basically the same as the setNext one, except its from the other way around
     */
    private boolean setPrev(String name, Node toSet) {
        Node n = getNode(name);
        boolean override = false;
        if (tunnelEntrances.get(getNodeName(toSet)) != null) override = true;
        if (tunnelEntrances.get(name) != null && override) ((TunnelEntrance)n).setSecond(toSet);
        else if (crosses.get(name) != null) {
            if (n.getPrev() == null) n.setPrev(toSet);
            else if (((Cross)n).getPrev2() == null) ((Cross)n).setPrev2(toSet);
            else if (override) n.setPrev(toSet);
            else if (n.getPrev() != toSet && ((Cross)n).getPrev2() != toSet) return false;
        }
        else if (n.getPrev() == null || override) n.setPrev(toSet);
        else if (n.getPrev() != toSet) return false;
        return true;
    }
    
    /**
     * The same as the getNode one, except its for Trains
     */
    private Train getTrain(String name) {
        Train toReturn;
        toReturn = engines.get(name);
        if (toReturn != null) return toReturn;
        toReturn = coalCars.get(name);
        if (toReturn != null) return toReturn;
        toReturn = cars.get(name);
        if (toReturn != null) return toReturn;
        return null;
    }
      
    /**
     * The same as the setNext one, except its for Trains
     */
    private boolean setNextTrain(String name, Train toSet) {
        Train t = getTrain(name);
            if (t.getNextCar() == null) t.setNextCar(toSet);
            else if (t.getNextCar() != toSet) return false;
        return true;
    }
   
    /**
     * The same as the setPrev one, except its for Trains
     */
    private boolean setPrevTrain(String name, Train toSet) {
        Train t = getTrain(name);
            if (((Car)t).getPrevTrain() == null) ((Car)t).setPrevTrain(toSet);
            else if (((Car)t).getPrevTrain() != toSet) return false;
        return true;
    }
    
    /**
     * Finds the key, the Train was set to
     * @param train the value which leads to a unique key
     * @return returns the key, if there is one, else returns null
     */
    public String getName(Train train) {
        if (train == null) return null;
        Set<String> set;
        set = engines.keySet();
        for (String s : set)
            if ((Train)engines.get(s) == train) return s;
        set = coalCars.keySet();
        for (String s : set)
            if ((Train)coalCars.get(s) == train) return s;
        set = cars.keySet();
        for (String s : set)
            if ((Train)cars.get(s) == train) return s;        
        return "Error";
    }
    
    /**
     * Finds the key, the Node was set to
     * @param node the value which leads to a unique key
     * @return returns the key, if there is one, else returns null
     */
    public String getNodeName(Node node) {
        if (node == null) return null;
        Set<String> set;
        set = rails.keySet();
        for (String s : set)
            if ((Node)rails.get(s) == node) return s;
        set = stations.keySet();
        for (String s : set)
            if ((Node)stations.get(s) == node) return s;
        set = switches.keySet();
        for (String s : set)
            if ((Node)switches.get(s) == node) return s;
        set = crosses.keySet();
        for (String s : set)
            if ((Node)crosses.get(s) == node) return s;
        set = tunnelEntrances.keySet();
        for (String s : set)
            if ((Node)tunnelEntrances.get(s) == node) return s;
        return null;
    }
    
    /**
     * Finds a specific option and its parameter in the command 
     * @param params the command seperated by '-'
     * @param keyShort the option's short name
     * @param keyLong the option's long name
     * @return returns the parameter(s)
     */
    private String checkParam(String params[], String keyShort, String keyLong) {
        for (String param : params) {
            if (param.contentEquals(keyShort)) return null;
            if (param.contentEquals(keyLong))  return null;
            if (param.startsWith(keyShort + " "))
                return param.substring(keyShort.length() + 1);
            if (param.startsWith(keyLong + " "))
                return param.substring(keyLong.length() + 1);
        }
        return "";
    }
    
    /**
     * Gets one command at a time, acts accordingly
     * @param code the command itself
     * @return returns the outcome it caused. 
     * If the command was to move the trains, and the trains crashed, then it returns that the game should be over.
     * @throws java.lang.Exception
     */
    public Status decideActions(String code) throws Exception {
        String parameters[] = code.split(" -");
        String type = "", name = "", remove = "", coords = "", setnext = "", setprev = "", seton = "", setcolor = "", change = "", all = "", steps = "";
        if (parameters.length > 1 ) {                               // Checks for all the parameters every command could have, if it has any
            type = checkParam(parameters, "t", "-type");
            name = checkParam(parameters, "c", "-create");
            remove = checkParam(parameters, "r", "-remove");
            coords = checkParam(parameters, "C", "-coords");
            setnext = checkParam(parameters, "sN", "-setnext");
            setprev = checkParam(parameters, "sP", "-setprev");
            seton = checkParam(parameters, "sO", "-seton");
            setcolor = checkParam(parameters, "sC", "-setcolor");
            change = checkParam(parameters, "o", "-change");
            all = checkParam(parameters, "a", "-all");
            steps = checkParam(parameters, "s", "-steps");
            /*for(int i = 0; i < parameters.length; i++) {
                System.out.println(parameters[i]);
            }*/
        }
        
        switch(parameters[0]) {                                     // Decides which command was called
            case "node":
                if (name == null || remove == null) throw new Exception("missing node name");   // Command cannot function without a node name
                if (!name.isEmpty() && !remove.isEmpty()) throw new Exception("can't create and remove an object at the same time"); // Cannot create and remove objects at the same time
                if (!name.isEmpty()) {                // If the command says to create or modify
                    Node node = getNode(name);          // Checks if the node was created earlier
                    if (node == null) {
                        if (type == null || type.isEmpty()) throw new Exception("missing node type");     // Command cannot function without a node type// If not creates it accordingly, and puts it an appropriate map
                        switch(type) {
                            case "Rail": 
                                node = new Rail();
                                rails.put(name, (Rail)node);
                                break;
                            case "Station": 
                                node = new Station();
                                if (!setcolor.isEmpty()) ((Station)node).setColor(Color.getColorEnum(setcolor));            // Stations must have colors
                                else throw new Exception("stations must have color");
                                stations.put(name, (Station)node);
                                break;
                            case "Switch": 
                                node = new Switch();
                                switches.put(name, (Switch)node);
                                break;
                            case "Cross": 
                                node = new Cross();
                                crosses.put(name, (Cross)node);
                                break;
                            case "TunnelEntrance": 
                                node = new TunnelEntrance();
                                tunnelEntrances.put(name, (TunnelEntrance)node);
                                break;
                            default: throw new Exception("not valid node type"); // Command must have a valid type
                        }
                    }
                    if (switches.get(name) != null && change == null) {
                        changeSwitch((Switch)node);
                    }
                    if (!coords.isEmpty()) {                    // Checks if user wants to change node's coordinates
                        String coord[] = coords.split(" ");
                        if (coord.length != 2) throw new Exception("a node has two coordinates");
                        node.setX(Integer.parseInt(coord[0]));
                        node.setY(Integer.parseInt(coord[1]));
                    }
                    if (!setnext.isEmpty()) {                   // Checks if user wants to change node's nextNode. If yes, sets up the connection from the other way too
                        String nexts[] = setnext.split(" ");
                        Node next[] = new Node[2];
                        if (nexts.length < 1 || nexts.length > 2) throw new Exception("not the correct number of parameters");
                        for (int i = 0; i < nexts.length; i++) {
                            next[i] = getNode(nexts[i]);
                            if (next[i] == null) throw new Exception("there is no node with the name " + nexts[i] + " to set previous");
                            if (!setPrev(nexts[i], node)) throw new Exception("previous node cannot be set for " + nexts[i]);
                            if (!setNext(name, next[i])) throw new Exception("next node cannot be set for " + name);
                        }
                    }
                    if (!setprev.isEmpty()) {                   // Checks if user wants to change node's prevNode. If yes, sets up the connection from the other way too
                        String prevs[] = setprev.split(" ");
                        Node prev[] = new Node[2];
                        if (prevs.length < 1 || prevs.length > 2) throw new Exception("not the correct number of parameters");
                        for (int i = 0; i < prevs.length; i++) {
                            prev[i] = getNode(prevs[i]);
                            if (prev[i] == null) throw new Exception("there is no train with the name " + prevs[i] + " to set previous");
                            if (!setNext(prevs[i], node)) throw new Exception("next node cannot be set for " + prevs[i]);
                            if (!setPrev(name, prev[i])) throw new Exception("previous node cannot be set for " + name);
                        }
                    }
                    if (tunnelEntrances.size() == 2) {
                        tunnelEntrances.forEach((String key, TunnelEntrance te) -> {
                            te.changeOutput();
                        });
                    }
                }
                if (!remove.isEmpty()) {                        //Checks if user wants to remove a TunnelEntrance
                    //if (tunnelEntrances.get(remove) == null) throw new Exception("there is no tunnel entrance with the name " + remove + " to remove");
                    //tunnelEntrances.remove(remove);
                    removeTunnelEntrance(tunnelEntrances.get(remove));
                }
                break;
            case "train":
                if (name == null || name.isEmpty()) throw new Exception("missing train name");    // Command cannot function without a train name
                if (type == null || type.isEmpty()) throw new Exception("missing train type");    // Command cannot function without a train type
                Train train = getTrain(name);           // Cheks if the train was created earlier
                if (train == null) {                    // If not creates it accordingly, and puts it an appropriate map
                    switch(type) {
                        case "Engine":
                            train = new Engine();
                            engines.put(name, (Engine)train);
                            break;
                        case "Car":
                            train = new Car();
                            if (!setcolor.isEmpty()) ((Car)train).setColor(Color.getColorEnum(setcolor));            // Stations must have colors
                            else throw new Exception("cars must have color");
                            cars.put(name, (Car)train);
                            break;
                        case "CoalCar":
                            train = new CoalCar();
                            coalCars.put(name, (CoalCar)train);
                            break;
                        default: throw new Exception("not valid train type");    // Command must have a valid type
                    }
                }
                if (!coords.isEmpty()) {                    // Checks if user wants to change train's coordinates
                    String coord[] = coords.split(" ");
                    if (coord.length != 4) throw new Exception("a train has four coordinates");
                    train.setX(Integer.parseInt(coord[0]));
                    train.setY(Integer.parseInt(coord[1]));
                    train.setEndX(Integer.parseInt(coord[2]));
                    train.setEndY(Integer.parseInt(coord[3]));
                }
                if (!setnext.isEmpty()) {                   // Checks if user wants to change train's next Train. If yes then sets up the connection from the other way too
                    String nexts[] = setnext.split(" ");
                    Train next;
                    if (nexts.length < 1 || nexts.length > 2) throw new Exception("not the correct number of parameters");
                    next = getTrain(nexts[0]);
                    if (next == null) throw new Exception("there is no train with the name " + nexts[0] + " to set previous");
                    if (!setPrevTrain(nexts[0], train)) throw new Exception("previous train cannot be set for " + nexts[0]);
                    if (!setNextTrain(name, next)) throw new Exception("next train cannot be set for " + name);
                }
                if (!setprev.isEmpty()) {                   // Checks if user wants to change thain's prev Train. If yes then sets up the connection from the other way too
                    String prevs[] = setprev.split(" ");
                    Train prev;
                    if (prevs.length < 1 || prevs.length > 2) throw new Exception("not the correct number of parameters");
                    prev = getTrain(prevs[0]);
                    if (prev == null) throw new Exception("there is no train with the name " + prevs[0] + " to set previous");
                    if (!setNextTrain(prevs[0], train)) throw new Exception("next train cannot be set for " + prevs[0]);
                    if (!setPrevTrain(name, prev)) throw new Exception("previous train cannot be set for " + name);
                }
                if (!seton.isEmpty()) {                     // Checks if user wants to change the Node the Train is on
                    Node on = getNode(seton);
                    if (on == null) throw new Exception("there is no node with the name " + seton + " to set as train's on node");
                    train.setOnNode(on);
                    on.addTrain(train);
                }
                break;
            case "move":
                if (steps == null) throw new Exception("missing steps parameter");      // Checks if command has steps option, but without parameter
                if (steps.isEmpty()) {
                    Status s = moveEngines();
                    if (s == Status.CRASHED) return s;
                    else if (s == Status.GAME_WON) return s;
                }                                                           // If there are no options, then it calls the train mover function once
                else                                                                                        // Calls it the number of times the parameter had
                    for (int i = 0; i < Integer.parseInt(steps); i++) {
                        Status s = moveEngines();
                        if (s == Status.CRASHED) return s;                                                  // Checks if the trains had crashed on the map
                        else if (s == Status.GAME_WON) return s;
                    }      
                break;
            case "ls":
                if (type == null) throw new Exception("missing type parameter");
                if (all == null || type.contains("Rail")) {
                    rails.forEach((String nodeName, Rail node) -> {
                    System.out.println(nodeName);
                    System.out.println("\tcoordinates: " + node.getX() + ", " + node.getY());
                    System.out.println("\tnextNode: " + getNodeName(node.getNext()));
                    System.out.println("\tprevNode: " + getNodeName(node.getPrev()));
                    System.out.print("\ttrains:");
                    for (Train t : node.getTrains()) {
                        System.out.print(" " + getName(t));
                    }
                    System.out.println();
                    });
                }
                if (all == null || type.contains("Switch")) {
                    switches.forEach((String nodeName, Switch node) -> {
                    System.out.println(nodeName);
                    System.out.println("\tcoordinates: " + node.getX() + ", " + node.getY());
                    System.out.println("\tnextNode: " + getNodeName(node.getNext()));
                    System.out.println("\tnext2Node: " + getNodeName(node.getSecond()));
                    System.out.println("\tprevNode: " + getNodeName(node.getPrev()));
                    System.out.print("\ttrains:");
                    for (Train t : node.getTrains()) {
                        System.out.print(" " + getName(t));
                    }
                    System.out.println();
                    });
                }
                if (all == null || type.contains("Station")) {
                    stations.forEach((String nodeName, Station node) -> {
                    System.out.println(nodeName);
                    System.out.println("\tcoordinates: " + node.getX() + ", " + node.getY());
                    System.out.println("\tnextNode: " + getNodeName(node.getNext()));
                    System.out.println("\tprevNode: " + getNodeName(node.getPrev()));
                    System.out.println("\tcolor: " + node.getColor().toString());
                    System.out.print("\ttrains:");
                    for (Train t : node.getTrains()) {
                        System.out.print(" " + getName(t));
                    }
                    System.out.println();
                    });
                }
                if (all == null || type.contains("Cross")) {
                    crosses.forEach((String nodeName, Cross node) -> {
                    System.out.println(nodeName);
                    System.out.println("\tcoordinates: " + node.getX() + ", " + node.getY());
                    System.out.println("\tnextNode: " + getNodeName(node.getNext()));
                    System.out.println("\tnext2Node: " + getNodeName(node.getNext2()));
                    System.out.println("\tprevNode: " + getNodeName(node.getPrev()));
                    System.out.println("\tprev2Node: " + getNodeName(node.getPrev2()));
                    System.out.print("\ttrains:");
                    for (Train t : node.getTrains()) {
                        System.out.print(" " + getName(t));
                    }
                    System.out.println();
                    });
                }
                if (all == null || type.contains("TunnelEntrance")) {
                    tunnelEntrances.forEach((String nodeName, TunnelEntrance node) -> {
                    System.out.println(nodeName);
                    System.out.println("\tcoordinates: " + node.getX() + ", " + node.getY());
                    System.out.println("\tnextNode: " + getNodeName(node.getNext()));
                    System.out.println("\tprevNode: " + getNodeName(node.getPrev()));
                    System.out.print("\ttrains:");
                    for (Train t : node.getTrains()) {
                        System.out.print(" " + getName(t));
                    }
                    System.out.println();
                    });
                }
                if (all == null || type.contains("Engine") || type.contains("Train")) {
                    engines.forEach(((String trainName, Engine trainObject) -> {
                        System.out.println(trainName);
                        System.out.println("\tcoordinates: " + trainObject.getX() + ", " + trainObject.getY() + ", " + trainObject.getEndX() + ", " + trainObject.getEndY());
                        System.out.println("\tonNode: " + getNodeName(trainObject.getOnNode()));
                        System.out.println("\tprevNode: " + getNodeName(trainObject.getPrevNode()));
                        System.out.println("\tnextCar: " + getName(trainObject.getNextCar()));
                    }));
                }
                if (all == null || type.contains("Car") || type.contains("Train")) {
                    cars.forEach(((String trainName, Car trainObject) -> {
                        System.out.println(trainName);
                        System.out.println("\tcoordinates: " + trainObject.getX() + ", " + trainObject.getY() + ", " + trainObject.getEndX() + ", " + trainObject.getEndY());
                        System.out.println("\tcolor: " + trainObject.getColor().toString());
                        System.out.println("\tonNode: " + getNodeName(trainObject.getOnNode()));
                        System.out.println("\tprevNode: " + getNodeName(trainObject.getPrevNode()));
                        System.out.println("\tnextTrain: " + getName(trainObject.getNextCar()));
                        System.out.println("\tprevTrain: " + getName(trainObject.getPrevTrain()));
                    }));
                    
                }
                if (all == null || type.contains("CoalCar") || type.contains("Train")) {
                    coalCars.forEach(((String trainName, CoalCar trainObject) -> {
                        System.out.println(trainName);
                        System.out.println("\tcoordinates: " + trainObject.getX() + ", " + trainObject.getY() + ", " + trainObject.getEndX() + ", " + trainObject.getEndY());
                        System.out.println("\tonNode: " + getNodeName(trainObject.getOnNode()));
                        System.out.println("\tprevNode: " + getNodeName(trainObject.getPrevNode()));
                        System.out.println("\tnextTrain: " + getName(trainObject.getNextCar()));
                        System.out.println("\tprevTrain: " + getName(trainObject.getPrevTrain()));
                    }));
                    
                }
                break;
            default: throw new Exception("no command like that");
        }
        return Status.CONTINUE;
    }

    /**
     * @param x1 
     * @param y1 
     * @param x2 
     * @param y2
     */
    private void addTunnelEntrance(int x1, int y1, int x2, int y2) {
        //TunnelEntrance tE = new TunnelEntrance();
    }

    /**
     * Törli a paraméterül kapott alagút bejáratot a tárolójából.
     * @param tE A törlendő alagút bejárat
     */
    private void removeTunnelEntrance(TunnelEntrance tE) throws Exception {
        if (tunnelEntrances.size() == 2) {
            try {
                ((TunnelEntrance)tE.getNext()).getSecond();
                tE.getPrev().setNext(tE.getSecond());
                tE.getSecond().setPrev(tE.getPrev());
                ((TunnelEntrance)tE.getNext()).changeOutput();
            }
            catch (Exception e) {
                tE.getNext().setPrev(tE.getSecond());
                tE.getSecond().setNext(tE.getNext());
                ((TunnelEntrance)tE.getPrev()).changeOutput();
            }
        }
        else {
            tE.getPrev().setNext(tE.getNext());
            tE.getNext().setPrev(tE.getPrev());
        }
            
        tunnelEntrances.remove(getNodeName(tE));
    }

    /**
     * Átállítja a paraméterül kapott váltó kimenetét.
     * @param s Az átállítandó váltó.
     */
    private void changeSwitch(Switch s) {
        s.changeOutput();
    }

    /**
     * Törli a vonatot a modellből
     * @param trainPart A törlendő vonatrész.
     */
    private void removeTrain(Train trainPart) {

        if(trainPart.getNextCar()==null)
            return;

        Train next = trainPart.getNextCar();
        if(trainPart.getColor()==Color.ENGINE)
            engines.remove(getName(trainPart));
        else if(trainPart.getColor()==Color.COAL_CAR)
            coalCars.remove(getName(trainPart));
        else
            cars.remove(getName(trainPart));

        removeTrain(next);

    }

    /**
     * Eldönti, hogy van-e még vonat a pályán.
     * Visszatér a megfelelő logikai értékkel.
     * @return true - Ha üres a pálya.
     * @return false - Ha nem üres a pálya.
     */
    private boolean isMapEmpty() {
        return engines.isEmpty();
    }

}