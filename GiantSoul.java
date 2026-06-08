import java.util.ArrayList;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BigSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GiantSoul extends Soul
{
    /**
     * Act - do whatever the BigSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    static GreenfootImage costume = new GreenfootImage("giant.png");
    static final String NAME = "Giant Soul";
    static final int BASE_HEALTH = 120;
    static final int BASE_ATTACK = 25;
    static final String ATTACK_NAME = "Stomp";
    static final String PASSIVE_NAME = "Grow";
    static final String ULTIMATE_NAME = "Giant";

    static final int ATTACK_INCREASE = 7;
    static final int HEALTH_INCREASE = 10;

    int imageScale = 100;
    
    public GiantSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.big, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, costume);
        image.scale(imageScale, imageScale);
    }

    public void passive(Entity other) {
        this.attack += (int)(ATTACK_INCREASE*levelModifier);
        this.health += (int)(HEALTH_INCREASE*levelModifier);
        imageScale += 5;
        image.scale(imageScale, imageScale);
    }

    public String getAttackDetails() {
        return "Stomp: deals " + attack + " damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Grow: adds " + (int)(ATTACK_INCREASE*levelModifier) + " attack & " + (int)(HEALTH_INCREASE*levelModifier) + " health.";
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        canSwap = false;
        imageScale *= 1.3;
        image.scale(imageScale, imageScale);
        attack *= 1.5;
        health *= 1.5;
    }

    public String unwrappedGetUltimateDetails() {
        return "Giant: increases attack & health by 50%. Loses the ability to swap.";
    }
}
