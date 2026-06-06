import java.util.ArrayList;

import greenfoot.GreenfootImage;

/**
 * Write a description of class Blaze here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlazeSoul extends Soul 
{

    /**
     * Constructor for objects of class Blaze
     */
    static final GreenfootImage COSTUME = new GreenfootImage("blaze.png");
    static final String NAME = "Blaze Soul";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 25;
    static final String ATTACK_NAME = "Burn";
    static final String PASSIVE_NAME = "FlameThrower";

    static final String ULTIMATE_NAME = "Firestorm";
    static final String ULTIMATE_DETAILS = "High damage + burning, self-damaging.";

    static final String PASSIVE_DETAILS = "Set your enemy on fire.";
    static final int BURST_MULTIPLIER = 2;
    static final int PASSIVE_COOLDOWN = 4;

    public BlazeSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.fire, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, COSTUME);

    }

    public void passive(Entity other) {
        other.burningDamage = (int) (15 * this.levelModifier);
        other.burningDuration = 6;
        // Any additional initialization code for FireSpirit can go here
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        this.health -= 10 * this.levelModifier;
        this.burningDamage = (int) (3 * this.levelModifier);
        this.burningDuration = 3;

        others.get(0).takeDamage((int)(effectivenessMultiplier(others.get(0)) * (int)(attack * BURST_MULTIPLIER * this.levelModifier)));
        others.get(0).burningDamage = (int) (15 * this.levelModifier);
        others.get(0).burningDuration += 6;
    }


    public String getAttackDetails() {
        return "Burn: deals " + attack + " fire damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "FlameThrower: burns enemy for " + (int)(15 * this.levelModifier) + " damage over 6 turns. Does not stack.";
    }

    public String unwrappedGetUltimateDetails() {
        return "Firestorm: deals " + (int)(attack * 2 * this.levelModifier) + " fire damage and burns the target.";
    }
}
