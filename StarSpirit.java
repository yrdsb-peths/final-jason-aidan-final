import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StarSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StarSpirit extends Spirit
{
    /**
     * Act - do whatever the StarSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("star.png");
    static final String NAME = "Star Spirit";
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 15;
    static final String ATTACK_NAME = "Flare";
    static final String PASSIVE_NAME = "Meteor Rain";
    static final String PASSIVE_DETAILS = "Extreme burning to your enemies overtime!";
    
    public StarSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.star, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);
    }

    public void passive(Entity other) {
        
        // Any additional initialization code for FireSpirit can go here
    }

    public Soul getUpgraded() {
        return null;
    }
}
