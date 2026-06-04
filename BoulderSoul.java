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
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class BoulderSoul
     */
    
    static final GreenfootImage COSTUME = new GreenfootImage("boulder.png");
    static final String NAME = "Boulder Soul";
    static final int BASE_HEALTH = 80;
    static final int BASE_ATTACK = 25;
    static final String ATTACK_NAME = "Crumble";
    static final String PASSIVE_NAME = "Boulder Shell";

    static final String ULTIMATE_NAME = "Earthquake";
    static final String ULTIMATE_DETAILS = "High damage + burning, self-damaging.";

    static final String PASSIVE_DETAILS = "Reduce the damage taken";
    
    static final int MAX_DEFENSE = 15;
    static final int INTANGIBILITY_DURATION = 4;
    int intangibilityLeft = 0;
    static final int DEFENSE_INCREASE = 5;
    static final int PASSIVE_COOLDOWN = 4;
    
    public BoulderSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.fire, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, PASSIVE_COOLDOWN, COSTUME);

    }
    
    public void passive(Entity other) {
        defense += DEFENSE_INCREASE * levelModifier;
        if (defense >= MAX_DEFENSE * levelModifier) {
            defense = (int) (MAX_DEFENSE * levelModifier);
            BattleScreen.getInstance().actionStack.add(new Action("Max defense of " + defense + " reached!"));
        }
    }
    
    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        defense = (int) (MAX_DEFENSE *  levelModifier);
        intangibilityLeft = INTANGIBILITY_DURATION;
        
    }
    
    public void applyStatusEffects()
    {
        super.applyStatusEffects();
        if(intangibilityLeft > 0)
        {
            intangibilityLeft--;
        }
    }
    
    public void takeDamage(int damage) {
        if(intangibilityLeft == 0)
        {
            this.health -= Math.max(damage - this.defense, 0);            
        }
    }

    public void takeDamage(double damage) {
        if(intangibilityLeft == 0)
        {
            this.health -= (int) Math.max(damage - this.defense, 0);
        }
    }
    
    public int effectiveDamage(double damage, Entity other) {
        if(intangibilityLeft == 0)
        {
            return (int) Math.max(damage - other.defense, 0);
        } else {
            return 0;
        }
    }

    public int effectiveDamage(int damage) {
        if(intangibilityLeft == 0)
        {
            return (int) Math.max(damage - this.defense, 0);
        } else {
            return 0;
        }
    }
    
    public String getAttackDetails() {
        return "Crumble: deals " + attack + " rock damage.";
    }

    public String unwrappedGetPassiveDetails() {
        return "Boulder Shell: raises defense by " + (int)(DEFENSE_INCREASE * this.levelModifier) + ". Max: " + (int)(MAX_DEFENSE * this.levelModifier) + ".";
    }
    
    public String unwrappedGetUltimateDetails() {
        return "Earthquake: becomes intangible";
    }
}
