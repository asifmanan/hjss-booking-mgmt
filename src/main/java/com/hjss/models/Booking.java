package com.hjss.models;

import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.IdGenerator;
import com.hjss.utilities.Rating;
import org.threeten.extra.YearWeek;

import javax.swing.text.Utilities;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static com.hjss.utilities.DateUtil.getYearWeekForDate;

public class Booking implements Identifiable {
    private String bookingId;
    private Learner learner;
    private Lesson lesson;
    private BookingStatus bookingStatus;
    private RatingAndReview ratingAndReview;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    public Booking(Learner learner, Lesson lesson){
        this.learner = learner;
        this.lesson = lesson;
        this.bookingStatus = BookingStatus.Active;
        this.createdOn = LocalDateTime.now();

        this.generateId();
    }
    private void generateId(){
        YearWeek yearWeek = getYearWeekForDate(this.createdOn.toLocalDate());
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
    public Boolean cancelBooking(){
        if(this.bookingStatus == BookingStatus.Active){
            this.bookingStatus = BookingStatus.Cancelled;
            this.updatedOn = LocalDateTime.now();
            return true;
        } else if (this.bookingStatus == BookingStatus.Attended) {
            return false;
        }
        return null;
    }
    public void updateBooking(Lesson lesson){
        this.bookingStatus = BookingStatus.Active;
        this.lesson = lesson;
    }
    public void attendBooking(){
        this.bookingStatus = BookingStatus.Attended;
        this.updatedOn = LocalDateTime.now();
    }
    public void attendAndReview(){
        attendBooking();
        String leftMargin = " ".repeat(3);
        String message = String.format("Thank you %s, for attending the lesson", this.learner.getFirstName() );

    }
    public Lesson getLesson() {
        return this.lesson;
    }
    public Learner getLearner() {
        return this.learner;
    }
    public Rating getRating(){
        return this.ratingAndReview.getRating();
    }
    public String getReview(){
        return this.ratingAndReview.getReview();
    }
}
