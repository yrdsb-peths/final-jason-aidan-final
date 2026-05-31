import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
abstract public class Soul extends Entity
{
    /**
     * Act - do whatever the Spirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    // Creates a fixed list of spirit types that can be used to dynamically create spirit objects

    boolean ultimateUsed;


    abstract public void ultimate(Entity other);
    
    
    public Soul(String name, int health, int attack, Element type, String attackName, String passiveName, String passiveDetails, GreenfootImage image)
    {
        super(name, health, attack, type, attackName, passiveName, passiveDetails, image);
        ultimateUsed = false;
    }
}
