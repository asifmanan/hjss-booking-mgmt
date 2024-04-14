package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.models.Booking;
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
        this.headers = Arrays.asList(   "BookingId",
                                        "Status",
                                        "LessonDate",
                                        "StartTime","EndTime",
                                        "LessonGrade");
        setColumnWidths(this.headers);
        this.tablePrinter = new TablePrinter(this.headers,this.columnWidths);
    }
    private void setColumnWidths(List<String> headers) {
        this.columnWidths = Map.of(
                "BookingId",10,
                "Status",12,
                "LessonDate",12,
                "StartTime",12,
                "EndTime",12,
                "LessonGrade",12
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
