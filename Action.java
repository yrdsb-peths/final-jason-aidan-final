/**
 * Write a description of class Action here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Action  
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Action
     */
    String text;
    Runnable func;
    Boolean isCompleted;


    
    public Action(String text, Runnable func)
    {
        this.text = text;
        this.func = () -> {
            func.run(); 
            BattleScreen.getInstance().updateAllVisuals();
            isCompleted = true;
        };
        this.isCompleted = false;

    }

    public Action(Action action, Runnable cFunc)
    {
        this(action.text, () -> {
            action.func.run();
            cFunc.run();
        });

        this.isCompleted = action.isCompleted;
    }

    // public Action(Runnable cFunc)
    // {
    //     this("", cFunc);

    // }

    public Action(String text)
    {
        this.text = text;
        this.func = null;
        this.isCompleted = false;
    }

}
