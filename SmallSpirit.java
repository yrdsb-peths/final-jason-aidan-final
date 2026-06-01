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
    static final int BASE_HEALTH = 40;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "Bite";
    static final String PASSIVE_NAME = "Shrink";
    static final String PASSIVE_DETAILS = "Your Opponent now has a change to miss!";
    
    public SmallSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.small, ATTACK_NAME, PASSIVE_NAME, costume);
        this.evasion = 0;
    }

    public Soul getUpgraded() {
        return null;
    }

    public void passive(Entity other) {

        this.evasion = 1 - (1 - this.evasion)/3;
        if (this.evasion > 0.9) {
            this.evasion = 0.9;
            BattleScreen.getInstance().actionStack.add(
                new Action("Your evasion has reached the max of 90%!", 
                () -> {
                }
            ));
            
        } else {
            this.health = (int) (this.health * 0.75);
            this.attack = (int) (this.attack * 0.75);
        }
    }

    public String getAttackDetails() {
        return "Bite: deals " + attack + " damage.";
    }

    public String getPassiveDetails() {
        return "Shrink: raises evasion from " + (int) (this.evasion * 100) + "% to " + (int) ((1 - (1 - this.evasion)/3) * 100) + "% while reducing your stats by 1/4.";
    }
}
