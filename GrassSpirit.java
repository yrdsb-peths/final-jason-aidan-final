import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GrassSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GrassSpirit extends Spirit
{
    /**
     * Act - do whatever the GrassSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static final GreenfootSound HEAL = new GreenfootSound("heal-short.mp3");

    static GreenfootImage costume = new GreenfootImage("grass.png");
    static final String NAME = "Grass Spirit";
    static final int BASE_HEALTH = 60;
    static final int BASE_ATTACK = 15;
    static final String ATTACK_NAME = "Seed";
    static final String PASSIVE_NAME = "Grow";
    static final String PASSIVE_DETAILS = "Heal & regenerate";

    static final int PASSIVE_COOLDOWN = 6;
    static final int HEALING_AMOUNT = 5;
    static final int HEALING_DURATION = 6;

    public GrassSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.grass, ATTACK_NAME, PASSIVE_NAME, PASSIVE_COOLDOWN, costume);

    }

    public void passive(Entity other) {
        HEAL.play();
        this.healingAmount = (int) (HEALING_AMOUNT * this.levelModifier);
        this.healingDuration = HEALING_DURATION;
        
    }

    public Soul getUpgraded() {
        return new TreeSoul();
    }


    public String getAttackDetails() {
        return "Seed deals " + attack + " grass damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Grow: heals " + (int)(HEALING_AMOUNT * this.levelModifier) + " HP for " + HEALING_DURATION + " turns. Does not stack.";
    }
}
