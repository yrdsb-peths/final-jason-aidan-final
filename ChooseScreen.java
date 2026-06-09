import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ChoseScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class ChooseScreen extends Actor
{

    MyWorld world;
    
    Button[] player1Displays;
    Button[] player2Displays;

    ArrayList<Entity> player1Entities;
    ArrayList<Entity> player2Entities;

    Chooser chooser1;
    Chooser chooser2;

    Button submitButton;

    int state;

    int player1points;
    int player2points;

    Label player1pointsLabel;
    Label player2pointsLabel;
    
    GreenfootSound lobbyMusic;


    /**
     * Act - do whatever the ChoseScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public ChooseScreen(ArrayList<Entity> player1Entities, ArrayList<Entity> player2Entities, MyWorld world) {
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
        
        chooser1 = createSpiritChooser(45, 50, 60);
        chooser2 = createSpiritChooser(375, 50, 60);

        player1points = MyWorld.POINTS_PER_PLAYER;
        player2points = MyWorld.POINTS_PER_PLAYER;
        
        lobbyMusic = new GreenfootSound("lobby-start-M.wav");
        lobbyMusic.play();
    }   

    public void act()
    {
        if(!lobbyMusic.isPlaying())
        {
            lobbyMusic = new GreenfootSound("lobby-loop-M.wav");
            lobbyMusic.playLoop();
        }
        
        if (this.state == 0) {
            chooseSpirit(chooser1, chooser2);
            updateDisplay();

            boolean finishedChoosing = player1Entities.size() == MyWorld.MAX_ENTITIES && player2Entities.size() == MyWorld.MAX_ENTITIES;


            if (finishedChoosing && submitButton.isPressed) {
                initPointsLabel();
                this.state = 1;
                submitButton.isPressed = false;
                submitButton.setImage("submit_button.png");
                submitButton.getImage().scale(150, 150);
            }

        } else if (this.state == 1) {

            checkLevelUp();

            if (submitButton.isPressed) {
                for (Entity entity : player1Entities) {
                    entity.fixLevel();
                }
                for (Entity entity : player2Entities) {
                    entity.fixLevel();
                }
                chooser1.remove();
                chooser2.remove();
                submitButton.remove();
                removeDisplays();
                world.currentState = States.BATTLE;
                world.screenCreated = false;
                world.removeObject(player1pointsLabel);
                world.removeObject(player2pointsLabel);
                world.removeObject(this);
                lobbyMusic.stop();
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

    public void chooseSpirit(Chooser chooser1, Chooser chooser2) { 

        if (chooser1 == null || chooser2 == null) {
            System.out.println("Error creating chooser");
            return;
        }

        if (chooser1.selectedIndices == null || chooser2.selectedIndices == null) {
            System.out.println("Error: selectedIndices is null");
            return;
        }

        player1Entities.clear();
        player2Entities.clear();
        
        for (int index : chooser1.selectedIndices) {
            try {
                player1Entities.add(Spirit.spiritTypes.get(index).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (int index : chooser2.selectedIndices) {
            try {
                player2Entities.add(Spirit.spiritTypes.get(index).getDeclaredConstructor().newInstance());
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
        // Code to display the player's entitys on the screen

        try{
            GreenfootImage[] costumeList = new GreenfootImage[Spirit.spiritTypes.size()];
            int i = 0;
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {

                Spirit spirit = spiritClass.getDeclaredConstructor().newInstance();
                costumeList[i] = new GreenfootImage(spirit.image);
                costumeList[i].scale(50, 50);
                i++;
            }

            Chooser chooser = new Chooser(costumeList, MyWorld.MAX_ENTITIES, spacing, 4);

            // chooser.switches[0].status 

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
        // player1pointsLabel.setLineColor(Color.WHITE);
        // player2pointsLabel.setLineColor(Color.WHITE);
        player1pointsLabel.setLineColor((Color)null);
        player2pointsLabel.setLineColor((Color)null);
        world.addObject(player1pointsLabel, 100, MyWorld.HEIGHT-50);
        world.addObject(player2pointsLabel, MyWorld.WIDTH-110, MyWorld.HEIGHT-50);
    }

    private void updatePointsLabel() {
        player1pointsLabel.setValue("Upgrade points: " + player1points);
        player2pointsLabel.setValue("Upgrade points: " + player2points);
    }

    public void checkLevelUp() {
        Button[] displays;
        int points;
        ArrayList<Entity> entities;

        for (int j = 0; j < 2; j++ ) {
            if (j == 0) {
                displays = player1Displays;
                points = player1points;
                entities = player1Entities;
            } else {
                displays = player2Displays;
                points = player2points;
                entities = player2Entities;
            }

            for (int i = 0; i < displays.length; i++) {
                if (displays[i].isPressed) {

                    if (points == 0) { continue; }

                    // primitives aren't pass by copy
                    if (j == 0) {
                        player1points --;
                    } else {
                        player2points --;
                    }

                    entities.get(i).levelUp();

                    if (entities.get(i).level > 5 && entities.get(i) instanceof Spirit level1) {
                        Soul upgrade = level1.getUpgraded();
                        if (upgrade != null) {
                            entities.set(i, upgrade); 
                        }
                        
                    }
                    updatePointsLabel();
                    updateDisplay();
                    displays[i].isPressed = false;
                }
            }
        }
    }

    
}
