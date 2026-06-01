import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BigSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BigSpirit extends Spirit
{
    /**
     * Act - do whatever the BigSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    static GreenfootImage costume = new GreenfootImage("big.png");
    static final String NAME = "Big Spirit";
    static final int BASE_HEALTH = 120;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Stomp";
    static final String PASSIVE_NAME = "Ground Pound";
    static final String PASSIVE_DETAILS = "Increase your damage output by 10 damage";
    
    public BigSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.big, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);

    }

    public void passive(Entity other) {
        
    }

    public Soul getUpgraded() {
        return null;
    }
}
