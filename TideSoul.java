import greenfoot.GreenfootImage;
import java.util.ArrayList;

/**
 * Write a description of class Blaze here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TideSoul extends Soul 
{

    /**
     * Constructor for objects of class Blaze
     */
    static final GreenfootImage COSTUME = new GreenfootImage("tide.png");
    static final String NAME = "Tide Spirit";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 35;
    static final String ATTACK_NAME = "Wave";
    static final String PASSIVE_NAME = "Cleanse";

    static final String ULTIMATE_NAME = "Tsunami";
    static final String ULTIMATE_DETAILS = "Returns absorbed damage.";

    static final String PASSIVE_DETAILS = "Remove effects and heal. Absorb damage from stuns, burns, and poisons.";

    int floodStacks = 0;
    
    public TideSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.water, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, ULTIMATE_NAME, ULTIMATE_DETAILS, COSTUME);
    }

    public void cleanse(Entity other) {
        if (other.stunDuration > 0) {
            floodStacks++;
        }
        if (other.burningDuration > 0) {
            floodStacks++;
        }
        if (other.poisonedDuration > 0) {
            floodStacks++;
        }
        other.stunDuration = 0;
        other.burningDuration = 0;
        other.poisonedDuration = 0;
        this.health += (int)(20 * levelModifier);
    }

    public void passive(Entity other) {
        other.burningDamage = (int) (15 * this.levelModifier);
        other.burningDuration = 5;
        // Any additional initialization code for TideSoul can go here
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        if (!this.ultimateUsed) {
            
        }
    }
}
