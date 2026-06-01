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
    static GreenfootImage costume = new GreenfootImage("grass.png");
    static final String NAME = "Grass Spirit";
    static final int BASE_HEALTH = 60;
    static final int BASE_ATTACK = 15;
    static final String ATTACK_NAME = "Seed";
    static final String PASSIVE_NAME = "Grow";
    static final String PASSIVE_DETAILS = "Heal & regenerate";

    public GrassSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.grass, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);

    }

    public void passive(Entity other) {
        this.healingAmount = (int) (5.0 * this.levelModifier);
        this.healingDuration = 6;
        
    }

    public Soul getUpgraded() {
        return null;
    }

}
