import processing.core.PImage;

import java.util.List;

public abstract class ActionE extends AnimationE{

    private double actionPeriod;

    public ActionE(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod) {
        super(id,position,images,animationPeriod);
        this.actionPeriod = actionPeriod;

    }
    public double getActionPeriod() {
        return actionPeriod;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */

    public abstract void executeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) ;


    public  void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

                super.scheduleActions(scheduler,world, imageStore);
                scheduler.scheduleEvent(this,new ActivityA(this,world, imageStore), actionPeriod);
                //scheduleEvent(createAnimationAction( 0), getAnimationPeriod(), scheduler);

    }
//    public Action createActivityAction( WorldModel world, ImageStore imageStore) {
//        return new ActivityA( this, world, imageStore); //removed 0
//    }


}
