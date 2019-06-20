package calendarevents;

import java.sql.Time;

public class TimeInterval implements Comparable<TimeInterval> {


    private int startTime;
    private int endTime;

    public TimeInterval(int startTime, int endTime) {
        if (startTime >= endTime || startTime < 0 || endTime > 1440)
            throw new IllegalArgumentException("Invalid start and end time");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeInterval(String startTimeString, String endTimeString) {
        String[] startTimeParts = startTimeString.split(":");
        int startTime = (Integer.parseInt(startTimeParts[0]) * 60) + (Integer.parseInt(startTimeParts[1]));
        String[] endTimeParts = endTimeString.split(":");
        int endTime = (Integer.parseInt(endTimeParts[0]) * 60) + (Integer.parseInt(endTimeParts[1]));
        if (startTime >= endTime || startTime < 0 || endTime > 1440)
            throw new IllegalArgumentException("Invalid start and end time");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() { return startTime; }

    public int getEndTime() { return endTime; }

    /***
     * Determines if two time intervals overlap
     * @param t1 The first time interval
     * @param t2 The second time interval
     * @return A boolean indicating whether or not the intervals overlap
     */
    public static boolean overlaps(TimeInterval t1, TimeInterval t2) {
       return (t1.startTime >= t2.startTime && t1.startTime <= t2.endTime)
               || (t1.endTime >= t2.startTime && t1.endTime <= t2.endTime)
               || (t1.startTime <= t2.startTime && t1.endTime >= t2.endTime);
    }

    @Override
    public int compareTo(TimeInterval t2) {
        return Integer.compare(t2.startTime, this.startTime);
    }
}
