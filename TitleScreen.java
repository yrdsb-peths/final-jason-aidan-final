import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends Actor
{
    /**
     * Act - do whatever the TitleScreen wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    final String pvpInstructionsString1 = "Instructions: Both players pick 3 spirits, 12 upgrade points per player.";
    final String pvpInstructionsString2 = "Instructions: Both players pick 4 spirits, 16 upgrade points per player.";
    final String pvpInstructionsString3 = "Instructions: Both players pick 5 spirits, 20 upgrade points per player.";


    MyWorld world;
    Button pvpButton1;
    Button pvpButton2;
    Button pvpButton3;

    Label title;
    DialogueBox instructions;

    public TitleScreen(MyWorld world) {
        this.world = world;

        GreenfootImage image1 = new GreenfootImage("submit_button.png");
        image1.scale(100, 100);
        pvpButton1 = new Button(image1, 20);
        setImage((GreenfootImage)null);
        world.addObject(pvpButton1, MyWorld.WIDTH/2 - 100, MyWorld.HEIGHT/2);

        GreenfootImage image2 = new GreenfootImage("submit_button.png");
        image2.scale(100, 100);
        pvpButton2 = new Button(image2, 20);
        setImage((GreenfootImage)null);
        world.addObject(pvpButton2, MyWorld.WIDTH/2, MyWorld.HEIGHT/2);

        GreenfootImage image3 = new GreenfootImage("submit_button.png");
        image3.scale(100, 100);
        pvpButton3 = new Button(image3, 20);
        setImage((GreenfootImage)null);
        world.addObject(pvpButton3, MyWorld.WIDTH/2 + 100, MyWorld.HEIGHT/2);


        title = new Label("Turn-based game", 30);
        title.setLineColor(null);
        title.setFillColor(Color.BLACK);
        world.addObject(title, MyWorld.WIDTH/2, MyWorld.HEIGHT/4);


        instructions = new DialogueBox(400, 300, Color.WHITE, "", Color.BLACK, new Font(15));
        world.addObject(instructions, MyWorld.WIDTH/2 + 50, MyWorld.WIDTH*2/3);
    }

    public void act()
    {
        // Add your action code here.

        instructions.setText("");
        
        if (pvpButton1.isPressed) {
            MyWorld.MAX_ENTITIES = 3;
            MyWorld.POINTS_PER_PLAYER = 12;
            nextScreen();
        }

        if (pvpButton2.isPressed) {
            MyWorld.MAX_ENTITIES = 4;
            MyWorld.POINTS_PER_PLAYER = 16;
            nextScreen();
        }

        if (pvpButton3.isPressed) {
            MyWorld.MAX_ENTITIES = 5;
            MyWorld.POINTS_PER_PLAYER = 20;
            nextScreen();
        }

        if (pvpButton1.isHovering) {
            instructions.setText(pvpInstructionsString1);
        }

        if (pvpButton2.isHovering) {
            instructions.setText(pvpInstructionsString2);
        }

        if (pvpButton3.isHovering) {
            instructions.setText(pvpInstructionsString3);
        }
    }

    public void nextScreen() {
        world.currentState = States.CHOOSING;
        world.screenCreated = false;
        world.removeObject(pvpButton1);
        world.removeObject(pvpButton2);
        world.removeObject(pvpButton3);
        world.removeObject(instructions);
        world.removeObject(title);
        world.removeObject(this);
    }

}
