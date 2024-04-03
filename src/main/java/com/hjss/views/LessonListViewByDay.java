package com.hjss.views;

import com.hjss.controllers.LessonController;
import com.hjss.models.Lesson;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LessonListViewByDay extends LessonListView{
    public LessonListViewByDay(LessonController lessonController) {
        super(lessonController);
    }

    private DayOfWeek getDayOfWeek(Terminal terminal, LineReader lineReader){
        HelpText helpText = new HelpText(leftMargin + "TYPE [DAY] and ENTER to VIEW LESSONS\n",
                leftMargin+"TYPE :c and ENTER to cancel\n",
                leftMargin+"DAYS OF WEEK: Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday\n");
        String dayPrompt = "DayOfWeek: ";
        String regex = "(?i)^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$";
        String dayOfWeekString = InputValidator.getAndValidateString(terminal, lineReader, dayPrompt, regex, helpText);
        if (dayOfWeekString == null) return null;
        else return DayOfWeek.valueOf(dayOfWeekString.trim().toUpperCase());
    }
    private List<Lesson> getAndValidateLessonList(){
        List<String> daysOfWeekCompleter = Arrays.stream(DayOfWeek.values())
                .map(Enum::name).collect(Collectors.toList());
        TerminalManager.updateCompleter(daysOfWeekCompleter);
        try {
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
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
            return null;
        }
    }
    @Override
    public void viewLessonsPaginated() {
        try {
            TerminalManager.disableAutocomplete();
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();

            List<Lesson> dayLessons = getAndValidateLessonList();
            if (dayLessons==null) return;

            int lessonCount = dayLessons.size();
            int pageSize = 10;
            int pageCount = (int) Math.ceil((double) lessonCount / pageSize);

            String input = "";
            int currentPage = 0;

            do {
                terminal.puts(InfoCmp.Capability.clear_screen);
                printHeader();

                int start = currentPage * pageSize;
                int end = Math.min(start + pageSize, lessonCount);

                for (int i = start; i < end; i++) {
                    Lesson lesson = dayLessons.get(i);
                    List<String> lessonData = getLessonData(lesson);
                    getTablePrinter().printRow(lessonData);
                }
                terminal.writer().println(String.format("\n   Page %d/%d", currentPage + 1, pageCount));

                input = getUserInput(terminal, lineReader);

                switch (input) {
                    case "n":
                        if (currentPage < pageCount - 1) {
                            currentPage++;
                        }
                        break;
                    case "p":
                        if (currentPage > 0) {
                            currentPage--;
                        }
                        break;
                }
            } while (!input.equalsIgnoreCase(":c"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
