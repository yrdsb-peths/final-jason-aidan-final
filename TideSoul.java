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
    static final int BASE_HEALTH = 150;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Wave";
    static final String PASSIVE_NAME = "Cleanse";

    static final String ULTIMATE_NAME = "Tsunami";
    static final String ULTIMATE_DETAILS = "Heal or damage based on health. More effective with more stacks.";

    static final String PASSIVE_DETAILS = "Remove effects and heal. Increased stacks from stuns, burns, and poisons.";

    int floodStacks;
    
    public TideSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.water, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, COSTUME);
        this.floodStacks = 2;
    }

    public void attack(Entity other) {
        super.attack(other);
        floodStacks++;
    }   

    public void passive(Entity other) {
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
        this.health += (int)(15 * levelModifier);
        floodStacks+=2;
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        if (!this.ultimateUsed) {
            this.health += floodStacks * 5 * levelModifier;

            double effectiveDamage = effectivenessMultiplier(others.get(0)) * (int)(floodStacks * levelModifier) * 10;
            others.get(0).takeDamage(effectiveDamage);
            ultimateUsed = true;
        }
    }


    public String getAttackDetails() {
        return "Wave: deals " + attack + " water damage and gains 1 flood stack.";
    }

    public String getPassiveDetails() {
        return "Cleanse: removes effects, heals " + (int)(15 * levelModifier) + " health, and gains 2 flood stacks.";
    }

    public String getUltimateDetails() {
        return "Tsunami: heals " + (int)(floodStacks * 5 * levelModifier) + " health and does " + (int)(floodStacks * 5 * levelModifier) + " water damage. Based on flood stacks.";
    }
}
