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
    static final String NAME = "Fire Spirit";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 35;
    static final String ATTACK_NAME = "Burn";
    static final String PASSIVE_NAME = "Ignite";

    static final String ULTIMATE_NAME = "Burst";
    static final String ULTIMATE_DETAILS = "High damage + burning, self-damaging.";

    static final String PASSIVE_DETAILS = "Set your enemy on fire.";
    
    public BlazeSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.fire, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, ULTIMATE_NAME, ULTIMATE_DETAILS, COSTUME);

    }

    public void passive(Entity other) {
        other.burningDamage = (int) (15 * this.levelModifier);
        other.burningDuration = 5;
        // Any additional initialization code for FireSpirit can go here
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        if (!this.ultimateUsed) {
            this.health -= 10 * this.levelModifier;
            this.burningDamage = (int) (3 * this.levelModifier);
            this.burningDuration = 3;

            others.get(0).health -= (int)(effectivenessMultiplier(others.get(0)) * (int)(attack * 2 * this.levelModifier));
            others.get(0).burningDamage = (int) (15 * this.levelModifier);
            others.get(0).burningDuration += 5;
            ultimateUsed = true;
        }
    }
}
