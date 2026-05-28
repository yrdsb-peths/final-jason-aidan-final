import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OrdinarySpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OrdinarySpirit extends Spirit
{
    /**
     * Act - do whatever the OrdinarySpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("ordinary.png");
    static final String NAME = "Ordinary Spirit";
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    static final String attackName = "Thing";
    static final String passiveName = "Transformation";
    static final String passiveDetails = "You'll find out soon...";
    
    public OrdinarySpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.ordinary, attackName, passiveName, passiveDetails, costume);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
    }
}
