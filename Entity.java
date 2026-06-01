import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
abstract public class Entity
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
    boolean isStunned;
    int stunDuration;
    
    Element type;

    GreenfootImage image;
    
    String attackName = "";
    String passiveName = "";
    String passiveDetails = "";

    // Creates a fixed list of spirit types that can be used to dynamically create spirit object
    
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
        this.isStunned = false;
        this.stunDuration = 10;
        updateModifier();
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
            BattleScreen.getInstance().actionStack.add(new Action(
                this.name + " took " + this.burningDamage + " burning damage.",
                () -> {
                    this.health -= this.burningDamage;
                    this.burningDuration--;
                }
            ));
            
        }
        //apply poison damage and decrease poison duration
        if(this.poisonedDuration > 0)
        {
            
            int damage = (int)(this.health * this.poisonedPercentage);
            BattleScreen.getInstance().actionStack.add(new Action(
                this.name + " took " + damage + " (" + (this.poisonedPercentage*100) + "% of health) poison damage.",
                () -> {
                    this.health -= damage;
                    this.poisonedDuration--;
                }
            ));

        }

        if (this.healingDuration > 0)
        {

            BattleScreen.getInstance().actionStack.add(new Action(
                this.name + " healed " + this.healingAmount + "hp.",
                () -> {
                    this.health += this.healingAmount;
                    this.healingDuration--;
                }
            ));
        }
    }

    public void levelUp(int x) {
        level += x;
        updateModifier();
    }

    public void levelUp() {

        level ++;
        updateModifier();
    }

    public void updateModifier() {
        // 10% increase in stats per level
        levelModifier = (1.0 + (double) level / 10);
    }

    public void fixLevel() {
        
        this.health = (int) (this.health * levelModifier);
        this.attack = (int) (this.attack * levelModifier);
        System.out.println(this.health + " " + levelModifier);
    }

    public void attack(Entity other) {
        int effectiveness = comparedTo(other);

        if (effectiveness == 0) {
            int rand = Greenfoot.getRandomNumber(100);
            if (rand <= 10) {
                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action("> It missed!", () -> {}));
            } else if (rand > 10 && rand <= 90) {
                double outputDmg = attack;

                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action(
                    "> It dealt " + (int)outputDmg + " damage!",
                    () -> {
                        other.health -= outputDmg;
                    }
                ));
            } else if (rand > 90) {
                double outputDmg = attack * 1.5;

                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action(
                    "> It was a critical hit, dealing " + (int)outputDmg + " damage!",
                    () -> {
                        other.health -= outputDmg;
                    }
                ));
            }
        } else if (effectiveness > 0) {
            int randCrit = Greenfoot.getRandomNumber(100);
            if (randCrit < 10) {
                double outputDmg = attack * 3;
                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action(
                    "> It was a critical hit, dealing " + (int)outputDmg + " damage!",
                    () -> {
                        other.health -= outputDmg;
                    }
                ));
            } else { 
                double outputDmg = attack * 1.5;
                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action(
                    "> It was super effective, dealing " + (int)outputDmg + " damage!",
                    () -> {
                        other.health -= outputDmg;
                    }
                ));
            }
        } else if (effectiveness < 0) {
            int randMiss = Greenfoot.getRandomNumber(100);
            if (randMiss < 10) {
                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action("> It missed!", () -> {}));
            } else { 
                double outputDmg = attack * 0.5;
                BattleScreen.getInstance().actionStack.add(new Action("> " + name + " used " + attackName + "."));
                BattleScreen.getInstance().actionStack.add(new Action(
                    "> It was not very effective, dealing " + (int)outputDmg + " damage.",
                    () -> {
                        other.health -= outputDmg;
                    }
                ));
            }
        }
    }

    // basically a wrapper
    public void applyPassive(Entity other) {
        BattleScreen.getInstance().actionStack.add(new Action(
            "> " + name + " used " + passiveName + ".",
            () -> {passive(other);}
        ));
    }

    abstract public void passive(Entity other);

}
