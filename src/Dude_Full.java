import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dude_Full extends Dude{

    public Dude_Full(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int resourceLimit, int resourceCount, PathingStrategy strategy) {
        super(id, position,images,animationPeriod,actionPeriod,resourceLimit,resourceCount,strategy);

    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest( getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && move(world, fullTarget.get(), scheduler)) {
            transform( world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,new ActivityA(this, world, imageStore), getActionPeriod());
        }
    }

    //returns void
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Dude dude = new Dude_Not_Full(getId(), getPosition(),getImages(),getAnimationPeriod(), getActionPeriod(),  getResourceLimit(),getResourceCount(), getStrategy());

        world.removeEntity(scheduler, this);

        world.addEntity(dude.getPosition(),dude);
        dude.scheduleActions( scheduler, world, imageStore);
        return false;
    }

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler) {
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPositionDude( world, target.getPosition()); //removed dude

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity( scheduler,this, nextPos);
            }
            return false;
        }
    }

}
