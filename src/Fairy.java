import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Fairy extends ActionE implements Move {

    private PathingStrategy strategy;



    public Fairy(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, PathingStrategy strategy) {
        super(id, position,images,animationPeriod,actionPeriod);
        this.strategy=strategy;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public PathingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PathingStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest( getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Entity entity = fairyTarget.get();
            Point tgtPos = entity.getPosition();
            boolean result = move(world,entity, scheduler);
            if (result){
                if ((entity instanceof PinkStump)) {

                    PinkTree pinktree = new PinkTree(WorldModel.PINKTREE_KEY + "_" + getId(), tgtPos, imageStore.getImageList( WorldModel.PINKTREE_KEY), Functions.getNumFromRange(WorldModel.PINKTREE_ANIMATION_MAX, WorldModel.PINKTREE_ANIMATION_MIN));

                    world.removeEntityAt(tgtPos);
                    world.addEntity(pinktree.getPosition(),pinktree);
                    pinktree.scheduleActions( scheduler, world, imageStore);
                }
                else  {
                    Sapling sapling = new Sapling(WorldModel.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageStore.getImageList( WorldModel.SAPLING_KEY), WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_HEALTH, WorldModel.SAPLING_HEALTH_LIMIT);//000

                    world.addEntity(sapling.getPosition(),sapling);
                    sapling.scheduleActions( scheduler, world, imageStore);
                }

            }



        }

        scheduler.scheduleEvent(this,new ActivityA(this,world, imageStore), getActionPeriod());
    }

    public Point nextPositionFairy( WorldModel world, Point destPos) {

        Predicate<Point> canPass = point -> ((world.withinBounds(point))&&(!world.isOccupied(point)));
        BiPredicate<Point,Point> withinReach = ((point, point21) -> world.adjacent(point,point21));
        List<Point> path = this.getStrategy().computePath(getPosition(),destPos,canPass,withinReach,PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.isEmpty()){
            return getPosition();
        }
        Point first = path.get(path.size()-1);

        return first;
   }


    public boolean move(WorldModel world,  Entity target, EventScheduler scheduler) {
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity( scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPositionFairy( world, target.getPosition()); //removed fairy.

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity( scheduler, this, nextPos);
            }
            return false;
        }
    }


}
