import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GrassSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrassSpirit extends Spirit
{
    /**
     * Act - do whatever the GrassSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("grass.png");
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    static final String attackName = "Seed";
    static final String passiveName = "Pollenate";
    static final String passiveDetails = "Heal 10 health";

    public GrassSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.fire, attackName, passiveName, passiveDetails);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        other.poisonedPercentage = 0.2;
        other.poisonedDuration = 3;

        
        // Any additional initialization code for FireSpirit can go here
    }

}
