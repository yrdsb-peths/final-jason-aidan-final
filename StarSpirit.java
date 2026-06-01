import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StarSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StarSpirit extends Spirit
{
    /**
     * Act - do whatever the StarSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("star.png");
    static final String NAME = "Star Spirit";
    static final int BASE_HEALTH = 100;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Flare";
    static final String PASSIVE_NAME = "Meteor Shower";
    static final String PASSIVE_DETAILS = "Successive meteor strikes with a chance to hit each strike";
    
    public StarSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.star, ATTACK_NAME, PASSIVE_NAME, costume);
    }

    public void passive(Entity other) {
    }

    // overriding applyPassive to add successive meteor strikes with a chance to hit each strike
    public void applyPassive(Entity other) {
        
        // Any additional initialization code for FireSpirit can go here
        super.applyPassive(other);
        other.burningDamage = Math.max(other.burningDamage, (int)(3 * levelModifier));
        int j = 1;

        final double effectiveDamage = effectivenessMultiplier(other) * (int)(attack * levelModifier / 2);

        for (int i = 0; i < 5; i++) {
            
            if (Greenfoot.getRandomNumber(100) < 50) {
                BattleScreen.getInstance().actionStack.add(
                    new Action("Meteor strike (x" + j + ")" + effectivenessTag(other) + "!", 
                    () -> {
                        other.takeDamage(effectiveDamage);
                        other.burningDuration ++;
                    }
                ));
                j ++;

            }
            
        }
    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return "Flare: deals " + attack + " star damage.";
    }

    public String getPassiveDetails() {
        return "Meteor Shower: successively deals " + (int)(attack * levelModifier / 2) + " meteor damage and applies a small burn.";
    }
}
