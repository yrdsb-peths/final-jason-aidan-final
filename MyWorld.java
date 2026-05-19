import greenfoot.*;

public class MyWorld extends World {
    
    int maxSpirits = 5;
    Spirit[] playerSpirits;

    public MyWorld() {

        super(600, 400, 1);
        playerSpirits = new Spirit[maxSpirits];

        playerSpirits[0] = new FireSpirit();
        
    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
    
    
}
