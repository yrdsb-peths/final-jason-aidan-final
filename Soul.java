import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;

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
    String ultimateName;
    
    public Soul(String name, int health, int attack, Element type, String attackName, String passiveName, String ultimateName, GreenfootImage image)
    {
        super(name, health, attack, type, attackName, passiveName, image);
        this.ultimateUsed = false;
        this.ultimateName = ultimateName;
    }

    abstract public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others);

    public void applyUltimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        BattleScreen.getInstance().actionStack.add(new Action(
            "> " + name + " used " + ultimateName + ".",
            () -> {ultimate(allies, others);}
        ));
    }

}
