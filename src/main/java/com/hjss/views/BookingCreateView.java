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
        if (optionalLearner.isEmpty()) {
            return;
        } else {
            Learner learner = optionalLearner.get();
            // Proceed with the learner
            Lesson lesson = lessonListViewByDay.getLessonFromPaginatedList();
            if(lesson != null){
                System.out.println(learner.getId());
                System.out.println(lesson.getLessonDate());
            }

        }
    }
}
