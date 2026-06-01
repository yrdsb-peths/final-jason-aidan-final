import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class BattleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

// Singleton pattern
public class BattleScreen extends Actor
{
    /**
     * Act - do whatever the BattleScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */


    
    MyWorld world;
    
    Button attackButton;
    Button passiveButton;
    Button chooseNewButton;
    Button ultButton;

    HashMap<String, Label> player1Label;
    HashMap<String, Label> player2Label;
    
    Entity p1Entity;
    Entity p2Entity;

    ImageDisplay p1EntityDisplay;
    ImageDisplay p2EntityDisplay;
    
    int turnNumber;
    
    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    // Add a 'U' at the end to indicate upadating healthbar
    DialogueBox dialogueBox;

    ArrayList<Action> actionStack;

    GreenfootImage backgroundImage;
    
    private static BattleScreen instance;

    static public void newInstance(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
        instance = new BattleScreen(player1Entities, player2Entities, world);
    }

    static public BattleScreen getInstance() {
        return instance;
    }


    private BattleScreen(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
        
        setImage((GreenfootImage)null);

        this.player1Entities = player1Entities;
        this.player2Entities = player2Entities;

        this.world = world;

        actionStack = new ArrayList<Action>();
        
        initButtons();
        initEntityDisplay();


        initBackground();

        initDialogueBox();
        drawHealthbar();
        turnNumber = 1;
        
    }



    public void act()
    {
        if(player1Entities.size() > 0 && player2Entities.size() > 0)
        {
            playerAction();
            
        } else {
            gameOver();
        }
    }

    private void gameOver()
    {
        if(player1Entities.size() == 0)
        {
            // Player 2 wins
            System.out.println("Player 2 wins!");
        } else if(player2Entities.size() == 0) {
            // Player 1 wins
            System.out.println("Player 1 wins!");
        }
        world.removeObject(attackButton);
        world.removeObject(passiveButton);
        world.removeObject(chooseNewButton);
        world.removeObject(ultButton);
        world.removeObject(p1EntityDisplay);
        world.removeObject(p2EntityDisplay);
        world.removeObject(dialogueBox);
        world.currentState = States.CHOOSING;
        world.screenCreated = false;
        world.removeObject(this);
    }

    private void playerAction()
    {

        p1Entity = player1Entities.get(0);
        p2Entity = player2Entities.get(0);
        
        Entity currentEntity = turnNumber % 2 == 1 ? p1Entity : p2Entity;
        Entity opponentEntity = turnNumber % 2 == 0 ? p1Entity : p2Entity;

        ArrayList<Entity> currentEntities = turnNumber % 2 == 1 ? player1Entities : player2Entities;
        ArrayList<Entity> opponentEntities = turnNumber % 2 == 0 ? player1Entities : player2Entities;

        int turn = 2 - turnNumber % 2;

        if (actionStack.size() > 0) {

            if (isHovering()) {
                displayHoverText(currentEntity);
                return;
            }
            Action action = actionStack.get(0);
            dialogueBox.setText(action.text);

            if (!action.isCompleted && action.func != null) {
                action.func.run();
            }

            if (Greenfoot.mouseClicked(null)) {
                actionStack.remove(0);
            }
            return;
        }

        if (isHovering()) {
            displayHoverText(currentEntity);
        } else {
            dialogueBox.setText("Player "+turn+"'s turn");
        }

        showPlayerButtons();
        updateEntityImage();

        if (currentEntity.stunDuration > 0) {
            currentEntity.stunDuration --;
            actionStack.add(new Action(currentEntity.name + " is stunned.", () -> endAction()));
        }


        if (attackButton.isPressed) {
            attackButton.isPressed = false;
            currentEntity.attack(opponentEntity);
            attachToActions(() -> endAction());

        } else if (passiveButton.isPressed) {
            passiveButton.isPressed = false;
            // don't ever use 'passive' like currentEntity.passive(opponentEntity);
            currentEntity.applyPassive(opponentEntity);
            attachToActions(() -> endAction());

        } else if (chooseNewButton.isPressed) {
            chooseNewButton.isPressed = false;

        } else if (ultButton.isPressed) {
            ultButton.isPressed = false;
            if (currentEntity instanceof Soul soul) {
                
                soul.applyUltimate(currentEntities, opponentEntities);
                attachToActions(() -> endAction());}
            }
            
    }

    
    private void endAction()
    {
        int previousStackSize = actionStack.size();

        p1Entity.applyStatusEffects();
        p2Entity.applyStatusEffects();

        if (actionStack.size() > previousStackSize) {

            attachToActions(() -> { 
                checkIfFainted(); 
                turnNumber++; 
            });
        } else {

            checkIfFainted(); 
            turnNumber++;
        }
    }
    // attaches a func to last action without creates a new one
    private void attachToActions(Runnable func) {
        if (actionStack.size() > 0) {
            Action modifiedAction = new Action(actionStack.getLast(), () -> func.run());
            actionStack.set(actionStack.size()-1, modifiedAction);
        } else {
            func.run();
        }
    }
    
