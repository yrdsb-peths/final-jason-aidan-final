import java.util.ArrayList;

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
    
    Button attack;
    Button passive;
    Button chooseNew;
    Button flee;
    
    Spirit p1Spirit;
    Spirit p2Spirit;
    
    int playerTurn;
    
    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    public BattleScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        
        this.player1Spirits = player1Spirits;
        this.player2Spirits = player2Spirits;
        
        attack = new Button(null, 20);
        world.addObject(attack, MyWorld.WIDTH/4, MyWorld.HEIGHT/3*2);
        passive = new Button(null, 20);
        world.addObject(passive, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/3*2);
        chooseNew = new Button(null, 20);
        world.addObject(chooseNew, MyWorld.WIDTH/4, MyWorld.HEIGHT/6*5);
        flee = new Button(null, 20);
        world.addObject(flee, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/6*5);
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
            if(p1Spirit.comparedTo(p2Spirit) == 0)
            {
                p2Spirit.health -= p1Spirit.attack;
                System.out.println("p2 Spirit's health is at " + p2Spirit.health);
                System.out.println("p1 dealt " + p1Spirit.attack + " damage");
            } else if(p1Spirit.comparedTo(p2Spirit) > 0)
            {
                p2Spirit.health -= p1Spirit.attack * 1.5;
                System.out.println("p2 Spirit's health is at " + p2Spirit.health);
                System.out.println("p1 dealt " + p1Spirit.attack * 1.5 + ". Super Effective!");
            } else if(p1Spirit.comparedTo(p2Spirit) < 0)
            {
                p2Spirit.health -= p1Spirit.attack * 0.5;
                System.out.println("p2 Spirit's health is at " + p2Spirit.health);
                System.out.println("p1 dealt " + p1Spirit.attack * 0.5 + ". It's not very Effective");
            }
        }
        else
        {
            if(p2Spirit.comparedTo(p1Spirit) == 0)
            {
                p1Spirit.health -= p2Spirit.attack;
                System.out.println("p1 Spirit's health is at " + p1Spirit.health);
                System.out.println("p2 dealt " + p2Spirit.attack + " damage");
            } else if(p2Spirit.comparedTo(p1Spirit) > 0)
            {
                p1Spirit.health -= p2Spirit.attack * 1.5;
                System.out.println("p1 Spirit's health is at " + p1Spirit.health);
                System.out.println("p2 dealt " + p2Spirit.attack * 1.5 + ". Super Effective!");
            } else if(p2Spirit.comparedTo(p1Spirit) < 0)
            {
                p1Spirit.health -= p2Spirit.attack * 0.5;
                System.out.println("p1 Spirit's health is at " + p1Spirit.health);
                System.out.println("p2 dealt " + p2Spirit.attack * 0.5 + ". It's not very Effective");
            }
        }
    }
    
    public void nextTurn()
    {
        playerTurn++;
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
