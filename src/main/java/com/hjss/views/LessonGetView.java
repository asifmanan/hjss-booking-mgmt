package com.hjss.views;

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
    private String leftMargin = " ".repeat(3);
    public LessonGetView(LessonController lessonController, CoachController coachController){
        this.lessonController = lessonController;
        this.coachController = coachController;
    }
    public Lesson getLessonsByChoice(){
        return getLessonsByChoice(null);
    }
    public Lesson getLessonsByChoice(Learner learner){
        String userChoice = getViewLessonChoice();
        if (userChoice==null) return null;

        if(userChoice.equalsIgnoreCase("day")){
            lessonListView = new LessonListViewByDay(lessonController);
        }
        if(userChoice.equalsIgnoreCase("grade")){
            lessonListView = new LessonListViewByGrade(lessonController);
        }
        if(userChoice.equalsIgnoreCase("coach")){
            lessonListView = new LessonListViewByCoach(lessonController, coachController);
        }
        if(learner!=null) return lessonListView.getLessonFromPaginatedList(learner);
        return lessonListView.getLessonFromPaginatedList();
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
