import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SmallSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SmallSpirit extends Spirit
{
    /**
     * Act - do whatever the SmallSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    static GreenfootImage costume = new GreenfootImage("small.png");
    static final String NAME = "Small Spirit";
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 25;
    static final String ATTACK_NAME = "Bite";
    static final String PASSIVE_NAME = "Shrink";
    static final String PASSIVE_DETAILS = "Your opponent now has a change to miss!";

    static final double MAX_EVASION_CHANCE = 0.7;
    static final double HIT_CHANCE_CHANGE = 0.7;
    static final int STATS_CHANGE_DIVISOR = 10;
    
    public SmallSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.small, ATTACK_NAME, PASSIVE_NAME, costume);
        this.evasion = 0;
    }

    public Soul getUpgraded() {
        return new TinySoul();
    }

    public void passive(Entity other) {

        this.evasion = 1 - (1 - this.evasion) * HIT_CHANCE_CHANGE;
        if (this.evasion > MAX_EVASION_CHANCE) {
            this.evasion = MAX_EVASION_CHANCE;
            BattleScreen.getInstance().actionStack.add(
                new Action("Your evasion has reached the max of "+(int)(100 * MAX_EVASION_CHANCE)+"%!", 
                () -> {
                }
            ));
            
        } else {
            this.health = (int) (this.health * (1 - 1/(double)STATS_CHANGE_DIVISOR));
            this.attack = (int) (this.attack * (1- 1/(double)STATS_CHANGE_DIVISOR));
        }
    }

    public String getAttackDetails() {
        return "Bite: deals " + attack + " damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Shrink: raises evasion from " + (int) (this.evasion * 100) + "% to " + (int) ((1 - (1 - this.evasion) * HIT_CHANCE_CHANGE) * 100) + "% while reducing your stats by 1/" + STATS_CHANGE_DIVISOR + ".";
    }
}
