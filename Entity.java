import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
    int stunDuration;
    int defense;
    double evasion;
    int passiveCooldown; // # of turns until passive can be used again
    int passiveCooldownLeft; // # of turns left until passive can be used again
    int x;
    int y;
    boolean canSwap;
    // number from 0-1 representing chance to dodge an attack
    
    Element type;

    GreenfootImage image;
    
    String attackName = "";
    String passiveName = "";

    // Creates a fixed list of spirit types that can be used to dynamically create spirit object
    
    public Entity(String name, int health, int attack, Element type, String attackName, String passiveName, GreenfootImage image)
    {

        burningDuration = 0;
        poisonedDuration = 0;
        burningDamage = 0;
        poisonedPercentage = 0;
        healingAmount = 0;
        healingDuration = 0;
        evasion = 0;
        stunDuration = 0;
        passiveCooldown = 0;
        passiveCooldownLeft = 0;
        canSwap = true;
        this.health = health;
        this.attack = attack;
        this.type = type;
        this.attackName = attackName;
        this.passiveName = passiveName;
        this.image = image;
        this.name = name;
        updateModifier();
        scaleImage();
    }

    public Entity(String name, int health, int attack, Element type, String attackName, String passiveName, int passiveCooldown, GreenfootImage image)
    {
        this(name, health, attack, type, attackName, passiveName, image);
        this.passiveCooldown = passiveCooldown;
    }

    public int comparedTo(Entity other)
    {
        return this.type.comparedTo(other.type);
    }

    public void applyStatusEffects()
    
    {
        if (this.passiveCooldownLeft > 0) {
            this.passiveCooldownLeft--;
        }
        //apply burn damage and decrease burn duration
        if(this.burningDuration > 0)
        {
            BattleScreen screenInstance = BattleScreen.getInstance();

            screenInstance.actionStack.add(new Action(
                "> " + this.name + " took " + this.burningDamage + " burning damage.",
                // "",
                () -> {
                    this.health -= this.burningDamage;
                    this.burningDuration--;
                    AnimationTask.addFlashAnimation(this, 10, 100);
                }
            ));
            
        }
        //apply poison damage and decrease poison duration
        if(this.poisonedDuration > 0)
        {
            
            BattleScreen screenInstance = BattleScreen.getInstance();

            int damage = (int)(this.health * this.poisonedPercentage);
            screenInstance.actionStack.add(new Action(
                "> " + this.name + " took " + damage + " (" + (this.poisonedPercentage*100) + "% of health) poison damage.",
                () -> {
                    this.health -= damage;
                    this.poisonedDuration--;
                    AnimationTask.addFlashAnimation(this, 10, 100);
                }
            ));

        }

        if (this.healingDuration > 0)
        {

            BattleScreen.getInstance().actionStack.add(new Action(
                "> " + this.name + " healed " + this.healingAmount + " HP.",
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
        levelModifier = Math.sqrt(1.0 + (double) level / 10);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fixLevel() {
        
        this.health = (int) (this.health * levelModifier);
        this.attack = (int) (this.attack * levelModifier);
    }

    public double effectivenessMultiplier(Entity defender) {
        int effectiveness = this.type.comparedTo(defender.type);
        if (effectiveness > 0) {
            return 1.3;
        } else if (effectiveness < 0) {
            return 0.75;
        }
        return 1;
    }

    public String effectivenessTag(Entity defender) {
        int effectiveness = this.type.comparedTo(defender.type);
       if (effectiveness > 0) {
            return " (very effective)";
        } else if (effectiveness < 0) {
            return " (not very effective)";
        }
        return "";
    }

    public void takeDamage(int damage) {
        this.health -= Math.max(damage - this.defense, 0);
        AnimationTask.addShakeAnimation(BattleScreen.getInstance().getOpponentEntity(), 3, damage / 2, 2);

    }

    public void takeDamage(double damage) {
        
        this.health -= (int) Math.max(damage - this.defense, 0);
        AnimationTask.addShakeAnimation(BattleScreen.getInstance().getOpponentEntity(), 3, (int) (damage / 2), 2);
    }

    public int effectiveDamage(double damage, Entity other) {
        if(other instanceof BoulderSoul soul && soul.intangibilityLeft != 0)
        {
            return 0;
        }
        return (int) Math.max(damage - other.defense, 0);
    }

    public int effectiveDamage(int damage, Entity other) {

        if(other instanceof BoulderSoul soul && soul.intangibilityLeft != 0)
        {
            return 0;
        }
        return (int) Math.max(damage - other.defense, 0);
    }
    public void attack(Entity other) {
        
        BattleScreen screenInstance = BattleScreen.getInstance();

        screenInstance.actionStack.add(new Action("> " + name + " used " + attackName + "."));

        if (Greenfoot.getRandomNumber(100) <= 100*other.evasion) {
            screenInstance.actionStack.add(new Action("> The attack was evaded!", () -> {}));
            return;
        }

        int outputDmg = attack;
        boolean missed = false;
        String messageString = "";

        int effectiveness = comparedTo(other);

        int luck = Greenfoot.getRandomNumber(100);
        boolean hasCrit = false;

        if (effectiveness == 0) {
            if (luck <= 10) {
                messageString = "> It missed!";
                missed = true;

            } else if (luck > 10 && luck <= 90) {
                outputDmg = attack;
                messageString = "> It dealt " + effectiveDamage(outputDmg, other) + " damage!";

            } else if (luck > 90) {
                outputDmg = (int)(1.5*attack);
                messageString = "> It was a critical hit, dealing " + effectiveDamage(outputDmg, other) + " damage!";
                hasCrit = true;
            }
        } else if (effectiveness > 0) {
            if (luck < 10) {
                outputDmg = 3*attack;
                messageString = "> Critical hit, dealing " + effectiveDamage(outputDmg, other) + " damage!";
                hasCrit = true;
            } else { 
                outputDmg = (int)(1.5*attack);
                messageString = "> Super effective, dealing " + effectiveDamage(outputDmg, other) + " damage!";
            }
        } else if (effectiveness < 0) {

            if (luck < 10) {
                messageString = "> It missed!";
                missed = true;

            } else { 
                outputDmg = (int)(0.5*attack);
                messageString = "> Not very effective, dealing " + effectiveDamage(outputDmg, other) + " damage.";
            }
        }

        final int finalDamage = outputDmg;
        final boolean finalMissed = missed;
        final String finalString = messageString;
        final boolean finalHasCrit = hasCrit;

        screenInstance.actionStack.add(new Action(
            finalString,
            () -> {
                if (!finalMissed) {
                   other.takeDamage((int)finalDamage);
                } else {
                    BattleScreen.getInstance().animationStack.add(new AnimationTask(
                        MyWorld.worldTime, 
                    () -> {
                        screenInstance.missDisplay.getImage().setTransparency(255);
                        screenInstance.missDisplay.setLocation(other.x, other.y);
                        
                    }
                    ));
                        BattleScreen.getInstance().animationStack.add(new AnimationTask(
                        MyWorld.worldTime + 10, 
                    () -> {
                        screenInstance.missDisplay.getImage().setTransparency(0);
                    }
                    ));
                }
                if(finalHasCrit){
                        BattleScreen.getInstance().animationStack.add(new AnimationTask(
                        MyWorld.worldTime, 
                    () -> {
                        screenInstance.critDisplay.getImage().setTransparency(255);
                        screenInstance.critDisplay.setLocation(other.x, other.y);
                        
                    }
                    ));
                        BattleScreen.getInstance().animationStack.add(new AnimationTask(
                        MyWorld.worldTime + 10, 
                    () -> {
                        screenInstance.critDisplay.getImage().setTransparency(0);
                    }
                    ));
                }
            }
        ));

    }
    
    public void applyPassive(Entity other) {
        BattleScreen.getInstance().actionStack.add(new Action(
            "> " + name + " used " + passiveName + ".",
            () -> {passive(other); this.passiveCooldownLeft = passiveCooldown;}
        ));
    }

    public String getPassiveDetails() {
        if (passiveCooldownLeft > 0) {
            return passiveName + " is on cooldown.";
        }
        return unwrappedGetPassiveDetails();
    }

    public void scaleImage() {
        int scaleX = 100;
        int scaleY = 100;

        image.scale(scaleX, scaleY);
    }

    abstract public void passive(Entity other);

    abstract public String getAttackDetails();

    abstract public String unwrappedGetPassiveDetails();

    abstract public String getUltimateDetails();


}
