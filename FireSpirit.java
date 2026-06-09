import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FireSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FireSpirit extends Spirit
{
    /**
     * Act - do whatever the FireSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static final GreenfootSound BURN_SOUND = new GreenfootSound("fire-attack.mp3");
    static final GreenfootSound LIGHT_MATCH = new GreenfootSound("ignite.mp3");

    static GreenfootImage costume = new GreenfootImage("fire.png");
    static final String NAME = "Fire Spirit";
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Burn";
    static final String PASSIVE_NAME = "FlameThrower";
    static final String PASSIVE_DETAILS = "Burn your enemies overtime!";
    static final int PASSIVE_COOLDOWN = 4;
    public FireSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.fire, ATTACK_NAME, PASSIVE_NAME, PASSIVE_COOLDOWN, costume, BURN_SOUND);

    }

    public void passive(Entity other) {
        LIGHT_MATCH.play();
        other.burningDamage = Math.max(other.burningDamage, (int)(7 * levelModifier));
        other.burningDuration = Math.max(other.burningDuration, 4);

        
        // Any additional initialization code for FireSpirit can go here
    }

    public BlazeSoul getUpgraded() {
        return new BlazeSoul();
    }


    public String getAttackDetails() {
        return "Burn: deals " + attack + " fire damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "FlameThrower: burns enemy for " + (int)(7 * this.levelModifier) + " damage over " + (int)(4 * this.levelModifier) + " turns. Does not stack.";
    }
}
