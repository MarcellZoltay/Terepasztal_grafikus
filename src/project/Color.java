package project;

/**
 * Color enum, a színek reprezentálása.
 */
public enum Color {
    ENGINE,
    COAL_CAR,
    BLUE,
    RED,
    GREEN,
    PINK,
    BLUE_GRAY,
    RED_GRAY,
    GREEN_GRAY,
    PINK_GRAY;

    /**
     * Megfordítja a szín-t
     * @return
     */
    public Color opposit() {
        switch(this) {
            case BLUE: return Color.BLUE_GRAY;
            case RED: return Color.RED_GRAY;
            case GREEN: return Color.GREEN_GRAY;
            case PINK: return Color.PINK_GRAY;
            case BLUE_GRAY: return Color.BLUE;
            case RED_GRAY: return Color.RED;
            case GREEN_GRAY: return Color.GREEN;
            case PINK_GRAY: return Color.PINK;
            default: return this;
        }
    }

    /**
     *  Megnézi hogy az enum alapján üres-e a színe, melyet a GRAY taggal ellátott elemek enumok jeleznek
     * @return A boolean érték, hogy üres-e a szín
     */
    public boolean isEmpty(){
        switch(this) {
            case BLUE: return false;
            case RED: return false;
            case GREEN: return false;
            case PINK: return false;
            case BLUE_GRAY: return true;
            case RED_GRAY: return true;
            case GREEN_GRAY: return true;
            case PINK_GRAY: return true;
            default: return true;
        }
    }

    /**
     *  Egy string alapján beállítja a kívánt színt
     * @param s
     * @return
     */
    public static Color getColorEnum(String s){
        Color ret = null;

        if(s.equalsIgnoreCase("BLUE"))
            ret = BLUE;
        else if(s.equalsIgnoreCase("RED"))
            ret = RED;
        else if(s.equalsIgnoreCase("GREEN"))
            ret = GREEN;
        else if(s.equalsIgnoreCase("PINK"))
            ret = PINK;

        return ret;
    }

}