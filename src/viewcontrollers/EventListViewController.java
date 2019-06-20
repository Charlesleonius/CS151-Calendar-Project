package viewcontrollers;

import calendarevents.CalendarEvent;
import calendarevents.CalendarEventStore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventListViewController {

    private CalendarEventStore eventStore;

    EventListViewController(CalendarEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void displayEventList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        ArrayList<ArrayList<CalendarEvent>> eventsByDay = eventStore.getAllEvents();
        for (ArrayList<CalendarEvent> eventList : eventsByDay) {
            System.out.println(" " + formatter.format(eventList.get(0).date));
            for (CalendarEvent event : eventList)
                System.out.println(event.title + " : " + event.getStartTime() + " - " + event.getEndTime());
        }
        System.out.println();
        MainMenuViewController.displayHomeScreen();
    }

}
