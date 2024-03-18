import java.util.*;

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler {
    public PriorityQueue<Event> getEventQueue() {
        return eventQueue;
    }

    private PriorityQueue<Event> eventQueue;

    public Map<Entity, List<Event>> getPendingEvents() {
        return pendingEvents;
    }

    private Map<Entity, List<Event>> pendingEvents;
    private double currentTime;
    public double getCurrentTime() {return currentTime;}

    public EventScheduler() {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.currentTime = 0;
    }


    public void unscheduleAllEvents( Entity entity) {
        List<Event> pending = pendingEvents.remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                eventQueue.remove(event);
            }
        }
    }

    public void removePendingEvent( Event event) {
        List<Event> pending = pendingEvents.get(event.getEntity());

        if (pending != null) {
            pending.remove(event);
        }
    }

    public void updateOnTime( double time) {
        double stopTime = currentTime + time;
        while (!eventQueue.isEmpty() && eventQueue.peek().getTime() <= stopTime) {
            Event next = eventQueue.poll();
            removePendingEvent( next);
            currentTime = next.getTime();
            next.getAction().executeAction( this);
        }
        currentTime = stopTime;
    }
    public void scheduleEvent(Entity entity,Action action, double afterPeriod) {
        double time = getCurrentTime() + afterPeriod;

        Event event = new Event(action, time, entity);

        getEventQueue().add(event);

        // update list of pending events for the given entity
        List<Event> pending = getPendingEvents().getOrDefault(entity, new LinkedList<>());
        pending.add(event);
        getPendingEvents().put(entity, pending);
    }
}
