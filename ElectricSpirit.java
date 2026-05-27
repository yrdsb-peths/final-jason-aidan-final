import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ElectricSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ElectricSpirit extends Spirit
{
    /**
     * Act - do whatever the ElectricSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("electric.png");
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 20;
    static final String attackName = "Shock";
    static final String passiveName = "Thunder Strike";
    static final String passiveDetails = "Increase your chances of a critical hit!";
    
    public ElectricSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.electric, attackName, passiveName, passiveDetails);
        setLocation(200, 300);
        setImage(costume);
    }
}
