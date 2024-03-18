import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity { //comment //safe comit
    private Point position;
    private String id;
    private List<PImage> images;
    private int imageIndex;


    public Entity(String id, Point position, List<PImage> images) {
        //this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
//        this.resourceLimit = resourceLimit;
//        this.resourceCount = resourceCount;
//        this.actionPeriod = actionPeriod;
//        this.animationPeriod = animationPeriod;
//
//        this.health = health;
//        this.healthLimit = healthLimit;
    }


    public String getId() {return id;}

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public List<PImage> getImages() {return images;}
    public int getImageIndex() {return imageIndex;}
    public void nextImage() {

        imageIndex = imageIndex + 1;
    }

//    public String log(Entity entity){
//        return entity.getId().isEmpty() ? null :
//                String.format("%s %d %d %d", entity.getId(), entity.getPosition().x, entity.getPosition().y, entity.getImageIndex());
//    }
    //public void setImageIndex(int imageIndex) {this.imageIndex = imageIndex;}

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
//    public String log(){
//        return this.id.isEmpty() ? null :
//                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
//    }

//    public double getAnimationPeriod() {
//        switch (kind) {
//            case DUDE_FULL:
//            case DUDE_NOT_FULL:
//            case OBSTACLE:
//            case FAIRY:
//            case SAPLING:
//            case TREE:
//                return animationPeriod;
//            default:
//                throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", kind));
//        }
//    }

//    public void nextImage() {
//        imageIndex = imageIndex + 1;
//    }

//    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        health++;
//        if (!transformPlant( world, scheduler, imageStore)) {
//            scheduleEvent(createActivityAction(world, imageStore), actionPeriod, scheduler);
//        }
//    }
//
//
//
//    public void executeTreeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//
//        if (!transformPlant( world, scheduler, imageStore)) {
//
//            scheduleEvent(createActivityAction( world, imageStore), this.actionPeriod, scheduler);
//        }
//    }

//    public void executeFairyActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> fairyTarget = world.findNearest( position, new ArrayList<>(List.of(EntityKind.STUMP)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (moveToFairy(world,this,fairyTarget.get(), scheduler)) {
//
//                Entity sapling = world.createSapling(WorldModel.SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, imageStore.getImageList( WorldModel.SAPLING_KEY), 0);
//
//                sapling.addEntity(world);
//                sapling.scheduleActions( scheduler, world, imageStore);
//            }
//        }
//
//       scheduleEvent(createActivityAction( world, imageStore), actionPeriod, scheduler);
//    }
//    public void executeDudeNotFullActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> target = world.findNearest(position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));
//
//        if (target.isEmpty() || !moveToNotFull(world,this, target.get(), scheduler) || !transformNotFull(world, scheduler, imageStore)) {
//            scheduleEvent(createActivityAction( world, imageStore), actionPeriod, scheduler);
//        }
//    }
//
//    public void executeDudeFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> fullTarget = world.findNearest( position, new ArrayList<>(List.of(EntityKind.HOUSE)));
//
//        if (fullTarget.isPresent() && moveToFull(world,this, fullTarget.get(), scheduler)) {
//            transformFull( world, scheduler, imageStore);
//        } else {
//            scheduleEvent(createActivityAction( world, imageStore), actionPeriod, scheduler);
//        }
//    }
//    public  void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {  //not sure what to do
//        switch (kind) {
//            case DUDE_FULL:
//                scheduleEvent(ActivityA.createActivityAction( world, imageStore), actionPeriod, scheduler);
//                scheduleEvent(ActivityA.createAnimationAction( 0), getAnimationPeriod(), scheduler);
//                break;
//
//            case DUDE_NOT_FULL:
//                scheduleEvent(ActivityA.createActivityAction( world, imageStore), actionPeriod, scheduler);
//                scheduleEvent(ActivityA.createAnimationAction( 0),getAnimationPeriod(), scheduler);
//                break;
//
//            case OBSTACLE:
//                scheduleEvent(ActivityA.createAnimationAction( 0), getAnimationPeriod(), scheduler);
//                break;
//
//            case FAIRY:
//                scheduleEvent(ActivityA.createActivityAction( world, imageStore), actionPeriod, scheduler);
//                scheduleEvent(ActivityA.createAnimationAction(0), getAnimationPeriod(), scheduler);
//                break;
//
//            case SAPLING:
//                scheduleEvent(ActivityA.createActivityAction(world, imageStore), actionPeriod, scheduler);
//                scheduleEvent(ActivityA.createAnimationAction( 0), getAnimationPeriod(), scheduler);
//                break;
//
//            case TREE:
//                scheduleEvent(ActivityA.createActivityAction(world, imageStore), actionPeriod, scheduler);
//                scheduleEvent(ActivityA.createAnimationAction( 0), getAnimationPeriod(), scheduler);
//                break;
//
//            default:
//        }
//    }

//    public  boolean transformNotFull( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (resourceCount >= resourceLimit) {
//            Entity dude = world.createDudeFull(id, position, actionPeriod, animationPeriod, resourceLimit, images);
//
//            world.removeEntity( scheduler, this);
//            scheduler.unscheduleAllEvents(this);
//
//            dude.addEntity(world);
//            dude.scheduleActions( scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        Entity dude = world.createDudeNotFull(id, position, actionPeriod, animationPeriod, resourceLimit, images);
//
//        world.removeEntity(scheduler, this);
//
//        dude.addEntity(world);
//        dude.scheduleActions( scheduler, world, imageStore);
//    }

//    public boolean transformPlant( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (kind == EntityKind.TREE) {
//            return transformTree( world, scheduler, imageStore);
//        } else if (kind == EntityKind.SAPLING) {
//            return transformSapling( world, scheduler, imageStore);
//        } else {
//            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
//        }
//    }

//    public boolean transformTree( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (health <= 0) {
//            Entity stump = world.createStump(WorldModel.STUMP_KEY + "_" + id, position, imageStore.getImageList( WorldModel.STUMP_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            stump.addEntity(world);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (health <= 0) {
//            Entity stump = world.createStump(WorldModel.STUMP_KEY + "_" + id, position, imageStore.getImageList( WorldModel.STUMP_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            stump.addEntity(world);
//
//            return true;
//        } else if (health >= healthLimit) {
//            Entity tree = world.createTree(WorldModel.TREE_KEY + "_" + id, position, getNumFromRange(WorldModel.TREE_ACTION_MAX, WorldModel.TREE_ACTION_MIN), getNumFromRange(WorldModel.TREE_ANIMATION_MAX, WorldModel.TREE_ANIMATION_MIN), getIntFromRange(WorldModel.TREE_HEALTH_MAX, WorldModel.TREE_HEALTH_MIN), imageStore.getImageList( WorldModel.TREE_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            tree.addEntity(world);
//            tree.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public Point nextPositionFairy( WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - position.x);
//        Point newPos = new Point(position.x + horiz, position.y);
//
//        if (horiz == 0 || world.isOccupied( newPos)) {
//            int vert = Integer.signum(destPos.y - position.y);
//            newPos = new Point(position.x, position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = position;
//            }
//        }
//
//        return newPos;
//    }

//    public Point nextPositionDude( WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - position.x);
//        Point newPos = new Point(position.x + horiz, position.y);
//
//        if (horiz == 0 || world.isOccupied( newPos) && world.getOccupancyCell( newPos).kind != EntityKind.STUMP) {
//            int vert = Integer.signum(destPos.y - position.y);
//            newPos = new Point(position.x, position.y + vert);
//
//            if (vert == 0 || world.isOccupied( newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//                newPos = position;
//            }
//        }
//
//        return newPos;
//    }



//    public Action createAnimationAction( int repeatCount) {
//        return new AnimationA( this , null, null, repeatCount);
//    }

//    public Action createActivityAction( WorldModel world, ImageStore imageStore) {
//        return new ActivityA( this, world, imageStore);  //0 removed
//    }

//    public int getIntFromRange(int max, int min) {
//        Random rand = new Random();
//        return min + rand.nextInt(max-min);
//    }

//    public double getNumFromRange(double max, double min) {
//        Random rand = new Random();
//        return min + rand.nextDouble() * (max - min);
//    }

//    public void scheduleEvent(Action action, double afterPeriod, EventScheduler scheduler) {
//        double time = scheduler.getCurrentTime() + afterPeriod;
//
//        Event event = new Event(action, time, this);
//
//        scheduler.getEventQueue().add(event);
//
//        // update list of pending events for the given entity
//        List<Event> pending = scheduler.getPendingEvents().getOrDefault(this, new LinkedList<>());
//        pending.add(event);
//        scheduler.getPendingEvents().put(this, pending);
//    }

//    public boolean moveToFairy(WorldModel world, Entity fairy, Entity target, EventScheduler scheduler) {
//        if (world.adjacent(fairy.position, target.position)) {
//            world.removeEntity( scheduler, target);
//            return true;
//        } else {
//            Point nextPos = fairy.nextPositionFairy( world, target.position);
//
//            if (!fairy.position.equals(nextPos)) {
//                world.moveEntity( scheduler, fairy, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToFull(WorldModel world, Entity dude, Entity target, EventScheduler scheduler) {
//        if (world.adjacent(dude.position, target.position)) {
//            return true;
//        } else {
//            Point nextPos = dude.nextPositionDude( world, target.position);
//
//            if (!dude.position.equals(nextPos)) {
//                world.moveEntity( scheduler, dude, nextPos);
//            }
//            return false;
//        }
//    }
//    public boolean moveToNotFull(WorldModel world,Entity dude, Entity target, EventScheduler scheduler) {
//        if (world.adjacent(dude.position, target.position)) {
//            dude.setResourceCount(dude.getResourceCount()+1);
//            target.setHealth(target.getHealth()-1);
//            return true;
//        } else {
//            Point nextPos = dude.nextPositionDude( world, target.position);
//
//            if (!dude.position.equals(nextPos)) {
//                world.moveEntity( scheduler, dude, nextPos);
//            }
//            return false;
//        }
//    }

}
