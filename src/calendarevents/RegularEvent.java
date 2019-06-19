package calendarevents;

import calendarevents.CalendarEvent;

import java.time.LocalDate;

class RegularEvent extends CalendarEvent {
    LocalDate startDate;
    LocalDate endDate;
    String[] weekdays;
}
