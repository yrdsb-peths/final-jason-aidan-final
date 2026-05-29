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
    static Element dark = new Element("Dark");
    static Element star = new Element("Star");
    static Element sus = new Element("Sus");
    
    static Map<Element, Map<Element, Integer>> typeEffectiveness = Map.ofEntries(

    Map.entry(fire, Map.ofEntries(
        Map.entry(fire, 0),
        Map.entry(water, -1),
        Map.entry(grass, 1),
        Map.entry(electric, 0),
        Map.entry(rock, 0),
        Map.entry(poison, 1),
        Map.entry(ordinary, 0),
        Map.entry(big, 1),
        Map.entry(small, 0),
        Map.entry(dark, 1),
        Map.entry(star, 0),
        Map.entry(sus, -1)
    )),

    Map.entry(water, Map.ofEntries(
        Map.entry(fire, 1),
        Map.entry(water, 0),
        Map.entry(grass, -1),
        Map.entry(electric, -1),
        Map.entry(rock, 0),
        Map.entry(poison, 1),
        Map.entry(ordinary, 0),
        Map.entry(big, 0),
        Map.entry(small, 0),
        Map.entry(dark, 0),
        Map.entry(star, 1),
        Map.entry(sus, -1)
    )),

    Map.entry(grass, Map.ofEntries(
        Map.entry(fire, -1),
        Map.entry(water, 1),
        Map.entry(grass, 0),
        Map.entry(electric, 0),
        Map.entry(rock, 0),
        Map.entry(poison, -1),
        Map.entry(ordinary, 0),
        Map.entry(big, 0),
        Map.entry(small, 1),
        Map.entry(dark, 0),
        Map.entry(star, 1)
    )),

    Map.entry(electric, Map.ofEntries(
        Map.entry(fire, -1),
        Map.entry(water, 1),
        Map.entry(grass, 0),
        Map.entry(electric, 0),
        Map.entry(rock, -1),
        Map.entry(poison, 1),
        Map.entry(ordinary, 0),
        Map.entry(big, 1),
        Map.entry(small, 0),
        Map.entry(dark, 1),
        Map.entry(star, 0),
        Map.entry(sus, -1)
    )),

    Map.entry(rock, Map.ofEntries(
        Map.entry(fire, 1),
        Map.entry(water, 0),
        Map.entry(grass, 0),
        Map.entry(electric, 1),
        Map.entry(rock, 0),
        Map.entry(poison, -1),
        Map.entry(ordinary, 0),
        Map.entry(big, 0),
        Map.entry(small, 0),
        Map.entry(dark, -1),
        Map.entry(star, 0),
        Map.entry(sus, -1)
    )),

    Map.entry(poison, Map.ofEntries(
        Map.entry(fire, 0),
        Map.entry(water, -1),
        Map.entry(grass, 1),
        Map.entry(electric, -1),
        Map.entry(rock, 1),
        Map.entry(poison, 0),
        Map.entry(ordinary, 0),
        Map.entry(big, 1),
        Map.entry(small, 0),
        Map.entry(dark, -1),
        Map.entry(star, 0),
        Map.entry(sus, -1)
    )),

    Map.entry(ordinary, Map.ofEntries(
        Map.entry(fire, -1),
        Map.entry(water, -1),
        Map.entry(grass, -1),
        Map.entry(electric, -1),
        Map.entry(rock, -1),
        Map.entry(poison, -1),
        Map.entry(ordinary, 1),
        Map.entry(big, -1),
        Map.entry(small, -1),
        Map.entry(dark, -1),
        Map.entry(star, -1),
        Map.entry(sus, 1)
    )),

    Map.entry(big, Map.ofEntries(
        Map.entry(fire, 0),
        Map.entry(water, 0),
        Map.entry(grass, 0),
        Map.entry(electric, 0),
        Map.entry(rock, 0),
        Map.entry(poison, 0),
        Map.entry(ordinary, 0),
        Map.entry(big, 1),
        Map.entry(small, -1),
        Map.entry(dark, 0),
        Map.entry(star, 0),
        Map.entry(sus, -1)
    )),

    Map.entry(small, Map.ofEntries(
        Map.entry(fire, 1),
        Map.entry(water, 0),
        Map.entry(grass, 1),
        Map.entry(electric, 1),
        Map.entry(rock, 0),
        Map.entry(poison, 1),
        Map.entry(ordinary, 0),
        Map.entry(big, 1),
        Map.entry(small, 0),
        Map.entry(dark, 1),
        Map.entry(star, 0),
        Map.entry(sus, -1)
    )),

    Map.entry(dark, Map.ofEntries(
        Map.entry(fire, 0),
        Map.entry(water, 0),
        Map.entry(grass, 1),
        Map.entry(electric, 0),
        Map.entry(rock, 0),
        Map.entry(poison, -1),
        Map.entry(ordinary, 0),
        Map.entry(big, 0),
        Map.entry(small, 0),
        Map.entry(dark, 1),
        Map.entry(star, 1),
        Map.entry(sus, -1)
    )),

    Map.entry(star, Map.ofEntries(
        Map.entry(fire, 0),
        Map.entry(water, 0),
        Map.entry(grass, -1),
        Map.entry(electric, 0),
        Map.entry(rock, -1),
        Map.entry(poison, 0),
        Map.entry(ordinary, 0),
        Map.entry(big, 1),
        Map.entry(small, 0),
        Map.entry(dark, 1),
        Map.entry(star, 1),
        Map.entry(sus, -1)
    )),

    Map.entry(sus, Map.ofEntries(
        Map.entry(fire, 1),
        Map.entry(water, 1),
        Map.entry(grass, 1),
        Map.entry(electric, 1),
        Map.entry(rock, 1),
        Map.entry(poison, 1),
        Map.entry(ordinary, -1),
        Map.entry(big, 1),
        Map.entry(small, 1),
        Map.entry(dark, 1),
        Map.entry(star, 1),
        Map.entry(sus, -1)
    ))

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
