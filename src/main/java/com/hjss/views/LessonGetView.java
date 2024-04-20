package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.CoachController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LessonGetView {
    private LessonController lessonController;
    private CoachController coachController;
    private LessonListView lessonListView;
    private BookingController bookingController;
    private String leftMargin = " ".repeat(3);
    public LessonGetView(LessonController lessonController, CoachController coachController){
        this.lessonController = lessonController;
        this.coachController = coachController;
    }
    public LessonGetView(LessonController lessonController, CoachController coachController, BookingController bookingController){
        this(lessonController, coachController);
        this.bookingController = bookingController;
    }
    public Lesson getLessonsByChoice(){
        return getLessonsByChoice(null);
    }
    public Lesson getLessonsByChoice(Learner learner){
        String userChoice = getViewLessonChoice();
        if (userChoice==null) return null;

        lessonListView = getLessonListViewByChoice(userChoice);

        if(learner!=null) return lessonListView.getLessonFromPaginatedList(learner);
        return lessonListView.getLessonFromPaginatedList();
    }
    private LessonListView getLessonListViewByChoice(String userChoice) {
        return switch (userChoice.toLowerCase()) {
            case "day" -> createLessonListViewByDay();
            case "grade" -> createLessonListViewByGrade();
            case "coach" -> createLessonListViewByCoach();
//            Although this case is handled by getViewLessonChoice, it only accepts valid strings
            default -> throw new IllegalArgumentException("Invalid choice: " + userChoice);
        };
    }

    private LessonListView createLessonListViewByDay() {
        if (bookingController != null) {
            return new LessonListViewByDay(lessonController, bookingController);
        } else {
            return new LessonListViewByDay(lessonController);
        }
    }

    private LessonListView createLessonListViewByGrade() {
        if (bookingController != null) {
            return new LessonListViewByGrade(lessonController, bookingController);
        } else {
            return new LessonListViewByGrade(lessonController);
        }
    }

    private LessonListView createLessonListViewByCoach() {
        if (bookingController != null) {
            return new LessonListViewByCoach(lessonController, coachController, bookingController);
        } else {
            return new LessonListViewByCoach(lessonController, coachController);
        }
    }

    private String getViewLessonChoice(){
        List<String> choices = Arrays.asList("day","grade","coach");
        try {
            TerminalManager.updateCompleter(choices);
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            HelpText helpText = new HelpText();
            helpText.setText("\n"+leftMargin+"Select how you would like to view the lessons:"+
                    "\n"+leftMargin+"TYPE grade and PRESS ENTER to VIEW LESSONS by GRADE"+
                    "\n"+leftMargin+"TYPE day and PRESS ENTER to VIEW LESSONS by DAY"+
                    "\n"+leftMargin+"TYPE coach and PRESS ENTER to VIEW LESSONS by COACH"+
                    "\n\n"+leftMargin+"TYPE :c to cancel operation and go BACK");
            String prompt = "ViewLessons>>";
            while (true){
                String input = InputValidator.inputGetter(terminal, lineReader, prompt, helpText);
                if(input==null) return null;
                if(choices.contains(input)){
                    return input;
                } else {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Invalid Choice, please try again.\n");
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
