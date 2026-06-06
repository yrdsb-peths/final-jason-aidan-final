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
    static final String NAME = "Tide Soul";
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Wave";
    static final String PASSIVE_NAME = "Cleanse";

    static final String ULTIMATE_NAME = "Tsunami";
    static final String ULTIMATE_DETAILS = "Heal or damage based on health. More effective with more stacks.";

    static final String PASSIVE_DETAILS = "Remove effects and heal. Increased stacks from stuns, burns, and poisons.";

    static final int HEALING_AMOUNT = 30;
    static final int PASSIVE_COOLDOWN = 4;

    int floodStacks;
    
    public TideSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.water, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, COSTUME);
        this.floodStacks = 2;
    }

    public void attack(Entity other) {
        super.attack(other);
        floodStacks++;
    }   

    public void passive(Entity other) {
        if (stunDuration > 0) {
            floodStacks++;
        }
        if (burningDuration > 0) {
            floodStacks++;
        }
        if (poisonedDuration > 0) {
            floodStacks++;
        }
        this.stunDuration = 0;
        this.burningDuration = 0;
        this.poisonedDuration = 0;
        this.defense = Math.max(this.defense, 0);
        this.attack = Math.max(this.attack, BASE_ATTACK);
        this.health += (int)(HEALING_AMOUNT * levelModifier);
        floodStacks+=3;
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        this.health += floodStacks * 5 * levelModifier;

        double effectiveDamage = effectivenessMultiplier(others.get(0)) * (int)(floodStacks * levelModifier) * 10;
        others.get(0).takeDamage(effectiveDamage);
        ultimateUsed = true;
    }


    public String getAttackDetails() {
        return "Wave: deals " + attack + " water damage and gains 1 flood stack.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Cleanse: removes negative effects & debuffs, heals " + (int)(HEALING_AMOUNT * levelModifier) + " health, and gains 3 flood stacks.";
    }

    public String unwrappedGetUltimateDetails() {
        return "Tsunami: heals " + (int)(floodStacks * 5 * levelModifier) + " health and does " + (int)(floodStacks * 10 * levelModifier) + " water damage. Based on flood stacks.";
    }
}
