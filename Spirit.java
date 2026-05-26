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
    int burningDamage;
    int burningDuration; // # of turns
    double poisonedPercentage; // percentage damage between 0 and 1
    int poisonedDuration; // # of turns
    int healingAmount;
    int healingDuration; // # of turns
    Element type;

    // Creates a fixed list of spirit types that can be used to dynamically create spirit objects

    static List<Class<? extends Spirit>> spiritTypes = List.of(
        FireSpirit.class, 
        WaterSpirit.class, 
        GrassSpirit.class
    );

    public void passive(Spirit other) {
        System.out.println("This spirit has no passive ability.");
        // Any additional initialization code for FireSpirit can go here
    }
    
    
    public Spirit(int health, int attack, Element type)
    {
        burningDuration = 0;
        poisonedDuration = 0;
        burningDamage = 0;
        poisonedPercentage = 0;

        this.health = health;
        this.attack = attack;
        this.type = type;
    }

    public int comparedTo(Spirit other)
    {
        return this.type.comparedTo(other.type);
    }


}
