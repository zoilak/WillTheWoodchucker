import processing.core.PImage;

import java.util.List;

public class Sapling extends Plant implements Transform {
    private int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod , int health, int healthLimit) {
        super(id,position,images,animationPeriod,actionPeriod, health);

        this.healthLimit = healthLimit;

    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        setHealth(getHealth()+1);//health++
        if (!transform( world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,new ActivityA(this,world, imageStore), getActionPeriod());
        }
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            Stump stump = new Stump(WorldModel.STUMP_KEY + "_" + getId(), getPosition(), imageStore.getImageList( WorldModel.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump.getPosition(),stump);

            return true;
        } else if (getHealth() >= healthLimit) {
            Tree tree = new Tree(WorldModel.TREE_KEY + "_" + getId(), getPosition(), imageStore.getImageList( WorldModel.TREE_KEY), Functions.getNumFromRange(WorldModel.TREE_ANIMATION_MAX, WorldModel.TREE_ANIMATION_MIN), Functions.getNumFromRange(WorldModel.TREE_ACTION_MAX, WorldModel.TREE_ACTION_MIN), Functions.getIntFromRange(WorldModel.TREE_HEALTH_MAX, WorldModel.TREE_HEALTH_MIN));

            world.removeEntity(scheduler, this);

            world.addEntity(tree.getPosition(),tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

}
