import java.util.ArrayList;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BigSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TinySoul extends Soul
{
    /**
     * Act - do whatever the BigSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    static GreenfootImage costume = new GreenfootImage("tiny.png");
    static final String NAME = "Tiny Soul";
    static final int BASE_HEALTH = 60;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Bite";
    static final String PASSIVE_NAME = "Shrink";
    static final String ULTIMATE_NAME = "Same Size";

    static final double HIT_CHANCE_CHANGE = 0.5;
    static final int STATS_CHANGE_DIVISOR = 10;
    
    public TinySoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.small, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, costume);
    }

    public void passive(Entity other) {
        this.evasion = 1 - (1 - this.evasion) * HIT_CHANCE_CHANGE;
        if (this.evasion > 0.8) {
            this.evasion = 0.8;
            BattleScreen.getInstance().actionStack.add(
                new Action("Your evasion has reached the max of 80%!", 
                () -> {
                }
            ));
            
        } else {
            this.health = (int) (this.health * (1 - 1/(double)STATS_CHANGE_DIVISOR));
            this.attack = (int) (this.attack * (1- 1/(double)STATS_CHANGE_DIVISOR));
        }
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        others.get(0).level = -9;
        others.get(0).updateModifier();
        others.get(0).fixLevel();
        others.get(0).image.scale(50, 50);

    }

    public String getAttackDetails() {
        return "Bite: deals " + attack + " damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Shrink: raises evasion from " + (int) (this.evasion * 100) + "% to " + (int) ((1 - (1 - this.evasion) * HIT_CHANCE_CHANGE) * 100) + "% while reducing your stats by 1/" + STATS_CHANGE_DIVISOR + ".";
    }

    public String unwrappedGetUltimateDetails() {
        return "Same Size: Reduces opponent stats to 30%";
    }
}
