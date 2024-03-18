import processing.core.PImage;

import java.util.List;

public abstract class AnimationE extends Entity{

    //private double actionPeriod;
    private double animationPeriod;

	public AnimationE(String id, Point position, List<PImage> images, double animationPeriod)
        {
            super(id, position, images);

            this.animationPeriod = animationPeriod;

        }

        //public int getAnimationPeriod();
        public double getAnimationPeriod()
        {
            return animationPeriod;
        }

//        public Action createAnimationAction(int repeatCount)
//        {
//            return new AnimationA(this,  repeatCount);
//        }


    public  void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

        //scheduleEvent(createActivityAction(world, imageStore), actionPeriod, scheduler);
        scheduler.scheduleEvent(this,new AnimationA(this, 0), getAnimationPeriod());

    }
}
