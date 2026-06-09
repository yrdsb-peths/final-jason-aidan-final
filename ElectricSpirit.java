import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ElectricSpirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ElectricSpirit extends Spirit
{
    /**
     * Act - do whatever the ElectricSpirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    static final GreenfootSound ELECTRIC_STRIKE = new GreenfootSound("electric-strike.mp3");
    static final GreenfootSound ELECTRIC_CHARGE = new GreenfootSound("electric-charge.mp3");


    static GreenfootImage costume = new GreenfootImage("electric.png");
    static final String NAME = "Electric Spirit";
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 15;
    static final String ATTACK_NAME = "Shock";
    static final String PASSIVE_NAME = "Charge";
    static final String PASSIVE_DETAILS = "Do increased damaged & double the stun chance per charge (base 12.5%)";

    int charge;
    
    public ElectricSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.electric, ATTACK_NAME, PASSIVE_NAME, costume, ELECTRIC_STRIKE);
        charge = 0;

    }
    
    public void passive(Entity other) {
        charge ++;
        ELECTRIC_CHARGE.play();
    }

    public void attack(Entity other) {
        
        attack = attack * (charge + 1);
        super.attack(other);
        attack = attack / (charge + 1);

        if (Greenfoot.getRandomNumber(100) <= Math.pow(2, charge) * 12.5) {
            other.stunDuration = 1;
        }
        charge = 0;
    }

    public Soul getUpgraded() {
        return new ThunderSoul();
    }


    public String getAttackDetails() {
        if (charge == 0) {
            return "Shock: deals " + attack + " electric damage.";
        } else {
            return "Shock: deals " + (attack * (charge + 1)) + " electric damage. " + (Math.pow(2, charge) * 12.5) + "% chance to stun.";
        }
    }

    public String unwrappedGetPassiveDetails() {
        return "Charge: boosts next attack and stun chance. Can stack.";
    }
}
