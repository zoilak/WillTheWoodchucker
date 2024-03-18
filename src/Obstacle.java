import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimationE {


    //worried about animation period not being used

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod)
    {
        super(id, position, images, animationPeriod);

    }
//    @Override
//    public  void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//
//        //scheduleEvent(createActivityAction(world, imageStore), actionPeriod, scheduler);
//        scheduler.scheduleEvent(this,new AnimationA(this, 0), getAnimationPeriod());
//
//    }


}
