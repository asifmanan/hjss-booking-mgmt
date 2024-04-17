package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.CoachController;
import com.hjss.models.Booking;
import com.hjss.models.Coach;
import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.InputValidator;
import com.hjss.utilities.TablePrinter;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class MonthlyCoachReportView {
    private CoachController coachController;
    private BookingController bookingController;
    private TablePrinter tablePrinter;
    private Map<String, Integer> coachListColWidth = new HashMap<>();
    private List<String> coachListHeader;
    private int tableWidth;
    public MonthlyCoachReportView(BookingController bookingController, CoachController coachController){
        this.bookingController = bookingController;
        this.coachController = coachController;
        this.coachListHeader = Arrays.asList(   "CoachId",
                                                "FirstName",
                                                "LastName",
                                                "Avg. Rating"
                                                );
        setCoachListColWidth();
        updateTableWidth();
        tablePrinter = new TablePrinter(coachListHeader, coachListColWidth);
    }
    private void setCoachListColWidth() {
        this.coachListColWidth = Map.of(
                "CoachId",10,
                "FistName",12,
                "LastName",12,
                "Avg. Rating",12
                );
    }
    private void updateTableWidth(){
        tableWidth = 0;
        for(Map.Entry<String, Integer> col : coachListColWidth.entrySet()){
            tableWidth += col.getValue();
        }
        tableWidth += coachListColWidth.size()*3;
    }
    private void printTableHeader(){
        tablePrinter.printHeader();
    }
    private Integer getMonthFromInput(){
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
    public void generateCoachReport(){
        Integer month = getMonthFromInput();
        if (month == null) {
            System.out.println("Operation Cancelled!");
            return;
        }
        List<Coach> coachList = coachController.getAllObjects();
        List<Booking> bookingListByMonth = bookingController.getLessonsByMonth(month);
        try {
            Terminal terminal = TerminalManager.getTerminal();
            terminal.puts(InfoCmp.Capability.clear_screen);
        } catch (IOException e){
            return;
        }

        System.out.println(" ".repeat(3) + "MONTHLY COACH REPORT"+"\n");
        System.out.println(" ".repeat(3) + "-".repeat(tableWidth));
        tablePrinter.printHeader();
        System.out.println(" ".repeat(3) + "-".repeat(tableWidth));
        for(Coach coach : coachList){
            List<String> coachData = getCoachData(coach, bookingListByMonth);
            tablePrinter.printRow(coachData);
        }
        System.out.println(" ".repeat(3) + "-".repeat(tableWidth)+"\n");
    }

    private List<String> getCoachData(Coach coach, List<Booking> monthlyBookingList){
        List<String> coachData = new ArrayList<>();
        coachData.add(coach.getId());
        coachData.add(coach.getFirstName());
        coachData.add(coach.getLastName());
        Integer coachRating = coach.getMonthlyCoachRating(monthlyBookingList);
        coachData.add(coachRating == null ? "<Unrated>" : coachRating.toString());
        return coachData;
    }


}

