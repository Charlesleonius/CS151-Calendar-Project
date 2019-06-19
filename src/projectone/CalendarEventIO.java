package projectone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CalendarEventIO {

    static void loadEventsFromFile(String path, CalendarEventStore store) {
        try {
            FileReader fr = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                String title = line;
                String[] eventDetailParts = br.readLine().split(" ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy")
                        .withLocale(Locale.getDefault());
                if (eventDetailParts.length == 3) { // One time event
                    OneTimeEvent event = new OneTimeEvent();
                    event.title = title;
                    event.date = LocalDate.parse(eventDetailParts[0], formatter);
                    event.startTime = eventDetailParts[1];
                    event.endTime = eventDetailParts[2];
                    store.addEvent(event);
                } else if (eventDetailParts.length == 5) {
                    RegularEvent event = new RegularEvent();
                    event.title = title;
                    event.weekdays = eventDetailParts[0].split("");
                    event.startTime = eventDetailParts[1];
                    event.endTime = eventDetailParts[2];
                    event.startDate = LocalDate.parse(eventDetailParts[3], formatter);
                    event.endDate = LocalDate.parse(eventDetailParts[4], formatter);
                    store.addEvent(event);
                } else {
                    System.out.println("Invalid format");
                }
                line = br.readLine();
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

}
