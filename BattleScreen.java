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
    Button fleeButton;

    HashMap<String, Label> player1Label;
    HashMap<String, Label> player2Label;
    
    Entity p1Entity;
    Entity p2Entity;

    ImageDisplay p1EntityDisplay;
    ImageDisplay p2EntityDisplay;
    
    int turnNumber;
    
    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    DialogueBox dialogueBox;

    static ArrayList<String> messageStack;

    GreenfootImage backgroundImage;
    


    public BattleScreen(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
        
        setImage((GreenfootImage)null);

        this.player1Entities = player1Entities;
        this.player2Entities = player2Entities;

        this.world = world;

        messageStack = new ArrayList<String>();
        
        initButtons();
        initLabels();
        p1EntityDisplay = new ImageDisplay();
        p2EntityDisplay = new ImageDisplay();

        setBackground();

        initTextBox();
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

    public void gameOver()
    {
        if(player1Entities.size() == 0)
        {
            // Player 2 wins
            System.out.println("Player 2 wins!");
        } else if(player2Entities.size() == 0) {
            // Player 1 wins
            System.out.println("Player 1 wins!");
        }
        player1Label.forEach((key, label) -> {
            world.removeObject(label);
        });
        player2Label.forEach((key, label) -> {
            world.removeObject(label);
        });
        world.removeObject(attackButton);
        world.removeObject(passiveButton);
        world.removeObject(chooseNewButton);
        world.removeObject(fleeButton);
        world.removeObject(p1EntityDisplay);
        world.removeObject(p2EntityDisplay);
        world.removeObject(dialogueBox);
        world.currentState = States.CHOOSING;
        world.screenCreated = false;
        world.removeObject(this);
    }

    //returns true if the given list has no spirits
    public boolean isEmptyEntities(ArrayList<Entity> list)
    {
        if(list.size() == 0)
        {
            return true;
        }
        return false;
    }

    //players "playerIndex" turn, player can attack, use passive, choose new spirit, or flee battle
    public void playerAction()
    {
        if (messageStack.size() > 0) {
            if (messageStack.get(0) != "") {
                dialogueBox.setText(messageStack.get(0));
                drawHealthbar();
            } else {
                messageStack.set(0, "");
                
            }
            if (Greenfoot.mouseClicked(world)) {
                messageStack.remove(0);
            }
            return;
        }

        updateTextBox("Player "+(2 - turnNumber % 2)+"'s turn");

        p1Entity = player1Entities.get(0);
        p2Entity = player2Entities.get(0);
        
        Entity currentEntity = turnNumber % 2 == 1 ? p1Entity : p2Entity;
        Entity opponentEntity = turnNumber % 2 == 0 ? p1Entity : p2Entity;

        showPlayerButtons();
        updateImageDisplays();


        if (attackButton.isPressed) {
            attackButton.isPressed = false;
            opponentEntity.health -= calculateAttack(currentEntity, opponentEntity);
            nextTurn(currentEntity, opponentEntity, 0);


        } else if (passiveButton.isPressed) {
            passiveButton.isPressed = false;
            //System.out.println(currentEntity + " used " + currentEntity.passiveName);
            currentEntity.passive(opponentEntity);
            nextTurn(currentEntity, opponentEntity, 1);

        } else if (chooseNewButton.isPressed) {
            chooseNewButton.isPressed = false;
            // Handle choosing new spirit logic here
            nextTurn(currentEntity, opponentEntity, 2);

        } else if (fleeButton.isPressed) {
            fleeButton.isPressed = false;
            // Handle fleeing logic here, such as ending the game or declaring the other player as the winner
            nextTurn(currentEntity, opponentEntity, 3);
        }


        checkIfFainted();
    }

    public int calculateAttack(Entity attacker, Entity defender) {
        double outputDmg = 0.0;
        int effectiveness = attacker.comparedTo(defender);
        if(effectiveness == 0) {
            int rand = Greenfoot.getRandomNumber(100);
            if(rand <= 10)
            {
                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("> It missed!");

            } else if(rand > 10 && rand <= 90)
            {
                outputDmg = attacker.attack;

                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("> It dealt " + (int)outputDmg + " damage!");

            } else if(rand > 90)
            {
                outputDmg = attacker.attack * 1.5;

                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("It was a critical hit, dealing " + (int)outputDmg + " damage!");
            }
        } else if(effectiveness > 0) {
            int randCrit = Greenfoot.getRandomNumber(100);
            if(randCrit < 10) {
                outputDmg = attacker.attack * 3;
                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("> It was a critical hit, dealing " + (int)outputDmg + " damage!");
            } else { 
                outputDmg = attacker.attack * 1.5;
                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("> It was super effective, dealing " + (int)outputDmg + " damage!");
            }
        } else if(effectiveness < 0) {
            int randMiss = Greenfoot.getRandomNumber(100);
            if(randMiss < 10) {
                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("> It missed!");
            } else { 
                outputDmg = attacker.attack * 0.5;
                messageStack.add("> " + attacker.name + " used " + attacker.attackName + "!");
                messageStack.add("> It was not very effective, dealing " + (int)outputDmg + " damage.");
            }
        }
        return (int)outputDmg;
    }
    
    public void nextTurn(Entity currentEntity, Entity opponentEntity, int buttonPressed)
    {
        



        int turn = 2 - turnNumber % 2;

        updateLabels();
        if (buttonPressed == 1) {
            messageStack.add("> Player " + (turn) + " used " + currentEntity.passiveName + "!");
        } else if (buttonPressed == 2) {
            updateTextBox("Player " + (turn) + " switched spirit!");
        } else if (buttonPressed == 3) {
            updateTextBox("Player " + (turn) + " fled from the battle!");
        }

        currentEntity.applyStatusEffects();
        opponentEntity.applyStatusEffects();

        turnNumber++;

        
    }
    
    public void checkIfFainted()
    {
        if(p1Entity.health <= 0)
        {
            p1Entity = player1Entities.remove(0);
            messageStack.add(p1Entity.name + " has fainted!");
        }
        if(p2Entity.health <= 0)
        {
            p2Entity = player2Entities.remove(0);
            messageStack.add(p2Entity.name + " has fainted!");
            
        }

        updateImageDisplays();
        updateLabels();
    }
    
    public void showPlayerButtons()
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
        fleeButton.setImage(fleeImage);

    }

    public void initButtons()
    {

        attackButton = new Button(null, 20);
        passiveButton = new Button(null, 20);
        chooseNewButton = new Button(null, 20);
        fleeButton = new Button(null, 20);


        world.addObject(attackButton, 90, MyWorld.HEIGHT-90);
        world.addObject(passiveButton, MyWorld.WIDTH-90, MyWorld.HEIGHT-90);
        world.addObject(chooseNewButton, 90, MyWorld.HEIGHT-30);
        world.addObject(fleeButton, MyWorld.WIDTH-90, MyWorld.HEIGHT-30);

    }


    public void initLabels()
    {

        int fontSize = 20;

        Label attack1label = new Label("ATT: " + player1Entities.get(0).attack, fontSize);
        Label type1label = new Label("Type: " + player1Entities.get(0).type.toString(), fontSize);

        Label attack2label = new Label("ATT: " + player2Entities.get(0).attack, fontSize);
        Label type2label = new Label("Type: " + player2Entities.get(0).type.toString(), fontSize);

        attack1label.setFillColor(Color.BLACK);
        attack1label.setLineColor(null);
        attack2label.setFillColor(Color.BLACK);
        attack2label.setLineColor(null);

        type1label.setFillColor(Color.BLACK);
        type1label.setLineColor(null);
        type2label.setFillColor(Color.BLACK);
        type2label.setLineColor(null);

        player1Label = new HashMap<String, Label>(Map.of(
            "attack", attack1label,
            "type", type1label
        ));

        player2Label = new HashMap<String, Label>(Map.of(
            "attack", attack2label,
            "type", type2label
        ));

        world.addObject(attack1label, MyWorld.WIDTH/4 - 100, MyWorld.HEIGHT/2-30);
        world.addObject(type1label, MyWorld.WIDTH/4 - 85, MyWorld.HEIGHT/2);

        world.addObject(attack2label, MyWorld.WIDTH/4 * 3 + 80, MyWorld.HEIGHT/2-30);
        world.addObject(type2label, MyWorld.WIDTH/4 * 3 + 20, MyWorld.HEIGHT/2);

    }
    public void updateLabels()
    {


        player1Label.get("attack").setValue("ATT: " + p1Entity.attack);
        player1Label.get("type").setValue("Type: " + p1Entity.type.toString());


        player2Label.get("attack").setValue("ATT: " + p2Entity.attack);
        player2Label.get("type").setValue("Type: " + p2Entity.type.toString());
    }

    public void updateImageDisplays()
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


        world.addObject(p1EntityDisplay, MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        world.addObject(p2EntityDisplay, MyWorld.WIDTH/7 * 6, MyWorld.HEIGHT/5);
    }

    public void initTextBox()
    {
        dialogueBox = new DialogueBox(220, 110, Color.WHITE, "> Game Starts!", Color.BLACK, new Font(15));
        world.addObject(dialogueBox, 300, 340);
        updateTextBox("Player 1's turn");
    }
    public void updateTextBox(String string)
    {
        dialogueBox.setText(string);
        
    }

    public void setBackground()
    {

        int i = Greenfoot.getRandomNumber(4);
        backgroundImage = new GreenfootImage("background"+(i+1)+".png");
        backgroundImage.scale(600, 400);

        int borderWidth = 2;
        backgroundImage.setColor(Color.BLACK);
        backgroundImage.fillRect(190 - borderWidth, 285 - borderWidth, 220 + 2 * borderWidth, 110 + 2 * borderWidth);
        world.setBackground(new GreenfootImage(backgroundImage));
        
    }


    public void drawHealthbar()
    {
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
