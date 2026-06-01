import java.util.ArrayList;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ElectricSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThunderSoul extends Soul
{
    /**
     * Act - do whatever the ElectricSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static GreenfootImage costume = new GreenfootImage("electric.png");
    static final String NAME = "Electric Spirit";
    static final int BASE_HEALTH = 90;
    static final int BASE_ATTACK = 30;
    static final String ATTACK_NAME = "Shock";
    static final String PASSIVE_NAME = "Charge";
    static final String PASSIVE_DETAILS = "Do increased damaged & double the stun chance per charge (base 25%)";
    static final String ULTIMATE_NAME = "Thunderstorm";
    static final String ULTIMATE_DETAILS = "Massive electric damage, reaches enemies depending on charge";

    int charge;
    
    public ThunderSoul()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.electric, ATTACK_NAME, PASSIVE_NAME, ULTIMATE_NAME, costume);
        charge = 0;

    }
    
    public void passive(Entity other) {
        charge ++;
    }

    public void attack(Entity other) {
        attack = attack * (charge + 1);
        super.attack(other);
        attack = attack / (charge + 1);

        if (Greenfoot.getRandomNumber(100) <= (int)Math.pow(2, charge) * 25) {
            other.stunDuration = 1;
        }
        charge = 0;
    }

    public void ultimate(ArrayList<Entity> allies, ArrayList<Entity> others) {
        if (!this.ultimateUsed) {

            int damage = (int)((30 + attack * (charge + 1) / 10) * levelModifier);
            // Logic to hit additional enemies based on charge can be implemented here

            for (int i = 0; i < Math.min(charge+1, others.size()); i++) {

                final int index = i;
                final double effectiveDamage = effectivenessMultiplier(others.get(index)) * damage;
                BattleScreen.getInstance().actionStack.add(
                    new Action("Thunderstorm hits " + others.get(index).name + " for " + (int)effectiveDamage + " damage" + effectivenessTag(others.get(index)) + "!",
                    () -> {
                        others.get(index).takeDamage(effectiveDamage);
                    }
                ));
            }
            ultimateUsed = true;

            charge = 0;
        }     
        
    }


    public String getAttackDetails() {
        return "Shock: deals " + attack + " electric damage; expends charges.";
    }

    public String getPassiveDetails() {
        return "Charge: Boosts attack or thunderstorm once. Can stack.";
    }

    public String getUltimateDetails() {
        if (charge == 0) {
            return "Thunderstorm: strikes the next enemy with " + (int)(30 + attack / 10) + " electric damage.";
        }
        return "Thunderstorm: strikes the next " + (charge + 1) + " enemies with " + (int)(30 + attack * (charge + 1) / 10) + " electric damage. Expends charges.";
    }
}
