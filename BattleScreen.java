import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class BattleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

// Singleton pattern
public class BattleScreen extends Actor
{
    /**
     * Act - do whatever the BattleScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */


    
    MyWorld world;
    
    Button attackButton;
    Button passiveButton;
    Button chooseNewButton;
    Button ultButton;

    HashMap<String, Label> player1Label;
    HashMap<String, Label> player2Label;
    
    Entity p1Entity;
    Entity p2Entity;

    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    ImageDisplay p1EntityDisplay;
    ImageDisplay p2EntityDisplay;
    
    int turnNumber;
    
    DialogueBox dialogueBox;

    DialogueBox infoBox;

    ArrayList<Action> actionStack;
    ArrayList<AnimationTask> animationStack;

    GreenfootImage backgroundImage;

    ArrayList<ImageDisplay> statusEffectsDisplay1;
    ArrayList<ImageDisplay> statusEffectsDisplay2;
    
    private static BattleScreen instance;

    static public void newInstance(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
        instance = new BattleScreen(player1Entities, player2Entities, world);
    }

    static public BattleScreen getInstance() {
        return instance;
    }


    private BattleScreen(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
        
        setImage((GreenfootImage)null);

        this.player1Entities = player1Entities;
        this.player2Entities = player2Entities;

        this.p1Entity = player1Entities.get(0);
        this.p2Entity = player2Entities.get(0);

        this.world = world;

        actionStack = new ArrayList<Action>();
        animationStack = new ArrayList<AnimationTask>();
        statusEffectsDisplay1 = new ArrayList<ImageDisplay>();
        statusEffectsDisplay2 = new ArrayList<ImageDisplay>();

        setEntityLocations();
        
        initButtons();
        initEntityDisplay();

        initBackground();

        initDialogueBox();
        initInfoBox();
        drawStatsBars();
        turnNumber = 1;
        
    }



    public void act()
    {
        if(player1Entities.size() > 0 && player2Entities.size() > 0)
        {
            playerAction();
            
        } else {
            gameOver();
        }
    }

    private void gameOver()
    {
        if(player1Entities.size() == 0)
        {
            // Player 2 wins
            System.out.println("Player 2 wins!");
        } else if(player2Entities.size() == 0) {
            // Player 1 wins
            System.out.println("Player 1 wins!");
        }
        for (ImageDisplay display : statusEffectsDisplay1) {
            world.removeObject(display);
        }
        for (ImageDisplay display : statusEffectsDisplay2) {
            world.removeObject(display);
        }
        world.removeObject(attackButton);
        world.removeObject(passiveButton);
        world.removeObject(chooseNewButton);
        world.removeObject(ultButton);
        world.removeObject(p1EntityDisplay);
        world.removeObject(p2EntityDisplay);
        world.removeObject(dialogueBox);
        world.currentState = States.CHOOSING;
        world.screenCreated = false;
        world.removeObject(this);

    }

    private void playerAction()
    {
        p1Entity = player1Entities.get(0);
        p2Entity = player2Entities.get(0);
        

        int turn = 2 - turnNumber % 2;

        updateEntityImage();
        runAnimation();

        if (actionStack.size() > 0) {
            nextAction();
            return;
        }

        buttonActionLogic(turn);
    }

    public Entity getCurrentEntity() {
        return turnNumber % 2 == 1 ? p1Entity : p2Entity;
    }

    public Entity getOpponentEntity() {
        return turnNumber % 2 == 0 ? p1Entity : p2Entity;
    }

    public ImageDisplay getCurrentEntityDisplay() {
        return turnNumber % 2 == 1 ? p1EntityDisplay : p2EntityDisplay;
    }

    public ImageDisplay getOpponentEntityDisplay() {
        return turnNumber % 2 == 0 ? p1EntityDisplay : p2EntityDisplay;
    }

    public ArrayList<Entity> getCurrentEntities() {
        return turnNumber % 2 == 1 ? player1Entities : player2Entities;
    }

    public ArrayList<Entity> getOpponentEntities() {
        return turnNumber % 2 == 0 ? player1Entities : player2Entities;
    }

