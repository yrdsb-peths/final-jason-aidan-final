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
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    
    public OrdinarySpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.ordinary);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
    }
}
