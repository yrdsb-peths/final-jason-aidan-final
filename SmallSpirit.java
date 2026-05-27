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
    static final int BASE_HEALTH = 40;
    static final int BASE_ATTACK = 10;
    static final String attackName = "Bite";
    static final String passiveName = "Swift Dodge";
    static final String passiveDetails = "Your Opponent now has a change to miss!";
    
    public SmallSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.small, attackName, passiveName, passiveDetails, costume);
        setLocation(200, 300);
        setImage(costume);
    }
}
