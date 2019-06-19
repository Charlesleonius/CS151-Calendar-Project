package viewcontrollers;

import calendarevents.CalendarEventIO;
import calendarevents.CalendarEventStore;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        displayHomeScreen();
        CalendarEventIO.loadEventsFromFile("../../Desktop/events.txt", CalendarEventStore.shared);
        System.out.println("\nLoading is done!\n");
        System.out.println("Select one of the following options:");
        System.out.println("[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> menuOptions = new ArrayList<>(Arrays.asList("V", "C", "G", "E", "D", "Q"));
        String menuSelection = scanner.nextLine();
        while(!menuOptions.contains(menuSelection.toUpperCase())) {
            System.out.println("Select one of the following options:");
            System.out.println("[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
        }
        CalendarViewController calendarViewController = new CalendarViewController(CalendarEventStore.shared);
        switch(menuSelection.toUpperCase()) {
            case "V":
                System.out.println("Selected view");
                calendarViewController.displayGranularityMenu();
                break;
            case "C":
                System.out.println("Selected create");
                CreateEventViewController createEventViewController = new CreateEventViewController(CalendarEventStore.shared);
                createEventViewController.displayCreateEventForm();
                break;
            case "G":
                System.out.println("Selected go to");
                calendarViewController.displayGotoMenu();
                break;
            case "E":
                System.out.println("Selected event");
                break;
            case "D":
                System.out.println("Selected delete");
                DeleteEventViewController deleteEventViewController = new DeleteEventViewController(CalendarEventStore.shared);
                deleteEventViewController.displayGranularityMenu();
                break;
            case "Q":
                System.out.println("Goodbye");
                break;
        }
    }

    static void displayHomeScreen() {
        LocalDateTime dt = LocalDateTime.now().plusMonths(0).minusDays(0); // Get the current date time
        Month month = dt.getMonth(); // Get the current months
        int currentDay = dt.getDayOfMonth(); // Get the current day of the month
        int days = month.length(false); // Get the total number of days in the month
        CalendarViewController.printCalendarHeader(dt.toLocalDate());
        // Print each day of the month in its weekday position
        int dayCounter = 0; // Number of days printed
        while (dayCounter < days) {
            if (dayCounter + 1 == currentDay) { // Print highlighted day if its the current day
                System.out.print("[" + (dayCounter + 1) + "]");
                if (dayCounter + 1 >= 10) System.out.print(" "); // Only print right padding if next day has left padding
            } else {
                for (int i = Integer.toString(dayCounter + 1).length(); i < 2; i++) { // Fill in space if day number is only one digit
                    System.out.print(" ");
                }
                System.out.print((dayCounter + 1) + " ");
            }
            // Start a new line if the current printing day is the last day of the week
            if (dayCounter % 7 == 0) System.out.println();
            dayCounter++;
        }
        System.out.println();
    }

}
