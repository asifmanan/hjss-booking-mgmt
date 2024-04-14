package com.hjss.servicelayer;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;

import java.util.ArrayList;
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
    }
    private void populateGradedLessons(){
        this.gradedLessonList = lessonController.getGradedLessons();
    }
    private void populateGradedLearners(){
        this.gradedLearnerList = learnerController.getGradedLearners();
    }

    public List<List<Learner>> getGradedLearnerList() {
        return gradedLearnerList;
    }
}