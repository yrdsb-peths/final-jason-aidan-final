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
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 20;
    static final String attackName = "Flare";
    static final String passiveName = "Meteor Rain";
    static final String passiveDetails = "Extreme burning to your enemies overtime!";
    
    public StarSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.star, attackName, passiveName, passiveDetails, costume);
        setLocation(200, 300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
        // Any additional initialization code for FireSpirit can go here
    }
}
