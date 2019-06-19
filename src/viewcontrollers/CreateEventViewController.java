package viewcontrollers;

import calendarevents.CalendarEventStore;
import calendarevents.*;

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

    void displayCreateEventForm() {
        OneTimeEvent event = new OneTimeEvent();
        Scanner scanner = new Scanner(System.in);
        String title = "";
        while (title.equals("")) {
            System.out.print("Title: ");
            title = scanner.nextLine();
        }
        event.title = title;
        System.out.println();
        while (event.date == null) {
            System.out.print("Date (MM/DD/YYYY): ");
            String dateString = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                    .withLocale(Locale.getDefault());
            try {
                event.date = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
               System.out.println("Please use the correct date format");
            }
        }
        String twentyFourHourTimePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(twentyFourHourTimePattern);
        String timeString;
        while (event.startTime == null) {
            System.out.println("Starting time (24-Hour): ");
            timeString = scanner.nextLine();
            if (pattern.matcher(timeString).matches()) {
               event.startTime = timeString;
            }
        }
        while (event.endTime == null) {
            System.out.println("Ending time (24-Hour): ");
            timeString = scanner.nextLine();
            if (pattern.matcher(timeString).matches()) {
                event.endTime = timeString;
            }
        }
        eventStore.addEvent(event);
    }

}
