import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DarkSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DarkSpirit extends Spirit
{
    /**
     * Act - do whatever the DarkSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("dark.png");
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 20;
    static final String attackName = "Shadow";
    static final String passiveName = "Total Blackout";
    static final String passiveDetails = "Exttreme poison to your enemies overtime";
    
    public DarkSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.dark, attackName, passiveName, passiveDetails, costume);
        setLocation(200,300);
        setImage(costume);
    }

    public void passive(Spirit other) {
        
    }
}
