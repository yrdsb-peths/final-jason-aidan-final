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

    ChooseScreen chooseScreen;
    
    Button attack;
    Button passive;
    Button chooseNew;
    Button flee;
    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        player1Spirits = new ArrayList<>();
        player2Spirits = new ArrayList<>();

        attack = new Button(null, 20);
        addObject(attack,WIDTH/4,HEIGHT/3*2);
        passive = new Button(null, 20);
        addObject(passive,WIDTH/4 * 3,HEIGHT/3*2);
        chooseNew = new Button(null, 20);
        addObject(chooseNew,WIDTH/4,HEIGHT/6*5);
        flee = new Button(null, 20);
        addObject(flee,WIDTH/4 * 3,HEIGHT/6*5);
        
        chooseScreen = new ChooseScreen(player1Spirits, player2Spirits, this);
        addObject(chooseScreen, WIDTH/2, HEIGHT/2);
    }

    public void act() {
        
        if (currentState == States.CHOOSING) {

            if (chooseScreen == null) {
                currentState = States.BATTLE;
            }
            
        } else if (currentState == States.BATTLE) {
            // Code to handle battle state

            // player 1 turn first
            // player 2 after and cycles after that
            int playerTurn = 1;
            playerTurn(1);
            //while(!isEmptySpirits(player1Spirits) || !isEmptySpirits(player2Spirits))
            //{
                //if(playerTurn % 2 != 0)
                //{
                    //playerTurn(1);
                //}
                //else
                //{
                    //playerTurn(2);
                //}
                //playerTurn++;
            //}
            //determineWinner();
        }
        
    }

    //returns true if the given list has no spirits
    public boolean isEmptySpirits(Spirit[] list)
    {
        boolean output = true;
        for(int i = 0; i < list.length; i++)
        {
            if(list[i] != null)
            {
                output = false;
            }
        }
        return output;
    }
    //players "playerIndex" turn, player can attack, use passive, choose new spirit, or flee battle
    public void playerTurn(int playerIndex)
    {
        if(playerIndex == 1)
        {
            //show all buttons, attack, passive, choose new, flee for p1
            attack.setImage(new GreenfootImage("button-red.png"));
            passive.setImage(new GreenfootImage("button-green.png"));
            chooseNew.setImage(new GreenfootImage("button-blue.png"));
            flee.setImage(new GreenfootImage("button-yellow.png"));
            //
        }
        else
        {
            //show all buttons for p2
            attack.setImage(new GreenfootImage("button-red.png"));
            passive.setImage(new GreenfootImage("button-green.png"));
            chooseNew.setImage(new GreenfootImage("button-blue.png"));
            flee.setImage(new GreenfootImage("button-yellow.png"));
        }
    }
}