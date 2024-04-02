package com.hjss.views;

import com.hjss.controllers.LessonController;
import com.hjss.models.Lesson;
import com.hjss.utilities.TablePrinter;
import io.consolemenu.ConsoleMenu;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;
import org.threeten.extra.YearWeek;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LessonListView {
    private LessonController lessonController;
    private List<Lesson> lessonList;
    private TablePrinter tablePrinter;
    private List<String> headers;
    private Map<String, Integer> columnWidths = new HashMap<>();

    public LessonListView(LessonController lessonController){
        this.lessonController = lessonController;
        this.lessonList = lessonController.getAllObjects();
        this.headers = Arrays.asList("Id", "Date", "Day", "Start", "End", "Grade", "Coach");
        setColumnWidths(this.headers);
        this.tablePrinter = new TablePrinter(this.headers, this.columnWidths);
    }
    private void setColumnWidths(List<String> headers) {
        this.columnWidths = Map.of(
                "Id",11,
                "Date",13,
                "Day",11,
                "Start",9,
                "End",9,
                "Grade",7,
                "Coach",12
        );
    }
    private void printHeader(){
        tablePrinter.printHeader();
    }
    public void printList(){
        printHeader();
        for(Lesson lesson : this.lessonList){
            List<String> lessonData = getLessonData(lesson);
            tablePrinter.printRow(lessonData);
        }
    }
    public void printPaginatedList() {
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
                weeklyLessons = getWeeklyLessons(currentYearWeek);
                terminal.puts(InfoCmp.Capability.clear_screen);

                String weekInfo = String.format("Displaying lessons for Week %d of %d", currentYearWeek.getWeek(), currentYearWeek.getYear());
                terminal.writer().println(weekInfo);

                if (weeklyLessons == null || weeklyLessons.isEmpty()) {
                    terminal.writer().println("No lessons available for this week.");
                } else {
                    printHeader();
                    for (Lesson lesson : weeklyLessons) {
                        lessonData = getLessonData(lesson);
                        tablePrinter.printRow(lessonData);
                    }
                }

                input = getInput(terminal, lineReader);
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
    }
    public List<Lesson> getWeeklyLessons(YearWeek yearWeek){
        return this.lessonList.stream().filter
                        (lesson -> lesson.getWeekDayTimeSlot().getYearWeek().equals(yearWeek))
                            .collect(Collectors.toList());
    }
    private List<String> getLessonData(Lesson lesson){
        List<String> lessonData = new ArrayList<>();

        lessonData.add(lesson.getId());
        lessonData.add(lesson.getLessonDate().toString());
        lessonData.add(lesson.getLessonDay().toString());
        lessonData.add(lesson.getStartTime().toString());
        lessonData.add(lesson.getEndTime().toString());
//            lessonData.add(lesson.getGradeInt().toString());
        lessonData.add(lesson.getGrade().toString());
        lessonData.add(lesson.getCoach().getFormattedFullName());

        return lessonData;
    }
    private String getInput(Terminal terminal, LineReader lineReader){
        String input="";
        StringBuilder output = new StringBuilder();
        output.append("\n\n");
        String leftMargin = "    ";
        output.append(leftMargin).append("TYPE :c to CANCEL and EXIT\n");
        output.append(leftMargin).append("TYPE n for NEXT PAGE\n");
        output.append(leftMargin).append("TYPE p for PREVIOUS PAGE\n");
        output.append(leftMargin).append("TYPE [BOOKING ID] to BOOK a LESSON\n");
        output.append(leftMargin).append("\n");
        terminal.writer().print(output);
        terminal.flush();
        input = lineReader.readLine("BookLesson>>").trim();
        return input;
    }
}
