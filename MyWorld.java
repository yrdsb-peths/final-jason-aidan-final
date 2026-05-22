import greenfoot.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

enum States {
    CHOOSING,
    BATTLE

}

public class MyWorld extends World {
    
    static int maxSpirits = 2;
    Spirit[] player1Spirits;
    Spirit[] player2Spirits;

    int playerNum = 1;
    States currentState = States.CHOOSING;

    ChooseScreen chooseScreen;
    
    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        player1Spirits = new Spirit[maxSpirits];
        player2Spirits = new Spirit[maxSpirits];
        
        chooseScreen = new ChooseScreen(player1Spirits, player2Spirits, this);
        addObject(chooseScreen, WIDTH/2, HEIGHT/2);
    }

    public void act() {
        
        if (currentState == States.CHOOSING) {

            if (chooseScreen.isFinished) {
                currentState = States.BATTLE;
            }
            
        } else if (currentState == States.BATTLE) {
            // Code to handle battle state
            for (int i = 0; i < maxSpirits; i++) {
                System.out.println("Player 1 Spirit " + (i+1) + ": " + player1Spirits[i].type);
                System.out.println("Player 2 Spirit " + (i+1) + ": " + player2Spirits[i].type);
            }
            // player 1 turn first
            // player 2 after and cycles after that
            //int playerTurn = 1;
            //while(!isEmptySpirits(player1Spritis) || !isEmptySpirits(player2Sprits))
            //{
                //if(playerTurn % 2 != 0)
                //{
                    //playerTurn(1);
                //}
                //else
                //{
                    //playerTurn(2);
                //}
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
            
        }
        else
        {
            
        }
    }
}