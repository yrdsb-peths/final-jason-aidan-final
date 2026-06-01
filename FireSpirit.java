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
    static GreenfootImage costume = new GreenfootImage("fire.png");
    static final String NAME = "Fire Spirit";
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Burn";
    static final String PASSIVE_NAME = "FlameThrower";
    static final String PASSIVE_DETAILS = "Burn your enemies overtime!";
    
    public FireSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.fire, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);

    }

    public void passive(Entity other) {
        other.burningDamage = Math.max(other.burningDamage, 5 * (1+this.level/10));
        other.burningDuration = Math.max(other.burningDuration, 5 * (1+this.level/10));

        
        // Any additional initialization code for FireSpirit can go here
    }

    public BlazeSoul getUpgraded() {
        return new BlazeSoul();
    }


}
