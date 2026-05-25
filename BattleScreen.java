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

    public BattleScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        attack = new Button(null, 20);
        world.addObject(attack, MyWorld.WIDTH/4, MyWorld.HEIGHT/3*2);
        passive = new Button(null, 20);
        world.addObject(passive, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/3*2);
        chooseNew = new Button(null, 20);
        world.addObject(chooseNew, MyWorld.WIDTH/4, MyWorld.HEIGHT/6*5);
        flee = new Button(null, 20);
        world.addObject(flee, MyWorld.WIDTH/4 * 3, MyWorld.HEIGHT/6*5);
    }


    public void act()
    {
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
