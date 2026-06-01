import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class ShadowSoul here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShadowSoul extends Soul
{
    /**
     * Act - do whatever the DarkSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("dark.png");
    static final String NAME = "Dark Spirit";
    static final int BASE_HEALTH = 130;
    static final int BASE_ATTACK = 15;
    static final String ATTACK_NAME = "Shadow";
    static final String PASSIVE_NAME = "Total Blackout";
    static final String PASSIVE_DETAILS = "Weaken your opponent's attack & defense.";

    static final String ULTIMATE_NAME = "Eclipse";
    static final String ULTIMATE_DETAILS = "Weaken all opponents' attack & defense.";
    
    public ShadowSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.dark, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, ULTIMATE_NAME, ULTIMATE_DETAILS, costume);
        this.evasion = 0.2;

    }

    public void passive(Entity other) {
        other.defense = Math.max(other.defense -= 7 * levelModifier, -10);
        other.attack = Math.max(other.attack -= 5 * levelModifier, 1);
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        others.get(0).health -= 20 * levelModifier;
        if (!ultimateUsed) {
            for (Entity other : others) {
                passive(other);
            }
            for (Entity ally : allies) {
                ally.evasion = Math.max(0.2, ally.evasion);
            }
        }     
        ultimateUsed = true;
    }
}
