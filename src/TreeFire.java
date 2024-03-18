import processing.core.PImage;

import java.util.List;

public class TreeFire extends Plant implements Transform {
    public TreeFire(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        super(id, position, images, actionPeriod, animationPeriod, health);

//        this.health=health;
//        this.healthLimit=healthLimit;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            Stump stump =  new Stump(WorldModel.STUMP_KEY + "_" + getId(), getPosition(), imageStore.getImageList( WorldModel.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump.getPosition(),stump);
            return true;
        }
        return false;
    }
}
