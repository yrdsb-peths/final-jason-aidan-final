import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Chooser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chooser extends Actor
{
    /**
     * Act - do whatever the Chooser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int spacing;
    GreenfootImage[] options;
    int maxOptions = 0;
    boolean switchesCreated = false;

    public Chooser(GreenfootImage[] options, int spacing)
    {
        this.spacing = spacing;
        this.options = options;
        maxOptions = options.length;
        System.out.println("test1");
    }

    public void act()
    {
        // can't use getWorld() in the constructor, so we create the switches here
        
        if (!switchesCreated) {
            for (int i = 0; i < maxOptions; i++) {
                
                System.out.println("test");

                GreenfootImage small = new GreenfootImage(options[i]);
                small.scale(50, 50);

                GreenfootImage large = new GreenfootImage(options[i]);
                large.scale(60, 60);

                SwitchA switcher = new SwitchA(large, small, 50, 50);
                
                System.out.println("test3");


                //getWorld().addObject(switch_, getX() + spacing * i, getY());
                
                System.out.println("test2");
            }
            switchesCreated = true;
        }

        // Add your action code here.
    }
}
