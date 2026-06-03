import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SusSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SusSpirit extends Spirit
{
    /**
     * Act - do whatever the SusSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("sus.png");
    static final String NAME = "Sus Spirit";
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 60;
    static final String ATTACK_NAME = "sus";
    static final String PASSIVE_NAME = "sus";
    static final String PASSIVE_DETAILS = "sus";

    static final double MISS_CHANCE_CHANGE = 0.9;

    int attackChance = 0;
    
    public SusSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.sus, ATTACK_NAME, PASSIVE_NAME, costume);
    }

    public void passive(Entity other) {
        attackChance = (int)(100 - (100 - attackChance) * MISS_CHANCE_CHANGE);
    }

    // override
    public void attack(Entity other) {
        if (Greenfoot.getRandomNumber(100) < attackChance) {
            super.attack(other);
            attackChance = 0;
        } else {
            BattleScreen.getInstance().actionStack.add(new Action("The attack didn't trigger.", () -> {}));
        }
    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return attackChance + "% chance to deal " + attack + " damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Sus: Increases attack chance from " + (int)(attackChance) + "% to " + (int)(100 - (100 - attackChance) * MISS_CHANCE_CHANGE) + "%.";
    }
}
