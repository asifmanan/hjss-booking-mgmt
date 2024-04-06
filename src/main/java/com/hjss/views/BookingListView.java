package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.models.Booking;
import com.hjss.models.Lesson;
import com.hjss.utilities.TablePrinter;

import java.util.*;

public class BookingListView {
    private BookingController bookingController;
    private List<Booking> bookingList;
    private TablePrinter tablePrinter;
    private List<String> headers;
    private Map<String, Integer> columnWidths = new HashMap<>();
    public BookingListView(BookingController bookingController){
        this.bookingController = bookingController;
        this.bookingList = bookingController.getAllObjects();
        this.headers = Arrays.asList("Id","Date","Status","Learner","Grade");
        setColumnWidths(this.headers);
        this.tablePrinter = new TablePrinter(this.headers,this.columnWidths);
    }
    private void setColumnWidths(List<String> headers) {
        this.columnWidths = Map.of(
                "Id",11,
                "Date",13,
                "Status",13,
                "Learner",20,
                "Grade",6
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
        bookingData.add(booking.getLesson().getLessonDate().toString());
        bookingData.add(booking.getBookingStatus().toString());
        bookingData.add(booking.getLearner().getId());
        bookingData.add(booking.getLesson().getGrade().toString());

        return bookingData;
    }
    public void printList(){
        this.bookingList = bookingController.getAllObjects();
        printHeader();
        for(Booking booking : this.bookingList){
            List<String> bookingData = getBookingData(booking);
            tablePrinter.printRow(bookingData);
        }
    }
    private void printPaginatedBookingList(){

    }
}
