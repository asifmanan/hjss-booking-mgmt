package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.utilities.Pair;
import com.hjss.utilities.TablePrinter;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.*;

public class MonthlyLearnerReportView {
    private BookingController bookingController;
    private LearnerController learnerController;
    private List<Learner> learnerList;
    private TablePrinter tablePrinter;
    private int tableWidth;
    private List<String> lessonListHeader;
    private Map<String, Integer> lessonListColWidth = new HashMap<>();
    public MonthlyLearnerReportView(BookingController bookingController, LearnerController learnerController){
        this.bookingController = bookingController;
        this.learnerController = learnerController;
        this.lessonListHeader = Arrays.asList(  "LessonId",
                                                "LessonDate",
                                                "Status",
                                                "StartTime",
                                                "EndTime",
                                                "LessonGrade",
                                                "Coach"
                                                );
        setLessonListColWidth();
        updateTableWidth();
        this.tablePrinter = new TablePrinter(this.lessonListHeader,this.lessonListColWidth);
    }
    private void updateLearnerList(){
        learnerList = learnerController.getAllObjects();
    }
    private void setLessonListColWidth() {
        this.lessonListColWidth = Map.of(
                "LessonId",10,
                "LessonDate",12,
                "Status",10,
                "StartTime",12,
                "EndTime",12,
                "LessonGrade",12,
                "Coach",15
                );
    }
    private void updateTableWidth(){
        tableWidth = 0;
        for(Map.Entry<String, Integer> col : lessonListColWidth.entrySet()){
            tableWidth += col.getValue();
        }
        tableWidth += lessonListColWidth.size()*3;
    }
    protected void printLessonListHeader(){
        tablePrinter.printHeader();
    }
    protected List<String> getBookingData(Booking booking){
        List<String> bookingData = new ArrayList<>();

        bookingData.add(booking.getLesson().getId());
        bookingData.add(booking.getLesson().getWeekDayTimeSlot().getDate().toString());
        bookingData.add(booking.getBookingStatus().toString());
        bookingData.add(booking.getLesson().getStartTime().toString());
        bookingData.add(booking.getLesson().getEndTime().toString());
        bookingData.add(booking.getLesson().getGrade().toString());
        bookingData.add(booking.getLesson().getCoach().getFormattedFullName());

        return bookingData;
    }

    public void printLessonListByLearner(Learner learner){
        List<Booking> bookingListByLearner = bookingController.getBookingsByLearner(learner);
        System.out.print("\n");
        printLearnerInfo(learner);
        if(bookingListByLearner.isEmpty()){
            System.out.println("\n" + " ".repeat(3)+"<No bookings found>"+"\n");
            return;
        }
        printLessonListHeader();
        for(Booking booking : bookingListByLearner){
            List<String> bookingData = getBookingData(booking);
            tablePrinter.printRow(bookingData);
        }
        System.out.print("\n");
    }
    public void printLearnerReport(){
        updateLearnerList();
        for(Learner learner : learnerList){
            printLessonListByLearner(learner);
        }
    }
    private void printLearnerInfo(Learner learner){
        String leftMargin = " ".repeat(3);
        try{
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            String learnerInfo =leftMargin + "Learner Name: "+learner.getFormattedFullName()
                    + " ".repeat(5) + "Learner ID: "+learner.getId() ;
            int learnerInfoLength = learnerInfo.length();
            terminal.writer().println(leftMargin + "=".repeat(tableWidth));
            terminal.writer().println(leftMargin + "Learner Report");
            terminal.writer().println(leftMargin + "-".repeat(tableWidth));
            terminal.writer().println(learnerInfo);
            terminal.writer().println(leftMargin + "-".repeat(tableWidth));

        } catch (IOException e){
            return;
        }
    }
    private void printPaginatedList(){

    }
}