    private void nextAction() {
        dialogueBox.show();
        infoBox.hide();
        hideButtons();

        if (isHovering()) {
            displayHoverText();
            return;
        }

        Action action = actionStack.get(0);

        if (!action.isCompleted && action.func != null) {
            action.func.run();
        }

        if (action.text.isEmpty()) {
            actionStack.remove(0);
            return;
        }

        dialogueBox.setText(action.text);

        if (Greenfoot.mouseClicked(null)) {
            actionStack.remove(0);
        }
    }

    private void runAnimation() {
        
        if (animationStack.size() <= 0) {
            return;
        }

        for (int i = animationStack.size() - 1; i >= 0; i --) {

            AnimationTask task = animationStack.get(i);

            if (task.executionTime == MyWorld.worldTime && task.func != null) {
                task.func.run();
                animationStack.remove(i);

                updateAllVisuals();

            } else if (task.executionTime < MyWorld.worldTime && task.func != null) {
                task.func.run();
                animationStack.remove(i);
                // System.out.println("Warning: this animation was skipped");

                updateAllVisuals();
            }
        }

    }

    private void buttonActionLogic(int turn) {
        dialogueBox.hide();
        infoBox.show();

        showPlayerButtons();
        

        if (attackButton.getImage() != null)
        {
            attackButton.getImage().setTransparency(255);
            passiveButton.getImage().setTransparency(255);
            chooseNewButton.getImage().setTransparency(255);
            ultButton.getImage().setTransparency(255);
        }
        
        if (isHovering()) {
            displayHoverText();
        } else {
            infoBox.setText("Player "+turn+"'s turn");
        }

        if (getCurrentEntity().stunDuration > 0) {
            getCurrentEntity().stunDuration --;
            actionStack.add(new Action(getCurrentEntity().name + " is stunned.", () -> endAction()));
        }


        if (attackButton.isPressed) {
            attackButton.isPressed = false;
            getCurrentEntity().attack(getOpponentEntity());
            attachToActions(() -> endAction());

        } else if (passiveButton.isPressed) {
            passiveButton.isPressed = false;
            // don't ever use 'passive' like getCurrentEntity().passive(getOpponentEntity());
            if (getCurrentEntity().passiveCooldownLeft > 0) {
                return;
            }
            getCurrentEntity().applyPassive(getOpponentEntity());
            attachToActions(() -> endAction());

        } else if (chooseNewButton.isPressed) {
            chooseNewButton.isPressed = false;
            //show player list
            //allow for player to click and swap spirits
            //spirits keep stats upon swaping
            
        } else if (ultButton.isPressed) {
            ultButton.isPressed = false;
            if (getCurrentEntity() instanceof Soul soul && !soul.ultimateUsed) {
                
                soul.applyUltimate(getCurrentEntities(), getOpponentEntities());
                attachToActions(() -> endAction());
            }
        }
    }

    
    private void endAction()
    {
        int previousStackSize = actionStack.size();

        p1Entity.applyStatusEffects();
        p2Entity.applyStatusEffects();

        if (actionStack.size() > previousStackSize) {

            attachToActions(() -> { 
                checkIfFainted(); 
                turnNumber++; 
            });
        } else {

            checkIfFainted(); 
            turnNumber++;
        }
    }
    // attaches a func to last action without creates a new one
    private void attachToActions(Runnable func) {
        if (actionStack.size() > 0) {
            Action modifiedAction = new Action(actionStack.getLast(), () -> func.run());
            actionStack.set(actionStack.size()-1, modifiedAction);
        } else {
            func.run();
        }
    }
    
    private void checkIfFainted()
    {
        if (p1Entity.health <= 0)
        {
            // System.out.println("hello");
            actionStack.add(new Action(
                p1Entity.name + " has fainted!",
                () -> {
                    player1Entities.remove(0); 
                    updateEntityImage();
                    if (player1Entities.size() == 0) {
                        gameOver();
                    } else {
                        p1Entity = player1Entities.get(0);
                        checkIfFainted();
                    }
                    
                }
            ));
        }
        if (p2Entity.health <= 0)
        {
            actionStack.add(new Action(
                p2Entity.name + " has fainted!",
                
                () -> {
                    player2Entities.remove(0);
                    updateEntityImage();
                    if (player2Entities.size() == 0) {
                        gameOver();
                    } else {
                        p2Entity = player2Entities.get(0);
                        checkIfFainted();
                    }
                    
                }
            ));
            
        }
    }

