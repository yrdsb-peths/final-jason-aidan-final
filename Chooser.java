import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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
    Switch[] switches;
    ArrayList<Integer> selectedIndices;
    int maxOptions;
    int selectedNumber;
    boolean switchesCreated = false;

    public Chooser(GreenfootImage[] options, int maxOptions, int spacing)
    {
        this.spacing = spacing;
        this.options = options;
        this.maxOptions = maxOptions;
        this.selectedNumber = 0;
        switches = new Switch[options.length];
        selectedIndices = new ArrayList<Integer>();

        for (int i = 0; i < options.length; i++) {
                                
            GreenfootImage small = new GreenfootImage(options[i]);
            small.scale(50, 50);

            GreenfootImage large = new GreenfootImage(options[i]);
            large.scale(60, 60);


            Switch switch_ = new Switch(large, small, i, this);   
            
            switches[i] = switch_;
        }
    }

    public void act()
    {
        // can't use getWorld() in the constructor, so we create the switches here
        
        if (!switchesCreated) {
            for (int i = 0; i < options.length; i++) {
                getWorld().addObject(switches[i], getX() + spacing * i, getY());
            }
            switchesCreated = true;
        }
        // Add your action code here.
    }

    public void updateSelectedNumber(int index) {
        // Code to return the amount of the selected option

        if (selectedNumber == maxOptions && !selectedIndices.contains(index)) {
            return;
        }

        for (int i = 0; i < selectedIndices.size(); i++) {
            if (selectedIndices.get(i) == index) {
                selectedIndices.remove(i);
                selectedNumber--;
                return;
            }
        }
        selectedIndices.add(index);
        selectedNumber++;

    }

    public void remove() {
        for (Switch switch_ : switches) {
            getWorld().removeObject(switch_);
        }
        getWorld().removeObject(this);
    }
}
