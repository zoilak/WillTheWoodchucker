public class ActivityA implements Action {
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;


    public ActivityA(Entity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;


    }

    public void executeAction( EventScheduler scheduler) {

        ((ActionE) this.entity).executeActivity(world, imageStore, scheduler);
    }

    // @Override
//    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//
//    }
}
