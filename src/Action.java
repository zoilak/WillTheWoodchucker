/**
 * An action that can be taken by an entity
 */
public interface Action {
   default void executeAction(EventScheduler scheduler) {
    }

}
