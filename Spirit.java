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
    
    int health;
    int attack;
    String type;
    String weakness;
    
    public Spirit(int health, int attack, String type, String weakness)
    {
        this.health = health;
        this.attack = attack;
        this.type = type;
        this.weakness = weakness;
    }
    public void act()
    {
        
    }
}
