package com.hjss.views;

import com.hjss.models.Booking;
import io.consolemenu.TerminalManager;
import org.jline.terminal.Terminal;

import java.io.IOException;

public class BookingDetailView {
    private Booking booking;
    private String leftMargin = " ".repeat(5);
    public BookingDetailView(Booking booking){
        this.booking = booking;
    }
    public void printBookingDetails(){
        try{
            Terminal terminal = TerminalManager.getTerminal();
            terminal.writer().print("\n");
            terminal.writer().println(leftMargin+ "Booking details for "+booking.getLearner().getFormattedFullName()+"\n");
            terminal.writer().println(leftMargin+ "BookingID     "+booking.getId());
            terminal.writer().println(leftMargin+ "Lesson Date   "+booking.getLesson().getLessonDate());
            terminal.writer().println(leftMargin+ "Grade Level   "+booking.getLesson().getGrade());
            terminal.writer().println(leftMargin+ "Coach Name    "+booking.getLesson().getCoach().getFormattedFullName());

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
