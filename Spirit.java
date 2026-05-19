import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spirit extends Actor
{
    /**
     * Act - do whatever the Spirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Spirit(String type, String name)
    {
        if(type == "Fire")
        {
            Spirit f = new FireSpirit(name);
        }
    }
    public void act()
    {
        
    }
}
