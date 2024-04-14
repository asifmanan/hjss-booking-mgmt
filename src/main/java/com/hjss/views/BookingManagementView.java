package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import com.hjss.utilities.Pair;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BookingManagementView {
    private LearnerGetOrCreateView learnerGetOrCreateView;
    private BookingCreateView bookingCreateView;
    private BookingController bookingController;
    private List<Booking> bookingListByLearner;
    private String leftMargin = " ".repeat(3);
    public BookingManagementView(BookingCreateView bookingCreateView, LearnerGetOrCreateView learnerGetOrCreateView){
        this.bookingCreateView = bookingCreateView;
        this.learnerGetOrCreateView = learnerGetOrCreateView;
        this.bookingController = bookingCreateView.getBookingController();
    }

    public void attendBooking(){
        while(true){
            Booking booking = getAndValidateBooking();
            if(booking==null){
                return;
            }
            try{
                Terminal terminal = TerminalManager.getTerminal();
                LineReader lineReader = TerminalManager.getLineReader();
                if(booking.getBookingStatus()==BookingStatus.Active){
                    booking.attendAndRate(terminal, lineReader);
                    return;
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("Booking Selected: "+booking.getId());
        }
    }

    public void cancelOrChangeBooking(){
//        Learner learner = learnerGetOrCreateView.getAndSelectLearnerIfNotPresent();
//        BookingListViewByLearner bookingListView = new BookingListViewByLearner(bookingController, learner);
//        if(bookingListView.isBookingListEmpty()){
//            System.out.println(leftMargin+learner.getFormattedFullName()+", does not have any swimming lessons booked.\n");
//            return;
//        }
//        bookingListView.printBookingList();
        while (true){
            Booking booking = getAndValidateBooking();
            if(booking==null){
                return;
            }
            try{
                TerminalManager.updateCompleter(Arrays.asList("cancel","change"));
                Terminal terminal = TerminalManager.getTerminal();
                LineReader lineReader = TerminalManager.getLineReader();
                HelpText helpText = new HelpText();
                helpText.setText(leftMargin+"TYPE Cancel if you want to CANCEL the Selected Booking"+
                        "\n"+leftMargin+"TYPE Change if you want to CHANGE the Selected Booking");
                String prompt = "Cancel/Change: ";
                terminal.writer().print("\n\n");

                String input = InputValidator.inputGetter(terminal, lineReader, prompt, helpText);
                if(input==null) return;
                if(input.equalsIgnoreCase("cancel")){
                    Boolean status = booking.cancelBooking();
                    if(status==null){
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"This booking is already Cancelled.");
                        continue;
                    } else if (status) {
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Booking Cancelled Successfully.");
                        continue;
                    } else {
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Operation Failed! Attended Booking cannot be Cancelled.");
                        continue;
                    }
                }
                if(input.equalsIgnoreCase("change")){
                    terminal.writer().println("Select a New Lesson");
                    Booking newBooking = bookingCreateView.updateBooking(booking);
                    if(newBooking!=null){
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Booking updated Successfully.");
                    }
                }

            } catch (IOException e){
                    e.printStackTrace();
            }
        }
    }

    public Booking getAndValidateBooking(){
        Learner learner = learnerGetOrCreateView.getAndSelectLearnerIfNotPresent();
        if(learner==null) return null;
        BookingListViewByLearner bookingListView = new BookingListViewByLearner(bookingController, learner);
        if(bookingListView.isBookingListEmpty()){
            System.out.println(leftMargin+learner.getFormattedFullName()+", does not have any swimming lessons booked.\n");
            return null;
        }
        Booking booking;
        List<String> bookingListOfIds = bookingListView.getBookingList().stream().map(Booking::getId).toList();
        try{
            TerminalManager.updateCompleter(bookingListOfIds);
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();

            while(true){

                terminal.writer().println(leftMargin+"Showing Booking for "+learner.getFormattedFullName()+ ", LearnerID: "+learner.getId());
                terminal.writer().print("\n");
                bookingListView.printBookingList();
                terminal.writer().print("\n\n");
                Pair<Booking, Boolean> bookingBooleanPair = fetchBookingById(terminal, lineReader);
                if(bookingBooleanPair==null) {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Operation Aborted! Booking Not Selected");
                    return null;
                }
                if(bookingBooleanPair.getInvalidFlag()) {
                    if(bookingBooleanPair.getObj()==null){
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Invalid Input, please try again");
                        continue;
                    } else {
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Cancelled or Attended Bookings cannot be Attended/Changed.");
                        continue;
                    }
                }
                booking = bookingBooleanPair.getObj();
                if(booking == null){
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Booking Not Found!");
                } else {
                    return booking;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public Pair<Booking, Boolean> fetchBookingById(Terminal terminal, LineReader lineReader){
        HelpText helpText = new HelpText(leftMargin + "TYPE [BOOKING ID] and ENTER to SELECT a BOOKING",
                "\n"+leftMargin + "TYPE :c and ENTER to GO BACK\n","");

        String bookingPrompt = "BOOKING ID: ";
        String input = InputValidator.inputGetter(terminal, lineReader, bookingPrompt, helpText);
        if(input==null) return null;
        Booking booking = null;
        Pair<Booking, Boolean> bookingBooleanPair = new Pair<>(booking, true);
        if(input.matches("^\\d{7}")){
            booking = bookingController.getBookingById(input);
            bookingBooleanPair.setInvalidFlag(false);
            if(booking!=null){
                if(booking.getBookingStatus() != BookingStatus.Active){
                    bookingBooleanPair.setInvalidFlag(true);
                }
            }
        }
        bookingBooleanPair.setObject(booking);
        return bookingBooleanPair;
    }

}
