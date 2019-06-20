package calendarevents;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CalendarEventStore {

    private static boolean initialized = false;
    public static final CalendarEventStore shared = new CalendarEventStore();
    private static HashMap<Integer, ArrayList<CalendarEvent>> eventMap = new HashMap<>();
    private static ArrayList<CalendarEvent> events = new ArrayList<>();

    /***
     * Overloads the default constructor to prevent multiple instantiations
     * @throws ExceptionInInitializerError
     */
    private CalendarEventStore() throws ExceptionInInitializerError {
        if (initialized) throw new ExceptionInInitializerError("Singleton class has already been initialized!");
        initialized = true;
    }

    /***
     * Returns a list of events for a given date
     * @param date The date for which events should be retrieved
     * @return Maybe be null if there are no events for the given day
     */
    public ArrayList<CalendarEvent> getEventsForDay(LocalDate date) {
        return eventMap.get(date.getDayOfYear());
    }

    public ArrayList<ArrayList<CalendarEvent>> getAllEvents() {
        ArrayList<ArrayList<CalendarEvent>> eventsByDay = new ArrayList<>();
        List<Integer> keys = eventMap.keySet().stream().sorted().collect(Collectors.toList());
        for (Integer key : keys)
            eventsByDay.add(eventMap.get(key));
        return eventsByDay;
    }

    public ArrayList<CalendarEvent> getEvents() {
        return events;
    }

    /***
     * Adds an event to the internal calendar data store
     * @param event The event to be added (OneTimeEvent or RegularEvent)
     */
    public void addEvent(CalendarEvent event) throws IllegalArgumentException {
        if (event instanceof OneTimeEvent) {
            OneTimeEvent oneTimeEvent = (OneTimeEvent) event;
            ArrayList<CalendarEvent> eventList = eventMap.get(oneTimeEvent.date.getDayOfYear());
            if (eventList == null) eventList = new ArrayList<>();
            eventList.stream().filter(x -> TimeInterval.overlaps(x.timeInterval, event.timeInterval))
                    .findFirst()
                    .ifPresent(x -> { throw new IllegalArgumentException(""); });
            event.date = LocalDate.ofYearDay(2019, oneTimeEvent.date.getDayOfYear());
            eventList.add(event);
            eventMap.put(oneTimeEvent.date.getDayOfYear(), eventList);
        } else if (event instanceof RegularEvent) {
            RegularEvent regularEvent = (RegularEvent) event;
            for (int i = regularEvent.startDate.getDayOfYear(); i < regularEvent.endDate.getDayOfYear() + 1; i++) {
                ArrayList<CalendarEvent> eventList = eventMap.get(i);
                if (eventList == null) eventList = new ArrayList<>();
                eventList.stream().filter(x -> TimeInterval.overlaps(x.timeInterval, event.timeInterval))
                        .findFirst()
                        .ifPresent(x -> { throw new IllegalArgumentException(""); });
                event.date = LocalDate.ofYearDay(2019, i);
                eventList.add(event);
                eventMap.put(i, eventList);
            }
        }
        events.add(event);
    }

    /***
     * Removes an event from the internal calendar data store
     * @param event The event to be removed (OneTimeEvent or RegularEvent)
     */
    public void removeEvent(CalendarEvent event) {
        if (event instanceof  OneTimeEvent) {
            OneTimeEvent oneTimeEvent = (OneTimeEvent) event;
            ArrayList<CalendarEvent> eventList = eventMap.get(oneTimeEvent.date.getDayOfYear());
            if (eventList == null) return;
            eventList.remove(event);
            eventMap.put(oneTimeEvent.date.getDayOfYear(), eventList);
        } else if (event instanceof  RegularEvent) {
            RegularEvent regularEvent = (RegularEvent) event;
            for (int i = regularEvent.startDate.getDayOfYear(); i < regularEvent.endDate.getDayOfYear() + 1; i++) {
                ArrayList<CalendarEvent> eventList = eventMap.get(i);
                if (eventList == null) eventList = new ArrayList<>();
                eventList.remove(event);
                eventMap.put(i, eventList);
            }
        }
    }

}
