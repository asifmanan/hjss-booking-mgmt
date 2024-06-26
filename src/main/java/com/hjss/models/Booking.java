package com.hjss.models;

import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.IdGenerator;
import com.hjss.utilities.Rating;

import org.threeten.extra.YearWeek;

import java.time.LocalDateTime;

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
        this.ratingAndReview = new RatingAndReview();
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
    public void updateLesson(Lesson lesson){
        this.bookingStatus = BookingStatus.Active;
        this.lesson = lesson;
    }
    public Boolean attendBooking(){
        if(this.bookingStatus==BookingStatus.Attended){
            return null;
        }
        this.bookingStatus = BookingStatus.Attended;
        if(lesson.getGradeLevel() > learner.getGradeLevel()){
            learner.gradeLevelUp();
            this.updatedOn = LocalDateTime.now();
            return true;
        }
        return false;
    }
    public void attendAndRate(int ratingInt){
        Boolean bookingAndLevelUp = attendBooking();
        if(bookingAndLevelUp == null){
            return;
        }
        try{
            Rating rating = Rating.fromInt(ratingInt);
            this.setRating(rating);
        } catch (IllegalArgumentException e){
            Rating rating = Rating.Ok;
            this.setRating(rating);
        }

    }
    public void attendAndRate(){
        String leftMargin = " ".repeat(3);
        Boolean gradeLevelUp = attendBooking();
        if(gradeLevelUp==null){
            return;
        }
        HelpText helpText = new HelpText();
        if(gradeLevelUp) {
            String congratulatoryNote = String.format("Congratulations %s, you have advanced to grade level %s", this.learner.getFirstName(), this.learner.getGradeLevel());
            helpText.setPrepend(congratulatoryNote);
        }
        helpText.setText("Thank you for attending the lesson");

        Rating rating = RatingAndReview.getRatingInput(helpText);
        this.setRating(rating);
        String review = RatingAndReview.getReviewInput();
        this.ratingAndReview.setReview(review);
    }
    public Lesson getLesson() {
        return this.lesson;
    }
    public Learner getLearner() {
        return this.learner;
    }
    public void setRating(Rating rating){
        this.ratingAndReview.setRating(rating);
    }
    public Rating getRating(){
        return this.ratingAndReview.getRating();
    }
    public String getReview(){
        return this.ratingAndReview.getReview();
    }
    public LocalDateTime getCreatedOn(){
        return this.createdOn;
    }
    public LocalDateTime getUpdatedOn(){
        return this.updatedOn;
    }
}
