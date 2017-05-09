package project;

import java.util.*;

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
    private ArrayList<Engine> engines;

    /**
     * A kocsikat tároló Map
     */
    private ArrayList<Car> cars;

    /**
     * A szeneskocsikat tároló Map
     */
    private ArrayList<CoalCar> coalCars;

    /**
     * Az állomásokat tároló Map
     */
    private ArrayList<Station> stations;

    /**
     * A síneket tároló Map
     */
    private ArrayList<Rail> rails;

    /**
     * A kereszteződéseket tároló Map
     */
    private ArrayList<Cross> crosses;

    /**
     * A váltókat tároló Map
     */
    private ArrayList<Switch> switches;

    /**
     * Az alagút bejáratokat tároló Map
     */
    private ArrayList<TunnelEntrance> tunnelEntrances;


    //******************************//
    //         Konstruktorok        //
    //******************************//
    /**
     * Konstruktor
     * Inicializálja a tagváltozókat
     */
    public Model() {
        engines = new ArrayList<>();
        cars = new ArrayList<>();
        coalCars = new ArrayList<>();
        stations = new ArrayList<>();
        rails = new ArrayList<>();
        crosses = new ArrayList<>();
        switches = new ArrayList<>();
        tunnelEntrances = new ArrayList<>();
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
        do {
            movedLast = Arrays.copyOf(moved, moved.length);
            for (int i = 0; i < engines.size(); i++ )
                if(!movedLast[i]) {
                    Status s = engines.get(i).move();
                    if (s == Status.DELETE_TRAIN) toRemove.add(engines.get(i));
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

    public void Load(){

        int xk=700;
        int yk=420;
        //Crosskör és a közepe

        Cross c03=new Cross(xk,yk,null,null,null,null);

        Cross c01=new Cross(xk,yk-50,          null,null,null,null);
        Cross c02=new Cross(xk,yk+50,          null,null,null,null);
        //dc//Cross c01=new Cross(xk,yk,          null,null,null,null);
        //dc//Cross c02=new Cross(xk,yk,          null,null,null,null);
        Cross c1=new Cross(xk,yk-100,       c01,null,null,null);
        Cross c2=new Cross(xk-75,yk-75,  c01,null,null,c1);
        Cross c3=new Cross(xk-100,yk,       c03,null,null,c2);
        Cross c4=new Cross(xk-75,yk+75,  c02,null,null, c3);
        Cross c5=new Cross(xk,yk+100,       c02,null,null, c4);
        Cross c6=new Cross(xk+75,yk+75,  c02,null,null,c5);
        Cross c7=new Cross(xk+100,yk,       c03,null,null,c6);
        Cross c8=new Cross(xk+75,yk-75, c01,null,c1,c7);
        //Egymásra állításuk

        //dc//c01.setNext(c1);
        //dc//c01.setPrev(c5);
        //dc//c01.setNext2(c3);
        //dc//c01.setPrev2(c7);
        c01.setNext(c03);
        c01.setPrev(c1);
        c01.setNext2(c2);
        c01.setPrev2(c8);

        c02.setNext(c03);
        c02.setPrev(c5);
        c02.setNext2(c4);
        c02.setPrev2(c6);

        c03.setNext(c01);
        c03.setPrev(c02);
        c03.setNext2(c3);
        c03.setPrev2(c7);
        //dc//c02.setNext(c6);
        //dc//c02.setPrev(c2);
        //dc//c02.setNext2(c4);
        //dc//c02.setPrev2(c8);

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
        Switch v10= new Switch((v1.getX()+v8.getX())/2+((v1.getX()+v8.getX())/2-c03.getX()),(v1.getY()+v8.getY())/2+((v1.getY()+v8.getY())/2)-c03.getY(),v1,v8,rail);
        Switch v11= new Switch((v7.getX()+v8.getX())/2+((v7.getX()+v8.getX())/2-c03.getX()),(v7.getY()+v8.getY())/2+((v7.getY()+v8.getY())/2)-c03.getY(), v7,v8,rail);
        Switch v12= new Switch((v6.getX()+v7.getX())/2+((v6.getX()+v7.getX())/2-c03.getX()),(v6.getY()+v7.getY())/2+((v6.getY()+v7.getY())/2)-c03.getY(),v6,v7,rail);
        Switch v13= new Switch((v5.getX()+v6.getX())/2+((v5.getX()+v6.getX())/2-c03.getX()),(v5.getY()+v6.getY())/2+((v5.getY()+v6.getY())/2)-c03.getY(),v5,v6,rail);
        Switch v14= new Switch((v4.getX()+v5.getX())/2+((v4.getX()+v5.getX())/2-c03.getX()),(v4.getY()+v5.getY())/2+((v4.getY()+v5.getY())/2)-c03.getY(),v4,v5,rail);
        Switch v15= new Switch((v3.getX()+v4.getX())/2+((v3.getX()+v4.getX())/2-c03.getX()),(v3.getY()+v4.getY())/2+((v3.getY()+v4.getY())/2)-c03.getY(),v3,v4,rail);
        Switch v16= new Switch((v2.getX()+v3.getX())/2+((v2.getX()+v3.getX())/2-c03.getX()),(v2.getY()+v3.getY())/2+((v2.getY()+v3.getY())/2)-c03.getY(),v2,v3,rail);
        Switch v17= new Switch((v1.getX()+v2.getX())/2+((v1.getX()+v2.getX())/2-c03.getX()),(v1.getY()+v2.getY())/2+((v1.getY()+v2.getY())/2)-c03.getY(),v1,v2,rail);
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
        rails.add(k);
        rails.add(l);
        rails.add(j);
        rails.add(m);
        crosses.add(one);


        Cross two=new Cross(xk+500,yk,null,null,null,null);
        Rail n=new Rail(xk+300,yk-200,v10,two);
        Rail o=new Rail(xk+300,yk-50,v11,two);
        Rail q=new Rail(xk+300,yk+200,v13,two);
        Rail p=new Rail(xk+300,yk+50,v12,two);
        two.setNext(n);
        two.setNext2(q);
        two.setPrev(p);
        two.setPrev2(o);
        rails.add(n);
        rails.add(o);
        rails.add(p);
        rails.add(q);
        crosses.add(two);

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
        cc.setPrevTrain(engine);
        engine.setNextCar(cc);

        Engine engine2=new Engine(c7.getX(),c7.getY(),one.getX(),one.getY(),c7);
        engine2.setPrevNode(c03);
        Car cc2=new Car(c03.getX()+5,c03.getY()+5,xk-2,yk-2,c03,Color.RED);
        cc2.setPrevTrain(engine2);
        cc2.setPrevNode(c3);
        engine2.setNextCar(cc2);

        Engine engine3=new Engine(v15.getX(),v15.getY(),one.getX(),one.getY(),v15);
        engine3.setPrevNode(d415);
        Car cc3=new Car(d415.getX()+5,d415.getY()+5,xk-2,yk-2,d415,Color.GREEN);
        cc3.setPrevNode(v4);
        cc3.setPrevTrain(engine3);
        engine3.setNextCar(cc3);

        v10.setPrev(n);
        v11.setPrev(o);
        v12.setPrev(p);
        v13.setPrev(q);
        v14.setPrev(m);
        v15.setPrev(j);
        v16.setPrev(l);
        v17.setPrev(k);



        engines.add(engine);
        cars.add(cc);
        engines.add(engine2);
        cars.add(cc2);
        engines.add(engine3);
        cars.add(cc3);
        stations.add(s1);
        stations.add(s2);
        stations.add(s3);
        stations.add(s4);
        rails.add(a);
        rails.add(b);
        rails.add(c);
        rails.add(d);
        rails.add(e);
        rails.add(f);
        rails.add(g);
        rails.add(h);
        rails.add(a117);
        rails.add(a110);
        rails.add(b217);
        rails.add(b216);
        rails.add(c315);
        rails.add(c316);
        rails.add(d415);
        rails.add(d414);
        rails.add(e514);
        rails.add(e513);
        rails.add(f613);
        rails.add(f612);
        rails.add(g712);
        rails.add(g711);
        rails.add(h811);
        rails.add(h810);

        crosses.add(c01);
        crosses.add(c02);
        crosses.add(c1);
        crosses.add(c2);
        crosses.add(c3);
        crosses.add(c4);
        crosses.add(c5);
        crosses.add(c6);
        crosses.add(c7);
        crosses.add(c8);
        switches.add(v1);
        switches.add(v2);
        switches.add(v3);
        switches.add(v4);
        switches.add(v5);
        switches.add(v6);
        switches.add(v7);
        switches.add(v8);
        //rails.put("",rail);
        //rails.put("",rail1);
        //rails.put("",rail2);
        switches.add(v10);
        switches.add(v11);
        switches.add(v12);
        switches.add(v13);
        switches.add(v14);
        switches.add(v15);
        switches.add(v16);
        switches.add(v17);

        //makeParts(o,v11,15);
        Math.sqrt(((k.getX()-v17.getX())*(k.getX()-v17.getX()))+((k.getY()-v17.getY())*(k.getY()-v17.getY())));
    }
    
    /*private void Load() {
        int xk = 750;
        int yk = 400;
        Cross c1 = new Cross(xk, yk, null, null, null, null);
        Rail r1 = new Rail(xk - 200, yk, c1, null);
        c1.setPrev(r1);
        Rail r2 = new Rail(xk, yk - 200, c1, null);
        c1.setNext(r2);
        Switch r3 = new Switch(xk + 200, yk, c1, null, null);
        c1.setNext2(r3);
        Rail r4 = new Rail(xk, yk + 200, c1, null);
        c1.setPrev2(r4);
        
        Rail r12 = new Rail(xk - 200, yk - 200, r1, r2);
        r1.setPrev(r12);
        r2.setPrev(r12);
        
        Rail r23 = new Rail(xk + 200, yk - 200, r2, r3);
        r3.setPrev(r23);
        
        Rail r34 = new Rail(xk + 200, yk + 200, r3, r4);
        r3.setSecond(r34);
        
        crosses.add(c1);
        rails.add(r1);
        switches.add(r3);
        rails.add(r2);
        rails.add(r4);
        rails.add(r12);
        rails.add(r23);
        rails.add(r34);
        
        Engine e = new Engine(r1.getX(), r1.getY(),)
        
    }*/

    private int getDistance(int x, int y, int lx1, int ly1, int lx2, int ly2) {
        if (((x < lx1 && x > lx2) || (x > lx1 && x < lx2)) && ((y < ly1 && y > ly2) || (y > ly1 && y < ly2))) {
            int normX = lx1 - lx2, normY = ly1 - ly2;
            int C = normX * x + normY * y;
            int lineC = lx1 * normX * -1 + ly1 * normY;
            int intersectY = (lineC + C) / (normY * 2);
            int intersectX = (normY * intersectY + C) / normX;
            return (int)Math.sqrt(Math.pow(intersectX - x, 2) + Math.pow(intersectY - y, 2));
        }
        return -1;
    }

    public void decideActions(int x1, int y1, int x2, int y2) {
        boolean options = false;

        for(Switch s: switches){
            if( (x1>(s.getX()-20) && x1<(s.getX()+20)) && (y1>(s.getY()-20) && y1<(s.getY()+20))) {
                changeSwitch(s);
                options = true;
            }
        }
        if (!options) {
            for(TunnelEntrance t: tunnelEntrances){
                if( (x1>(t.getX()-10) && x1<(t.getX()+10)) && (y1>(t.getY()-10) && y1<(t.getY()+10))) {
                    removeTunnelEntrance(t);
                    options = true;
                }
            }
        }
        if (!options) {
            rails.forEach( (Rail r) -> {
                int d = getDistance(x1, y1, r.getX(), r.getY(), r.getNext().getX(), r.getNext().getY());
                if (d != -1 && d < 5) {
                    addTunnelEntrance(r, r.getNext(), x1, y1, x2, y2);
                }
            });

            stations.forEach( (Station r) -> {
                int d = getDistance(x1, y1, r.getX(), r.getY(), r.getNext().getX(), r.getNext().getY());
                if (d != -1 && d < 5) {
                    addTunnelEntrance(r, r.getNext(), x1, y1, x2, y2);
                }
            });

            crosses.forEach( (Cross r) -> {
                int d = getDistance(x1, y1, r.getX(), r.getY(), r.getNext().getX(), r.getNext().getY());
                if (d != -1 && d < 5) {
                    addTunnelEntrance(r, r.getNext(), x1, y1, x2, y2);
                }
                d = getDistance(x1, y1, r.getX(), r.getY(), r.getNext2().getX(), r.getNext2().getY());
                if (d != -1 && d < 5) {
                    addTunnelEntrance(r, r.getNext2(), x1, y1, x2, y2);
                }
            });
        }
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private void addTunnelEntrance(Node p, Node n, int x1, int y1, int x2, int y2) {
        if (tunnelEntrances.size() == 2) return;
        int d1 =(int)Math.sqrt(Math.pow(p.getX() - x1, 2) + Math.pow(p.getY() - y1, 2));
        int d2 =(int)Math.sqrt(Math.pow(p.getX() - x2, 2) + Math.pow(p.getY() - y2, 2));
        TunnelEntrance t;
        if (d2 < d1) t = new TunnelEntrance(x1, y1, n, null, p);
        else t = new TunnelEntrance(x1, y1, p, null, n);

        if (p.getNext() == n) p.setNext(t);
        else ((Cross)p).setNext2(t);
        if (n.getNext() == p) n.setPrev(t);
        else ((Cross)n).setPrev2(t);

        if (tunnelEntrances.size() == 1) {
            tunnelEntrances.get(0).setSecond(t);
            tunnelEntrances.get(0).changeOutput();
            t.setSecond(tunnelEntrances.get(0));
            t.changeOutput();
        }
        tunnelEntrances.add(t);
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

        tunnelEntrances.remove(tE);
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

        if(trainPart.getNextCar() != null)
            removeTrain(trainPart.getNextCar());

        trainPart.getOnNode().removeTrain(trainPart);
        trainPart.getPrevNode().removeTrain(trainPart);
        if(trainPart.getColor().equals(Color.ENGINE))
            engines.remove((Engine)trainPart);
        else if(trainPart.getColor().equals(Color.COAL_CAR))
            coalCars.remove((CoalCar)trainPart);
        else
            cars.remove((Car)trainPart);

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
        return rails;
    }

    public ArrayList<Cross> getCrosses() {
        return crosses;
    }

    public ArrayList<Switch> getSwitches() {
        return switches;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public ArrayList<TunnelEntrance> getTunnelEntrances() {
        return tunnelEntrances;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public ArrayList<CoalCar> getCoalCars() {
        return coalCars;
    }

}