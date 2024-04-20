package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.Grade;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LessonListViewByGrade extends LessonListView{
    public LessonListViewByGrade(LessonController lessonController) {
        super(lessonController);
    }
    public LessonListViewByGrade(LessonController lessonController, BookingController bookingController) {
        super(lessonController, bookingController);
    }

    @Override
    protected List<Lesson> fetchLessons(Terminal terminal, LineReader lineReader) {
        List<String> gradeCompleter = Arrays.stream(Grade.values())
                        .filter(Grade -> Grade.getValue()!=0)
                        .map(Grade -> Integer.toString(Grade.getValue())).toList();
        TerminalManager.updateCompleter(gradeCompleter);
        try{
            terminal = TerminalManager.getTerminal();
            lineReader = TerminalManager.getLineReader();
            List<Lesson> lessonList = null;
            while(true){
                String gradeString = getGrade(terminal, lineReader);
                if (gradeString==null) return null; //for user cancel
                Grade grade = Grade.fromString(gradeString);
                lessonList = getLessonsByGrade(grade);
                if(lessonList.isEmpty()){
                    terminal.writer().print(leftMargin+"No data found for grade ["+grade+"], please select another grade level");
                } else break;
            }
            return lessonList;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private String getGrade(Terminal terminal, LineReader lineReader) {
        HelpText helpText = new HelpText(leftMargin + "TYPE [GRADE LEVEL] and ENTER to VIEW LESSONS\n",
                leftMargin+"TYPE :c and ENTER to cancel\n\n"+leftMargin+"Press TAB to activate AUTOCOMPLETE\n",
                leftMargin+"GRADE LEVELS: 1 | 2 | 3 | 4 | 5 \n");
        String gradePrompt = "GradeLevel: ";
        String regex = "[12345]";
        String grade = InputValidator.getAndValidateString(terminal, lineReader, gradePrompt, regex, helpText);
        if (grade == null) return null; //for user cancel
        else return grade.trim();
    }
}
