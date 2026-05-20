import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Switch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Switch extends Actor
{
    /**
     * Act - do whatever the Switch wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    int status = 0; // 0 for off, 1 for on
    int width;
    int height;
    GreenfootImage onImage;
    GreenfootImage offImage;
    Chooser chooser;

    public Switch(GreenfootImage onImage, GreenfootImage offImage, int width, int height, Chooser chooser) {
        
        // System.out.println("created");
        
        this.onImage = onImage;
        this.offImage = offImage;
        this.chooser = chooser;
        
        this.width = width;
        this.height = height;
        
        setImage(offImage);

        // System.out.println("initialized");
        
        
    }


    public void act()
    {
        // Add your action code here.
        if (Greenfoot.mouseClicked(this)) {
            toggle();
        }
    }

    public void toggle() {

        if (chooser == null) {
            return;
        }

        if (status == 0 && chooser.selectedNumber < chooser.maxOptions) {
            setImage(onImage);
            status = 1;
            
        } else {
            setImage(offImage);
            status = 0;
        }

        chooser.updateSelectedNumber();
    }
}
