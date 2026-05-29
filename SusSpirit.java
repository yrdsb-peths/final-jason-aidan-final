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
    static final int BASE_HEALTH = 150;
    static final int BASE_ATTACK = 10;
    static final String attackName = "sus";
    static final String passiveName = "sus";
    static final String passiveDetails = "sus";
    
    public SusSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.sus, attackName, passiveName, passiveDetails, costume);
        setLocation(200, 300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
        // Any additional initialization code for FireSpirit can go here
    }
}
