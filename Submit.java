import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Submit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Submit extends Actor
{
    /**
     * Act - do whatever the Submit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    boolean startGame;
    public Submit()
    {
        GreenfootImage image = new GreenfootImage("karo.png");
        setImage(image);
        startGame = false;
    }
    public void act()
    {
        // Add your action code here.
        if (Greenfoot.mouseClicked(this) && !startGame) {
            startGame = true;
            Greenfoot.stop();
        }
    }
}
