import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Firefighter extends ActionE implements  Move {

    private PathingStrategy strategy;

    public Firefighter(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, PathingStrategy strategy) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.strategy=strategy;
    }

    public PathingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PathingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(getPosition(), new ArrayList<>(List.of(TreeFire.class,GrassOnFire.class)));

        //trying to find trees on fire and creating stumps
        if (target.isPresent()) {
            Entity entity= target.get();
            Point tgtPos = entity.getPosition();
            if (( entity instanceof TreeFire) && (move(world, entity, scheduler))) {
                PinkStump pinkstump = new PinkStump(WorldModel.PINKSTUMP_KEY + "_" + getId(), tgtPos, imageStore.getImageList(WorldModel.PINKSTUMP_KEY));
                world.removeEntity(scheduler, entity);
                scheduler.unscheduleAllEvents(entity);
                world.addEntity(pinkstump.getPosition(), pinkstump);
            }
            else if ((entity instanceof GrassOnFire) && (move(world, entity, scheduler))){
                world.removeEntity(scheduler,entity);
            }
        }
        scheduler.scheduleEvent(this, new ActivityA(this, world, imageStore), getActionPeriod());

    }

//    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        Optional<Entity> target = world.findNearest(getPosition(), new ArrayList<>(List.of(TreeFire.class,GrassOnFire.class)));
//
//        //trying to find trees on fire and creating stumps
//        if (target.isPresent()) {
//            Point tgtPos = target.get().getPosition();
//            if (move(world, target.get(), scheduler)) {
//                Stump stump = new Stump(WorldModel.STUMP_KEY + "_" + getId(), tgtPos, imageStore.getImageList(WorldModel.STUMP_KEY));
//
//                //world.removeEntity(scheduler, this);
//                world.addEntity(stump.getPosition(), stump);
//            }
//        }
//        return false;
//
//    }


    public boolean move(WorldModel world,  Entity target, EventScheduler scheduler) {
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity( scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPositionFF( world, target.getPosition()); //removed fairy.

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity( scheduler, this, nextPos);
            }
            return false;
        }
   }



    public Point nextPositionFF(WorldModel world, Point destPos) {
        //Predicate<Point> canPass = point -> world.getOccupancyCell(point)!=null && world.withinBounds(point);
        Predicate<Point> canPass = point -> ((world.withinBounds(point)) && (!world.isOccupied(point)));
        BiPredicate<Point, Point> withinReach = ((point, point21) -> world.adjacent(point, point21));
        List<Point> path = this.getStrategy().computePath(getPosition(), destPos, canPass, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.isEmpty()) {
            return getPosition();
        }
        Point first = path.get(path.size() - 1);

        return first;
    }
}

