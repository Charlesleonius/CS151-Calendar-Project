package viewcontrollers;

import calendarevents.CalendarEventStore;

import java.util.Arrays;
import java.util.Scanner;

public class DeleteEventViewController {

    private CalendarEventStore eventStore;

    DeleteEventViewController(CalendarEventStore eventStore) {
        this.eventStore = eventStore;
    }

    void displayGranularityMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuSelection = "";
        while (!Arrays.asList("S", "A", "DR").contains(menuSelection.toUpperCase())) {
            System.out.println("[S]elect a single one time event to be deleted.\n" +
                    "[A]ll one time events on the given date will be deleted.\n" +
                    "[DR] Delete all regular events matching the given name.");
            menuSelection = scanner.nextLine().trim();
        }
        System.out.println("Enter the date (dd/mm/yyyy): ");
    }
}
