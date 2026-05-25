import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spirit extends Actor
{
    /**
     * Act - do whatever the Spirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int health;
    int attack;
    String type;

    // Creates a fixed list of spirit types that can be used to dynamically create spirit objects

    static List<Class<? extends Spirit>> spiritTypes = List.of(
        FireSpirit.class, 
        WaterSpirit.class, 
        GrassSpirit.class
    );
    
    
    public Spirit(int health, int attack, String type)
    {
        this.health = health;
        this.attack = attack;
        this.type = type;
    }

    public void act()
    {
        
    }
}