    private boolean isHovering() {
        return attackButton.isHovering || passiveButton.isHovering || chooseNewButton.isHovering || ultButton.isHovering;
    }

    private void displayHoverText() {

        
        if (attackButton.isHovering) {
            infoBox.setText(getCurrentEntity().getAttackDetails());
            if (attackButton.getImage() != null)
            {
                attackButton.getImage().setTransparency(200);
            }
        } else if (passiveButton.isHovering) {
            infoBox.setText(getCurrentEntity().getPassiveDetails());
            if (passiveButton.getImage() != null)
            {
                passiveButton.getImage().setTransparency(200);
            }
        } else if (chooseNewButton.isHovering) {
            if (chooseNewButton.getImage() != null)
            {
                chooseNewButton.getImage().setTransparency(200);
            }
        } else if (ultButton.isHovering) {
            infoBox.setText(getCurrentEntity().getUltimateDetails());
            if (ultButton.getImage() != null)
            {
                passiveButton.getImage().setTransparency(200);
            }
        }
    }

    public void updateAllVisuals() {
        updateEntityImage();
        drawStatsBars();
        updateStatusEffectDisplay();
    }

    private void initInfoBox() {
        infoBox = new DialogueBox(220, 110, Color.WHITE, " ", Color.BLACK, new Font(14));
        world.addObject(infoBox, 300, 340);
    }
    
    private void showPlayerButtons()
    {
        int num = 2 - turnNumber % 2;

        GreenfootImage attackImage = new GreenfootImage("attack_P" + num + ".png");
        GreenfootImage passiveImage = new GreenfootImage("passive_P" + num + ".png");
        GreenfootImage chooseNewImage = new GreenfootImage("chooseNew_P" + num + ".png");
        GreenfootImage ultImage = new GreenfootImage("ultimate_P" + num + ".png");
        int scaleX = 180;
        int scaleY = 60;

        attackImage.scale(scaleX,scaleY);
        passiveImage.scale(scaleX,scaleY);
        chooseNewImage.scale(scaleX,scaleY);
        ultImage.scale(scaleX,scaleY);

        attackButton.setImage(attackImage);
        passiveButton.setImage(passiveImage);
        chooseNewButton.setImage(chooseNewImage);
        ultButton.setImage(ultImage);

    }

    private void initButtons()
    {

        attackButton = new Button(null, 20);
        passiveButton = new Button(null, 20);
        chooseNewButton = new Button(null, 20);
        ultButton = new Button(null, 20);


        world.addObject(attackButton, 90, MyWorld.HEIGHT-90);
        world.addObject(passiveButton, MyWorld.WIDTH-90, MyWorld.HEIGHT-90);
        world.addObject(chooseNewButton, 90, MyWorld.HEIGHT-30);
        world.addObject(ultButton, MyWorld.WIDTH-90, MyWorld.HEIGHT-30);

    }

    private void hideButtons() {
        attackButton.setImage((GreenfootImage)null);
        passiveButton.setImage((GreenfootImage)null);
        chooseNewButton.setImage((GreenfootImage)null);
        ultButton.setImage((GreenfootImage)null);
    }

