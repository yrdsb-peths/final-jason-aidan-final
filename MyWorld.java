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
    
    Button attack;
    Button passive;
    Button chooseNew;
    Button flee;
    
    static int WIDTH = 600;
    static int HEIGHT = 400;

    boolean screenCreated;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        player1Spirits = new ArrayList<>();
        player2Spirits = new ArrayList<>();
        
        screenCreated = false;

        attack = new Button(null, 20);
        addObject(attack,WIDTH/4,HEIGHT/3*2);
        passive = new Button(null, 20);
        addObject(passive,WIDTH/4 * 3,HEIGHT/3*2);
        chooseNew = new Button(null, 20);
        addObject(chooseNew,WIDTH/4,HEIGHT/6*5);
        flee = new Button(null, 20);
        addObject(flee,WIDTH/4 * 3,HEIGHT/6*5);
        

    }

    public void act() {
        
        if (currentState == States.CHOOSING) {

            if (!screenCreated) {
                addObject(new ChooseScreen(player1Spirits, player2Spirits, this), WIDTH/2, HEIGHT/2);
                screenCreated = true;
            }

        } else if (currentState == States.BATTLE) {
            // Code to handle battle state

            // player 1 turn first
            // player 2 after and cycles after that
            int playerTurn = 1;
            playerTurn(1);
            //if(!isEmptySpirits(player1Spirits) || !isEmptySpirits(player2Spirits))
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
            //} else {
                //determine Winner
            //}
            //
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
        showPlayerButtons(playerIndex);
        if(playerIndex == 1)
        {
            //player chooses button by click
        }
        else
        {
            //player chooses button by click
        }
    }
    
    public void showPlayerButtons(int num)
    {
        GreenfootImage a;
        GreenfootImage p;
        GreenfootImage c;
        GreenfootImage f;
        int scaleX = 210;
        int scaleY = 70;
        if(num == 1)
        {
            a = new GreenfootImage("attack_P1.png");
            p = new GreenfootImage("passive_P1.png");
            c = new GreenfootImage("chooseNew_P1.png");
            f = new GreenfootImage("flee_P1.png");
            a.scale(scaleX,scaleY);
            p.scale(scaleX,scaleY);
            c.scale(scaleX,scaleY);
            f.scale(scaleX,scaleY);
        }
        else
        {
            a = new GreenfootImage("attack_P2.png");
            p = new GreenfootImage("passive_P2.png");
            c = new GreenfootImage("chooseNew_P2.png");
            f = new GreenfootImage("flee_P2.png");
            a.scale(scaleX,scaleY);
            p.scale(scaleX,scaleY);
            c.scale(scaleX,scaleY);
            f.scale(scaleX,scaleY);
        }
        attack.setImage(a);
        passive.setImage(p);
        chooseNew.setImage(c);
        flee.setImage(f);
    }
}