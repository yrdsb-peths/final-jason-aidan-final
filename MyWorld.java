import greenfoot.*;

public class MyWorld extends World {
    
    int maxSpirits = 3;
    Spirit[] playerSpirits;

    public MyWorld() {

        super(600, 400, 1);
<<<<<<< HEAD
        Spirit f = new Spirit("Fire", "fire spirit");
=======

        playerSpirits = new Spirit[maxSpirits];
        
>>>>>>> 3d2bd7bf353032a3eb907c0965cb73c36b5fcdde
    }

    public void chooseSpirit() {
        // Code to allow the player to choose a spirit
    }
}
