import greenfoot.*;  // Requires Actor, World, GreenfootImage, Greenfoot, Color, etc.
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

public class ChooseScreenAI extends Actor
{
    MyWorld world;
    
    Button[] player1Displays;
    Button[] player2Displays;

    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    Chooser chooser1; // Removed chooser2 as the AI doesn't need physical controls

    Button submitButton;

    int state;

    int player1points;
    int player2points;

    Label player1pointsLabel;
    Label player2pointsLabel;

    static int MAX_CHOOSE_POINTS = 20;

    public ChooseScreenAI(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
        this.player1Entities = player1Entities;
        this.player2Entities = player2Entities;
        
        this.world = world;
        this.state = 0;

        setImage((GreenfootImage)null);

        GreenfootImage image = new GreenfootImage("lvlUp.png");
        image.scale(150,150);
        submitButton = new Button(image, 20);
        world.addObject(submitButton, MyWorld.WIDTH/2, MyWorld.HEIGHT/4 * 3);

        initSelectedDisplay();
        
        // Player 1 uses physical chooser controls
        chooser1 = createSpiritChooser(45, 50, 60);

        player1points = MAX_CHOOSE_POINTS;
        player2points = MAX_CHOOSE_POINTS;

        // Draft the AI's team automatically upon creation
        generateAIEntities();
        updateDisplay();
    }   

    public void act()
    {
        if (this.state == 0) {
            chooseSpirit(chooser1);
            updateDisplay();

            // AI's team size is already MAX_ENTITIES, so we only wait for the human player
            boolean finishedChoosing = player1Entities.size() == MyWorld.MAX_ENTITIES;

            if (finishedChoosing && submitButton.isPressed) {
                initPointsLabel();
                this.state = 1;
                submitButton.isPressed = false;
                submitButton.setImage("submit_button.png");
                submitButton.getImage().scale(150, 150);
                
                // AI automatically spends all its points instantly as we enter State 1
                aiLevelUp();
            }

        } else if (this.state == 1) {

            // Only handles manual player clicks
            checkLevelUp();

            if (submitButton.isPressed) {
                for (Entity entity : player1Entities) {
                    entity.fixLevel();
                }
                for (Entity entity : player2Entities) {
                    entity.fixLevel();
                }
                chooser1.remove();
                submitButton.remove();
                removeDisplays();
                world.currentState = States.BATTLE;
                world.screenCreated = false;
                world.removeObject(player1pointsLabel);
                world.removeObject(player2pointsLabel);
                world.removeObject(this);
            }
        }
    }

    public void initSelectedDisplay() {
        player1Displays = new Button[MyWorld.MAX_ENTITIES];
        player2Displays = new Button[MyWorld.MAX_ENTITIES];

        for (int i = 0; i < MyWorld.MAX_ENTITIES; i++) {
            player1Displays[i] = new Button(null, 10);
            world.addObject(player1Displays[i], 20 + 40 * i, 300);
        
            player2Displays[i] = new Button(null, 10);
            world.addObject(player2Displays[i], 420 + 40 * i, 300);
        }
    }

