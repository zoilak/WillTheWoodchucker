public class AnimationA implements Action{
    //private ActionKind kind;
    private Entity entity;
//    private WorldModel world;
//    private ImageStore imageStore;
    private int repeatCount;
    //private int imageIndex;


    public AnimationA(Entity entity, int repeatCount) {
        //this.kind = kind;
        this.entity = entity;
        //this.world = world;
        this.repeatCount = repeatCount;
    }


    public void executeAction( EventScheduler scheduler) {
        this.entity.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(this.entity,new AnimationA(this.entity,Math.max(repeatCount - 1, 0)), ((AnimationE) this.entity).getAnimationPeriod());
        }
    }
//    public Entity getEntity(){
//        return this.entity;
//    }
//    public int getRepeatCount(){
//        return this.repeatCount;
//    }


}
