import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class ShadowSoul here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShadowSoul extends Soul
{
    /**
     * Act - do whatever the DarkSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("shadow.png");
    static final String NAME = "Shadow Soul";
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 15;
    static final String ATTACK_NAME = "Shadow";
    static final String PASSIVE_NAME = "Total Blackout";
    static final String PASSIVE_DETAILS = "Weaken your opponent's attack & defense.";

    static final String ULTIMATE_NAME = "Eclipse";
    static final String ULTIMATE_DETAILS = "Weaken all opponents' attack & defense.";

    static final int PASSIVE_COOLDOWN = 4;

    static final double EVASION_INCREASE = 0.2;
    static final int ATTACK_DEBUFF = 4;
    static final int DEFENSE_DEBUFF = 6;
    
    public ShadowSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.dark, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, costume);
        this.evasion = EVASION_INCREASE;

    }

    public void passive(Entity other) {
        other.defense = Math.max((int)(other.defense - DEFENSE_DEBUFF * levelModifier), -20);
        other.attack = Math.max((int)(other.attack - ATTACK_DEBUFF * levelModifier), 3);
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        others.get(0).health -= 20 * levelModifier;
        for (Entity other : others) {
            passive(other);
        }
        for (Entity ally : allies) {
            ally.evasion = Math.max(EVASION_INCREASE, ally.evasion);
        }
    }


    public String getAttackDetails() {
        return "Shadow: deals " + attack + " dark damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Total Blackout: lowers enemy attack (" + (int)(ATTACK_DEBUFF * levelModifier) + ") and defense (" + (int)(DEFENSE_DEBUFF * levelModifier) + ").";
    }

    public String unwrappedGetUltimateDetails() {
        return "Eclipse: deals " + (int)(20 * levelModifier) + " dark damage. Lowers all enemies' attack (" + (int)(ATTACK_DEBUFF * levelModifier) + "), defense (" + (int)(DEFENSE_DEBUFF * levelModifier) + "), and accuracy.";
    }
}
