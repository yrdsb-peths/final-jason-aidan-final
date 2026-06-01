import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
abstract public class Spirit extends Entity
{
    /**
     * Act - do whatever the Spirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    // Creates a fixed list of spirit types that can be used to dynamically create spirit objects

    static List<Class<? extends Spirit>> spiritTypes = List.of(
        FireSpirit.class, 
        WaterSpirit.class, 
        GrassSpirit.class,
        ElectricSpirit.class,
        RockSpirit.class,
        PoisonSpirit.class,
        OrdinarySpirit.class,
        BigSpirit.class,
        SmallSpirit.class,
        DarkSpirit.class,
        StarSpirit.class,
        SusSpirit.class
    );

    

    
    
    
    public Spirit(String name, int health, int attack, Element type, String attackName, String passiveName, GreenfootImage image)
    {
        super(name, health, attack, type, attackName, passiveName, image);
    }


    abstract public Soul getUpgraded();

    public String getUltimateDetails() {
        return "Spirits below level 5 don't have an ultimate.";
    }

}