    private void checkIfFainted()
    {
        if (p1Entity.health <= 0)
        {
            
            actionStack.add(new Action(
                p1Entity.name + " has fainted!",
                () -> {
                    p1Entity = player1Entities.remove(0); 
                    if (player1Entities.size() == 0) {
                        gameOver();
                    } else {
                        p1Entity = player1Entities.get(0);
                        checkIfFainted();
                    }
                    
                }
            ));
        }
        if (p2Entity.health <= 0)
        {
            actionStack.add(new Action(
                p2Entity.name + " has fainted!",
                () -> {
                    p2Entity = player2Entities.remove(0);
                    if (player2Entities.size() == 0) {
                        gameOver();
                    } else {
                        p2Entity = player2Entities.get(0);
                        checkIfFainted();
                    }
                    
                }
            ));
            
        }
    }

    private boolean isHovering() {
        return attackButton.isHovering || passiveButton.isHovering || chooseNewButton.isHovering || ultButton.isHovering;
    }

    private void displayHoverText(Entity currentEntity) {
        if (attackButton.isHovering) {
            dialogueBox.setText(currentEntity.getAttackDetails());
        } else if (passiveButton.isHovering) {
            dialogueBox.setText(currentEntity.getPassiveDetails());
        } else if (chooseNewButton.isHovering) {

        } else if (ultButton.isHovering) {
            dialogueBox.setText(currentEntity.getUltimateDetails());
        }
    }

    public void updateAllVisuals() {
        updateEntityImage();
        drawHealthbar();
    }
    
    private void showPlayerButtons()
    {
        int num = 2 - turnNumber % 2;

        GreenfootImage attackImage = new GreenfootImage("attack_P" + num + ".png");
        GreenfootImage passiveImage = new GreenfootImage("passive_P" + num + ".png");
        GreenfootImage chooseNewImage = new GreenfootImage("chooseNew_P" + num + ".png");
        GreenfootImage fleeImage = new GreenfootImage("flee_P" + num + ".png");
        int scaleX = 180;
        int scaleY = 60;

        attackImage.scale(scaleX,scaleY);
        passiveImage.scale(scaleX,scaleY);
        chooseNewImage.scale(scaleX,scaleY);
        fleeImage.scale(scaleX,scaleY);

        attackButton.setImage(attackImage);
        passiveButton.setImage(passiveImage);
        chooseNewButton.setImage(chooseNewImage);
        ultButton.setImage(fleeImage);

    }

    private void initButtons()
    {

        attackButton = new Button(null, 20);
        passiveButton = new Button(null, 20);
        chooseNewButton = new Button(null, 20);
        ultButton = new Button(null, 20);


        world.addObject(attackButton, 90, MyWorld.HEIGHT-90);
        world.addObject(passiveButton, MyWorld.WIDTH-90, MyWorld.HEIGHT-90);
        world.addObject(chooseNewButton, 90, MyWorld.HEIGHT-30);
        world.addObject(ultButton, MyWorld.WIDTH-90, MyWorld.HEIGHT-30);

    }

    private void initEntityDisplay() {
        p1EntityDisplay = new ImageDisplay();
        p2EntityDisplay = new ImageDisplay();
        
        world.addObject(p1EntityDisplay, MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        world.addObject(p2EntityDisplay, MyWorld.WIDTH/7 * 6, MyWorld.HEIGHT/5);
    }
    private void updateEntityImage()
    {
        
        int scaleX = 100;
        int scaleY = 100;

        // create new copy
        GreenfootImage p1Image = new GreenfootImage(p1Entity.image);
        GreenfootImage p2Image = new GreenfootImage(p2Entity.image);

        p1Image.scale(scaleX, scaleY);
        p2Image.scale(scaleX, scaleY);

        p1EntityDisplay.setImage(p1Image);
        p2EntityDisplay.setImage(p2Image);

    }

    private void initDialogueBox()
    {
        dialogueBox = new DialogueBox(220, 110, Color.WHITE, "> Game Starts!", Color.BLACK, new Font(15));
        world.addObject(dialogueBox, 300, 340);
        dialogueBox.setText("Player 1's turn");
    }

    private void initBackground()
    {
        int i = Greenfoot.getRandomNumber(4);
        backgroundImage = new GreenfootImage("background"+(i+1)+".png");
        backgroundImage.scale(600, 400);

        int borderWidth = 2;
        backgroundImage.setColor(Color.BLACK);
        backgroundImage.fillRect(190 - borderWidth, 285 - borderWidth, 220 + 2 * borderWidth, 110 + 2 * borderWidth);
        world.setBackground(new GreenfootImage(backgroundImage));
        
    }

    private void drawHealthbar()
    {
        if (player1Entities.size() == 0 || player2Entities.size() == 0) {
            return;
        }
        
        int borderWidth = 2;
        int health1 = player1Entities.get(0).health;
        int health2 = player2Entities.get(0).health;

        GreenfootImage tempBackground = new GreenfootImage(backgroundImage);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(20 - borderWidth, 150 - borderWidth, health1 + 2 * borderWidth, 15 + 2 * borderWidth);
        tempBackground.setColor(Color.GREEN);
        tempBackground.fillRect(20, 150, health1, 15);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(450 - borderWidth, 150 - borderWidth, health2 + 2 * borderWidth, 15 + 2 * borderWidth);
        tempBackground.setColor(Color.GREEN);
        tempBackground.fillRect(450, 150, health2, 15);

        world.setBackground(tempBackground);
    }
}
