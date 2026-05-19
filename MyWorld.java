import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MyWorld extends World {
    
    int maxSpirits = 5;
    Spirit[] playerSpirits;

    public MyWorld() {

        super(600, 400, 1);
        playerSpirits = new Spirit[maxSpirits];
        displaySpirits();

    }

    public void displaySpirits(){
        // Code to display the player's spirits on the screen

        try{
            GreenfootImage[] costumeList = new GreenfootImage[Spirit.spiritTypes.size()];
            int i = 0;
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {

                Spirit spirit = spiritClass.getDeclaredConstructor().newInstance();
                addObject(spirit, i * 100 + 50, 350);
                costumeList[i] = new GreenfootImage(spirit.getImage());
                costumeList[i].scale(50, 50);
                i++;
            }

            Chooser chooser = new Chooser(costumeList, 100);
            addObject(chooser, 300, 200);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
    
    
}
