import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SmallSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SmallSpirit extends Spirit
{
    /**
     * Act - do whatever the SmallSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    static GreenfootImage costume = new GreenfootImage("small.png");
    static final String NAME = "Small Spirit";
    static final int BASE_HEALTH = 40;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "Bite";
    static final String PASSIVE_NAME = "Shrink";
    static final String PASSIVE_DETAILS = "Your Opponent now has a change to miss!";
    
    public SmallSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.small, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);
        this.evasion = 0.3;
    }

    public Soul getUpgraded() {
        return null;
    }

    public void passive(Entity other) {
        this.attack = (int) (this.attack * 0.9);
        this.health = (int) (this.health * 0.9);
        this.evasion = 1 - (1 - this.evasion)/3;
    }
}
