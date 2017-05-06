package project;

import java.util.*;
import java.util.concurrent.TimeUnit;

/** Attributes are stored as a map hash, in order to be able to reference them by the user
 *  The final product wont have it like that, its just the purpose of the prototype
 */
public class Model {

    //******************************//
    //         Tagvaltozok          //
    //******************************//
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
        engines = new TreeMap<>();
        cars = new TreeMap<>();
        coalCars = new TreeMap<>();
        stations = new TreeMap<>();
        rails = new TreeMap<>();
        crosses = new TreeMap<>();
        switches = new TreeMap<>();
        tunnelEntrances = new TreeMap<>();
        Load();
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

    private void Load(){
        int xk=749;
        int yk=507;
        //Crosskör és a közepe
        Cross c01=new Cross(xk,yk,          null,null,null,null);
        Cross c02=new Cross(xk,yk,          null,null,null,null);
        Cross c1=new Cross(xk,yk-100,       c01,null,null,null);
        Cross c2=new Cross(xk-75,yk-75,  c02,null,null,c1);
        Cross c3=new Cross(xk-100,yk,       c01,null,null,c2);
        Cross c4=new Cross(xk-75,yk+75,  c02,null,null, c3);
        Cross c5=new Cross(xk,yk+100,       c01,null,null, c4);
        Cross c6=new Cross(xk+75,yk+75,  c02,null,null,c5);
        Cross c7=new Cross(xk+100,yk,       c01,null,null,c6);
        Cross c8=new Cross(xk+75,yk-75, c02,null,c1,c7);
        //Egymásra állításuk
        c01.setNext(c1);
        c01.setPrev(c5);
        c01.setNext2(c3);
        c01.setPrev2(c7);
        c02.setNext(c6);
        c02.setPrev(c2);
        c02.setNext2(c4);
        c02.setPrev2(c8);
        c1.setPrev2(c8);
        c1.setNext2(c2);
        c2.setNext2(c3);
        c3.setNext2(c4);
        c4.setNext2(c5);
        c5.setNext2(c6);
        c6.setNext2(c7);
        c7.setNext2(c8);
        //Elsőkör váltó
        Switch v1=new Switch(xk,yk-150,         null,null,c8);
        Switch v2=new Switch(xk-100,yk-100,  null,null,c1);
        Switch v3=new Switch(xk-150,yk,         null,null,c2);
        Switch v4=new Switch(xk-100,yk+100,  null,null,c3);
        Switch v5=new Switch(xk,yk+150,         null,null,c4);
        Switch v6=new Switch(xk+100,yk+100,  null,null,c5);
        Switch v7=new Switch(xk+150,yk,         null,null,c6);
        Switch v8=new Switch(xk+100,yk-100,  null,null,c7);

        //Próba
        Rail rail=new Rail(xk+300,yk,null,null);
        Rail rail1=new Rail(xk-300,yk-300,rail,null);
        Rail rail2=new Rail(xk+300,yk-300,rail1,rail);
        rail.setNext(rail2);
        rail.setPrev(rail1);
        rail1.setPrev(rail2);

        c1.setPrev(v1);
        c2.setPrev(v2);
        c3.setPrev(v3);
        c4.setPrev(v4);
        c5.setPrev(v5);
        c6.setPrev(v6);
        c7.setPrev(v7);
        c8.setPrev(v8);

        //Második kör váltó
        Switch v10= new Switch((v1.getX()+v8.getX())/2+((v1.getX()+v8.getX())/2-c01.getX()),(v1.getY()+v8.getY())/2+((v1.getY()+v8.getY())/2)-c01.getY(),v1,v8,rail);
        Switch v11= new Switch((v7.getX()+v8.getX())/2+((v7.getX()+v8.getX())/2-c01.getX()),(v7.getY()+v8.getY())/2+((v7.getY()+v8.getY())/2)-c01.getY(), v7,v8,rail);
        Switch v12= new Switch((v6.getX()+v7.getX())/2+((v6.getX()+v7.getX())/2-c01.getX()),(v6.getY()+v7.getY())/2+((v6.getY()+v7.getY())/2)-c01.getY(),v6,v7,rail);
        Switch v13= new Switch((v5.getX()+v6.getX())/2+((v5.getX()+v6.getX())/2-c01.getX()),(v5.getY()+v6.getY())/2+((v5.getY()+v6.getY())/2)-c01.getY(),v5,v6,rail);
        Switch v14= new Switch((v4.getX()+v5.getX())/2+((v4.getX()+v5.getX())/2-c01.getX()),(v4.getY()+v5.getY())/2+((v4.getY()+v5.getY())/2)-c01.getY(),v4,v5,rail);
        Switch v15= new Switch((v3.getX()+v4.getX())/2+((v3.getX()+v4.getX())/2-c01.getX()),(v3.getY()+v4.getY())/2+((v3.getY()+v4.getY())/2)-c01.getY(),v3,v4,rail);
        Switch v16= new Switch((v2.getX()+v3.getX())/2+((v2.getX()+v3.getX())/2-c01.getX()),(v2.getY()+v3.getY())/2+((v2.getY()+v3.getY())/2)-c01.getY(),v2,v3,rail);
        Switch v17= new Switch((v1.getX()+v2.getX())/2+((v1.getX()+v2.getX())/2-c01.getX()),(v1.getY()+v2.getY())/2+((v1.getY()+v2.getY())/2)-c01.getY(),v1,v2,rail);
        v10.setPrev(v17);

        v1.setNext(v17);
        v1.setSecond(v10);
        v2.setNext(v17);
        v2.setSecond(v16);
        v3.setNext(v16);
        v3.setSecond(v15);
        v4.setNext(v15);
        v4.setSecond(v14);
        v5.setNext(v14);
        v5.setSecond(v13);
        v6.setNext(v13);
        v6.setSecond(v12);
        v7.setNext(v12);
        v7.setSecond(v11);
        v8.setNext(v11);
        v8.setSecond(v10);


        Rail a=new Rail((c1.getX()+v1.getX())/2,(c1.getY()+c1.getY())/2, c1, v1);
        c1.setPrev(a);
        v1.setPrev(a);
        Rail b=new Rail((c2.getX()+v2.getX())/2,(c2.getY()+c2.getY())/2, c2, v2);
        c2.setPrev(b);
        v2.setPrev(b);
        Rail c=new Rail((c3.getX()+v3.getX())/2,(c3.getY()+c3.getY())/2, c3, v3);
        c3.setPrev(c);
        v3.setPrev(c);
        Rail d=new Rail((c4.getX()+v4.getX())/2,(c4.getY()+c4.getY())/2, c4, v4);
        c4.setPrev(d);
        v4.setPrev(d);
        Rail e=new Rail((c5.getX()+v5.getX())/2,(c5.getY()+c5.getY())/2, c5, v5);
        c5.setPrev(e);
        v5.setPrev(e);
        Rail f=new Rail((c6.getX()+v6.getX())/2,(c6.getY()+c6.getY())/2, c6, v6);
        c6.setPrev(f);
        v6.setPrev(f);
        Rail g=new Rail((c7.getX()+v7.getX())/2,(c7.getY()+c7.getY())/2, c7, v7);
        c7.setPrev(g);
        v7.setPrev(g);
        Rail h=new Rail((c8.getX()+v8.getX())/2,(c8.getY()+c8.getY())/2, c8, v8);
        c8.setPrev(h);
        v8.setPrev(h);


        Rail a110=new Rail((v1.getX()+v10.getX())/2,(v1.getY()+v10.getY())/2, v1, v10);
        v1.setNext(a110);
        v10.setNext(a110);
        Rail a117=new Rail((v1.getX()+v17.getX())/2,(v1.getY()+v17.getY())/2, v1, v17);
        v1.setSecond(a117);
        v17.setNext(a117);
        Rail b217=new Rail((v2.getX()+v17.getX())/2,(v2.getY()+v17.getY())/2, v2, v17);
        v2.setNext(b217);
        v17.setSecond(b217);
        Rail b216=new Rail((v2.getX()+v16.getX())/2,(v2.getY()+v16.getY())/2, v2, v16);
        v2.setSecond(b216);
        v16.setNext(b216);
        Rail c316=new Rail((v3.getX()+v16.getX())/2,(v3.getY()+v16.getY())/2, v3, v16);
        v3.setNext(c316);
        v16.setSecond(c316);
        Rail c315=new Rail((v3.getX()+v15.getX())/2,(v3.getY()+v15.getY())/2, v3, v15);
        v3.setSecond(c315);
        v15.setNext(c315);
        Rail d415=new Rail((v4.getX()+v15.getX())/2,(v4.getY()+v15.getY())/2, v4, v15);
        v4.setNext(d415);
        v15.setSecond(d415);
        Rail d414=new Rail((v4.getX()+v14.getX())/2,(v4.getY()+v14.getY())/2, v4, v14);
        v4.setSecond(d414);
        v14.setNext(d414);
//
        Rail e514=new Rail((v5.getX()+v14.getX())/2,(v5.getY()+v14.getY())/2, v5, v14);
        v5.setNext(e514);
        v14.setSecond(e514);
        Rail e513=new Rail((v5.getX()+v13.getX())/2,(v5.getY()+v13.getY())/2, v5, v13);
        v5.setSecond(e513);
        v13.setNext(e513);
        Rail f613=new Rail((v6.getX()+v13.getX())/2,(v6.getY()+v13.getY())/2, v6, v13);
        v6.setNext(f613);
        v13.setSecond(f613);
        Rail f612=new Rail((v6.getX()+v12.getX())/2,(v6.getY()+v12.getY())/2, v6, v12);
        v6.setSecond(f612);
        v12.setNext(f612);
        Rail g712=new Rail((v7.getX()+v12.getX())/2,(v7.getY()+v12.getY())/2, v7, v12);
        v7.setNext(g712);
        v12.setSecond(g712);
        Rail g711=new Rail((v7.getX()+v11.getX())/2,(v7.getY()+v11.getY())/2, v7, v11);
        v7.setSecond(g711);
        v11.setNext(g711);
        Rail h811=new Rail((v8.getX()+v11.getX())/2,(v8.getY()+v11.getY())/2, v8, v11);
        v8.setNext(h811);
        v11.setSecond(h811);
        Rail h810=new Rail((v8.getX()+v10.getX())/2,(v8.getY()+v10.getY())/2, v8, v10);
        v8.setSecond(h810);
        v10.setSecond(h810);


        Cross one=new Cross(xk-500,yk,null,null,null,null);
        Rail k=new Rail(xk-300,yk-200,v17,one);
        Rail l=new Rail(xk-300,yk-50,v16,one);
        Rail m=new Rail(xk-300,yk+200,v14,one);
        Rail j=new Rail(xk-300,yk+50,v15,one);
        one.setNext(k);
        one.setNext2(m);
        one.setPrev(j);
        one.setPrev2(l);
        rails.put("k",k);
        rails.put("l",l);
        rails.put("j",j);
        rails.put("m",m);
        crosses.put("one",one);


        Cross two=new Cross(xk+500,yk,null,null,null,null);
        Rail n=new Rail(xk+300,yk-200,v10,two);
        Rail o=new Rail(xk+300,yk-50,v11,two);
        Rail q=new Rail(xk+300,yk+200,v13,two);
        Rail p=new Rail(xk+300,yk+50,v12,two);
        two.setNext(n);
        two.setNext2(q);
        two.setPrev(p);
        two.setPrev2(o);
        rails.put("n",n);
        rails.put("o",o);
        rails.put("p",p);
        rails.put("q",q);
        crosses.put("two",two);

        Station s1=new Station(xk-500,yk-150,k,one,Color.BLUE);
        Station s2=new Station(xk-500,yk+150,m,one,Color.RED);
        Station s3=new Station(xk+500,yk-150,n,two,Color.GREEN);
        Station s4=new Station(xk+500,yk+150,q,two,Color.PINK);
        one.setNext(s1);
        one.setNext2(s2);
        two.setNext(s3);
        two.setNext2(s4);
        k.setPrev(s1);
        m.setPrev(s2);
        n.setPrev(s3);
        q.setPrev(s4);

        Engine engine=new Engine(k.getX(),k.getY(),one.getX(),one.getY(),k);
        engine.setPrevNode(s1);
        Car cc=new Car(s1.getX()+5,s1.getY()+5,xk-2,yk-2,s1,Color.BLUE);
        cc.setPrevNode(one);
        engine.setNextCar(cc);

        Engine engine2=new Engine(c7.getX(),c7.getY(),one.getX(),one.getY(),c7);
        engine2.setPrevNode(c01);
        Car cc2=new Car(c01.getX()+5,c01.getY()+5,xk-2,yk-2,c01,Color.RED);
        cc2.setPrevNode(c3);
        engine2.setNextCar(cc2);

        Engine engine3=new Engine(v15.getX(),v15.getY(),one.getX(),one.getY(),v15);
        engine3.setPrevNode(d415);
        Car cc3=new Car(d415.getX()+5,d415.getY()+5,xk-2,yk-2,d415,Color.GREEN);
        cc3.setPrevNode(v4);
        engine3.setNextCar(cc3);

        v10.setPrev(n);
        v11.setPrev(o);
        v12.setPrev(p);
        v13.setPrev(q);
        v14.setPrev(m);
        v15.setPrev(j);
        v16.setPrev(l);
        v17.setPrev(k);



        engines.put("engine",engine);
        cars.put("cc",cc);
        engines.put("engine2",engine2);
        cars.put("cc2",cc2);
        engines.put("engine3",engine3);
        cars.put("cc3",cc3);
        stations.put("s1",s1);
        stations.put("s2",s2);
        stations.put("s3",s3);
        stations.put("s4",s4);
        rails.put("a",a);
        rails.put("b",b);
        rails.put("c",c);
        rails.put("d",d);
        rails.put("e",e);
        rails.put("f",f);
        rails.put("g",g);
        rails.put("h",h);
        rails.put("a117",a117);
        rails.put("a110",a110);
        rails.put("b217",b217);
        rails.put("b216",b216);
        rails.put("c315",c315);
        rails.put("c316",c316);
        rails.put("d415",d415);
        rails.put("d414",d414);
        rails.put("e514",e514);
        rails.put("e513",e513);
        rails.put("f613",f613);
        rails.put("f612",f612);
        rails.put("g712",g712);
        rails.put("g711",g711);
        rails.put("h811",h811);
        rails.put("h810",h810);

        crosses.put("c01",c01);
        crosses.put("c02",c02);
        crosses.put("c1",c1);
        crosses.put("c2",c2);
        crosses.put("c3",c3);
        crosses.put("c4",c4);
        crosses.put("c5",c5);
        crosses.put("c6",c6);
        crosses.put("c7",c7);
        crosses.put("c8",c8);
        switches.put("v1",v1);
        switches.put("v2",v2);
        switches.put("v3",v3);
        switches.put("v4",v4);
        switches.put("v5",v5);
        switches.put("v6",v6);
        switches.put("v7",v7);
        switches.put("v8",v8);
        //rails.put("",rail);
        //rails.put("",rail1);
        //rails.put("",rail2);
        switches.put("v10",v10);
        switches.put("v11",v11);
        switches.put("v12",v12);
        switches.put("v13",v13);
        switches.put("v14",v14);
        switches.put("v15",v15);
        switches.put("v16",v16);
        switches.put("v17",v17);
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
                            //if (!setPrev(nexts[i], node)) throw new Exception("previous node cannot be set for " + nexts[i]);
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
                            //if (!setNext(prevs[i], node)) throw new Exception("next node cannot be set for " + prevs[i]);
                            if (!setPrev(name, prev[i])) throw new Exception("previous node cannot be set for " + name);
                        }
                    }
                    if (tunnelEntrances.size() == 2) {
                        //tunnelEntrances.forEach((String key, TunnelEntrance te) -> {
                        //    te.changeOutput();
                        //});
                        //ArrayList<TunnelEntrance> tunnelEntrance_array = new ArrayList<>();
//
                        //String keys[] = tunnelEntrances.keySet().toArray(new String[0]);
//
                        //for (int i = 0; i < keys.length; i++ )
                        //    tunnelEntrance_array.add(tunnelEntrances.get(keys[i]));
//
                        //tunnelEntrance_array.get(0).changeOutput();
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
                    System.out.println("\tnext2Node: " + getNodeName(node.getSecond()));
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

    public void decideActions(int x1, int y1, int x2, int y2) {

        boolean torles = false;

        ArrayList<Switch> switches = getSwitches();
        for(Switch s: switches){
            if( (x1>(s.getX()-20) && x1<(s.getX()+20)) && (y1>(s.getY()-20) && y1<(s.getY()+20)))
                s.changeOutput();
        }

        ArrayList<TunnelEntrance> tunnelEntrances = getTunnelEntrances();
        TunnelEntrance torlendo = null;
        // Törlés
        for(TunnelEntrance t: tunnelEntrances){
            if( (x1>(t.getX()-10) && x1<(t.getX()+10)) && (y1>(t.getY()-10) && y1<(t.getY()+10)))
                torlendo = t;
        }
        if(torlendo!=null) {
            removeTunnelEntrance(torlendo);
            torles = true;
        }

        // Hozzáadás
        if(!torles && tunnelEntrances.size()<2) {

        }

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
    private void removeTunnelEntrance(TunnelEntrance tE) {
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


    //******************************//
    //           Getterek           //
    //******************************//
    public ArrayList<Rail> getRails() {
        ArrayList<Rail> rail_array = new ArrayList<>();

        String keys[] = rails.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            rail_array.add(rails.get(keys[i]));

        return rail_array;
    }

    public ArrayList<Cross> getCrosses() {
        ArrayList<Cross> cross_array = new ArrayList<>();

        String keys[] = crosses.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            cross_array.add(crosses.get(keys[i]));

        return cross_array;
    }

    public ArrayList<Switch> getSwitches() {
        ArrayList<Switch> switch_array = new ArrayList<>();

        String keys[] = switches.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            switch_array.add(switches.get(keys[i]));

        return switch_array;
    }

    public ArrayList<Station> getStations() {
        ArrayList<Station> station_array = new ArrayList<>();

        String keys[] = stations.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            station_array.add(stations.get(keys[i]));

        return station_array;
    }

    public ArrayList<TunnelEntrance> getTunnelEntrances() {
        ArrayList<TunnelEntrance> tunnelEntrance_array = new ArrayList<>();

        String keys[] = tunnelEntrances.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            tunnelEntrance_array.add(tunnelEntrances.get(keys[i]));

        return tunnelEntrance_array;
    }

    public ArrayList<Engine> getEngines() {
        ArrayList<Engine> engine_array = new ArrayList<>();

        String keys[] = engines.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            engine_array.add(engines.get(keys[i]));

        return engine_array;
    }

    public ArrayList<Car> getCars() {
        ArrayList<Car> car_array = new ArrayList<>();

        String keys[] = cars.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            car_array.add(cars.get(keys[i]));

        return car_array;
    }

    public ArrayList<CoalCar> getCoalCars() {
        ArrayList<CoalCar> coalCar_array = new ArrayList<>();

        String keys[] = coalCars.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++ )
            coalCar_array.add(coalCars.get(keys[i]));

        return coalCar_array;
    }

}