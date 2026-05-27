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
    
    ImageDisplay[] player1Displays;
    ImageDisplay[] player2Displays;

    ArrayList<Spirit> player1Spirits;
    ArrayList<Spirit> player2Spirits;

    Chooser chooser1;
    Chooser chooser2;

    Button submitButton;

    /**
     * Act - do whatever the ChoseScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public ChooseScreen(ArrayList<Spirit> player1Spirits, ArrayList<Spirit> player2Spirits, MyWorld world) {
        this.player1Spirits = player1Spirits;
        this.player2Spirits = player2Spirits;
        
        this.world = world;

        GreenfootImage image = new GreenfootImage("submit_button.png");
        image.scale(150,150);
        submitButton = new Button(image, 20);
        world.addObject(submitButton, MyWorld.WIDTH/2, MyWorld.HEIGHT/4 * 3);

        initSelectedDisplay();
        
        chooser1 = createSpiritChooser(70, 50, 70);
        chooser2 = createSpiritChooser(400, 50, 70);
    }   

    public void act()
    {
        chooseSpirit(chooser1, chooser2);
        updateDisplay();

        boolean finishedChoosing = player1Spirits.size() == MyWorld.maxSpirits && player2Spirits.size() == MyWorld.maxSpirits;


        if (finishedChoosing && submitButton.isPressed) {
            chooser1.remove();
            chooser2.remove();
            submitButton.remove();
            removeDisplays();
            world.currentState = States.BATTLE;
            world.screenCreated = false;
            world.removeObject(this);
        }
    }

    public void initSelectedDisplay() {

        player1Displays = new ImageDisplay[MyWorld.maxSpirits];
        player2Displays = new ImageDisplay[MyWorld.maxSpirits];

        for (int i = 0; i < MyWorld.maxSpirits; i++) {
            
            player1Displays[i] = new ImageDisplay();
            world.addObject(player1Displays[i], 70 + 70 * i, 300);
        
        
            player2Displays[i] = new ImageDisplay();
            world.addObject(player2Displays[i], 400 + 70 * i, 300);
            
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
                player1Displays[i].setImage(player1Spirits.get(i).getImage());
            } else {
                player1Displays[i].setImage((GreenfootImage)null);
            }
            if (i < player2Spirits.size()) {
                player2Displays[i].setImage(player2Spirits.get(i).getImage());
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

            Chooser chooser = new Chooser(costumeList, 2, spacing);

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
