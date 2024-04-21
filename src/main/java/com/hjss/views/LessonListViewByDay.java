package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Lesson;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LessonListViewByDay extends LessonListView{
    public LessonListViewByDay(LessonController lessonController) {
        super(lessonController);
    }
    public LessonListViewByDay(LessonController lessonController, BookingController bookingController) {
        super(lessonController, bookingController);
    }


    @Override
    protected List<Lesson> fetchLessons(Terminal terminal, LineReader lineReader){
        List<String> daysOfWeekCompleter = Arrays.stream(DayOfWeek.values())
                .map(day -> day.name().toLowerCase()).collect(Collectors.toList());
        TerminalManager.updateCompleter(daysOfWeekCompleter);
        try {
            terminal = TerminalManager.getTerminal();
            lineReader = TerminalManager.getLineReader();
            List<Lesson> dayLessons = null;
            while (true){
                DayOfWeek dayOfWeek = getDayOfWeek(terminal, lineReader);
                if (dayOfWeek == null) return null;
                dayLessons = getLessonsByDay(dayOfWeek);
                if(dayLessons.isEmpty()){
                    terminal.writer().print(leftMargin+"No data found for " + dayOfWeek + ", please select another day\n\n");
                } else break;
            }
            return dayLessons;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private DayOfWeek getDayOfWeek(Terminal terminal, LineReader lineReader){
        HelpText helpText = new HelpText(leftMargin + "TYPE [DAY] and ENTER to VIEW LESSONS\n",
                leftMargin+"TYPE :c and ENTER to cancel\n\n"+leftMargin+"Press TAB to activate AUTOCOMPLETE\n",
                leftMargin+"DAYS OF WEEK: Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday\n");
        String dayPrompt = "DayOfWeek: ";
        String regex = "(?i)^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$";
        String dayOfWeekString = InputValidator.getAndValidateString(terminal, lineReader, dayPrompt, regex, helpText);
        if (dayOfWeekString == null) return null;
        else return DayOfWeek.valueOf(dayOfWeekString.trim().toUpperCase());
    }
}
