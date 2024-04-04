package com.hjss.views;

import com.hjss.controllers.LessonController;
import com.hjss.models.Lesson;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;
import org.threeten.extra.YearWeek;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LessonListViewByWeek extends LessonListView{
    public LessonListViewByWeek(LessonController lessonController) {
        super(lessonController);
    }

    @Override
    protected List<Lesson> fetchLessons(Terminal terminal, LineReader lineReader) {
        return null;
    }

    private Lesson getLessonById(String lessonId){
        return getLessonController().getLesson(lessonId);
    }
    public Lesson getLessonFromPaginatedList() {
        try {
            TerminalManager.disableAutocomplete();
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();

            YearWeek yearWeek = YearWeek.from(LocalDate.now());
            List<Lesson> weeklyLessons;
            List<String> lessonData;

            String input = "";
            int plusWeek = 0;

            do {
                YearWeek currentYearWeek = yearWeek.plusWeeks(plusWeek);
                weeklyLessons = getLessonsByWeek(currentYearWeek);
                terminal.puts(InfoCmp.Capability.clear_screen);

                String weekInfo = String.format("Displaying lessons for Week %d of %d", currentYearWeek.getWeek(), currentYearWeek.getYear());
                terminal.writer().println(weekInfo);

                if (weeklyLessons == null || weeklyLessons.isEmpty()) {
                    terminal.writer().println("No lessons available for this week.");
                } else {
                    printHeader();
                    for (Lesson lesson : weeklyLessons) {
                        lessonData = getLessonData(lesson);
                        getTablePrinter().printRow(lessonData);
                    }
                }

                input = getUserInput(terminal, lineReader);
                if (input.matches("(?i)LE\\d{6}")){
                    Lesson lesson = getLessonById(input);
                    if (lesson!=null){
                        return lesson;
                    }
                }
                switch (input) {
                    case "n":
                        plusWeek++;
                        break;
                    case "p":
                        plusWeek--;
                        break;
                }

            } while (!input.equalsIgnoreCase(":c"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
