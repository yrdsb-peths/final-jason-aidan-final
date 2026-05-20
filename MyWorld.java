import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MyWorld extends World {
    
    int maxSpirits = 2;
    Spirit[] playerSpirits;
    int playerNum = 1;
    
    Chooser chooser;
    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        playerSpirits = new Spirit[maxSpirits];
        Submit s = new Submit();
        addObject(s,500,350);
        
        chooser = displaySpirits();
    }

    public void act() {
        
        chooseSpirit(chooser);
        
        //System.out.println(playerSpirits[0]);
        //System.out.println(playerSpirits[1]);

    }

    public Chooser displaySpirits(){
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

            return chooser;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();

            return null;
        }


    }

    public void chooseSpirit(Chooser chooser) {

        if (chooser == null) {
            System.out.println("Error creating chooser");
            return;
        }

        int j = 0;
        int i = 0;
        try {
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {
                
                if (chooser.switches[i].status == 1) {
                    playerSpirits[j] = spiritClass.getDeclaredConstructor().newInstance();
                    j++;
                }
                i++;

            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
        }

        
    }
}
