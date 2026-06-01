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
    
    public RockSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.rock, ATTACK_NAME, PASSIVE_NAME, costume);

    }

    public void passive(Entity other) {
        if (defense >= 10 * levelModifier) {
            BattleScreen.getInstance().actionStack.add(new Action("Max defense of " + defense + " reached!"));
        } else {
            defense += 3 * levelModifier;
        }
        
        
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
