import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Dude_Not_Full extends Dude {

    public Dude_Not_Full(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount, PathingStrategy strategy) {
        super(id, position, images, animationPeriod,actionPeriod,  resourceLimit,resourceCount,strategy);
    }

    public void executeActivity (WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<Entity> target = world.findNearest(getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !move(world,  target.get(), scheduler) || !transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,new ActivityA(this,world, imageStore), getActionPeriod());
        }
    }

    public boolean transform (WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        if (getResourceCount() >= getResourceLimit()) {
            //System.out.println("transform");
            Dude dude = new Dude_Full(getId(), getPosition(),getImages(),getAnimationPeriod(), getActionPeriod(), getResourceLimit(), getResourceCount(),getStrategy());

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude.getPosition(),dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    //there is no Target

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler){ //create move  //is target a tree
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            this.setResourceCount(this.getResourceCount() + 1);
//            System.out.println(getResourceLimit());
//            System.out.println(getResourceCount() + " " + getPosition());
            ((Plant)target).setHealth(((Plant)target).getHealth() - 1);
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.getPosition()); //removed dude

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

}