    private void initEntityDisplay() {
        p1EntityDisplay = new ImageDisplay();
        p2EntityDisplay = new ImageDisplay();
        
        world.addObject(p1EntityDisplay, MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        world.addObject(p2EntityDisplay, MyWorld.WIDTH/7 * 6, MyWorld.HEIGHT/5);

        p1Entity.setLocation(MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        p2Entity.setLocation(MyWorld.WIDTH/7 * 6, MyWorld.HEIGHT/5);
    }

    private void updateEntityImage()
    {
        
        int scaleX = 100;
        int scaleY = 100;

        // create new copy
        GreenfootImage p1Image = new GreenfootImage(p1Entity.image);
        GreenfootImage p2Image = new GreenfootImage(p2Entity.image);

        

        p1Image.scale(scaleX, scaleY);
        p2Image.scale(scaleX, scaleY);

        p1EntityDisplay.setImage(p1Image);
        p2EntityDisplay.setImage(p2Image);

        p1EntityDisplay.setLocation(p1Entity.x, p1Entity.y);
        p2EntityDisplay.setLocation(p2Entity.x, p2Entity.y);

    }

    private void initDialogueBox()
    {
        dialogueBox = new DialogueBox(220, 110, Color.WHITE, "> Game Starts!", Color.BLACK, new Font(15));
        world.addObject(dialogueBox, 300, 340);
        dialogueBox.setText("Player 1's turn");
    }

    private void initBackground()
    {
        int i = Greenfoot.getRandomNumber(4);
        backgroundImage = new GreenfootImage("background"+(i+1)+".png");
        backgroundImage.scale(600, 400);

        int borderWidth = 2;
        backgroundImage.setColor(Color.BLACK);
        backgroundImage.fillRect(190 - borderWidth, 285 - borderWidth, 220 + 2 * borderWidth, 110 + 2 * borderWidth);
        world.setBackground(new GreenfootImage(backgroundImage));
        
    }

    private void drawStatsBars()
    {
        if (player1Entities.size() == 0 || player2Entities.size() == 0) {
            return;
        }
        
        int borderWidth = 2;
        int health1 = player1Entities.get(0).health;
        int health2 = player2Entities.get(0).health;

        GreenfootImage tempBackground = new GreenfootImage(backgroundImage);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(20 - borderWidth, 150 - borderWidth, health1 + 2 * borderWidth, 15 + 2 * borderWidth);
        tempBackground.setColor(Color.GREEN);
        tempBackground.fillRect(20, 150, health1, 15);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(450 - borderWidth, 150 - borderWidth, health2 + 2 * borderWidth, 15 + 2 * borderWidth);
        tempBackground.setColor(Color.GREEN);
        tempBackground.fillRect(450, 150, health2, 15);

        int attack1 = player1Entities.get(0).attack;
        int attack2 = player2Entities.get(0).attack;

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(20 - borderWidth, 180 - borderWidth, attack1 + 2 * borderWidth, 15 + 2 * borderWidth);
        tempBackground.setColor(Color.RED);
        tempBackground.fillRect(20, 180, attack1, 15);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(450 - borderWidth, 180 - borderWidth, attack2 + 2 * borderWidth, 15 + 2 * borderWidth);
        tempBackground.setColor(Color.RED);
        tempBackground.fillRect(450, 180, attack2, 15);

        world.setBackground(tempBackground);

        world.setBackground(tempBackground);
    }

    private void updateStatusEffectDisplay() {
        if (p1Entity == null && p2Entity == null) {
            return;
        }

        for (ImageDisplay display : statusEffectsDisplay1) {
            world.removeObject(display);
        }

        for (ImageDisplay display : statusEffectsDisplay2) {
            world.removeObject(display);
        }

        statusEffectsDisplay1.clear();
        statusEffectsDisplay2.clear();

        int gap = 40;

        int x1 = 30;
        int x2 = 460;

        int y = 220;

        if (p1Entity.burningDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("burning.png"));
            statusEffectsDisplay1.add(image);
            world.addObject(image, x1, y);
            x1 += gap;

        }
        if (p2Entity.burningDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("burning.png"));
            statusEffectsDisplay2.add(image);
            world.addObject(image, x2, y);
            x2 += gap;

        }
        if (p1Entity.poisonedDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("poisoned.png"));
            statusEffectsDisplay1.add(image);
            world.addObject(image, x1, y);
            x1 += gap;
        }
        if (p2Entity.poisonedDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("poisoned.png"));
            statusEffectsDisplay2.add(image);
            world.addObject(image, x2, y);
            x2 += gap;
        }
        if (p1Entity.healingDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("healing.png"));
            statusEffectsDisplay1.add(image);
            world.addObject(image, x1, y);
            x1 += gap;        
        }
        if (p2Entity.healingDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("healing.png"));
            statusEffectsDisplay2.add(image);
            world.addObject(image, x2, y);
            x2 += gap;          
        }
    }

    private void setEntityLocations() {
        for (Entity entity : player1Entities) {
            entity.setLocation(MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        }

        for (Entity entity : player2Entities) {
            entity.setLocation(MyWorld.WIDTH*6/7, MyWorld.HEIGHT/5);
        }
    }

}
