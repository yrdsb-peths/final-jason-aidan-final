import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

enum States {
    CHOOSING,
    BATTLE

}

public class MyWorld extends World {
    
    static int maxSpirits = 5;
    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    int playerNum = 1;
    States currentState = States.CHOOSING;

    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    GreenfootImage image;

    boolean screenCreated;
    
    public MyWorld() {

        

        super(WIDTH, HEIGHT, 1);
        player1Spirits = new ArrayList<>();
        player2Spirits = new ArrayList<>();

        
        
        screenCreated = false;

    }

    public void act() {
        
        if (currentState == States.CHOOSING) {

            if (!screenCreated) {
                addObject(new ChooseScreen(player1Spirits, player2Spirits, this), WIDTH/2, HEIGHT/2);
                screenCreated = true;
                image = new GreenfootImage("backgroundStarter.png");
                image.scale(600,400);
                setBackground(image);
            }

        } else if (currentState == States.BATTLE) {

            if (!screenCreated) {
                addObject(new BattleScreen(player1Spirits, player2Spirits, this), WIDTH/2, HEIGHT/2);
                screenCreated = true;
            }
        }
        
    }
}
