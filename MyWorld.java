import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MyWorld extends World {
    
    int maxSpirits = 5;
    Spirit[] playerSpirits;
    int playerNum = 1;
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        playerSpirits = new Spirit[maxSpirits];
        displaySpirits();
        Submit s = new Submit();
        addObject(s,500,350);
    }

    public void displaySpirits(){
        // Code to display the player's spirits on the screen

        try{
            GreenfootImage[] costumeList = new GreenfootImage[Spirit.spiritTypes.size()];
            int i = 0;
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {

                Spirit spirit = spiritClass.getDeclaredConstructor().newInstance();
                costumeList[i] = new GreenfootImage(spirit.getImage());
                costumeList[i].scale(50, 50);
                i++;
            }

            Chooser chooser = new Chooser(costumeList, 2, 100);
            // chooser.switches[0].status 
            addObject(chooser, 300, 200);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
    
    
}
