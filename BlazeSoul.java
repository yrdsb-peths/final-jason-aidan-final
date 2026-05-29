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
    static GreenfootImage costume = new GreenfootImage("blaze.png");
    static final String NAME = "Fire Spirit";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 20;
    static final String attackName = "Burn";
    static final String passiveName = "FlameThrower";
    static final String ultimateName = "Burst";
    static final String passiveDetails = "Burn your enemies overtime!";
    
    public BlazeSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.fire, attackName, passiveName, passiveDetails, costume);

    }

    public void passive(Spirit other) {
        other.burningDamage = (int) (5 * this.levelModifier);
        other.burningDuration = 5;
        // Any additional initialization code for FireSpirit can go here
    }

    public void ultimate(Spirit other) {
        this.health -= 10 * this.levelModifier;
        this.burningDamage = (int) (3 * this.levelModifier);
        this.burningDuration = 3;

        other.health -= 50 * this.levelModifier;
        other.burningDamage = (int) (10 * this.levelModifier);
        other.burningDuration += 3;
    }
}
