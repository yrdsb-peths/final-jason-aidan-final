import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RockSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RockSpirit extends Spirit
{
    /**
     * Act - do whatever the RockSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("rock.png");
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 20;
    static final String attackName = "Crumble";
    static final String passiveName = "Boulder Shell";
    static final String passiveDetails = "Reduce the damage taken";
    
    public RockSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.rock, attackName, passiveName, passiveDetails);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
    }
}
