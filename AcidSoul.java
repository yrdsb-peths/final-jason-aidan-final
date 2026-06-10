import java.util.ArrayList;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AcidSoul here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AcidSoul extends Soul
{
    /**
     * Act - do whatever the AcidSoul wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("acid.png");
    static final String NAME = "Acid Soul";
    static final int BASE_HEALTH = 90;
    static final int BASE_ATTACK = 25;
    static final String ATTACK_NAME = "Toxin";
    static final String PASSIVE_NAME = "Corrosive Acid";
    static final String ULTIMATE_NAME = "Life-curse";
    static final int PASSIVE_COOLDOWN = 4;

    static final int CURSE_EXECUTION_TIME = 8;

    int curseTime;
    Entity curseTarget;
    
    public AcidSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.poison, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, costume);
        curseTarget = null;

    }
    
    public void passive(Entity other) {
        other.poisonedPercentage = 0.25;
        other.poisonedDuration = 4;
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        BattleScreen screenInstance = BattleScreen.getInstance();
        int maxHealth = 0;
        for (Entity other : others) {
            if (other.health > maxHealth) {
                maxHealth = other.health;
                curseTarget = other;
            }
        }
        screenInstance.actionStack.add(
            new Action("> " + curseTarget.name + " has been cursed!",
            () -> {
                curseTarget.curseTime = (int)(CURSE_EXECUTION_TIME / levelModifier);
                curseTarget.isCursed = true;
                curseStartRound = screenInstance.turnNumber;
            }
        ));
        
    }


    public String getAttackDetails() {
        return "Toxin deals " + attack + " poison damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Corrosive Acid poisons enemy for 25% health 4 times.";
    }

    public String unwrappedGetUltimateDetails() {

        return "Targets the enemy with highest health. They will die in " + (int)(CURSE_EXECUTION_TIME / levelModifier) + " turns.";
    }
}
