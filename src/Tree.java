import processing.core.PImage;

import java.util.List;

public class Tree extends Plant implements Transform{


//    public int getHealth() {
//        return health;
//    }
//
//    public void setHealth(int health) {
//        this.health = health;
//    }
//
//    private int health;
//    private int healthLimit;
    //getter
    public Tree(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health){
        super(id,position,images,animationPeriod,actionPeriod,health);
//        this.health=health;
//        this.healthLimit=healthLimit;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */



    public void executeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!transform( world, scheduler, imageStore)) {  //removed transfromPlant not sure if really needed

            scheduler.scheduleEvent(this,new ActivityA(this, world, imageStore), getActionPeriod());
        }
    }

    public boolean transform( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (getHealth() <= 0) {
            Stump stump =  new Stump(WorldModel.STUMP_KEY + "_" + getId(), getPosition(), imageStore.getImageList( WorldModel.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump.getPosition(),stump);

            return true;
        }

        return false;
    }

    //maybe split it up
//    public boolean transformPlant( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (kind == EntityKind.TREE) {
//            return transformTree( world, scheduler, imageStore);
//        } else if (kind == EntityKind.SAPLING) {
//            return Sapling.transformSapling( world, scheduler, imageStore);
//        } else {
//            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
//        }
//    }

}
