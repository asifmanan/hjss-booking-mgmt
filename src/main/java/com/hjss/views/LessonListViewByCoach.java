package com.hjss.views;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Coach;
import com.hjss.models.Lesson;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import com.hjss.utilities.Pair;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LessonListViewByCoach extends LessonListView {
    CoachController coachController;
    List<Coach> coachList;
    CoachListView coachListView;
    public LessonListViewByCoach(LessonController lessonController, CoachController coachController) {
        super(lessonController);
        this.coachController = coachController;
        coachList = coachController.getAllObjects();
        this.coachListView = new CoachListView(coachController);
    }

    @Override
    protected List<Lesson> fetchLessons(Terminal terminal, LineReader lineReader) {
        List<String> coachIds = coachList.stream().map(Coach::getId).toList();
        TerminalManager.updateCompleter(coachIds);
        try{
            terminal = TerminalManager.getTerminal();
            lineReader = TerminalManager.getLineReader();
            List<Lesson> lessonList = null;
            while(true){
                Pair<Coach, Boolean> valuePair = getCoach(terminal, lineReader);
                if(valuePair == null) return null;
                if(valuePair.getInvalidFlag()) continue;
                Coach coach = valuePair.getObj();
                if (coach!=null){
                    lessonList = getLessonByCoach(coach);
                    if(lessonList.isEmpty()){
                        terminal.writer().print(leftMargin+"No data found for " + coach.getFormattedFullName() + ", please select another day\n\n");
                    } else break;
                }
            }
            return lessonList;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private Pair<Coach, Boolean> getCoach(Terminal terminal, LineReader lineReader){
        Pair<Coach, Boolean> coachValuePair = null;
        coachListView.printCoachList();
        HelpText helpText = new HelpText(leftMargin + "TYPE [COACH NAME] and ENTER to VIEW LESSONS by COACH\n",
                leftMargin+"TYPE :c and ENTER to cancel\n",
                leftMargin+"COACH ID or COACH FIRSTNAME\n");

        String dayPrompt = "Coach [ID or FIRSTNAME]: ";
        String regex = "^(?i)[a-z]+|^\\d{8}$";
        String coachString = InputValidator.getAndValidateString(terminal, lineReader, dayPrompt, regex, helpText);
        if (coachString == null) return null;
        Coach coach = null;
//        coachString = coachString.trim();
        if(coachString.matches("^(?i)[a-z]+$")){
            coach = coachList.stream().filter(ch -> ch.getFirstName().equalsIgnoreCase(coachString)).findFirst().orElse(null);
        } else if(coachString.matches("^\\d{8}$")){
            coach = coachList.stream().filter(ch -> ch.getId().equalsIgnoreCase(coachString)).findFirst().orElse(null);
        }
        if (coach != null) return new Pair<>(coach, false);
        return new Pair<>(null, true);
    }
}
