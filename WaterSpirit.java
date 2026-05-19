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
    
    public WaterSpirit()
    {
        super(20,5,"Water");
        setLocation(200,300);
        GreenfootImage image = new GreenfootImage("button-blue.png");
        setImage(image);
    }
    public void act()
    {
        // Add your action code here.
    }
}
