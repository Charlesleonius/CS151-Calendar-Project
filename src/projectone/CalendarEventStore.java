package projectone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarEventStore {

    private static boolean initialized = false;
    public static final CalendarEventStore shared = new CalendarEventStore();
    private static HashMap<Integer, ArrayList<CalendarEvent>> eventMap = new HashMap<>();

    public CalendarEventStore() throws ExceptionInInitializerError {
        if (initialized) throw new ExceptionInInitializerError("Singleton class has already been initialized!");
        initialized = true;
    }

    public ArrayList<CalendarEvent> getEventsForDay(LocalDate date) {
        return eventMap.get(date.getDayOfYear());
    }

    public void addEvent(CalendarEvent event) {
        if (event instanceof  OneTimeEvent) {
            OneTimeEvent oneTimeEvent = (OneTimeEvent) event;
            ArrayList<CalendarEvent> eventList = eventMap.get(oneTimeEvent.date.getDayOfYear());
            if (eventList == null) eventList = new ArrayList<>();
            eventList.add(event);
            eventMap.put(oneTimeEvent.date.getDayOfYear(), eventList);
        } else if (event instanceof  RegularEvent) {
            RegularEvent regularEvent = (RegularEvent) event;
            for (int i = regularEvent.startDate.getDayOfYear(); i < regularEvent.endDate.getDayOfYear() + 1; i++) {
                ArrayList<CalendarEvent> eventList = eventMap.get(i);
                if (eventList == null) eventList = new ArrayList<>();
                eventList.add(event);
                eventMap.put(i, eventList);
            }
        }
    }

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
