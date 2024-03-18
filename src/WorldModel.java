import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {

    public static final double TREE_ANIMATION_MIN = 0.050;
    public static final double TREE_ACTION_MAX = 1.400;
    public static final double TREE_ACTION_MIN = 1.000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public static final double TREE_ANIMATION_MAX = 0.600;

    public static final String TREE_KEY = "tree";
    public static final String SAPLING_KEY = "sapling";
    public static final String STUMP_KEY = "stump";
    public static final int TREE_ANIMATION_PERIOD = 0;
    public static final int TREE_ACTION_PERIOD = 1;
    public static final int TREE_HEALTH = 2;
    public static final int TREE_NUM_PROPERTIES = 3;
    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 0;

    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_ANIMATION_PERIOD = 0;
    public static final int FAIRY_ACTION_PERIOD = 1;
    public static final int FAIRY_NUM_PROPERTIES = 2;
    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD = 0;
    public static final int DUDE_ANIMATION_PERIOD = 1;
    public static final int DUDE_LIMIT = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;
    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_ANIMATION_PERIOD = 0;
    public static final int OBSTACLE_NUM_PROPERTIES = 1;
    public static final int SAPLING_NUM_PROPERTIES = 1;

    public static final int SAPLING_HEALTH = 0;
    public static final int STUMP_NUM_PROPERTIES = 0;
    public static final int PROPERTY_ID = 1;
    public static final int PROPERTY_KEY = 0;
    public static final int PROPERTY_COL = 2;
    public static final int PROPERTY_ROW = 3;
    public static final int ENTITY_NUM_PROPERTIES = 4;
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    public static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));
    public static final String CHICKEN_KEY = "chicken";
    public static final int CHICKEN_ANIMATION_PERIOD = 0;
    public static final int CHICKEN_ACTION_PERIOD = 1;
    public static final int CHICKEN_NUM_PROPERTIES = 2;
    public static final int CHICKEN_LIMIT = 2;
    public static final String EGG_KEY = "egg";
    public static final int EGG_ANIMATION_PERIOD = 0;
    public static final int EGG_ACTION_PERIOD = 1;
    public static final int EGG_NUM_PROPERTIES = 2;
    public static final int EGG_LIMIT = 2;

    public static final String PINKSTUMP_KEY = "pinkStump";
    public static final int GOLDSTUMP_NUM_PROPERTIES = 0;

    public static final double FTREE_ANIMATION_MIN = 0.050;
    public static final double FTREE_ACTION_MAX = 1.400;
    public static final double FTREE_ACTION_MIN = 1.000;
    public static final int FTREE_HEALTH_MAX = 3;
    public static final int FTREE_HEALTH_MIN = 1;
    public static final double FTREE_ANIMATION_MAX = 0.600;

    public static final String FTREE_KEY = "treeFire";

    public static final double GRASSF_ANIMATION_MIN = 0.050;
    public static final double GRASSF_ACTION_MAX = 1.400;
    public static final double GRASSF_ACTION_MIN = 1.000;
    public static final int GRASSF_HEALTH_MAX = 3;
    public static final int GRASSF_HEALTH_MIN = 1;
    public static final double GRASSF_ANIMATION_MAX = 0.600;

    public static final String GRASSF_KEY = "grassFire";

    public static final String FF_KEY = "firefighter";
    public static final int FF_ACTION_PERIOD = 0;
    public static final int FF_ANIMATION_PERIOD = 1;
    public static final int FF_LIMIT = 2;
    public static final int FF_NUM_PROPERTIES = 3;

    public static final String SOIL_KEY = "soil";
    public static final double PINKTREE_ANIMATION_MIN = 0.050;
    public static final double PINKTREE_ACTION_MAX = 1.400;
    public static final double PINKTREE_ACTION_MIN = 1.000;
    public static final int PINKTREE_HEALTH_MAX = 3;
    public static final int PINKTREE_HEALTH_MIN = 1;
    public static final double PINKTREE_ANIMATION_MAX = 0.600;

    public static final String PINKTREE_KEY = "pinkTree";

    private int numRows;
    public int getNumRows(){return numRows;}
    private int numCols;
    public int getNumCols(){return numCols;}
    private Background[][] background;
    private Entity[][] occupancy;

    public Set<Entity> getEntities() {
        return entities;
    }

    private Set<Entity> entities;

    public WorldModel() {

    }

    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log(){
        List<String> list = new ArrayList<>();
        for (Entity entity : entities) {
            String log = log(entity);
            if(log != null) list.add(log);
        }
        return list;
    }

    public String log(Entity entity){
        return entity.getId().isEmpty() ? null :
                String.format("%s %d %d %d", entity.getId(), entity.getPosition().x, entity.getPosition().y, entity.getImageIndex());
    }
    public void addEntity(Point position,Entity entity) {
        if (withinBounds(position)) {
            //System.out.println("string");
            setOccupancyCell(position, entity);
            getEntities().add(entity);}
//        else{
//            System.out.println(position);
//            }
        }




    public void parseSapling( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Sapling entity = new Sapling(id, pt, imageStore.getImageList( SAPLING_KEY),SAPLING_ACTION_ANIMATION_PERIOD,SAPLING_ACTION_ANIMATION_PERIOD, health,SAPLING_HEALTH_LIMIT);
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    public void parseDude( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Dude entity = new Dude_Not_Full(id, pt,imageStore.getImageList( DUDE_KEY), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Integer.parseInt(properties[DUDE_LIMIT]), 0,new AStarPathingStrategy());
            tryAddEntity( entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }
    public void parseFairy( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Fairy entity = new Fairy(id, pt, imageStore.getImageList( FAIRY_KEY),Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]) , Double.parseDouble(properties[FAIRY_ACTION_PERIOD]), new AStarPathingStrategy());
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

    public  void parseTree( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Tree entity = new Tree(id, pt, imageStore.getImageList( TREE_KEY),  Double.parseDouble(properties[TREE_ANIMATION_PERIOD]),Double.parseDouble(properties[TREE_ACTION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    public void parseObstacle( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Obstacle entity = new Obstacle(id, pt, imageStore.getImageList( OBSTACLE_KEY), Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

    public void parseHouse( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            House entity = new House(id, pt, imageStore.getImageList(HOUSE_KEY));
            tryAddEntity( entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    public void parseStump( String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Stump entity = new Stump(id, pt, imageStore.getImageList(STUMP_KEY));
            tryAddEntity( entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }

    public void tryAddEntity( Entity entity) {
        if (isOccupied( entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity.getPosition(),entity);
    }

    public boolean withinBounds( Point pos) {
        return pos.y >= 0 && pos.y <numRows && pos.x >= 0 && pos.x < numCols;
    }

    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell( pos) != null;
    }

     /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */

    public  void moveEntity( EventScheduler scheduler, Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds( pos) && !pos.equals(oldPos)) {
            setOccupancyCell( oldPos, null);
            Optional<Entity> occupant = getOccupant( pos);
            occupant.ifPresent(target -> removeEntity( scheduler, target));
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public  void removeEntity( EventScheduler scheduler, Entity entity) {
        scheduler.unscheduleAllEvents( entity);
        removeEntityAt( entity.getPosition());
    }

    public void removeEntityAt( Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            entities.remove(entity);
            setOccupancyCell( pos, null);
            //System.out.println(entity.getId());
        }

    }

    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell( pos));
        } else {
            return Optional.empty();
        }
    }
//    public static ActivityA createActivityAction( ActionE entity,WorldModel world, ImageStore imageStore) {
//        return new ActivityA( entity, world, imageStore);
//    }
//    public static AnimationA createAnimationAction( AnimationE entity,int repeatCount) {
//        return new AnimationA( entity , null, null, repeatCount);
//    }

    public  Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }

    public void setOccupancyCell( Point pos, Entity entity) {
        occupancy[pos.y][pos.x] = entity;
    }

//    public static House createHouse(String id, Point position, List<PImage> images) {
//        return new House(id, position, images);//, 0, 0, 0, 0, 0, 0
//    }
//    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
//        return new Obstacle( id, position, images, animationPeriod);
//    }

//    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
//        return new Tree( id, position, images,  actionPeriod, animationPeriod, health);//0, 0,
//    }

//    public static Stump createStump(String id, Point position, List<PImage> images) {
//        return new Stump(id, position, images); //0, 0, 0, 0, 0, 0
//    }

//    public static Sapling createSapling(String id, Point position, List<PImage> images, int health) {
//        return new Sapling( id, position, images, 0, 0,  0,0);
//    }
//    public static Fairy createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images,PathingStrategy strategy) {
//        return new Fairy( id, position, images,  actionPeriod, animationPeriod,strategy);//0, 0,, 0, 0
//    }

    // need resource count, though it always starts at 0
//    public static Dude_Not_Full createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images,PathingStrategy strategy) {
//        return new Dude_Not_Full( id, position, images, actionPeriod, animationPeriod ,resourceLimit, 0,strategy);
//    }

    // don't technically need resource count ... full
//    public static Dude_Full createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images,PathingStrategy strategy) {
//        return new Dude_Full( id, position, images,actionPeriod, animationPeriod ,resourceLimit,0 ,strategy);//0,0
//    }

    public void load( Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        parseSaveFile( saveFile, imageStore, defaultBackground);
        if(background == null){
            background = new Background[numRows][numCols];
            for (Background[] row : background)
                Arrays.fill(row, defaultBackground);
        }
        if(occupancy == null){
            occupancy = new Entity[numRows][numCols];
            entities = new HashSet<>();
        }
    }

    public void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while(saveFile.hasNextLine()){
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if(line.endsWith(":")){
                headerLine = lineCounter;
                lastHeader = line;
                switch (line){
                    case "Backgrounds:" -> background = new Background[numRows][numCols];
                    case "Entities:" -> {
                        occupancy = new Entity[numRows][numCols];
                        entities = new HashSet<>();
                    }
                }
            }else{
                switch (lastHeader){
                    case "Rows:" -> numRows = Integer.parseInt(line);
                    case "Cols:" -> numCols = Integer.parseInt(line);
                    case "Backgrounds:" -> parseBackgroundRow( line, lineCounter-headerLine-1, imageStore);
                    case "Entities:" -> parseEntity( line, imageStore);
                }
            }
        }
    }

    public void parseBackgroundRow( String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if(row < numRows){
            int rows = Math.min(cells.length, numCols);
            for (int col = 0; col < rows; col++){
                background[row][col] = new Background(cells[col], imageStore.getImageList( cells[col]));
            }
        }
    }

    public void parseEntity( String line, ImageStore imageStore) {
        String[] properties = line.split(" ", ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[PROPERTY_COL]), Integer.parseInt(properties[PROPERTY_ROW]));

            properties = properties.length == ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case OBSTACLE_KEY -> parseObstacle( properties, pt, id, imageStore);
                case DUDE_KEY -> parseDude( properties, pt, id, imageStore);
                case FAIRY_KEY -> parseFairy( properties, pt, id, imageStore);
                case HOUSE_KEY ->parseHouse( properties, pt, id, imageStore);
                case TREE_KEY -> parseTree( properties, pt, id, imageStore);
                case SAPLING_KEY -> parseSapling( properties, pt, id, imageStore);
                case STUMP_KEY -> parseStump( properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void setBackgroundCell( Point pos, Background background) {   //not used
        this.background[pos.y][pos.x] = background;
    }

    public Background getBackgroundCell( Point pos) {
        return background[pos.y][pos.x];
    }

    public Optional<PImage> getBackgroundImage( Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(ImageStore.getCurrentImage(getBackgroundCell( pos)));
        } else {
            return Optional.empty();
        }
    }
    public Optional<Entity> findNearest(Point pos, List<Class<?>> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (Class<?> kind : kinds) {
            for (Entity entity : entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }
        return nearestEntity(ofType, pos);
    }

    public Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
            return Optional.of(nearest);
        }
    }
    public int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }


}
