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
    final String pvpInstructionsString = "Instructions: Both players pick 5 spirits, then click on the level up button, then upgrade the spirits by clicking on them.";

    MyWorld world;
    Button pvpButton;

    Label title;
    DialogueBox instructions;

    public TitleScreen(MyWorld world) {
        this.world = world;
        GreenfootImage image = new GreenfootImage("submit_button.png");
        image.scale(100, 100);
        pvpButton = new Button(image, 20);
        setImage((GreenfootImage)null);
        world.addObject(pvpButton, MyWorld.WIDTH/2, MyWorld.HEIGHT/2);


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
        
        if (pvpButton.isPressed) {
            world.currentState = States.CHOOSING;
            world.screenCreated = false;
            world.removeObject(pvpButton);
            world.removeObject(instructions);
            world.removeObject(title);
            world.removeObject(this);
        }

        if (pvpButton.isHovering) {
            instructions.setText(pvpInstructionsString);
        }
    }

}
