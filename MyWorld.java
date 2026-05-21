import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

enum States {
    CHOOSING,
    BATTLE

}

public class MyWorld extends World {
    
    int maxSpirits = 2;
    Spirit[] player1Spirits;
    Spirit[] player2Spirits;
    int playerNum = 1;
    States currentState = States.CHOOSING;
    
    Chooser chooser1;
    Chooser chooser2;
    Button submitButton;
    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        player1Spirits = new Spirit[maxSpirits];
        player2Spirits = new Spirit[maxSpirits];
        submitButton = new Button(new GreenfootImage("karo.png"), 20);
        addObject(submitButton, 500, 350);
        
        chooser1 = createSpiritChooser(70, 50, 70);
        chooser2 = createSpiritChooser(400, 50, 70);
    }

    public void act() {
        
        if (currentState == States.CHOOSING) {
            chooseSpirit(chooser1, chooser2);
            boolean finishedChoosing = true;
            for (int i = 0; i < maxSpirits; i++) {
                if (player1Spirits[i] == null || player2Spirits[i] == null) {
                    finishedChoosing = false;
                    break; 
                }
            }
            if (finishedChoosing && submitButton.isPressed) {
                currentState = States.BATTLE;
                chooser1.remove();
                chooser2.remove();
            }
        } else if (currentState == States.BATTLE) {
            // Code to handle battle state
            for (int i = 0; i < maxSpirits; i++) {
                System.out.println("Player 1 Spirit " + (i+1) + ": " + player1Spirits[i].type);
                System.out.println("Player 2 Spirit " + (i+1) + ": " + player2Spirits[i].type);
            }
        }
        


    }

    public Chooser createSpiritChooser(int x, int y, int spacing){
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

            Chooser chooser = new Chooser(costumeList, 2, spacing);

            addObject(chooser, x, y);

            return chooser;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();

            return null;
        }
    }

    public void chooseSpirit(Chooser chooser1, Chooser chooser2) { 

        if (chooser1 == null) {
            System.out.println("Error creating chooser");
            return;
        }

        if (chooser2 == null) {
            System.out.println("Error creating chooser");
            return;
        }

        int j1 = 0;
        int j2 = 0;
        int i = 0;
        try {
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {
                
                if (chooser1.switches[i].status == 1) {
                    player1Spirits[j1] = spiritClass.getDeclaredConstructor().newInstance();
                    j1++;
                }

                if (chooser2.switches[i].status == 1) {
                    player2Spirits[j2] = spiritClass.getDeclaredConstructor().newInstance();
                    j2++;
                }
                i++;

            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
        }

        
    }
    

}