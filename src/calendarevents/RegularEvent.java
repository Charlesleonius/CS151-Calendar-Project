package calendarevents;

import java.time.LocalDate;

class RegularEvent extends CalendarEvent {
    LocalDate startDate;
    LocalDate endDate;
    String[] weekdays;
}
