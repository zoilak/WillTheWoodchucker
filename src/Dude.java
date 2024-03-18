import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Dude extends ActionE implements Transform,Move {
    private int resourceLimit;
    //getter
    private int resourceCount;



    private PathingStrategy strategy;

    public Dude(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount, PathingStrategy strategy) {
        super(id, position,images,animationPeriod,actionPeriod);  //, int health, int healthLimit
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.strategy=strategy;
        //System.out.println(images.size());
    }
    public PathingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PathingStrategy strategy) {
        this.strategy = strategy;
    }
    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }
    public int getResourceCount() {
        return resourceCount;
    }
    //setter
    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
    public Point nextPositionDude( WorldModel world, Point destPos) {
        //Predicate<Point> canPass = point -> world.getOccupancyCell(point)!=null && world.withinBounds(point);
        Predicate<Point> canPass = point -> (world.withinBounds(point) &&(!world.isOccupied(point) )); //&& (world.getOccupancyCell(point).getClass() == Stump.class)
        BiPredicate<Point,Point> withinReach = world::adjacent;
        List<Point> path = this.getStrategy().computePath(getPosition(),destPos,canPass,withinReach,PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.isEmpty()){
            return getPosition();
        }
        Point first = path.get(path.size()-1);

//        int horiz = Integer.signum(destPos.x - getPosition().x);
//        Point newPos = new Point(getPosition().x + horiz, getPosition().y);
//
//        if (horiz == 0 || world.isOccupied( newPos) && !(world.getOccupancyCell( newPos) instanceof Stump) ){
//            int vert = Integer.signum(destPos.y - getPosition().y);
//            newPos = new Point(getPosition().x, getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied( newPos) && !(world.getOccupancyCell(newPos) instanceof Stump) ){
//                newPos = getPosition();
//            }
//        }

        return first;
    }
//    public boolean path(Point point1,Point point2,WorldModel world){
//        return world.adjacent(point1,point2);
//    }



}
