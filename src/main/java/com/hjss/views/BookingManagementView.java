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
    private BookingController bookingController;
    private LessonController lessonController;
    private LearnerGetOrCreateView learnerGetOrCreateView;
    private BookingCreateView bookingCreateView;
    private CoachController coachController;
    private String leftMargin = " ".repeat(3);
//    public BookingManagementView(BookingController bookingController, LearnerGetOrCreateView learnerGetOrCreateView, LessonController lessonController, CoachController coachController){
//        this.bookingController = bookingController;
//        this.learnerGetOrCreateView = learnerGetOrCreateView;
//        this.lessonController = lessonController;
//        this.coachController = coachController;
//    }
    public BookingManagementView(BookingCreateView bookingCreateView){
        this.bookingCreateView = bookingCreateView;
    }

//    public Lesson getLessonFromUserInput(){
//        LessonGetView lessonGetView = new LessonGetView(lessonController, coachController);
//        return lessonGetView.getLessonsByChoice();
//    }
//    public String getViewLessonChoice(){
//        List<String> choices = Arrays.asList("day","grade","coach");
//        try {
//            TerminalManager.updateCompleter(choices);
//            Terminal terminal = TerminalManager.getTerminal();
//            LineReader lineReader = TerminalManager.getLineReader();
//            HelpText helpText = new HelpText();
//            helpText.setText("\n"+leftMargin+"Select how you would like to view the lessons:"+
//                    "\n"+leftMargin+"TYPE grade and PRESS ENTER to VIEW LESSONS by GRADE"+
//                    "\n"+leftMargin+"TYPE day and PRESS ENTER to VIEW LESSONS by DAY"+
//                    "\n"+leftMargin+"TYPE coach and PRESS ENTER to VIEW LESSONS by COACH"+
//                    "\n\n"+leftMargin+"TYPE :c to cancel operation and go BACK");
//            String prompt = "ViewLessons>>";
//            while (true){
//                String input = InputValidator.inputGetter(terminal, lineReader, prompt, helpText);
//                if(input==null) return null;
//                if(choices.contains(input)){
//                    return input;
//                } else {
//                    terminal.puts(InfoCmp.Capability.clear_screen);
//                    terminal.writer().println(leftMargin+"Invalid Choice, please try again.\n");
//                }
//            }
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
    public void cancelOrChangeBooking(){
        Learner learner = learnerGetOrCreateView.getAndSelectLearnerIfNotPresent();
        BookingListViewByLearner bookingListView = new BookingListViewByLearner(bookingController, learner);
        if(bookingListView.isBookingListEmpty()){
            System.out.println(leftMargin+learner.getFormattedFullName()+", does not have any swimming lessons booked.\n");
            return;
        }
        bookingListView.printBookingList();

        Booking booking = getAndValidateBooking();
        if(booking!=null){
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
                    booking.cancelBooking();
                }
                if(input.equalsIgnoreCase("change")){
                    terminal.writer().println("Select a New Lesson");


                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public Booking getAndValidateBooking(){
        Booking booking;
        try{
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            while(true){
                Pair<Booking, Boolean> bookingBooleanPair = fetchBookingById(terminal, lineReader);
                if(bookingBooleanPair==null) {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Operation Aborted! Learner Not Selected");
                    return null;
                }
                if(bookingBooleanPair.getInvalidFlag()) {
                    if(bookingBooleanPair.getObj()==null){
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Invalid Input, please try again");
                        continue;
                    } else {
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().println(leftMargin+"Cancelled or Attended Bookings cannot be Changed.");
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
