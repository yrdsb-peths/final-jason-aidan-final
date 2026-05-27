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
    
    int turnNumber;
    
    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    public BattleScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        
        this.player1Spirits = player1Spirits;
        this.player2Spirits = player2Spirits;

        this.world = world;
        
        initButtons();
        initLabels();
        

        turnNumber = 1;
        
    }

    public void act()
    {
        if(player1Spirits.size() > 0 && player2Spirits.size() > 0)
        {
            playerAction();
            
        } else {
            //gameOver();
        }
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


        if (attackButton.isPressed) {
            attackButton.isPressed = false;
            opponentSpirit.health -= calculateAttack(currentSpirit, opponentSpirit);
            nextTurn(currentSpirit, opponentSpirit);
        } else if (passiveButton.isPressed) {
            passiveButton.isPressed = false;
            //System.out.println(currentSpirit + " used " + currentSpirit.passiveName);
            currentSpirit.passive(opponentSpirit);
            nextTurn(currentSpirit, opponentSpirit);
        } else if (chooseNewButton.isPressed) {
            chooseNewButton.isPressed = false;
            // Handle choosing new spirit logic here
            
            nextTurn(currentSpirit, opponentSpirit);
        } else if (fleeButton.isPressed) {
            fleeButton.isPressed = false;
            // Handle fleeing logic here, such as ending the game or declaring the other player as the winner
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
                System.out.println("Miss!");
            } else if(rand > 10 && rand <= 90)
            {
                outputDmg = attacker.attack;
                System.out.println("Regular attack!");
            } else if(rand > 90)
            {
                outputDmg = attacker.attack * 1.5;
                System.out.println("Normal Crit!");
            }
        } else if(effectiveness > 0) {
            int randCrit = Greenfoot.getRandomNumber(100);
            if(randCrit < 10) {
                outputDmg = attacker.attack * 3;
                System.out.println("Crit!");
            } else { 
                outputDmg = attacker.attack * 1.5;
                System.out.println("Super Effective!");
            }
        } else if(effectiveness < 0) {
            int randMiss = Greenfoot.getRandomNumber(100);
            if(randMiss < 10) {
                System.out.println("Miss!");
            } else { 
                outputDmg = attacker.attack * 0.5;
                System.out.println("Not Effective!");
            }
        }
        return (int)outputDmg;
    }
    
    public void nextTurn(Spirit currentSpirit, Spirit opponentSpirit)
    {
        currentSpirit.applyStatusEffects();
        opponentSpirit.applyStatusEffects();

        updateLabels();
        turnNumber++;
    }
    
    public void checkIfFainted()
    {
        if(p1Spirit.health <= 0)
        {
            
        }
        if(p2Spirit.health <= 0)
        {
            
        }
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

        Label health1label = new Label("Health: " + player1Spirits.get(0).health, 24);
        Label attack1label = new Label("Attack: " + player1Spirits.get(0).attack, 24);
        Label type1label = new Label("Type: " + player1Spirits.get(0).type.toString(), 24);

        Label health2label = new Label("Health: " + player2Spirits.get(0).health, 24);
        Label attack2label = new Label("Attack: " + player2Spirits.get(0).attack, 24);
        Label type2label = new Label("Type: " + player2Spirits.get(0).type.toString(), 24);

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

        world.addObject(health1label, MyWorld.WIDTH/4, MyWorld.HEIGHT/6);
        world.addObject(attack1label, MyWorld.WIDTH/4, MyWorld.HEIGHT/6 + 30);
        world.addObject(type1label, MyWorld.WIDTH/4, MyWorld.HEIGHT/6 + 60);

        world.addObject(health2label, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/6);
        world.addObject(attack2label, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/6 + 30);
        world.addObject(type2label, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/6 + 60);

    }
    public void updateLabels()
    {


        player1Label.get("health").setValue("Health: " + p1Spirit.health);
        player1Label.get("attack").setValue("Attack: " + p1Spirit.attack);
        player1Label.get("type").setValue("Type: " + p1Spirit.type.toString());


        player2Label.get("health").setValue("Health: " + p2Spirit.health);
        player2Label.get("attack").setValue("Attack: " + p2Spirit.attack);
        player2Label.get("type").setValue("Type: " + p2Spirit.type.toString());
    }
}
