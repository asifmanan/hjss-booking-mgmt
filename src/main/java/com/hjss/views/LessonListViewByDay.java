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
import java.util.List;

public class LessonListViewByDay extends LessonListView{
    public LessonListViewByDay(LessonController lessonController) {
        super(lessonController);
    }

    private DayOfWeek getDayOfWeek(Terminal terminal, LineReader lineReader){
        String dayPrompt = "DayOfWeek >>";
        String regex = "(?i)^(monday|tuesday|wednesday|thursday|friday|saturday|sunday)$";
        HelpText helpText = new HelpText("Enter day of the Week","Enter :c to cancel","");
        String dayOfWeekString = InputValidator.getAndValidateString(terminal, lineReader, dayPrompt, regex);
        return DayOfWeek.valueOf(dayOfWeekString.toUpperCase());
    }
    @Override
    public void viewLessonsPaginated() {
        try {
            TerminalManager.disableAutocomplete();
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();

            DayOfWeek dayOfWeek = getDayOfWeek(terminal, lineReader);

            List<Lesson> dayLessons = getLessonsByDay(dayOfWeek);

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
//                input = lineReader.readLine(String.format("Page %d/%d (n: next, p: previous, c: cancel)>>", currentPage + 1, pageCount)).trim();

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
