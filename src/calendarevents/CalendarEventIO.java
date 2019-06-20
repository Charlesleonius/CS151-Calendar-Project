package calendarevents;

import org.w3c.dom.events.Event;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarEventIO {

    /***
     * Reads a calendar file and populates the given event store
     * @param path Path of the file to be read
     * @param store The event store for which events will be loaded
     */
    public static void loadEventsFromFile(String path, CalendarEventStore store) {
        try {
            FileReader fr = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String title = line;
                String[] eventDetailParts = br.readLine().split(" ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy")
                        .withLocale(Locale.getDefault());
                CalendarEvent event = null;
                if (eventDetailParts.length == 3) { // One time event
                    event = new OneTimeEvent();
                    event.title = title;
                    ((OneTimeEvent) event).date = LocalDate.parse(eventDetailParts[0], formatter);
                } else if (eventDetailParts.length == 5) { // Regular event
                    event = new RegularEvent();
                    event.title = title;
                    ((RegularEvent) event).weekdays = eventDetailParts[0].split("");
                    ((RegularEvent) event).startDate = LocalDate.parse(eventDetailParts[3], formatter);
                    ((RegularEvent) event).endDate = LocalDate.parse(eventDetailParts[4], formatter);
                } else {
                    System.out.println("!!! Could not load your calendar file. Please verify that it's not corrupted !!!");
                    System.exit(1);
                }
                event.timeInterval = new TimeInterval(eventDetailParts[1], eventDetailParts[2]);
                store.addEvent(event);
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("!!! Could not load your calendar file. Please verify that it's not corrupted !!!");
            System.exit(1);
        }
    }

    /***
     * Creates a new calendar file and writes the events in a format that can be read back into the program
     * @param path Path of the output file
     * @param store The event store from which events will be written
     */
    public static void writeEventsToFile(String path, CalendarEventStore store) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy")
                    .withLocale(Locale.getDefault());
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            ArrayList<CalendarEvent> events = store.getEvents();
            for (CalendarEvent event: events) {
                if (event instanceof OneTimeEvent) {
                    writer.println(event.title);
                    String date = ((OneTimeEvent) event).date.format(formatter);
                    writer.println(date + " " + event.getStartTime() + " " + event.getEndTime());
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("!!! Could not write your calendar file. Please verify you specified a proper output file !!!");
            System.exit(1);
        }
    }

}
