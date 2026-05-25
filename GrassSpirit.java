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
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 7;

    public GrassSpirit()
    {
        super(BASE_HEALTH, BASE_ATTACK, Element.grass);
        setLocation(200,300);
        setImage(costume);
    }
    public void act()
    {
        // Add your action code here.
    }
}
