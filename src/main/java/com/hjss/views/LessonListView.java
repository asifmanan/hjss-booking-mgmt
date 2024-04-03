package com.hjss.views;

import com.hjss.controllers.LessonController;
import com.hjss.models.Coach;
import com.hjss.models.Lesson;
import com.hjss.utilities.TablePrinter;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public abstract class LessonListView {
    private LessonController lessonController;
    private List<Lesson> lessonList;
    private TablePrinter tablePrinter;
    private List<String> headers;
    private Map<String, Integer> columnWidths = new HashMap<>();
    protected String leftMargin = " ".repeat(3);

    
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
    public abstract Lesson getLessonFromPaginatedList();
    protected void printHeader(){
        tablePrinter.printHeader();
    }
    public void printList(){
        printHeader();
        for(Lesson lesson : this.lessonList){
            List<String> lessonData = getLessonData(lesson);
            tablePrinter.printRow(lessonData);
        }
    }


    public List<Lesson> getLessonsByWeek(YearWeek yearWeek){
        return this.lessonList.stream().filter
                        (lesson -> lesson.getWeekDayTimeSlot().getYearWeek().equals(yearWeek))
                            .collect(Collectors.toList());
    }
    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek){
        return this.lessonList.stream().filter
                        (lesson -> lesson.getWeekDayTimeSlot().getDayOfWeek().equals(dayOfWeek))
                            .collect(Collectors.toList());
    }
    public List<Lesson> getLessonByCoach(Coach coach){
        return this.lessonList.stream().filter
                        (lesson -> lesson.getCoach().equals(coach))
                            .collect(Collectors.toList());
    }
    protected List<String> getLessonData(Lesson lesson){
        List<String> lessonData = new ArrayList<>();

        lessonData.add(lesson.getId());
        lessonData.add(lesson.getLessonDate().toString());
        lessonData.add(lesson.getLessonDay().toString());
        lessonData.add(lesson.getStartTime().toString());
        lessonData.add(lesson.getEndTime().toString());
        lessonData.add(lesson.getGrade().toString());
        lessonData.add(lesson.getCoach().getFormattedFullName());

        return lessonData;
    }
    protected String getUserInput(Terminal terminal, LineReader lineReader){
        renderUserOption(terminal);
        return lineReader.readLine("BookLesson>>").trim();
    }
    private void renderUserOption(Terminal terminal){
        StringBuilder output = new StringBuilder();
        output.append("\n\n");

        output.append(leftMargin).append("TYPE :c to CANCEL and EXIT\n");
        output.append(leftMargin).append("TYPE n for NEXT PAGE\n");
        output.append(leftMargin).append("TYPE p for PREVIOUS PAGE\n");
        output.append(leftMargin).append("TYPE [BOOKING ID] to BOOK a LESSON\n");
        output.append(leftMargin).append("\n");

        terminal.writer().print(output);

        terminal.flush();
    }
    protected LessonController getLessonController(){
        return this.lessonController;
    }
    protected TablePrinter getTablePrinter(){
        return this.tablePrinter;
    }
    protected List<Lesson> getLessonList(){
        return this.lessonList;
    }
    private void renderPageHeading(Terminal terminal){

    }
}
