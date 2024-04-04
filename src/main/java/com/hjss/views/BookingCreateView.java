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
    private LessonListView lessonListView, lessonListViewByWeek, lessonListViewByDay, lessonListViewByGrade;
    public BookingCreateView(BookingController bookingController, LessonController lessonController, LearnerController learnerController){
        this.bookingController = bookingController;
        this.lessonController = lessonController;
        this.learnerController = learnerController;

        this.lessonListViewByDay = new LessonListViewByDay(lessonController);
        this.lessonListViewByGrade = new LessonListViewByGrade(lessonController);
        this.lessonListViewByWeek = new LessonListViewByWeek(lessonController);
    }

    public Optional<Learner> getOrCreateLearner() {
        LearnerCreateView learnerCreateView = new LearnerCreateView(learnerController);
        return learnerCreateView.getOrCreateLearner();
    }
    public void bookLessonByGrade(){
        this.lessonListView = new LessonListViewByGrade(lessonController);
        bookLesson();
    }
    public void bookLessonByDay(){
        this.lessonListView = new LessonListViewByDay(lessonController);
        bookLesson();
    }
    public void bookLesson() {
        LearnerCreateView learnerCreateView = new LearnerCreateView(learnerController);
        Optional<Learner> optionalLearner = learnerCreateView.getOrCreateLearner();

        if (!optionalLearner.isPresent()) {
            System.out.println("Booking operation cancelled.");
            return;  // Exit the method if no learner was selected (user cancelled the operation)
        }

        // A valid learner
        Learner learner = optionalLearner.get();

        // get lesson from the list
        Lesson lesson = lessonListView.getLessonFromPaginatedList();

        if (lesson == null) {
            System.out.println("No lesson selected.");
            return;  // Exit if no lesson is selected (Cancelled Operation)
        }

        // Check if the lesson is already booked by the learner
        if (bookingController.isAlreadyBookedByLearner(learner, lesson)) {
            System.out.println("Booking Unsuccessful, Lesson already booked by the same Learner.");
            return;
        }

        // Check if the lesson is fully booked (Max is 4)
        if (bookingController.isFullyBooked(lesson)) {
            System.out.println("Booking Unsuccessful, This lesson is fully booked!");
            return;
        }

        // If all checks pass, create the booking :)
        bookingController.createObject(learner, lesson);
        System.out.println("Booking successful.");
    }
}
