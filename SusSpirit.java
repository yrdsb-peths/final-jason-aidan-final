import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SusSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SusSpirit extends Spirit
{
    /**
     * Act - do whatever the SusSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("sus.png");
    static final String NAME = "Sus Spirit";
    static final int BASE_HEALTH = 120;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "sus";
    static final String PASSIVE_NAME = "sus";
    static final String PASSIVE_DETAILS = "sus";
    
    public SusSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.sus, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);
    }

    public void passive(Entity other) {
        
        // Any additional initialization code for FireSpirit can go here
    }

    public Soul getUpgraded() {
        return null;
    }
}
