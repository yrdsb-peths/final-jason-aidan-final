import java.util.ArrayList;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StarSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CelestialSoul extends Soul
{
    /**
     * Act - do whatever the StarSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("star.png");
    static final String NAME = "Celestial Soul";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 25;
    static final String ATTACK_NAME = "Flare";
    static final String PASSIVE_NAME = "Meteor Shower";
    static final String ULTIMATE_NAME = "idk";
    static final String PASSIVE_DETAILS = "Successive meteor strikes with a chance to hit each strike";

    static final int MAX_METEOR_STRIKES = 10;
    static final double METEOR_STRIKE_CHANCE = 0.5;
    static final int METEOR_STRIKE_DAMAGE_DIVISOR = 3;
    static final int METEOR_BURNING_DAMAGE = 3;

    static final int PASSIVE_COOLDOWN = 6;
    
    public CelestialSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.star, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, costume);
    }



    public void passive(Entity other) {
    }

    // overriding applyPassive to add successive meteor strikes with a chance to hit each strike
    public void applyPassive(Entity other) {
        
        // Any additional initialization code for FireSpirit can go here
        super.applyPassive(other);
        other.burningDamage = Math.max(other.burningDamage, (int)(METEOR_BURNING_DAMAGE * levelModifier));
        int j = 1;

        final double effectiveDamage = effectivenessMultiplier(other) * (int)(attack * levelModifier / METEOR_STRIKE_DAMAGE_DIVISOR);

        for (int i = 0; i < MAX_METEOR_STRIKES; i++) {
            
            if (Greenfoot.getRandomNumber(100) < METEOR_STRIKE_CHANCE * 100) {
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

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {

    }

    public Soul getUpgraded() {
        return null;
    }


    public String getAttackDetails() {
        return "Flare: deals " + attack + " star damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Meteor Shower: successively deals " + (int)(attack * levelModifier / METEOR_STRIKE_DAMAGE_DIVISOR) + " meteor damage and applies a small burn.";
    }

    public String unwrappedGetUltimateDetails() {
        return "";
    }
}
