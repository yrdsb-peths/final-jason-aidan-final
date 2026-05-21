import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

enum States {
    CHOOSING,
    BATTLE
}

public class MyWorld extends World {
    
    int maxSpirits = 2;
    Spirit[] playerSpirits;
    int playerNum = 1;
    States currentState = States.CHOOSING;
    
    Chooser chooser;
    Button submitButton;
    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        playerSpirits = new Spirit[maxSpirits];
        submitButton = new Button(new GreenfootImage("karo.png"), 20);
        addObject(submitButton, 500, 350);
        
        chooser = displaySpirits();
    }

    public void act() {
        
        if (currentState == States.CHOOSING) {
            chooseSpirit(chooser);
            boolean finishedChoosing = true;
            for (Spirit spirit : playerSpirits) {
                if (spirit == null) {
                    finishedChoosing = false;
                }
            }
            if (finishedChoosing && submitButton.isPressed) {
                currentState = States.BATTLE;
                chooser.remove();
            }
        } else if (currentState == States.BATTLE) {
            // Code to handle battle state
        }
        


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
