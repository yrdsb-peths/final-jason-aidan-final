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
    static final String ATTACK_NAME = "Protest";
    static final String PASSIVE_NAME = "Numerical Advantage";

    static final int PASSIVE_COOLDOWN = 8;
    static int MAXIMUM_COUNT = 6;

    static int totalPeopleLeft = -1;

    
    public OrdinarySpirit()
    {
        super(NAME,BASE_HEALTH, BASE_ATTACK, Element.ordinary, ATTACK_NAME, PASSIVE_NAME, PASSIVE_COOLDOWN, costume);
        MAXIMUM_COUNT *= levelModifier;
        if (totalPeopleLeft == -1) {
            totalPeopleLeft = MAXIMUM_COUNT;
        }
    }

    public void passive(Entity other) {
        BattleScreen instance = BattleScreen.getInstance();
        invite();
        invite();
        if (totalPeopleLeft <= 0) {
            BattleScreen.getInstance().actionStack.add(new Action(
                "Everyone has been invited.",
                () -> {}
            ));
        }
        instance.setEntityLocations();
    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return "Thing: deals " + attack + " damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Numerical Advantage: Invite 2 more people";
    }

    private void invite() {
        if (totalPeopleLeft > 0) {
            totalPeopleLeft --;
            BattleScreen instance = BattleScreen.getInstance();
            OrdinarySpirit newOrdinary = new OrdinarySpirit();
            newOrdinary.level = this.level;
            newOrdinary.updateModifier();
            newOrdinary.fixLevel();
            instance.getCurrentEntities().add(newOrdinary);
        }


    }
}
