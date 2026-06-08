import greenfoot.*;
import java.util.ArrayList;

enum States {
    TITLE,
    CHOOSING,
    CHOOSINGAI,
    BATTLE
}

public class MyWorld extends World {
    
    final static int MAX_ENTITIES = 2;
    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    int playerNum = 1;
    static int worldTime = 0;
    States currentState = States.TITLE;
    

    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    GreenfootImage image;

    boolean screenCreated;
    
    public MyWorld() {

        

        super(WIDTH, HEIGHT, 1);
        player1Entities = new ArrayList<>();
        player2Entities = new ArrayList<>();

        
        
        screenCreated = false;

    }

    public void act() {
        
        worldTime ++;

        if (currentState == States.TITLE) {
            if (!screenCreated) {
                screenCreated = true;
                addObject(new TitleScreen(this), WIDTH/2, HEIGHT/2);
            }



        } else if (currentState == States.CHOOSING) {

            if (!screenCreated) {
                addObject(new ChooseScreenPVP(player1Entities, player2Entities, this), WIDTH/2, HEIGHT/2);
                screenCreated = true;
                image = new GreenfootImage("backgroundStarter.png");
                image.scale(600, 400);
                setBackground(image);
            }

        } else if (currentState == States.CHOOSINGAI) {
            
            if (!screenCreated) {
                addObject(new ChooseScreenAI(player1Entities, player2Entities, this), WIDTH/2, HEIGHT/2);
                screenCreated = true;
                image = new GreenfootImage("backgroundStarter.png");
                image.scale(600, 400);
                setBackground(image);
            }
            
        } else if (currentState == States.BATTLE) {

            if (!screenCreated) {
                BattleScreen.newInstance(player1Entities, player2Entities, this);
                addObject(BattleScreen.getInstance(), WIDTH/2, HEIGHT/2);
                screenCreated = true;
            }
        }
        
    }
}
