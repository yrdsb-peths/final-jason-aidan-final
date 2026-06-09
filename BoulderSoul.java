import java.util.ArrayList;

import greenfoot.GreenfootImage;
/**
 * Write a description of class BoulderSoul here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoulderSoul extends Soul 
{

    /**
     * Constructor for objects of class BoulderSoul
     */
    
    static final GreenfootImage COSTUME = new GreenfootImage("boulder.png");
    static final String NAME = "Boulder Soul";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 30;
    static final String ATTACK_NAME = "Crumble";
    static final String PASSIVE_NAME = "Boulder Shell";

    static final String ULTIMATE_NAME = "Iron Ball";

    static final String PASSIVE_DETAILS = "Reduce the damage taken";
    
    static final int INTANGIBILITY_DURATION = 6;
    int intangibilityLeft = 0;
    static final int DEFENSE_INCREASE = 8;
    static final int MAX_DEFENSE = 4 * DEFENSE_INCREASE;
    static final int PASSIVE_COOLDOWN = 4;
    
    public BoulderSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.rock, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, COSTUME);

    }
    
    public void passive(Entity other) {
        defense += DEFENSE_INCREASE * levelModifier;
        if (defense >= MAX_DEFENSE * levelModifier) {
            defense = (int) (MAX_DEFENSE * levelModifier);
            BattleScreen.getInstance().actionStack.add(new Action("Max defense of " + defense + " reached!"));
        }
    }
    
    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        intangibilityLeft = INTANGIBILITY_DURATION;
        BattleScreen.getInstance().actionStack.add(
            new Action("Boulder is now invulnerable",() -> {
            }
        ));
    }
    
    public void applyStatusEffects()
    {
        
        if(intangibilityLeft > 0)
        {
            intangibilityLeft--;
        } else {
            super.applyStatusEffects();
        }
    }
    
    public void takeDamage(int damage) {
        if(intangibilityLeft == 0)
        {
            super.takeDamage(damage);         
        }
    }

    public void takeDamage(double damage) {
        if(intangibilityLeft == 0)
        {
            super.takeDamage(damage);
        }
    }
    
    public String getAttackDetails() {
        return "Crumble: deals " + attack + " rock damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Boulder Shell: raises defense by " + (int)(DEFENSE_INCREASE * this.levelModifier) + ". Max: " + (int)(MAX_DEFENSE * this.levelModifier) + ".";
    }
    
    public String unwrappedGetUltimateDetails() {
        return "Iron Ball: becomes invulnerable for " + INTANGIBILITY_DURATION + " turns.";
    }
}
