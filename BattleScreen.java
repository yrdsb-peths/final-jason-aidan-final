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

    static final int SWAP_COOLDOWN = 2;
    
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

    Boolean isSwapping;

    Chooser swapper;

    ImageDisplay critDisplay;
    ImageDisplay missDisplay;
    
    int p1SwapCooldownLeft;
    int p2SwapCooldownLeft;
    
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

        this.isSwapping = false;

        actionStack = new ArrayList<Action>();
        animationStack = new ArrayList<AnimationTask>();
        statusEffectsDisplay1 = new ArrayList<ImageDisplay>();
        statusEffectsDisplay2 = new ArrayList<ImageDisplay>();
        
        p1SwapCooldownLeft = 0;
        p2SwapCooldownLeft = 0;

        setEntityLocations();
        
        initButtons();
        initEntityDisplay();

        initBackground();

        initDialogueBox();
        initInfoBox();
        drawStatsBars();

        
        turnNumber = 1;
        
        initCritImage();
        initMissImage();
        
    }



    public void act()
    {
        if(player1Entities.size() > 0 && player2Entities.size() > 0)
        {
            playerAction();
            
        }
        else 
        {
            nextAction();
        }
    }



    private void playerAction()
    {
        p1Entity = player1Entities.get(0);
        p2Entity = player2Entities.get(0);

        int turn = 2 - turnNumber % 2;

        updateEntityImage();

        if (isSwapping) {
            swappingLogic();
            return;
        }

        runAnimation();

        if (actionStack.size() > 0) {
            nextAction();
            return;
        }

        buttonActionLogic(turn);
    }

    private void swappingLogic() {
        infoBox.hide();
        dialogueBox.hide();
        hideButtons();
        world.setBackground(new GreenfootImage(backgroundImage));
        removeStatusEffectDisplay();
        p1EntityDisplay.setImage((GreenfootImage)null);
        p2EntityDisplay.setImage((GreenfootImage)null);

        if (turnNumber % 2 == 0) {
            p2SwapCooldownLeft = SWAP_COOLDOWN;
        } else {
            p1SwapCooldownLeft = SWAP_COOLDOWN;
        }

        int spacing = 100;
        int scale = 80;

        if (swapper == null) {
            int length = getOpponentEntities().size();
            GreenfootImage[] costumes = new GreenfootImage[length];
            for (int i = 0; i < length; i++) {
                costumes[i] = new GreenfootImage(getOpponentEntities().get(i).image);
            }
            swapper = new Chooser(costumes, 1, spacing, 5, scale);
            world.addObject(swapper, MyWorld.WIDTH/2 - (spacing) * Math.min(5, length)/2 + 45, MyWorld.HEIGHT/2);

        }
        
        if (swapper.selectedNumber == 1) {
            int targetInd = swapper.selectedIndices.get(0);
            Entity temp = getOpponentEntity();
            getOpponentEntities().set(0, getOpponentEntities().get(targetInd));
            getOpponentEntities().set(targetInd, temp);
            swapper.remove();
            swapper = null;
            isSwapping = false;
            p1Entity = player1Entities.get(0);
            p2Entity = player2Entities.get(0);
            updateAllVisuals();

        }

        return;
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
            if (swappable() && getCurrentEntity().canSwap) {
                
                isSwapping = true;
                turnNumber++;
            }
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

        if (p1SwapCooldownLeft > 0) {
            p1SwapCooldownLeft --;
        }

        if (p2SwapCooldownLeft > 0) {
            p2SwapCooldownLeft --;
        }

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
    // attaches a func to last action without creating a new one

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
                        actionStack.add(new Action(
                            "Player 2 has won!",
                            () -> {actionStack.add(new Action("", () -> gameOver()));}
                        ));
                        
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
                        actionStack.add(new Action(
                            "Player 1 has won!",
                            () -> {actionStack.add(new Action("", () -> gameOver()));}
                        ));
                    } else {
                        p2Entity = player2Entities.get(0);
                        checkIfFainted();
                    }
                    
                }
            ));
            
        }
    }

    private void gameOver()
    {

        removeStatusEffectDisplay();

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

    private void attachToActions(Runnable func) {
        if (actionStack.size() > 0) {
            Action modifiedAction = new Action(actionStack.getLast(), () -> func.run());
            actionStack.set(actionStack.size()-1, modifiedAction);
        } else {
            func.run();
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
            if (!getCurrentEntity().canSwap) {
                infoBox.setText("This entity cannot be swapped currently.");
                return;
            }
            if (!swappable()) {
                infoBox.setText("Swapping is on cooldown.");
                return;
            }
            infoBox.setText("Swap to a new spirit. Costs a turn.");
            if (chooseNewButton.getImage() != null)
            {
                chooseNewButton.getImage().setTransparency(200);
            }
        } else if (ultButton.isHovering) {
            infoBox.setText(getCurrentEntity().getUltimateDetails());
            if (ultButton.getImage() != null)
            {
                ultButton.getImage().setTransparency(200);
            }
        }
    }

    public void updateAllVisuals() {
        if (world.currentState == States.CHOOSING) {
            return;
        }
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

        // create new copy
        GreenfootImage p1Image = new GreenfootImage(p1Entity.image);
        GreenfootImage p2Image = new GreenfootImage(p2Entity.image);

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

        
    }

    private void drawStatsBars()
    {
        if (player1Entities.size() == 0 || player2Entities.size() == 0) {
            return;
        }

        int borderWidth = 2;
        


        GreenfootImage tempBackground = new GreenfootImage(backgroundImage);

        int leftBarX = 20;
        int rightBarX = 420;

        int barHeight = 15;

        int healthbarY = 150;
        int attackbarY = 185;

        int attack1 = player1Entities.get(0).attack;
        int attack2 = player2Entities.get(0).attack;

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(leftBarX - borderWidth, attackbarY - borderWidth, attack1 + 2 * borderWidth, barHeight + 2 * borderWidth);
        tempBackground.setColor(Color.RED);
        tempBackground.fillRect(leftBarX, attackbarY, attack1, 15);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(rightBarX - borderWidth, attackbarY - borderWidth, attack2 + 2 * borderWidth, barHeight + 2 * borderWidth);
        tempBackground.setColor(Color.RED);
        tempBackground.fillRect(rightBarX, attackbarY, attack2, 15);

        int defense1 = player1Entities.get(0).defense;
        int defense2 = player2Entities.get(0).defense;

        Color defenseColor1 = Color.GRAY;
        Color defenseColor2 = Color.GRAY;

        int height = 8;

        if (defense1 < 0) {
            defenseColor1 = Color.BLACK;
            defense1 = -defense1;
        }

        if (defense2 < 0) {
            defenseColor2 = Color.BLACK;
            defense2 = -defense2;
        }

        if (defense1 > 0) {

            tempBackground.setColor(Color.BLACK);
            tempBackground.fillRect(leftBarX - borderWidth, healthbarY + barHeight/2 + height - borderWidth, 2 * defense1 + 2 * borderWidth, height + 2 * borderWidth);
            tempBackground.setColor(defenseColor1);
            tempBackground.fillRect(leftBarX, healthbarY + barHeight/2 + height, 2 * defense1, height);
        }

        if (defense2 > 0) {

            tempBackground.setColor(Color.BLACK);
            tempBackground.fillRect(rightBarX - borderWidth, healthbarY + barHeight/2 + height - borderWidth, 2 * defense2 + 2 * borderWidth, height + 2 * borderWidth);
            tempBackground.setColor(defenseColor2);
            tempBackground.fillRect(rightBarX, healthbarY + barHeight/2 + height, 2 * defense2, height);
        }

        int health1 = player1Entities.get(0).health;
        int health2 = player2Entities.get(0).health;



        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(leftBarX - borderWidth, healthbarY - borderWidth, health1 + 2 * borderWidth, barHeight + 2 * borderWidth);
        tempBackground.setColor(Color.GREEN);
        tempBackground.fillRect(leftBarX, healthbarY, health1, 15);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(rightBarX - borderWidth, healthbarY - borderWidth, health2 + 2 * borderWidth, barHeight + 2 * borderWidth);
        tempBackground.setColor(Color.GREEN);
        tempBackground.fillRect(rightBarX, healthbarY, health2, 15);

        tempBackground.setColor(Color.BLACK);
        tempBackground.fillRect(190 - borderWidth, 285 - borderWidth, 220 + 2 * borderWidth, 110 + 2 * borderWidth);

        world.setBackground(tempBackground);

    }

    private void updateStatusEffectDisplay() {
        if (p1Entity == null || p2Entity == null) {
            return;
        }

        removeStatusEffectDisplay();

        int gap = 0;
        int sc = 100;

        int x1 = p1Entity.x;
        int x2 = p2Entity.x;

        int y = p1Entity.y;

        if (p1Entity.burningDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("burning.png"));
            image.getImage().scale(sc,sc);
            statusEffectsDisplay1.add(image);
            world.addObject(image, x1, y);
            x1 += gap;

        }
        if (p2Entity.burningDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("burning.png"));
            image.getImage().scale(sc,sc);
            statusEffectsDisplay2.add(image);
            world.addObject(image, x2, y);
            x2 += gap;

        }
        if (p1Entity.poisonedDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("poisoned.png"));
            image.getImage().scale(sc,sc);
            statusEffectsDisplay1.add(image);
            world.addObject(image, x1, y);
            x1 += gap;
        }
        if (p2Entity.poisonedDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("poisoned.png"));
            image.getImage().scale(sc,sc);
            statusEffectsDisplay2.add(image);
            world.addObject(image, x2, y);
            x2 += gap;
        }
        if (p1Entity.healingDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("healing.png"));
            image.getImage().scale(sc,sc);
            statusEffectsDisplay1.add(image);
            world.addObject(image, x1, y);
            x1 += gap;        
        }
        if (p2Entity.healingDuration > 0) {
            ImageDisplay image = new ImageDisplay(new GreenfootImage("healing.png"));
            image.getImage().scale(sc,sc);
            statusEffectsDisplay2.add(image);
            world.addObject(image, x2, y);
            x2 += gap;          
        }
    }

    private void removeStatusEffectDisplay() {
        for (ImageDisplay display : statusEffectsDisplay1) {
            world.removeObject(display);
        }

        for (ImageDisplay display : statusEffectsDisplay2) {
            world.removeObject(display);
        }

        statusEffectsDisplay1.clear();
        statusEffectsDisplay2.clear();
    }

    public void setEntityLocations() {
        for (Entity entity : player1Entities) {
            entity.setLocation(MyWorld.WIDTH/7, MyWorld.HEIGHT/5);
        }

        for (Entity entity : player2Entities) {
            entity.setLocation(MyWorld.WIDTH*6/7, MyWorld.HEIGHT/5);
        }
    }

    private boolean swappable() {
        return (turnNumber % 2 == 1 ? p2SwapCooldownLeft : p1SwapCooldownLeft) == 0;
    }

    public Entity getCurrentEntity() {
        return turnNumber % 2 == 1 ? p1Entity : p2Entity;
    }

    public Entity getOpponentEntity() {
        return turnNumber % 2 == 0 ? p1Entity : p2Entity;
    }

    public ArrayList<Entity> getCurrentEntities() {
        return turnNumber % 2 == 1 ? player1Entities : player2Entities;
    }

    public ArrayList<Entity> getOpponentEntities() {
        return turnNumber % 2 == 0 ? player1Entities : player2Entities;
    }

    public void initCritImage(){
        critDisplay = new ImageDisplay(new GreenfootImage("criticalHit.png"));
        int critScale = 100;
        critDisplay.getImage().scale(critScale, critScale);
        critDisplay.getImage().setTransparency(0);
        world.addObject(critDisplay,0,0);
    }
    
    public void initMissImage(){
        missDisplay = new ImageDisplay(new GreenfootImage("miss.png"));
        int missScale = 100;
        missDisplay.getImage().scale(missScale, missScale);
        missDisplay.getImage().setTransparency(0);
        world.addObject(missDisplay,0,0);
    }
}
