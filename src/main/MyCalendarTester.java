package main;

import calendarevents.CalendarEventIO;
import calendarevents.CalendarEventStore;
import viewcontrollers.MainMenuViewController;

public class MyCalendarTester {

    public static void main(String[] args) {
        CalendarEventIO.loadEventsFromFile("../../Desktop/events.txt", CalendarEventStore.shared);
        System.out.println("\nLoading is done!\n");
        MainMenuViewController.displayHomeScreen();
    }

}
