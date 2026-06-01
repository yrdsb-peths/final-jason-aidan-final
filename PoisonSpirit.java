import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PoisonSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoisonSpirit extends Spirit
{
    /**
     * Act - do whatever the PoisonSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("poison.png");
    static final String NAME = "Poison Spirit";
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Toxin";
    static final String PASSIVE_NAME = "Corrosive Acid";
    static final String PASSIVE_DETAILS = "Poisons for 15% overtime!";
    
    public PoisonSpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.poison, ATTACK_NAME, PASSIVE_NAME, costume);

    }

    public void passive(Entity other) {
        other.poisonedPercentage = 0.15;
        other.poisonedDuration = 3;
    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return "Toxin deals " + attack + " poison damage.";
    }

    public String getPassiveDetails() {
        return "Corrosive Acid poisons enemy for 15% health over 3 turns.";
    }
}
