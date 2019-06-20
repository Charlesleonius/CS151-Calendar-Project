package calendarevents;

import java.time.LocalDate;

public class CalendarEvent {

    public String title;
    public TimeInterval timeInterval;
    public LocalDate date;

    public String getStartTime() {
        int hours = this.timeInterval.getStartTime() / 60;
        int minutes = this.timeInterval.getStartTime() % 60;
        return hours + ":" + minutes;
    }

    public String getEndTime() {
        int hours = this.timeInterval.getEndTime() / 60;
        int minutes = this.timeInterval.getEndTime() % 60;
        return hours + ":" + minutes;
    }

}
