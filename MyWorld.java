import greenfoot.*;

public class MyWorld extends World {
    
    int maxSpirits = 5;
    Spirit[] playerSpirits;

    public MyWorld() {

        super(600, 400, 1);
        playerSpirits = new Spirit[maxSpirits];
        displaySpirits();

    }

    public void displaySpirits() {
        // Code to display the player's spirits on the screen

        // for (int i = 0; i < Spirit.spiritTypes.length; i++) {
        //     if (Spirit.spiritTypes[i] != null) {
        //         addObject(new Spirit.spiritTypes[i].getConstructor(), 100 + i * 100, 350);
        //     }
        // }
        addObject(new FireSpirit(), 100, 350);
        addObject(new WaterSpirit(), 200, 350);
        addObject(new GrassSpirit(), 300, 350);
    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
    
    
}
