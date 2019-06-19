package viewcontrollers;

import calendarevents.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

class CalendarViewController {

    private enum DisplayType { DAY, MONTH };
    private CalendarEventStore eventStore;

    CalendarViewController(CalendarEventStore eventStore) {
        this.eventStore = eventStore;
    }

    void displayGranularityMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuSelection = "";
        while (!menuSelection.toUpperCase().equals("D") && !menuSelection.toUpperCase().equals("M")) {
            System.out.println("[D]ay view or [M]view ?");
            menuSelection = scanner.nextLine().trim();
            System.out.println(menuSelection);
        }
        if (menuSelection.toUpperCase().equals("D"))
            displayDayView(LocalDate.now());
        else
            displayMonthView(LocalDate.now());
    }

    private void displayPaginationMenu(DisplayType displayType, LocalDate date) {
        Scanner scanner = new Scanner(System.in);
        String menuSelection = "";
        while (!Arrays.asList("P", "N", "G").contains(menuSelection.toUpperCase())) {
            System.out.println("[P]revious or [N]ext or [G]o back to main menu ? ");
            menuSelection = scanner.nextLine().trim();
        }
        if (menuSelection.toUpperCase().equals("P"))
            if (displayType == DisplayType.DAY)
                displayDayView(date.plusDays(-1));
            else
                displayMonthView(date.plusMonths(-1));
        else if (menuSelection.toUpperCase().equals("N"))
            if (displayType == DisplayType.DAY)
                displayDayView(date.plusDays(1));
            else
                displayMonthView(date.plusMonths(1));
        else
            Main.displayHomeScreen();
    }

    void displayGotoMenu() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        while (date == null) {
            System.out.print("Date (MM/DD/YYYY): ");
            String dateString = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                    .withLocale(Locale.getDefault());
            try {
                date = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                System.out.println("Please use the correct date format");
            }
        }
        displayDayView(date);
    }

    private void displayDayView(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        System.out.println(" " + formatter.format(date));
        ArrayList<CalendarEvent> currentEvents = eventStore.getEventsForDay(date);
        if (currentEvents != null) {
            currentEvents.sort((CalendarEvent p1, CalendarEvent p2) -> p2.startTime.compareTo(p1.startTime));
            for (CalendarEvent currentEvent : currentEvents)
                System.out.println(currentEvent.title + " : " + currentEvent.startTime + " - " + currentEvent.endTime);
        } else {
            System.out.println("No events scheduled today!");
        }
        System.out.println();
        displayPaginationMenu(DisplayType.DAY, date);
    }

    private void displayMonthView(LocalDate date) {
        printCalendarHeader(date);
        LocalDate currentDate = date.minusDays(date.getDayOfMonth() - 1);
        // Print each day of the month in its weekday position
        Month month = date.getMonth(); // Get the current month
        int days = month.length(false); // Get the total number of days in the month
        int dayCounter = 0; // Number of days printed
        while (dayCounter < days) {
            ArrayList<CalendarEvent> currentEvents = eventStore.getEventsForDay(currentDate.plusDays(dayCounter));
            if (currentEvents != null && currentEvents.size() != 0) { // Print highlighted day if its the current day
                System.out.print("{" + (dayCounter + 1) + "}");
            } else {
                for (int i = Integer.toString(dayCounter + 1).length(); i < 2; i++) { // Fill in space if day number is only one digit
                    System.out.print(" ");
                }
                System.out.print((dayCounter + 1) + " ");
            }
            // Start a new line if the current printing day is the last day of the week
            if (dayCounter % 7 == 0) System.out.println();
            dayCounter++;
        }
        System.out.println();
        displayPaginationMenu(DisplayType.MONTH, date);
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    static void printCalendarHeader(LocalDate date) {
        Month month = date.getMonth(); // Get the current month
        DayOfWeek firstWeekDayOfMonth = date.getDayOfWeek().minus(date.getDayOfMonth() - 1); // Get the first week day of the month
        // Start calendar printing
        System.out.println("    " + capitalize(month.name().toLowerCase()) + " " + date.getYear()); // Print month header
        System.out.println("Su Mo Tu We Th Fr Sa"); // Print day of week header
        // Print spaces until first weekday is reached
        int firstMonthDayPrintOffset = firstWeekDayOfMonth.getValue() % 7; // Set sunday to be the first day
        for (int i = 0; i < firstMonthDayPrintOffset * 3; i++) {
            System.out.print(" ");
        }
    }

}
