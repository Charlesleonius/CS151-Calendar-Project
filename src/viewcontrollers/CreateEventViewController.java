package viewcontrollers;

import calendarevents.CalendarEventStore;
import calendarevents.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

class CreateEventViewController {

    private CalendarEventStore eventStore;

    CreateEventViewController(CalendarEventStore eventStore) {
        this.eventStore = eventStore;
    }

    /***
     * Displays the interactive form for creating a new event
     */
    void displayCreateEventForm() {
        OneTimeEvent event = new OneTimeEvent();
        Scanner scanner = new Scanner(System.in);
        String title = "";
        while (title.equals("")) {
            System.out.print("Title: ");
            title = scanner.nextLine();
        }
        event.title = title;
        while (event.date == null) {
            System.out.print("Date (MM/DD/YYYY): ");
            String dateString = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                    .withLocale(Locale.getDefault());
            try {
                event.date = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                System.out.println("Please use the correct date format (Ex: 01/12/2019)");
            }
        }
        String twentyFourHourTimePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(twentyFourHourTimePattern);
        String startTimeString = null;
        while (startTimeString == null) {
            System.out.print("Starting time (24-Hour): ");
            String timeStringInput = scanner.nextLine();
            if (pattern.matcher(timeStringInput).matches())
                startTimeString = timeStringInput;
            else
                System.out.println("Please use the correct 24 hour format (Ex: 10:30 or 16:00)");
        }
        String endTimeString = null;
        while (endTimeString == null) {
            System.out.print("Ending time (24-Hour): ");
            String timeStringInput = scanner.nextLine();
            if (pattern.matcher(timeStringInput).matches())
                endTimeString = timeStringInput;
            else
                System.out.println("Please use the correct 24 hour format (Ex: 10:30 or 16:00)");
        }
        try {
            event.timeInterval = new TimeInterval(startTimeString, endTimeString);
            eventStore.addEvent(event);
            System.out.println("\nEvent added to your calendar!\n");
        } catch (IllegalArgumentException e) {
            System.out.println("\nThere was a conflict with another event or your start and end times were invalid. Please try again.\n");
        }
        MainMenuViewController.displayHomeScreen();
    }

}
