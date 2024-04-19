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
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

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
        List<String> coachIds = coachList.stream()
                .flatMap(coach -> Stream.of(coach.getId(), coach.getFirstName())).toList();
        TerminalManager.updateCompleter(coachIds);
        try{
            terminal = TerminalManager.getTerminal();
            lineReader = TerminalManager.getLineReader();
            List<Lesson> lessonList = null;
            while(true){
                Pair<Coach, Boolean> valuePair = getCoach(terminal, lineReader);
                if(valuePair == null) {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Aborted!");
                    return null;
                }
                if(valuePair.getInvalidFlag()){
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin + "Invalid ID Entered");
                    continue;
                }
                Coach coach = valuePair.getObj();
                if (coach == null) {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Coach Not Found!");
                }
                else {
                    lessonList = getLessonByCoach(coach);
                    if(lessonList.isEmpty()){
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().print(leftMargin+"No data found for " + coach.getFormattedFullName() + ", please select another Coach\n\n");
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

        coachListView.printCoachList();
        HelpText helpText = new HelpText(leftMargin + "TYPE [COACH NAME] and ENTER to VIEW LESSONS by COACH\n",
                leftMargin+"TYPE :c and ENTER to cancel\n\n"+leftMargin+"Press TAB to activate AUTOCOMPLETE\n",
                leftMargin+"COACH ID or COACH FIRSTNAME\n");

        String coachPrompt = "Coach [ID or FIRSTNAME]: ";
//        String regex = "^(?i)[a-z]+|^\\d{6}$";

        String coachString = InputValidator.inputGetter(terminal, lineReader, coachPrompt, helpText);
        if (coachString == null) return null;
        Coach coach = null;
        Pair<Coach, Boolean> coachValuePair = new Pair<>(coach,true);
        if(coachString.matches("^(?i)[a-z]+$")){
            coachValuePair.setInvalidFlag(false);
            coach = coachList.stream().filter(ch -> ch.getFirstName().equalsIgnoreCase(coachString)).findFirst().orElse(null);

        } else if(coachString.matches("^\\d{6}$")){
            coachValuePair.setInvalidFlag(false);
            coach = coachList.stream().filter(ch -> ch.getId().equalsIgnoreCase(coachString)).findFirst().orElse(null);
        }
        coachValuePair.setObject(coach);
        return coachValuePair;
    }
}
