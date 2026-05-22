import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor
{
    /**
     * Act - do whatever the Button wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int delayLeft;
    int delay = 20; // Number of act cycles the button stays pressed
    boolean isPressed = false;
    GreenfootImage image;
    public Button(GreenfootImage image, int delay)
    {
        this.image = image;
        setImage(image);
        isPressed = false;
        this.delay = delay;
        this.delayLeft = 0;
    }
    public void act()
    {
        // Add your action code here.        
        if (Greenfoot.mouseClicked(this) && !isPressed) {
            isPressed = true;
            delayLeft = delay; // Set the delay for how long the button stays pressed
        }

        delayLeft--;

        if (delayLeft <= 0) {
            isPressed = false; // Reset the button state after the delay
        }
    }

    public void remove() {
        getWorld().removeObject(this);
    }
}