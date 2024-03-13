import com.hjss.backend.*;
import com.hjss.frontend.Menu;
import com.hjss.managers.TimeTableManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class HjssBookingManagement {
    public static void main(String[] args) {
//        timeTableTest();
        timeTableManagerTest();
    }

    public static void timeTableTest2(){
        TimeTable tt = new TimeTable();
        tt.addTimeSlotOnDay(DayOfWeek.MONDAY, new TimeSlot(LocalTime.of(16,00),LocalTime.of(17,00)));
        tt.printSchedule();
    }

    public static void timeTableManagerTest(){
        TimeTableManager ttManager = new TimeTableManager();
        TimeTable tt = ttManager.getWeeklyScheduleContainingDate(LocalDate.now());

        tt.addTimeSlotOnDay(DayOfWeek.FRIDAY, new TimeSlot(LocalTime.of(16,0),LocalTime.of(17,0)));
        tt.printSchedule();
    }

    public static void timeTableTest(){
        TimeTable schedule = new TimeTable();
        DayOfWeek day = DayOfWeek.FRIDAY;

        TimeSlot timeSlot = new TimeSlot(LocalTime.of(16,0),LocalTime.of(17,0));
        boolean check = schedule.addTimeSlotOnDay(day,timeSlot);
        System.out.println(check);

        timeSlot = new TimeSlot(LocalTime.of(17,0),LocalTime.of(18,0));
        check = schedule.addTimeSlotOnDay(day,timeSlot);
        System.out.println(check);

        timeSlot = new TimeSlot(LocalTime.of(16,30),LocalTime.of(18,30));
        check = schedule.addTimeSlotOnDay(day,timeSlot);
        if(!check) System.out.println("Time slot clashes...");


//        LocalDate wednesdayDate = LocalDate.of(2024, 3, 13);
//        LocalDate saturdayDate = LocalDate.of(2024, 3, 16);
    }

    public static Learner newLearner(){
        return new Learner("Asif", "Manan",
                Util.Gender.Male, LocalDate.of(1985, 4, 30),
                0, "+447769728661");
    }
    public static void lessonTest(){
        Learner learner = newLearner();
        // Create some sample lessons
        Lesson gradeOne = new Lesson(Util.GradeEnum.ONE, new Coach("Jack","Sparrow", Util.Gender.Male));
        Lesson gradeTwo = new Lesson(Util.GradeEnum.TWO, new Coach("Taylor","Swift",Util.Gender.Female));
        Lesson gradeThree = new Lesson(Util.GradeEnum.THREE, new Coach("Gunter","Stick",Util.Gender.Male));
        Lesson gradeFour = new Lesson(Util.GradeEnum.FOUR, new Coach("Walter","Back",Util.Gender.Male));
        Lesson gradeFive = new Lesson(Util.GradeEnum.FIVE, new Coach("Alice","Smith",Util.Gender.Female));

        boolean operation = gradeOne.addLearner(learner);
        if(operation) System.out.println("Success");
        operation = gradeOne.addLearner(learner);
        if(operation) System.out.println("Success");
        else System.out.println("Already exists");
    }

    public void menuTest(){
        Menu mainMenu = new Menu();
        mainMenu.addMenuItem(1,"Add New Learner");
        mainMenu.addMenuItem(2,"View Learner");
        mainMenu.addMenuItem(0,"Exit");

        mainMenu.run();
    }

    public void gradeLevelTest(){
        Learner l1 = newLearner();
        for(int i = 0; i < 6; i++){
            l1.gradeLevelUp();
            System.out.println("Grade: " + l1.getGradeLevel());
        }
        l1.gradeLevelUp();
        System.out.println("Grade: " + l1.getGradeLevel());

        LocalDate date = Util.convertToDate("2023-02-29");
        if (date != null){
            System.out.println(date);
        }
    }
}
