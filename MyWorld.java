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
    Button attack;
    Button passive;
    Button chooseNew;
    Button flee;
    
    static int WIDTH = 600;
    static int HEIGHT = 400;
    
    public MyWorld() {

        super(WIDTH, HEIGHT, 1);
        player1Spirits = new Spirit[maxSpirits];
        player2Spirits = new Spirit[maxSpirits];
        GreenfootImage image = new GreenfootImage("submit_button.png");
        image.scale(125,125);
        submitButton = new Button(image, 20);
        addObject(submitButton, WIDTH/2, HEIGHT/4 * 3);
        attack = new Button(null, 20);
        addObject(attack,WIDTH/4,HEIGHT/3*2);
        passive = new Button(null, 20);
        addObject(passive,WIDTH/4 * 3,HEIGHT/3*2);
        chooseNew = new Button(null, 20);
        addObject(chooseNew,WIDTH/4,HEIGHT/6*5);
        flee = new Button(null, 20);
        addObject(flee,WIDTH/4 * 3,HEIGHT/6*5);
        
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
                submitButton.remove();

                for (int i = 0; i < maxSpirits; i++) {
                    System.out.println("Player 1 Spirit " + (i+1) + ": " + player1Spirits[i].type);
                    System.out.println("Player 2 Spirit " + (i+1) + ": " + player2Spirits[i].type);
                }
            }
        } else if (currentState == States.BATTLE) {
            // Code to handle battle state
            for (int i = 0; i < maxSpirits; i++) {
                System.out.println("Player 1 Spirit " + (i+1) + ": " + player1Spirits[i].type);
                System.out.println("Player 2 Spirit " + (i+1) + ": " + player2Spirits[i].type);
            }
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

            // chooser.switches[0].status 

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