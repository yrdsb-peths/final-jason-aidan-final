import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OrdinarySpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OrdinarySpirit extends Spirit
{
    /**
     * Act - do whatever the OrdinarySpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("ordinary.png");
    static final String NAME = "Ordinary Spirit";
    static final int BASE_HEALTH = 50;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "Thing";
    static final String PASSIVE_NAME = "Transformation";
    static final String PASSIVE_DETAILS = "You'll find out soon...";
    
    public OrdinarySpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.ordinary, ATTACK_NAME, PASSIVE_NAME, costume);

    }

    public void passive(Entity other) {
        
    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return "";
    }

    public String getPassiveDetails() {
        return "";
    }
}
