import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DarkSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DarkSpirit extends Spirit
{
    /**
     * Act - do whatever the DarkSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("dark.png");
    static final String NAME = "Dark Spirit";
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "Shadow";
    static final String PASSIVE_NAME = "Total Blackout";
    static final String PASSIVE_DETAILS = "Weaken your opponent's attack & defense.";
    
    public DarkSpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.dark, ATTACK_NAME, PASSIVE_NAME, costume);
        this.evasion = 0.2;

    }

    public void passive(Entity other) {
        other.defense = Math.max(other.defense -= 5 * levelModifier, -10);
        other.attack = Math.max(other.attack -= 3 * levelModifier, 1);
    }

    public Soul getUpgraded() {
        return new ShadowSoul();
    }


    public String getAttackDetails() {
        return "Shadow deals " + attack + " dark damage.";
    }

    public String getPassiveDetails() {
        return "Total Blackout: lowers enemy attack (" + (3 * levelModifier) + ") and defense (" + (5 * levelModifier) + ").";
    }
}
