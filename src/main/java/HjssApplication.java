import com.hjss.controllers.TimeTableManager;
import com.hjss.models.*;
import com.hjss.views.EntryView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class HjssApplication {
    public static void main(String[] args) {
        EntryView entryView = new EntryView();
        entryView.initializeMenu();
    }


    public static void timeTableManagerTest(){
        TimeTableManager ttManager = new TimeTableManager();
        TimeTable tt = ttManager.getWeeklyScheduleContainingDate(LocalDate.now());

        tt.addTimeSlotOnDay(DayOfWeek.FRIDAY, new TimeSlot(LocalTime.of(16,0),LocalTime.of(17,0)));
        tt.printSchedule();
    }

    public static void timeTableTest(){
        DayOfWeek friday = DayOfWeek.FRIDAY;
        DayOfWeek monday = DayOfWeek.MONDAY;

        TimeTable timeTable = new TimeTable(LocalDate.now());
        TimeTable timeTableWithoutDate = new TimeTable();

        TimeSlot timeSlot = new TimeSlot(LocalTime.of(16,0),LocalTime.of(17,0));
        boolean check = timeTable.addTimeSlotOnDay(friday,timeSlot);
        if(!check) System.out.println("Time slot clashes...");
        else System.out.println("Time Slot Successfully added");

        TimeSlot timeSlot2 = new TimeSlot(LocalTime.of(17,0),LocalTime.of(18,0));
        check = timeTable.addTimeSlotOnDay(friday,timeSlot2);
        if(!check) System.out.println("Time slot clashes...");
        else System.out.println("Time Slot Successfully added");

        TimeSlot timeSlot3 = new TimeSlot(LocalTime.of(16,30),LocalTime.of(18,30));
        check = timeTable.addTimeSlotOnDay(friday,timeSlot);
        if(!check) System.out.println("Time slot clashes...");
        else System.out.println("Time Slot Successfully added");

        timeTable.printSchedule();

        System.out.println("#################");
        check = timeTableWithoutDate.addTimeSlotOnDay(monday, timeSlot);
        if(!check) System.out.println("Time slot clashes...");
        else System.out.println("Time Slot Successfully added");

        timeTableWithoutDate.printSchedule();

    }
}
