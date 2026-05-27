import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WaterSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterSpirit extends Spirit
{
    /**
     * Act - do whatever the WaterSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    static GreenfootImage costume = new GreenfootImage("water.png");
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    static final String attackName = "Splash";
    static final String passiveName = "Rainfall";
    static final String passiveDetails = "Remove effects and deal 20 damage";
    
    public WaterSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.water, attackName, passiveName, passiveDetails);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
        this.health += 10;
        other.healingAmount = 5;
        other.healingDuration = 3;
    }

}
