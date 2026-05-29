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

    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    Chooser chooser1;
    Chooser chooser2;

    Button submitButton;

    int state;

    int player1points;
    int player2points;

    static int MAX_CHOOSE_POINTS = 30;


    /**
     * Act - do whatever the ChoseScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public ChooseScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        this.player1Spirits = player1Spirits;
        this.player2Spirits = player2Spirits;
        
        this.world = world;
        this.state = 0;

        setImage((GreenfootImage)null);

        GreenfootImage image = new GreenfootImage("submit_button.png");
        image.scale(150,150);
        submitButton = new Button(image, 20);
        world.addObject(submitButton, MyWorld.WIDTH/2, MyWorld.HEIGHT/4 * 3);

        initSelectedDisplay();
        
        chooser1 = createSpiritChooser(45, 50, 60);
        chooser2 = createSpiritChooser(375, 50, 60);

        player1points = MAX_CHOOSE_POINTS;
        player2points = MAX_CHOOSE_POINTS;
    }   

    public void act()
    {

        if (this.state == 0) {
            chooseSpirit(chooser1, chooser2);
            updateDisplay();

            boolean finishedChoosing = player1Spirits.size() == MyWorld.maxSpirits && player2Spirits.size() == MyWorld.maxSpirits;


            if (finishedChoosing && submitButton.isPressed) {
                this.state = 1;
                submitButton.isPressed = false;
            }

        } else if (this.state == 1) {
            for (int i = 0; i < player1Displays.length; i++) {
                if (player1Displays[i].isPressed) {
                    System.out.println(i);
                    if (player1points > 0) {
                        player1points --;
                        player1Spirits.get(i).levelUp();
                        updateDisplay();
                    }
                    player1Displays[i].isPressed = false;
                }
            }

            for (int i = 0; i < player2Displays.length; i++) {
                if (player2Displays[i].isPressed) {
                    if (player2points > 0) {
                        player2points --;
                        player2Spirits.get(i).levelUp();
                        updateDisplay();
                    }
                    player2Displays[i].isPressed = false;
                }
            }

            if (submitButton.isPressed) {
                for (Spirit spirit : player1Spirits) {
                    spirit.fixLevel();
                }
                for (Spirit spirit : player2Spirits) {
                    spirit.fixLevel();
                }
                chooser1.remove();
                chooser2.remove();
                submitButton.remove();
                removeDisplays();
                world.currentState = States.BATTLE;
                world.screenCreated = false;
                world.removeObject(this);
            }
            
        }
    }

    public void initSelectedDisplay() {

        player1Displays = new Button[MyWorld.maxSpirits];
        player2Displays = new Button[MyWorld.maxSpirits];

        for (int i = 0; i < MyWorld.maxSpirits; i++) {
            
            player1Displays[i] = new Button(null, 10);
            world.addObject(player1Displays[i], 20 + 40 * i, 300);
        
        
            player2Displays[i] = new Button(null, 10);
            world.addObject(player2Displays[i], 420 + 40 * i, 300);
            
        }
    }

    public void chooseSpirit(Chooser chooser1, Chooser chooser2) { 

        if (chooser1 == null) {
            System.out.println("Error creating chooser");
            return;
        }

        if (chooser2 == null) {
            System.out.println("Error creating chooser");
            return;
        }

        if (chooser1.selectedIndices == null || chooser2.selectedIndices == null) {
            System.out.println("Error: selectedIndices is null");
            return;
        }

        player1Spirits.clear();
        player2Spirits.clear();
        
        for (int index : chooser1.selectedIndices) {
            try {
                player1Spirits.add(Spirit.spiritTypes.get(index).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (int index : chooser2.selectedIndices) {
            try {
                player2Spirits.add(Spirit.spiritTypes.get(index).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateDisplay() {
        
        for (int i = 0; i < MyWorld.maxSpirits; i++) {
            if (player1Displays[i] == null || player2Displays[i] == null) {
                continue;
            }

            if (i < player1Spirits.size()) {
                GreenfootImage image = new GreenfootImage(player1Spirits.get(i).getImage());

                int size = player1Spirits.get(i).level;
                image.scale(size + 40, size + 40);
                player1Displays[i].setImage(image);
            } else {
                player1Displays[i].setImage((GreenfootImage)null);
            }
            if (i < player2Spirits.size()) {
                GreenfootImage image = new GreenfootImage(player2Spirits.get(i).getImage());

                int size = player2Spirits.get(i).level;
                image.scale(size + 40, size + 40);
                player2Displays[i].setImage(image);
            } else {
                player2Displays[i].setImage((GreenfootImage)null);
            }
        }
    }

    public Chooser createSpiritChooser(int x, int y, int spacing){
        // Code to display the player's spirits on the screen

        try{
            GreenfootImage[] costumeList = new GreenfootImage[Spirit.spiritTypes.size()];
            int i = 0;
            for (Class<? extends Spirit> spiritClass : Spirit.spiritTypes) {

                Spirit spirit = spiritClass.getDeclaredConstructor().newInstance();
                costumeList[i] = new GreenfootImage(spirit.getImage());
                costumeList[i].scale(50, 50);
                i++;
            }

            Chooser chooser = new Chooser(costumeList, MyWorld.maxSpirits, spacing, 4);

            // chooser.switches[0].status 

            world.addObject(chooser, x, y);

            return chooser;

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();

            return null;
        }
    }

    public void removeDisplays() {
        for (int i = 0; i < MyWorld.maxSpirits; i++) {
            world.removeObject(player1Displays[i]);
            world.removeObject(player2Displays[i]);
        }
    }
}
