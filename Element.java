import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class Element here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class Element  
{
    // instance variables - replace the example below with your own
    private String stringName;

    static Element fire = new Element("Fire");
    static Element water = new Element("Water");
    static Element grass = new Element("Grass");

    static Map<Element, Map<Element, Integer>> typeEffectiveness = Map.of(
        fire, 
        Map.of(
            fire, 0, 
            water, -2, 
            grass, 2
    ),
        water, 
        Map.of(
            water, 0,
            fire, 2, 
            grass, -1
    ),
        grass, 
        Map.of(
            grass, 0,
            water, 2, 
            fire, 1
    )
    );

    /**
     * Constructor for objects of class Element
     */
    public Element(String stringName)
    {
        this.stringName = stringName;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int comparedTo(Element other)
    {
        // put your code here
        return typeEffectiveness.get(this).get(other);
    }

    public String toString() {
        return stringName;
    }
}
