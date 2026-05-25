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
    
    public FireSpirit()
    {
        super(50, 10, "Fire");
        setLocation(200, 300);
        setImage(costume);
    }
    public void act()
    {
        // Add your action code here.
    }
}
