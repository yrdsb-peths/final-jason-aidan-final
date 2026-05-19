import greenfoot.*;

public class MyWorld extends World {
    
    int maxSpirits = 3;
    Spirit[] playerSpirits;

    public MyWorld() {

        super(600, 400, 1);
        playerSpirits = new Spirit[maxSpirits];
        
    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
}
