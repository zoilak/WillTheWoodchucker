import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


public class Chicken extends Dude implements Move, Transform{

    public Chicken(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount, PathingStrategy strategy) {
        super(id, position,images,animationPeriod,actionPeriod,resourceLimit,resourceCount,strategy);

    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
    Optional<Entity> target = world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Tree.class)));

        if (target.isEmpty() || !move(world,  target.get(), scheduler) || !transform(world, scheduler, imageStore)) {
        scheduler.scheduleEvent(this,new ActivityA(this,world, imageStore), getActionPeriod());
    }
}
    public boolean move(WorldModel world,  Entity target, EventScheduler scheduler) {
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            //world.removeEntity( scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPositionChicken( world, target.getPosition()); //removed fairy.

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity( scheduler, this, nextPos);
            }
            return false;
        }
    }


    public Point nextPositionChicken( WorldModel world, Point destPos) {
        Predicate<Point> canPass = point -> (world.withinBounds(point) &&(!world.isOccupied(point) )); //&& (world.getOccupancyCell(point).getClass() == Stump.class)
        BiPredicate<Point,Point> withinReach = world::adjacent;
        List<Point> path = this.getStrategy().computePath(getPosition(),destPos,canPass,withinReach,PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.isEmpty()){
            return getPosition();
        }
        Point first = path.get(path.size()-1);


        return first;
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        Egg egg = new Egg(WorldModel.EGG_KEY, getPosition(),imageStore.getImageList( WorldModel.EGG_KEY));

        world.removeEntity(scheduler, this);

        world.addEntity(egg.getPosition(),egg);
        //egg.scheduleActions( scheduler, world, imageStore);
        return false;

    }

    //will call tranform as it tranforms into an egg when it is close to two tress
}
