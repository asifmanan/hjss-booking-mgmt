package com.hjss.views;

import com.hjss.controllers.LessonController;
import com.hjss.models.Lesson;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import com.hjss.utilities.Pair;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class LessonFilterByGrade {
    private LessonController lessonController;
    private final String leftMargin = "   ";
    private List<Lesson> lessonListByGrade;
    LessonFilterByGrade(LessonController lessonController){
        this.lessonController = lessonController;
    }

    private Integer getGrade() {
        Pair<String, Boolean> inputPair;
        while (true){
            HelpText helpText = new HelpText(leftMargin + "TYPE [GRADE LEVEL] and ENTER to VIEW LESSONS\n",
                    leftMargin+"TYPE :c and ENTER to cancel\n",
                    leftMargin+"GRADE LEVELS: 1 | 2 | 3 | 4 | 5 \n");
            try{
                Terminal terminal = TerminalManager.getTerminal();
                LineReader lineReader = TerminalManager.getLineReader();

                String gradePrompt = "GradeLevel: ";
                String regex = "[12345]";
                inputPair = InputValidator.getAndValidateStringSingleRun(terminal, lineReader, gradePrompt, regex, helpText);
                if (inputPair==null) return null;
                if(!inputPair.getInvalidFlag()){
                    int grade = Integer.parseInt(inputPair.getObj());
                    int lessonCount = lessonController.filterByGradeCount(grade);
                    if(lessonCount>0){
                        return grade;
                    }
                    else{
                        terminal.writer().println(leftMargin + "No lessons found for the selected Grade, please choose another.");
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void getGradeAndFetchLessons(){
        Integer grade = getGrade();
        if(grade != null) {
            fetchLessonsByGrade(grade);
        }
    }
    private void fetchLessonsByGrade(int grade){
        this.lessonListByGrade = lessonController.filterByGrade(grade);
    }
}
