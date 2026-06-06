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
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 10;
    static final String ATTACK_NAME = "Shadow";
    static final String PASSIVE_NAME = "Total Blackout";
    static final String PASSIVE_DETAILS = "Weaken your opponent's attack & defense.";
    static final int PASSIVE_COOLDOWN = 4;

    static final double EVASION_INCREASE = 0.1;
    static final int ATTACK_DEBUFF = 2;
    static final int DEFENSE_DEBUFF = 4;
    
    public DarkSpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.dark, ATTACK_NAME, PASSIVE_NAME, PASSIVE_COOLDOWN, costume);
        this.evasion = EVASION_INCREASE;

    }

    public void passive(Entity other) {
        other.defense = Math.max(other.defense - (int)(DEFENSE_DEBUFF * levelModifier), -50);
        other.attack = Math.max(other.attack - (int)(ATTACK_DEBUFF * levelModifier), 0);
    }

    public Soul getUpgraded() {
        return new ShadowSoul();
    }


    public String getAttackDetails() {
        return "Shadow deals " + attack + " dark damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Total Blackout: lowers enemy attack (" + (int)(ATTACK_DEBUFF * levelModifier) + ") and defense (" + (int)(DEFENSE_DEBUFF * levelModifier) + ").";
    }
}
