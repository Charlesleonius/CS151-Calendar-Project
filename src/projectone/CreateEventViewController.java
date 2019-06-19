package projectone;

import java.util.Scanner;

public class CreateEventViewController {

    private CalendarEventStore eventStore;

    CreateEventViewController(CalendarEventStore eventStore) {
        this.eventStore = eventStore;
    }

   void displayCreateEventForm() {
        CalendarEvent event = new CalendarEvent();
       Scanner scanner = new Scanner(System.in);
       String title = "";
       while (title.equals("")) {
           System.out.print("Title: ");
           title = scanner.nextLine();
       }
       event.title = title;
       System.out.println("Date (MM/DD/YYYY): ");
       System.out.println("Starting time (24-Hour): ");
       System.out.println("Ending time (24-Hour): ");
   }

}
