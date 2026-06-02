import java.util.ArrayList;

import greenfoot.GreenfootImage;

public class TreeSoul extends Soul {
    /**
     * Constructor for objects of class Blaze
     */
    static final GreenfootImage COSTUME = new GreenfootImage("tree.png");
    static final String NAME = "Tree Soul";
    static final int BASE_HEALTH = 90;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Seed";
    static final String PASSIVE_NAME = "Grow";

    static final String ULTIMATE_NAME = "Nourish";

    static final int PASSIVE_COOLDOWN = 4;
    static final int HEALING_AMOUNT = 7;
    static final int HEALING_DURATION = 6;

    static final int ATTACK_INCREASE = 5;
    static final int DEFENSE_INCREASE = 2;
    static final int DEFENSE_CAP = 4 * DEFENSE_INCREASE;
    static final int ATTACK_CAP = BASE_ATTACK + 4 * ATTACK_INCREASE;
    
    public TreeSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.water, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, COSTUME);
    }

    public void attack(Entity other) {
        super.attack(other);
    }   

    public void passive(Entity other) {
        this.healingAmount = (int) (HEALING_AMOUNT * this.levelModifier);
        this.healingDuration = HEALING_DURATION;
        this.attack += (int)(ATTACK_INCREASE * this.levelModifier);
        this.defense += (int)(DEFENSE_INCREASE * this.levelModifier);

        if (attack > ATTACK_CAP) {
            attack = ATTACK_CAP;
            BattleScreen.getInstance().actionStack.add(new Action("The maximum attack of " + ATTACK_CAP + " has been reached.", () -> {}));
        }

        if (defense > DEFENSE_CAP) {
            defense = DEFENSE_CAP;
            BattleScreen.getInstance().actionStack.add(new Action("The maximum attack of " + DEFENSE_CAP + " has been reached.", () -> {}));
        }
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        if (allies.size() > 1) {
            Entity nextEntity = allies.get(1);

            BattleScreen.getInstance().actionStack.add(new Action(
                "> " + nextEntity.name + " inherits " + health/3 + " health!", 
                () -> {
                    nextEntity.health += health/3;
                }
            ));

            BattleScreen.getInstance().actionStack.add(new Action(
                "> " + nextEntity.name + " inherits " + attack/3 + " attack!", 
                () -> {
                    nextEntity.attack += attack/3;
                }
            ));

            BattleScreen.getInstance().actionStack.add(new Action(
                "> " + nextEntity.name + " inherits " + defense/3 + " defense!", 
                () -> {
                    nextEntity.defense += defense/3;
                }
            ));

        } else {
            BattleScreen.getInstance().actionStack.add(new Action(
                "> There is no one left.", 
                () -> {}
            ));
        }
    }


    public String getAttackDetails() {
        return "Seed: deals " + attack + " grass damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Grow: Improves health & defense, regenerates.";
    }

    public String unwrappedGetUltimateDetails() {
        return "The next entity inherits a third of your stats.";
    }
}
