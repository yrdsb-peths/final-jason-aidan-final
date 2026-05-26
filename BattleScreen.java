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
    
    Button attack;
    Button passive;
    Button chooseNew;
    Button flee;


    HashMap<String, Label> player1Label;
    HashMap<String, Label> player2Label;
    
    Spirit p1Spirit;
    Spirit p2Spirit;
    
    int playerTurn;
    
    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    public BattleScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        
        this.player1Spirits = player1Spirits;
        this.player2Spirits = player2Spirits;

        this.world = world;

        initButtons();
        initLabels();
        

        playerTurn = 1;
        
    }

    public void act()
    {
        turn();
    }

    public void turn()
    {

        
        // player 1 turn first
        // player 2 after and cycles after that
        if(!isEmptySpirits(player1Spirits) || !isEmptySpirits(player2Spirits))
        {
            if(playerTurn % 2 != 0)
            {
                playerAction(1);
            }
            else
            {
                playerAction(2);
            }
            checkIfFainted();
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
    public void playerAction(int playerIndex)
    {
        
        showPlayerButtons(playerIndex);
        p1Spirit = player1Spirits.get(0);
        p2Spirit = player2Spirits.get(0);

        if(playerIndex == 1)
        {
            //player chooses button by click
            if(attack.isPressed)
            {
                attack.isPressed = false;
                determineAttack(playerIndex);
                nextTurn();
            }
            if(flee.isPressed)
            {
                //forfeit game p1
                flee.isPressed = false;
            }
        }
        else
        {
            //player chooses button by click
            if(attack.isPressed)
            {
                attack.isPressed = false;
                determineAttack(playerIndex);
                nextTurn();
            }
            if(flee.isPressed)
            {
                //forfeit game p2
                flee.isPressed = false;
            }
        }
    }

    public void determineAttack(int playerIndex)
    {
        if(playerIndex == 1)
        {
            double outputDmg = 0.0;
            if(p1Spirit.comparedTo(p2Spirit) == 0)
            {
                outputDmg = p1Spirit.attack;
            } else if(p1Spirit.comparedTo(p2Spirit) > 0)
            {
                int randCrit = Greenfoot.getRandomNumber(100);
                if(randCrit < 10)
                {
                    outputDmg = p1Spirit.attack * 3;
                    System.out.println("Crit!");
                } else{ 
                    outputDmg = p1Spirit.attack * 1.5;
                    System.out.println("Super Effective!");
                }
            } else if(p1Spirit.comparedTo(p2Spirit) < 0)
            {
                int randMiss = Greenfoot.getRandomNumber(100);
                if(randMiss < 10)
                {
                    System.out.println("Miss!");
                } else{ 
                    outputDmg = p1Spirit.attack * 0.5;
                    System.out.println("Not Effective!");
                }
            }
            p2Spirit.health -= (int)outputDmg;
            System.out.println("P2 health is at " + p2Spirit.health);
        }
        else
        {
            double outputDmg = 0.0;
            if(p2Spirit.comparedTo(p1Spirit) == 0)
            {
                outputDmg = p2Spirit.attack;
            } else if(p2Spirit.comparedTo(p1Spirit) > 0)
            {
                int randCrit = Greenfoot.getRandomNumber(100);
                if(randCrit < 10)
                {
                    outputDmg = p2Spirit.attack * 3;
                    System.out.println("Crit!");
                } else{ 
                    outputDmg = p2Spirit.attack * 1.5;
                    System.out.println("Super Effective!");
                }
            } else if(p2Spirit.comparedTo(p1Spirit) < 0)
            {
                int randMiss = Greenfoot.getRandomNumber(100);
                if(randMiss < 10)
                {
                    System.out.println("Miss!");
                } else{ 
                    outputDmg = p2Spirit.attack * 0.5;
                    System.out.println("Not Effective!");
                }
            }
            p1Spirit.health -= (int)outputDmg;
            System.out.println("P1 health is at " + p1Spirit.health);
        }
    }
    
    public void nextTurn()
    {
        playerTurn++;
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
    
    public void showPlayerButtons(int num)
    {
        GreenfootImage a;
        GreenfootImage p;
        GreenfootImage c;
        GreenfootImage f;
        int scaleX = 180;
        int scaleY = 60;
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

    public void initButtons()
    {

        attack = new Button(null, 20);
        passive = new Button(null, 20);
        chooseNew = new Button(null, 20);
        flee = new Button(null, 20);


        world.addObject(attack, 90, MyWorld.HEIGHT-90);
        world.addObject(passive, MyWorld.WIDTH-90, MyWorld.HEIGHT-90);
        world.addObject(chooseNew, 90, MyWorld.HEIGHT-30);
        world.addObject(flee, MyWorld.WIDTH-90, MyWorld.HEIGHT-30);

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
