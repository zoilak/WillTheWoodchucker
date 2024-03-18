/**
 * An event is made up of an Entity that is taking an
 * Action a specified time.
 */
public final class Event {
    private Action action;
    private double time;
    private Entity entity;
    public Entity getEntity() {return entity;}
    public double getTime() {return time;}
    public Action getAction() {return action;}

    public Event(Action action, double time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }
}
