import greenfoot.*;
import java.util.ArrayList;

enum States {
    CHOOSING,
    BATTLE
}

public class MyWorld extends World {
    
    static int maxEntities = 5;
    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    int playerNum = 1;
    States currentState = States.CHOOSING;

    
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
        
        if (currentState == States.CHOOSING) {

            if (!screenCreated) {
                addObject(new ChooseScreen(player1Entities, player2Entities, this), WIDTH/2, HEIGHT/2);
                screenCreated = true;
                image = new GreenfootImage("backgroundStarter.png");
                image.scale(600,400);
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
