/**
 * Write a description of class AnimationTask here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimationTask  
{
    int executionTime;
    Runnable func;

    public AnimationTask(int executionTime, Runnable func)
    {
        this.executionTime = executionTime;

        this.func = () -> {
            func.run(); 
            BattleScreen.getInstance().updateAllVisuals();
        };
    }
}
