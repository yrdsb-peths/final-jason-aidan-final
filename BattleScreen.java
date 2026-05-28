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
    
    Spirit p1Spirit;
    Spirit p2Spirit;

    ImageDisplay p1SpiritDisplay;
    ImageDisplay p2SpiritDisplay;
    
    int turnNumber;
    
    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    Label textBox;

    GreenfootImage backgroundImage;
    


    public BattleScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        
        setImage((GreenfootImage)null);

        this.player1Spirits = player1Spirits;
        this.player2Spirits = player2Spirits;

        this.world = world;
        
        initButtons();
        initLabels();
        p1SpiritDisplay = new ImageDisplay();
        p2SpiritDisplay = new ImageDisplay();

        setBackground();

        initTextBox();


        turnNumber = 1;
        
    }

    public void act()
    {
        if(player1Spirits.size() > 0 && player2Spirits.size() > 0)
        {
            playerAction();
            
        } else {
            gameOver();
        }
    }

    public void gameOver()
    {
        if(player1Spirits.size() == 0)
        {
            // Player 2 wins
            System.out.println("Player 2 wins!");
        } else if(player2Spirits.size() == 0) {
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
        world.removeObject(p1SpiritDisplay);
        world.removeObject(p2SpiritDisplay);
        world.removeObject(textBox);
        world.currentState = States.CHOOSING;
        world.screenCreated = false;
        world.removeObject(this);
    }

    //returns true if the given list has no spirits
    public boolean isEmptySpirits(ArrayList<Spirit> list)
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

        p1Spirit = player1Spirits.get(0);
        p2Spirit = player2Spirits.get(0);
        
        Spirit currentSpirit = turnNumber % 2 == 1 ? p1Spirit : p2Spirit;
        Spirit opponentSpirit = turnNumber % 2 == 0 ? p1Spirit : p2Spirit;

        showPlayerButtons();
        updateImageDisplays();


        if (attackButton.isPressed) {
            attackButton.isPressed = false;
            opponentSpirit.health -= calculateAttack(currentSpirit, opponentSpirit);
            nextTurn(currentSpirit, opponentSpirit, 0);

        } else if (passiveButton.isPressed) {
            passiveButton.isPressed = false;
            //System.out.println(currentSpirit + " used " + currentSpirit.passiveName);
            currentSpirit.passive(opponentSpirit);
            nextTurn(currentSpirit, opponentSpirit, 1);

        } else if (chooseNewButton.isPressed) {
            chooseNewButton.isPressed = false;
            // Handle choosing new spirit logic here
            nextTurn(currentSpirit, opponentSpirit, 2);

        } else if (fleeButton.isPressed) {
            fleeButton.isPressed = false;
            // Handle fleeing logic here, such as ending the game or declaring the other player as the winner
            nextTurn(currentSpirit, opponentSpirit, 3);
        }


        checkIfFainted();
    }

    public int calculateAttack(Spirit attacker, Spirit defender) {
        double outputDmg = 0.0;
        int effectiveness = attacker.comparedTo(defender);
        if(effectiveness == 0) {
            int rand = Greenfoot.getRandomNumber(100);
            if(rand <= 10)
            {
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It missed!");
            } else if(rand > 10 && rand <= 90)
            {
                outputDmg = attacker.attack;
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It dealt " + (int)outputDmg + " damage!");
            } else if(rand > 90)
            {
                outputDmg = attacker.attack * 1.5;
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It was a critical hit, \n dealing " + (int)outputDmg + " damage!");
            }
        } else if(effectiveness > 0) {
            int randCrit = Greenfoot.getRandomNumber(100);
            if(randCrit < 10) {
                outputDmg = attacker.attack * 3;
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It was a critical hit, \n dealing " + (int)outputDmg + " damage!");
            } else { 
                outputDmg = attacker.attack * 1.5;
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It was super effective, \n dealing " + (int)outputDmg + " damage!");
            }
        } else if(effectiveness < 0) {
            int randMiss = Greenfoot.getRandomNumber(100);
            if(randMiss < 10) {
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It missed!");
            } else { 
                outputDmg = attacker.attack * 0.5;
                updateTextBox(attacker.name + " used " + attacker.attackName + "!" + "\n" + "It was not very effective, \n dealing " + (int)outputDmg + " damage.");
            }
        }
        return (int)outputDmg;
    }
    
    public void nextTurn(Spirit currentSpirit, Spirit opponentSpirit, int buttonPressed)
    {
        currentSpirit.applyStatusEffects();
        opponentSpirit.applyStatusEffects();

        updateLabels();
        if (buttonPressed == 1) {
            updateTextBox("Player " + (turnNumber % 2 == 1 ? "1" : "2") + " used " + currentSpirit.passiveName + "!");
        } else if (buttonPressed == 2) {
            updateTextBox("Player " + (turnNumber % 2 == 1 ? "1" : "2") + " switched spirit!");
        } else if (buttonPressed == 3) {
            updateTextBox("Player " + (turnNumber % 2 == 1 ? "1" : "2") + " fled from the battle!");
        }
        turnNumber++;
    }
    
    public void checkIfFainted()
    {
        if(p1Spirit.health <= 0)
        {
            p1Spirit = player1Spirits.remove(0);
            
        }
        if(p2Spirit.health <= 0)
        {
            p2Spirit = player2Spirits.remove(0);
            
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

        Label health1label = new Label("HP: " + player1Spirits.get(0).health, fontSize);
        Label attack1label = new Label("ATT: " + player1Spirits.get(0).attack, fontSize);
        Label type1label = new Label("Type: " + player1Spirits.get(0).type.toString(), fontSize);

        Label health2label = new Label("HP: " + player2Spirits.get(0).health, fontSize);
        Label attack2label = new Label("ATT: " + player2Spirits.get(0).attack, fontSize);
        Label type2label = new Label("Type: " + player2Spirits.get(0).type.toString(), fontSize);

        health1label.setFillColor(Color.BLACK);
        health1label.setLineColor(null);
        health2label.setFillColor(Color.BLACK);
        health2label.setLineColor(null);

        attack1label.setFillColor(Color.BLACK);
        attack1label.setLineColor(null);
        attack2label.setFillColor(Color.BLACK);
        attack2label.setLineColor(null);

        type1label.setFillColor(Color.BLACK);
        type1label.setLineColor(null);
        type2label.setFillColor(Color.BLACK);
        type2label.setLineColor(null);

        player1Label = new HashMap<String, Label>(Map.of(
            "health", health1label,
            "attack", attack1label,
            "type", type1label
        ));

        player2Label = new HashMap<String, Label>(Map.of(
            "health", health2label,
            "attack", attack2label,
            "type", type2label
        ));

        world.addObject(health1label, MyWorld.WIDTH/4 - 20, MyWorld.HEIGHT/2-30);
        world.addObject(attack1label, MyWorld.WIDTH/4 - 100, MyWorld.HEIGHT/2-30);
        world.addObject(type1label, MyWorld.WIDTH/4 - 85, MyWorld.HEIGHT/2);

        world.addObject(health2label, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/2-30);
        world.addObject(attack2label, MyWorld.WIDTH/4 * 3 + 80, MyWorld.HEIGHT/2-30);
        world.addObject(type2label, MyWorld.WIDTH/4 * 3 + 20, MyWorld.HEIGHT/2);

    }
    public void updateLabels()
    {


        player1Label.get("health").setValue("HP: " + p1Spirit.health);
        player1Label.get("attack").setValue("ATT: " + p1Spirit.attack);
        player1Label.get("type").setValue("Type: " + p1Spirit.type.toString());


        player2Label.get("health").setValue("HP: " + p2Spirit.health);
        player2Label.get("attack").setValue("ATT: " + p2Spirit.attack);
        player2Label.get("type").setValue("Type: " + p2Spirit.type.toString());
    }

    public void updateImageDisplays()
    {
        
        int scaleX = 100;
        int scaleY = 100;

        // create new copy
        GreenfootImage p1Image = new GreenfootImage(p1Spirit.image);
        GreenfootImage p2Image = new GreenfootImage(p2Spirit.image);

        p1Image.scale(scaleX, scaleY);
        p2Image.scale(scaleX, scaleY);

        p1SpiritDisplay.setImage(p1Image);
        p2SpiritDisplay.setImage(p2Image);


        world.addObject(p1SpiritDisplay, MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        world.addObject(p2SpiritDisplay, MyWorld.WIDTH/7 * 6, MyWorld.HEIGHT/5);
    }

    public void initTextBox()
    {
        textBox = new Label("None", 20);
        textBox.setFillColor(Color.BLACK);
        textBox.setLineColor(null);
        world.addObject(textBox, MyWorld.WIDTH/2, MyWorld.HEIGHT-80);
    }
    public void updateTextBox(String string)
    {
        textBox.setValue(string);
        
    }

    public void setBackground()
    {

        int i = Greenfoot.getRandomNumber(4);
        backgroundImage = new GreenfootImage("background"+(i+1)+".png");
        backgroundImage.scale(600, 400);
        
        backgroundImage.setColor(Color.WHITE);
        backgroundImage.fillRect(190, 285, 220, 110);

        world.setBackground(backgroundImage);
        
    }
}
