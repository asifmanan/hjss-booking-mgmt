package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Lesson;

public class BookingCreateView {
    private BookingController bookingController;
    private LessonController lessonController;
    private LessonListView lessonListViewByWeek, lessonListViewByDay;
    public BookingCreateView(BookingController bookingController, LessonController lessonController){
        this.bookingController = bookingController;
        this.lessonController = lessonController;

        this.lessonListViewByDay = new LessonListViewByDay(lessonController);
        this.lessonListViewByWeek = new LessonListViewByWeek(lessonController);
    }
    public void getLessonByDay(){
        Lesson lesson = lessonListViewByDay.getLessonFromPaginatedList();

    }
}
