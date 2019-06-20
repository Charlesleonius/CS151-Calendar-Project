package main;

import calendarevents.TimeInterval;

import static org.junit.Assert.*;

public class TimeIntervalTest {

    @org.junit.Test
    public void overlaps() {
        TimeInterval t1 = new TimeInterval(60, 80);
        TimeInterval t2 = new TimeInterval(70, 90);
        assertEquals(TimeInterval.overlaps(t1,t2), true);
        assertEquals(TimeInterval.overlaps(t2,t1), true);
        t1 = new TimeInterval(95,110);
        assertEquals(TimeInterval.overlaps(t1,t2), false);
        t2 = new TimeInterval(100,105);
        assertEquals(TimeInterval.overlaps(t1,t2), true);
        assertEquals(TimeInterval.overlaps(t2,t1), true);
    }
}
