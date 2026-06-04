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

    static public void addFlashAnimation(Entity entity, int startTime, int endTime, int opacityDifference) {

        BattleScreen.getInstance().animationStack.add(new AnimationTask(
            startTime, 
            () -> {
                entity.image.setTransparency(Math.max(255-opacityDifference, 1));
            }
        ));
        BattleScreen.getInstance().animationStack.add(new AnimationTask(
            endTime, 
            () -> {
                entity.image.setTransparency(255);
            }
        ));
    }

    static public void addFlashAnimation(Entity entity, int timeDifference, int opacityDifference) {
        addFlashAnimation(entity, MyWorld.worldTime, MyWorld.worldTime + timeDifference, opacityDifference);
    }

    static public void addShakeAnimation(Entity entity, int count, int intensity, int interval) {

        int originalX = entity.x;
        int originalY = entity.y;

        for (int i = 0; i < count; i++) {
            // System.out.println(i);
            final int ind = i;
            BattleScreen.getInstance().animationStack.add(new AnimationTask(
            MyWorld.worldTime + interval * ind, 
            () -> {
                if (ind == 0) {
                    entity.setLocation(originalX + intensity / 2, originalY);
                }
                int direction = ind%2 == 0 ? -1 : 1;
                entity.setLocation(entity.x + direction * intensity, entity.y);
            }
            ));
        }

        BattleScreen.getInstance().animationStack.add(new AnimationTask(
        MyWorld.worldTime + interval * count, 
        () -> {
            entity.setLocation(originalX, originalY);
        }));
    }

    public AnimationTask(int executionTime, Runnable func)
    {
        this.executionTime = executionTime;

        this.func = () -> {
            func.run(); 
            BattleScreen.getInstance().updateAllVisuals();
        };
    }
}
