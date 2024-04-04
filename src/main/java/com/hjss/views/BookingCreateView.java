package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;

import java.util.Optional;

public class BookingCreateView {
    private BookingController bookingController;
    private LearnerController learnerController;
    private LessonController lessonController;
    private LessonListView lessonListViewByWeek, lessonListViewByDay;
    public BookingCreateView(BookingController bookingController, LessonController lessonController, LearnerController learnerController){
        this.bookingController = bookingController;
        this.lessonController = lessonController;
        this.learnerController = learnerController;

        this.lessonListViewByDay = new LessonListViewByDay(lessonController);
        this.lessonListViewByWeek = new LessonListViewByWeek(lessonController);
    }

    public void getOrCreateLearner(){
        LearnerListView learnerListView = new LearnerListView(learnerController);
        learnerListView.printLearnerList();
    }
    public void getLessonByDay(){
        LearnerCreateView learnerCreateView = new LearnerCreateView(learnerController);
        Optional<Learner> optionalLearner = learnerCreateView.getOrCreateLearner();
        if (optionalLearner.isPresent()) {
            Learner learner = optionalLearner.get();
            // Proceed with the learner
            Lesson lesson = lessonListViewByDay.getLessonFromPaginatedList();
            if(lesson != null){
                if(!bookingController.isAlreadyBookedByLearner(learner, lesson)){
                    if(!bookingController.isFullyBooked(lesson)){
                        bookingController.createObject(learner, lesson);
                    } else {
                        System.out.println("Booking Unsuccessful, This lesson is fully booked!");
                    }
                } else {
                    System.out.println("Booking Unsuccessful, Lesson already booked by the same Learner");
                }

            }
        }
        else {
            // else = if (optionalLearner.isEmpty())
            // (User cancel signal)
            return;
        }
    }
}
