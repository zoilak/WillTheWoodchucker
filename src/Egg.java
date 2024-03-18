import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Egg extends Entity{
    public Egg(String id, Point position, List<PImage> images ){
        super(id, position,images);

    }


    public void setOnFire(WorldModel world, EventScheduler scheduler,ImageStore imageStore){
        List<Point> area = new ArrayList<>();
        //Point curr = null;
        for(int x =-1;x<2;x++){
            for(int j =-1; j<2;j++){
                area.add(new Point(getPosition().x+x,getPosition().y+j));
            }
        }
        //System.out.println(area);
        for(Point cur: area) {
            if (world.withinBounds(cur)) {
                world.setBackgroundCell(cur,new Background(WorldModel.SOIL_KEY, imageStore.getImageList((WorldModel.SOIL_KEY))));
                if (world.getOccupancyCell(cur) instanceof Tree) {
                    //world.setBackgroundCell(cur,new Background(WorldModel.SOIL_KEY, imageStore.getImageList((WorldModel.SOIL_KEY))));
                    TreeFire treef = new TreeFire(WorldModel.FTREE_KEY + "_" + getId(), cur, imageStore.getImageList(WorldModel.FTREE_KEY), Functions.getNumFromRange(WorldModel.FTREE_ANIMATION_MAX, WorldModel.FTREE_ANIMATION_MIN), Functions.getNumFromRange(WorldModel.FTREE_ACTION_MAX, WorldModel.FTREE_ACTION_MIN), Functions.getIntFromRange(WorldModel.FTREE_HEALTH_MAX, WorldModel.FTREE_HEALTH_MIN));

                    world.removeEntity(scheduler, world.getOccupancyCell(cur) );
                    scheduler.unscheduleAllEvents(world.getOccupancyCell(cur));
                    world.addEntity(treef.getPosition(), treef);
                    treef.scheduleActions(scheduler, world, imageStore);

                } else if (world.getOccupancyCell(cur) == null) {
                    //world.setBackgroundCell(cur,new Background(WorldModel.SOIL_KEY, imageStore.getImageList((WorldModel.SOIL_KEY))));
                    GrassOnFire grassf = new GrassOnFire(WorldModel.GRASSF_KEY + "_" + getId(), cur, imageStore.getImageList(WorldModel.GRASSF_KEY), Functions.getNumFromRange(WorldModel.GRASSF_ANIMATION_MAX, WorldModel.GRASSF_ANIMATION_MIN));

                    world.addEntity(grassf.getPosition(), grassf);
                    grassf.scheduleActions(scheduler, world, imageStore);

                }

            }

        }
        world.removeEntity(scheduler, this);
    }
}
