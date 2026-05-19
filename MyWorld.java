import greenfoot.*;

public class MyWorld extends World {
    
    int maxSpirits = 5;
    Spirit[] playerSpirits;

    public MyWorld() {

        super(600, 400, 1);
        playerSpirits = new Spirit[maxSpirits];

        playerSpirits[0] = new FireSpirit();
        playerSpirits[1] = new WaterSpirit();
        playerSpirits[2] = new GrassSpirit();
    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
    
    
}
