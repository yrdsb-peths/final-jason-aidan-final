import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FireSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FireSpirit extends Spirit
{
    /**
     * Act - do whatever the FireSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("fire.png");
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    
    public FireSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.fire);
        setLocation(200, 300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        other.burningDamage = 5;
        other.burningDuration = 3;

        
        // Any additional initialization code for FireSpirit can go here
    }

}
