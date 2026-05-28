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
    static final int BASE_ATTACK = 30;
    static final String attackName = "Stomp";
    static final String passiveName = "Ground Pound";
    static final String passiveDetails = "Increase your damage output by 10 damage";
    
    public BigSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.big, attackName, passiveName, passiveDetails, costume);
        setLocation(200, 300);
        setImage(costume);
    }
}
