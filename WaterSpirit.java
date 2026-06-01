import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WaterSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterSpirit extends Spirit
{
    /**
     * Act - do whatever the WaterSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    static GreenfootImage costume = new GreenfootImage("water.png");
    static final String NAME = "Water Spirit";
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "Splash";
    static final String PASSIVE_NAME = "Purify";
    static final String PASSIVE_DETAILS = "Remove effects and heal";
    
    public WaterSpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.water, ATTACK_NAME, PASSIVE_NAME, costume);

    }

    public void passive(Entity other) {
        this.stunDuration = 0;
        this.burningDuration = 0;
        this.poisonedDuration = 0;
        this.health += (int)(10 * levelModifier);
    }

    public Soul getUpgraded() {
        return new TideSoul();
    }


    public String getAttackDetails() {
        return "Splash: deals " + attack + " water damage.";
    }

    public String getPassiveDetails() {
        return "Purify: removes status effects and heals " + (int)(10 * levelModifier) + " health.";
    }

}
