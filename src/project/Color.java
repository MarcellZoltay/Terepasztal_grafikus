package project;

/**
 * Color enum, a színek reprezentálása.
 */
public class Color {
    private String color;

    public Color() {
        color = null;
    }
    
    public Color(String s) {
        color = s;
    }
    
    public void opposit() {
        if (color.contains("_GRAY")) {
            color = color.substring(0, color.length() - 5);
        }
        else {
            color = color.concat("_GRAY");
        }
    }

    /**
     *
     * @return
     */
    public boolean isEmpty(){
        return color.contains("_GRAY");
    }
    
    public boolean equals(String s) {
        return color.equals(s);
    }

    /**
     *
     * @param s
     * @return
     */
    public String getColor(){
        return color;
    }
    
    public void setColor(String s) {
        color = s;
    }

}