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
    
    public GrassSpirit()
    {
        super(20, 10, "Grass");
        setLocation(200,300);
        GreenfootImage image = new GreenfootImage("button-green.png");
        setImage(image);
    }
    public void act()
    {
        // Add your action code here.
    }
}
