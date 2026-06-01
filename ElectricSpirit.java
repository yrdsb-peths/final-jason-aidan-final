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
    static GreenfootImage costume = new GreenfootImage("electric.png");
    static final String NAME = "Electric Spirit";
    static final int BASE_HEALTH = 70;
    static final int BASE_ATTACK = 20;
    static final String ATTACK_NAME = "Shock";
    static final String PASSIVE_NAME = "Charge";
    static final String PASSIVE_DETAILS = "Do increased damaged & double the stun chance per charge (start at 25%)";

    int charge;
    
    public ElectricSpirit()
    {
        super(NAME, BASE_HEALTH, BASE_ATTACK, Element.electric, ATTACK_NAME, PASSIVE_NAME, PASSIVE_DETAILS, costume);
        charge = 0;

    }
    
    public void passive(Entity other) {
        charge ++;
    }

    public void attack(Entity other) {
        attack = attack * (charge + 1);
        super.attack(other);
        attack = attack / (charge + 1);

        if (Greenfoot.getRandomNumber(100) <= charge * 25) {
            other.stunDuration = 1;
        }
        charge = 0;
    }

    public Soul getUpgraded() {
        return null;
    }
}
