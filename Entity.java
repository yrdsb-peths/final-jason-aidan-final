import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Entity
{
    /**
     * Act - do whatever the Spirit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int level;

    String name;
    int health;
    int attack;
    int burningDamage;
    int burningDuration; // # of turns
    double poisonedPercentage; // percentage damage between 0 and 1
    int poisonedDuration; // # of turns
    int healingAmount;
    int healingDuration; // # of turns
    double levelModifier;
    
    Element type;

    GreenfootImage image;
    
    String attackName = "";
    String passiveName = "";
    String passiveDetails = "";

    // Creates a fixed list of spirit types that can be used to dynamically create spirit object

    public void passive(Entity other) {
        System.out.println("This Entity has no passive ability.");
        // Any additional initialization code for FireSpirit can go here
    }
    
    
    public Entity(String name, int health, int attack, Element type, String attackName, String passiveName, String passiveDetails, GreenfootImage image)
    {

        burningDuration = 0;
        poisonedDuration = 0;
        burningDamage = 0;
        poisonedPercentage = 0;
        healingAmount = 0;
        healingDuration = 0;
        this.health = health;
        this.attack = attack;
        this.type = type;
        this.attackName = attackName;
        this.passiveName = passiveName;
        this.passiveDetails = passiveDetails;
        this.image = image;
        this.name = name;
        updateModified();
    }

    public int comparedTo(Entity other)
    {
        return this.type.comparedTo(other.type);
    }

    public void applyStatusEffects()
    
    {
        //apply burn damage and decrease burn duration
        if(this.burningDuration > 0)
        {
            this.health -= this.burningDamage;
            this.burningDuration--;
        }
        //apply poison damage and decrease poison duration
        if(this.poisonedDuration > 0)
        {
            this.health -= (int)(this.health * this.poisonedPercentage);
            this.poisonedDuration--;
        }

        if (this.healingDuration > 0)
        {
            this.health += this.healingAmount;
            this.healingDuration--;
        }
    }

    public void levelUp(int x) {
        level += x;
        updateModified();
    }

    public void levelUp() {

        level ++;
        updateModified();
    }

    public void updateModified() {
        levelModifier = (1.0 + (double) level / 10);
    }

    public void fixLevel() {
        // 10% increase in stats per level
        this.health = (int) (this.health * levelModifier);
        this.attack = (int) (this.attack * levelModifier);
        System.out.println(this.health + " " + levelModifier);
    }
}
