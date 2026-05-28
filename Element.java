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
    static Element electric = new Element("Electric");
    static Element rock = new Element("Rock");
    static Element poison = new Element("Poison");
    static Element ordinary = new Element("Ordinary");
    static Element big = new Element("Big");
    static Element small = new Element("Small");
    
    static Map<Element, Map<Element, Integer>> typeEffectiveness = Map.of(
        fire, 
        Map.of(
        //neutral
            fire, 0, 
            rock, 0,
            
        //super effective
            grass, 1, 
            poison, 1,
            ordinary, 1,
            small, 1,
            
        //not effective
            water, -1,
            electric, -1,
            big, -1
    ),
        water, 
        Map.of(
        //neutral
            water, 0, 
            big, 0,
            small, 0,
            
        //super effective
            fire, 1, 
            electric, 1,
            rock, 1,
            ordinary, 1,
            
        //not effective
            grass, -1,
            poison, -1
    ),
        grass, 
        Map.of(
        //neutral
            grass, 0, 
            rock, 0,
            big, 0,
            
        //super effective
            water, 1, 
            ordinary, 1,
            small, 1,
            
        //not effective
            fire, -1,
            electric, -1,
            poison, -1
    ),
        electric,
        Map.of(
        //neutral
            electric, 0, 
            
        //super effective
            grass, 1, 
            poison, 1,
            ordinary, 1,
            big, 1,
            
        //not effective
            fire, -1,
            water, -1,
            rock, -1,
            small, -1
    ),
        rock,
        Map.of(
        //neutral
            fire, 0,
            grass, 0,
            rock, 0, 
            
        //super effective 
            electric, 1,
            ordinary, 1,
            big, 1,
            small, 1,
            
        //not effective
            water, -1,
            poison, -1
    ),
        poison, 
        Map.of(
        //neutral
            poison, 0,
            
        //super effective
            water, 1,
            grass, 1, 
            rock, 1,
            ordinary, 1,
            big, 1,
            
        //not effective
            fire, -1,
            electric, -1,
            small, -1
    ),
        ordinary, 
        Map.of(
        //neutral
            fire, 0,
            water, 0,
            grass, 0,
            electric, 0,
            rock, 0,
            poison, 0,
            big, 0,
            small, 0,
            
        //super effective
            ordinary, 1
            
        //not effective
        
    ),
        big,
        Map.of(
        //neutral
        water, 0,
        
        //super effective
        fire, 1,
        grass, 1,
        ordinary, 1,
        big, 1,
        small, 1,
        
        //not effective
        electric, -1,
        rock, -1,
        poison, -1
        
    ),
        small,
        Map.of(
        //neutral
        water, 0,
        rock, 0,
        
        //super effective
        electric, 1,
        poison, 1,
        ordinary, 1,
        small, 1,
        
        //not effective
        fire, -1,
        grass, -1,
        big, -1
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
