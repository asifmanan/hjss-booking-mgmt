package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.InputValidator;
import com.hjss.utilities.Pair;
import com.hjss.utilities.TablePrinter;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.time.Month;
import java.util.*;
import java.util.stream.IntStream;

public class MonthlyLearnerReportView {
    private BookingController bookingController;
    private LearnerController learnerController;
    private List<Learner> learnerList;
    private TablePrinter tablePrinter;
    private int tableWidth;
    private List<String> lessonListHeader;
    private List<String> bookingSummaryHeader;
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
        this.bookingSummaryHeader = Arrays.asList(  "Booked Lessons",
                                                    "Cancelled Lessons",
                                                    "Attended Lessons"
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

    public void printLessonListByLearner(Learner learner, int month){
        List<Booking> bookingListByLearner = bookingController.getBookingsByLearner(learner);
        List<Booking> monthlyLearnerBookingList = bookingListByLearner.stream().
                filter(booking -> booking.getLesson().getWeekDayTimeSlot().getMonth()==month).toList();
        if(monthlyLearnerBookingList.isEmpty()) return;
        System.out.print("\n");
        printLearnerInfo(learner);
        printBookingSummary(monthlyLearnerBookingList);
//        if(monthlyLearnerBookingList.isEmpty()){
//            System.out.println("\n" + " ".repeat(3)+"<No bookings found>"+"\n");
//            return;
//        }
        printLessonListHeader();
        for(Booking booking : monthlyLearnerBookingList){
            List<String> bookingData = getBookingData(booking);
            tablePrinter.printRow(bookingData);
        }
        System.out.println(" ".repeat(3) + "-".repeat(tableWidth)+"\n");
    }
    public Integer getMonthFromInput(){
        String leftMargin = " ".repeat(3);
        try {
            Terminal terminal = TerminalManager.getTerminal();
            String prompt = "Month: ";
            String regex = "^(1[0-2]|[1-9])$";
            terminal.writer().println(leftMargin+"Enter Month (Number) to view Monthly Report");
            List<String> monthList = IntStream.rangeClosed(1,12).mapToObj(String::valueOf).toList();
            TerminalManager.updateCompleter(monthList);
            LineReader lineReader = TerminalManager.getLineReader();
            String month = InputValidator.getAndValidateString(terminal,lineReader,prompt,regex);
            if(month == null) return null;
            return Integer.parseInt(month);
        } catch (IOException e){
            return null;
        }
    }
    public void printLearnerReport(){
        updateLearnerList();
        Integer month = getMonthFromInput();
        if(month==null){
            return;
        }
        List<Booking> bookingListByMonth = bookingController.getAllObjects().stream().
                filter(booking -> booking.getLesson().getWeekDayTimeSlot().getMonth()==month).toList();

        if(bookingListByMonth.isEmpty()){
            System.out.println(" ".repeat(3) + "No data exists for the selected Month");
            return;
        }
        try {
            Terminal terminal = TerminalManager.getTerminal();
            terminal.puts(InfoCmp.Capability.clear_screen);
        } catch (IOException e){
            return;
        }
        System.out.println(" ".repeat(3) + "=".repeat(tableWidth));
        System.out.println(" ".repeat(3) + "LEARNER REPORT");
        System.out.println(" ".repeat(3) + "=".repeat(tableWidth)+"\n");
        for(Learner learner : learnerList){
            printLessonListByLearner(learner, month);
        }
    }
    private void printBookingSummary(List<Booking> monthlyLearnerBookingList){
        int activeLessons = (int) monthlyLearnerBookingList.stream().
                filter(booking -> booking.getBookingStatus() == BookingStatus.Active).count();
        int cancelledLessons = (int) monthlyLearnerBookingList.stream().
                filter(booking -> booking.getBookingStatus() == BookingStatus.Cancelled).count();
        int attendedLessons = (int) monthlyLearnerBookingList.stream().
                filter(booking -> booking.getBookingStatus() == BookingStatus.Attended).count();
        int bookedLessons = activeLessons + cancelledLessons + attendedLessons;

        String bookingSummary = String.format("Booked: %d    Active: %d    Cancelled: %d    Attended: %d    ",
                bookedLessons, activeLessons, cancelledLessons, attendedLessons);
        System.out.print(" ".repeat(3)+"Booking Summary  ->  ");
        System.out.println(bookingSummary);
        System.out.println(" ".repeat(3)+"-".repeat(tableWidth));
    }
    private void printLearnerInfo(Learner learner){
        String leftMargin = " ".repeat(3);
        try{
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            String learnerInfo =leftMargin + "Learner Name: "+learner.getFormattedFullName()
                    + " ".repeat(5) + "Learner ID: "+learner.getId();
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
