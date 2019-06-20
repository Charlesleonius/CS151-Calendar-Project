package viewcontrollers;

import calendarevents.CalendarEvent;
import calendarevents.CalendarEventStore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class DeleteEventViewController {

    private CalendarEventStore eventStore;

    DeleteEventViewController(CalendarEventStore eventStore) {
        this.eventStore = eventStore;
    }

    /**
     * Displays a menu and allows a user to select an event deletion granularity
     * Precondition Class is initialized with valid event store
     */
    void displayGranularityMenu() {
        Scanner scanner = new Scanner(System.in);
        // Get valid menu selection from user
        String menuSelection = "";
        while (!Arrays.asList("S", "A", "DR").contains(menuSelection.toUpperCase())) {
            System.out.println("[S]elect a single one time event to be deleted.\n" +
                    "[A]ll one time events on the given date will be deleted.\n" +
                    "[DR] Delete all regular events matching the given name.");
            menuSelection = scanner.nextLine().trim();
        }
        if (menuSelection.toUpperCase().equals("A") || menuSelection.toUpperCase().equals("S")) {
            // Get valid date from user
            LocalDate date = null;
            while (date == null) {
                System.out.println("Enter the date (dd/mm/yyyy): ");
                String dateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                        .withLocale(Locale.getDefault());
                try {
                    date = LocalDate.parse(dateString, formatter);
                } catch (Exception e) {
                    System.out.println("Please use the correct date format");
                }
            }
            // Display all events on selected day sorted by start time
            ArrayList<CalendarEvent> currentEvents = eventStore.getEventsForDay(date);
            if (currentEvents != null) {
                currentEvents.sort((CalendarEvent p1, CalendarEvent p2) -> p2.timeInterval.compareTo(p1.timeInterval));
                for (CalendarEvent currentEvent : currentEvents)
                    System.out.println(currentEvent.title + " : " + currentEvent.getStartTime() + " - " + currentEvent.getEndTime());
            } else {
                System.out.println("No events scheduled on this date!");
                return;
            }
            System.out.println();
            // Get valid event name
            String title = null;
            while (title == null || title.equals("")) {
                System.out.println("Please enter the exact name of the event to delete: ");
                title = scanner.nextLine();
            }
            // Find event with matching name and remove the event if found
            String finalTitle = title;
            currentEvents.stream().filter(n -> n.title.equals(finalTitle))
                    .findFirst()
                    .ifPresent(CalendarEventStore.shared::removeEvent);
            MainMenuViewController.displayHomeScreen();
        } else {
            System.out.println("Enter the exact name of the regular event to delete: ");
        }
    }
}
