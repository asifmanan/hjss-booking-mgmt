package com.hjss.servicelayer;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.Grade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BookingInitializer {
    private final BookingController bookingController;
    private final LessonController lessonController;
    private final LearnerController learnerController;
    private List<List<Lesson>> gradedLessonList;
    private List<List<Learner>> gradedLearnerList;


    public BookingInitializer(BookingController bookingController, LessonController lessonController, LearnerController learnerController) {
        this.bookingController = bookingController;
        this.lessonController = lessonController;
        this.learnerController = learnerController;
        populateGradedLearners();
        populateGradedLessons();
    }
    private void populateGradedLessons(){
        this.gradedLessonList = lessonController.getGradedLessons();
    }
    private void populateGradedLearners(){
        this.gradedLearnerList = learnerController.getGradedLearners();
    }

    public List<Learner> getGradedLearnerListByGrade(int grade) {
        return gradedLearnerList.get(grade);
    }
    public List<Lesson> getGradedLessonListByGrade(int grade){
        return this.gradedLessonList.get(grade);
    }
    public void initializeBookings(){
        for(int i = 0; i<= Grade.getMaxGrade(); i++){
            if(getGradedLearnerListByGrade(i).isEmpty() || getGradedLessonListByGrade(i).isEmpty()){
                continue;
            }
            for(Lesson lesson : getGradedLessonListByGrade(i)){
                List<Learner> learnersList = getGradedLearnerListByGrade(i);
                if(learnersList.isEmpty()) continue;
                Collections.shuffle(learnersList);
                for(Learner learner : learnersList){
                    if(bookingController.isFullyBooked(lesson)) break;
                    String bookingId = bookingController.createAndAddObject(learner, lesson);
                    Booking booking = bookingController.getBookingById(bookingId);
                    booking.attendBooking();
                }
            }
        }
    }
}