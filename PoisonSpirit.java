import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PoisonSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoisonSpirit extends Spirit
{
    /**
     * Act - do whatever the PoisonSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("poison.png");
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    
    public PoisonSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.poison);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
        this.health += 10;
        other.healingAmount = 5;
        other.healingDuration = 3;
    }
}