    /**
     * AI team generation logic. Selects a random lineup from all available spirit classes.
     */
    private void generateAIEntities() {
        player2Entities.clear();
        int spiritCount = Spirit.spiritTypes.size();
        if (spiritCount == 0) return;

        for (int i = 0; i < MyWorld.MAX_ENTITIES; i++) {
            int randomIndex = Greenfoot.getRandomNumber(spiritCount);
            try {
                Class<? extends Spirit> spiritClass = Spirit.spiritTypes.get(randomIndex);
                player2Entities.add(spiritClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Standard human selection logic mapped from the physical chooser UI.
     */
    public void chooseSpirit(Chooser chooser1) { 
        if (chooser1 == null) {
            System.out.println("Error creating chooser");
            return;
        }

        if (chooser1.selectedIndices == null) {
            System.out.println("Error: selectedIndices is null");
            return;
        }

        player1Entities.clear();
        
        for (int index : chooser1.selectedIndices) {
            try {
                player1Entities.add(Spirit.spiritTypes.get(index).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDisplay() {
        for (int i = 0; i < MyWorld.MAX_ENTITIES; i++) {
            if (player1Displays[i] == null || player2Displays[i] == null) {
                continue;
            }

            if (i < player1Entities.size()) {
                GreenfootImage image = new GreenfootImage(player1Entities.get(i).image);
                int size = player1Entities.get(i).level;
                image.scale(size + 40, size + 40);
                player1Displays[i].setImage(image);
            } else {
                player1Displays[i].setImage((GreenfootImage)null);
            }

            if (i < player2Entities.size()) {
                GreenfootImage image = new GreenfootImage(player2Entities.get(i).image);
                int size = player2Entities.get(i).level;
                image.scale(size + 40, size + 40);
                player2Displays[i].setImage(image);
            } else {
                player2Displays[i].setImage((GreenfootImage)null);
            }
        }
    }

    public Chooser createSpiritChooser(int x, int y, int spacing){
        try {
            GreenfootImage[] costumeList = new GreenfootImage[Spirit.spiritTypes.size()];
            int i = 0;
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {
                Spirit spirit = spiritClass.getDeclaredConstructor().newInstance();
                costumeList[i] = new GreenfootImage(spirit.image);
                costumeList[i].scale(50, 50);
                i++;
            }

            Chooser chooser = new Chooser(costumeList, MyWorld.MAX_ENTITIES, spacing, 4);
            world.addObject(chooser, x, y);
            return chooser;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeDisplays() {
        for (int i = 0; i < MyWorld.MAX_ENTITIES; i++) {
            world.removeObject(player1Displays[i]);
            world.removeObject(player2Displays[i]);
        }
    }

    private void initPointsLabel() {
        player1pointsLabel = new Label("Upgrade points: " + player1points, 20);
        player2pointsLabel = new Label("Upgrade points: " + player2points, 20);
        player1pointsLabel.setLineColor((Color)null);
        player2pointsLabel.setLineColor((Color)null);
        world.addObject(player1pointsLabel, 100, MyWorld.HEIGHT-50);
        world.addObject(player2pointsLabel, MyWorld.WIDTH-110, MyWorld.HEIGHT-50);
    }

    private void updatePointsLabel() {
        player1pointsLabel.setValue("Upgrade points: " + player1points);
        player2pointsLabel.setValue("Upgrade points: " + player2points);
    }

    /**
     * Handles manual leveling up when Player 1 clicks on their team icons.
     */
    public void checkLevelUp() {
        for (int i = 0; i < player1Displays.length; i++) {
            if (player1Displays[i].isPressed) {

                if (player1points == 0) { 
                    player1Displays[i].isPressed = false;
                    continue; 
                }

                player1points--;
                player1Entities.get(i).levelUp();

                if (player1Entities.get(i).level > 5 && player1Entities.get(i) instanceof Spirit level1) {
                    Soul upgrade = level1.getUpgraded();
                    if (upgrade != null) {
                        player1Entities.set(i, upgrade); 
                    }
                }
                updatePointsLabel();
                updateDisplay();
                player1Displays[i].isPressed = false;
            }
        }
    }

    /**
     * AI level up logic. Automatically distributes all MAX_CHOOSE_POINTS randomly 
     * among its drafted entities.
     */
    private void aiLevelUp() {
        int spiritCount = player2Entities.size();
        if (spiritCount == 0) return;

        while (player2points > 0) {
            int randomIndex = Greenfoot.getRandomNumber(spiritCount);
            Entity entity = player2Entities.get(randomIndex);
            
            entity.levelUp();

            if (entity.level > 5 && entity instanceof Spirit level1) {
                Soul upgrade = level1.getUpgraded();
                if (upgrade != null) {
                    player2Entities.set(randomIndex, upgrade); 
                }
            }
            player2points--;
        }
        updatePointsLabel();
        updateDisplay();
    }
}