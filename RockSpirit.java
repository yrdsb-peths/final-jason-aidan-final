import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RockSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RockSpirit extends Spirit
{
    /**
     * Act - do whatever the RockSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("rock.png");
    static final String NAME = "Rock Spirit";
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Crumble";
    static final String PASSIVE_NAME = "Boulder Shell";
    static final String PASSIVE_DETAILS = "Reduce the damage taken";

    static final int MAX_DEFENSE = 15;
    static final int DEFENSE_INCREASE = 5;
    static final int PASSIVE_COOLDOWN = 4;

    public RockSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.rock, ATTACK_NAME, PASSIVE_NAME, PASSIVE_COOLDOWN, costume);
    }

    public void passive(Entity other) {
        defense += DEFENSE_INCREASE * levelModifier;
        if (defense >= MAX_DEFENSE * levelModifier) {
            defense = (int) (MAX_DEFENSE * levelModifier);
            BattleScreen.getInstance().actionStack.add(new Action("Max defense of " + defense + " reached!"));
        }
    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return "Crumble: deals " + attack + " rock damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Boulder Shell: raises defense by " + (int)(DEFENSE_INCREASE * levelModifier) + ". Max: " + (int)(MAX_DEFENSE * levelModifier) + ".";
    }
}
