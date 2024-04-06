package com.hjss.models;

import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.IdGenerator;
import org.threeten.extra.YearWeek;

import javax.swing.text.Utilities;
import java.time.LocalDate;

import static com.hjss.utilities.DateUtil.getYearWeekForDate;

public class Booking implements Identifiable {
    private String bookingId;
    private Learner learner;
    private Lesson lesson;
    private BookingStatus bookingStatus;
    private LocalDate createdOn;
    private LocalDate updatedOn;
    public Booking(Learner learner, Lesson lesson){
        this.learner = learner;
        this.lesson = lesson;
        this.bookingStatus = BookingStatus.Active;
        this.createdOn = LocalDate.now();
        this.updatedOn = LocalDate.now();
        this.generateId();
    }
    private void generateId(){
        YearWeek yearWeek = getYearWeekForDate(this.createdOn);
        int week = yearWeek.getWeek();
        String formatWeek = String.format("%02d",week);

        String randomSequence = IdGenerator.generateRandomSequence(3);
        int sequence = IdGenerator.generateSequentialId(this.getClass());
        if (sequence>=100){
            IdGenerator.resetSequenceForClass(this.getClass());
            sequence = IdGenerator.generateSequentialId(this.getClass());
        }
        this.bookingId = randomSequence + formatWeek + String.format("%02d", sequence);
    }
    @Override
    public String getId() {
        return this.bookingId;
    }
    public BookingStatus getBookingStatus(){
        return this.bookingStatus;
    }
    public void cancelBooking(){
        this.bookingStatus = BookingStatus.Cancelled;
    }
    public void attendBooking(){
        this.bookingStatus = BookingStatus.Attended;
    }
    public Lesson getLesson() {
        return this.lesson;
    }
    public Learner getLearner() {
        return this.learner;
    }
}
