package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.TablePrinter;

import java.util.*;

public class BookingListViewByLearner {
    private BookingController bookingController;
    private List<Booking> bookingList;
    private TablePrinter tablePrinter;
    private List<String> headers;
    private Map<String, Integer> columnWidths = new HashMap<>();
    private Learner learner;
    private BookingStatus bookingStatus;

    public BookingListViewByLearner(BookingController bookingController, Learner learner){
        this.bookingController = bookingController;
        this.learner = learner;
        updateBookingList();
        this.headers = Arrays.asList(   "BookingId",
                                        "Status",
                                        "LessonDate",
                                        "StartTime","EndTime",
                                        "LessonGrade");
        setColumnWidths(this.headers);
        this.tablePrinter = new TablePrinter(this.headers,this.columnWidths);
    }
    public BookingListViewByLearner(BookingController bookingController, Learner learner, BookingStatus bookingStatus){
        this(bookingController, learner);
        this.bookingStatus = bookingStatus;
        if(this.bookingStatus!=null){
            updateBookingList();
        }

    }
    private void setColumnWidths(List<String> headers) {
        this.columnWidths = Map.of(
                "BookingId",10,
                "Status",12,
                "LessonDate",12,
                "StartTime",12,
                "EndTime",12,
                "LessonGrade",12
//                "End",9,
//                "Grade",7,
//                "Coach",12
        );
    }
    protected void printHeader(){
        tablePrinter.printHeader();
    }
    protected List<String> getBookingData(Booking booking){
        List<String> bookingData = new ArrayList<>();

        bookingData.add(booking.getId());
        bookingData.add(booking.getBookingStatus().toString());
        bookingData.add(booking.getLesson().getLessonDate().toString());
        bookingData.add(booking.getLesson().getStartTime().toString());
        bookingData.add(booking.getLesson().getEndTime().toString());
        bookingData.add(booking.getLesson().getGrade().toString());

        return bookingData;
    }
    private void updateBookingList(){
        List<Booking> bookingsByLearner = bookingController.getBookingsByLearner(learner);
        if(bookingStatus!=null){
            this.bookingList = bookingsByLearner.stream().
                    filter(booking -> booking.getBookingStatus() == this.bookingStatus).toList();
        } else {
            this.bookingList = bookingsByLearner;
        }
    }
    public boolean isBookingListEmpty(){
        updateBookingList();
        return this.bookingList.isEmpty();
    }

    public void printBookingList(){
        updateBookingList();
        printHeader();
        for(Booking booking : bookingList){
            List<String> bookingData = getBookingData(booking);
            tablePrinter.printRow(bookingData);
        }
    }
    public List<Booking> getBookingList(){
        return this.bookingList;
    }
    private void printPaginatedBookingList(){
//        Need to work on this function
    }
}
