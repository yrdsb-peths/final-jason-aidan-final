import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Switch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SwitchA extends Actor
{
    /**
     * Act - do whatever the Switch wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    int status = 0; // 0 for off, 1 for on
    int width;
    int height;
    GreenfootImage onImage = new GreenfootImage("switch-on.png");
    GreenfootImage offImage = new GreenfootImage("switch-off.png");

    public SwitchA(GreenfootImage onImage, GreenfootImage offImage, int width, int height) {
        
        System.out.println("hello1");
        this.onImage = onImage;
        this.offImage = offImage;
        
        System.out.println("hello2");
        this.width = width;
        this.height = height;
        
        System.out.println("hello3");
        setImage(offImage);
        
        
    }


    public void act()
    {
        System.out.println("hello4");
        // Add your action code here.
        if (Greenfoot.mouseClicked(this)) {
            toggle();
        }
    }

    public void toggle() {
        if (status == 0) {
            setImage(onImage);
            status = 1;
        } else {
            setImage(offImage);
            status = 0;
        }
    }
}
