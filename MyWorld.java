import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

enum States {
    CHOOSING,
    BATTLE

}

public class MyWorld extends World {
    
    static int maxSpirits = 2;
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
                int i = Greenfoot.getRandomNumber(3);
                if(i == 0)
                {
                    image = new GreenfootImage("background1.png");
                } else if (i == 1)
                {
                    image = new GreenfootImage("background2.png");
                } else if (i == 2)
                {
                    image = new GreenfootImage("background3.png");
                } else if (i == 3)
                {
                    image = new GreenfootImage("background4.png");
                }
                image.scale(600,400);
                setBackground(image);
            }
        }
        
    }
}
