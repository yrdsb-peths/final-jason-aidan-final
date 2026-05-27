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
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 20;
    static final String attackName = "Toxin";
    static final String passiveName = "Corrosive Acid";
    static final String passiveDetails = "Poison your enemies overtime!";
    
    public PoisonSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.poison, attackName, passiveName, passiveDetails);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        other.poisonedPercentage = 0.2;
        other.poisonedDuration = 3;
    }
}
