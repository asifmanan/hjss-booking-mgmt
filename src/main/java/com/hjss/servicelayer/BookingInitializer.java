package com.hjss.servicelayer;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.Grade;

import java.time.LocalDate;
import java.util.*;

public class BookingInitializer {
    private final BookingController bookingController;
    private final LessonController lessonController;
    private final LearnerController learnerController;
    private List<List<Lesson>> gradedLessonList;
    private List<List<Learner>> gradedLearnerList;
    private static final Random random = new Random();


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
            if(learnerController.filterByGrade(i).isEmpty()){
                continue;
            }
            for(Lesson lesson : lessonController.filterByGrade(i)){
                List<Learner> learnersList = new ArrayList<>(learnerController.filterByEligibleGrade(i));
                if(learnersList.isEmpty()) continue;
                Collections.shuffle(learnersList);
                lesson = lessonController.getLesson(lesson.getId());
                for(Learner learner : learnersList){
                    if(bookingController.isFullyBooked(lesson)) break;
                    learner = learnerController.getLearnerById(learner.getId());
                    String bookingId = bookingController.createAndAddObject(learner, lesson);
                    Booking booking = bookingController.getBookingById(bookingId);
                    if(lesson.getWeekDayTimeSlot().getDate().isBefore(LocalDate.now())){
                        randomAttendanceAndCancellation(booking);
                    }
                }
            }
        }
    }
    private boolean toBeOrNotToBe(){
        int chance = random.nextInt(100);
        return chance < 80;
    }
    private void randomAttendanceAndCancellation(Booking booking){
        boolean attendBooking = toBeOrNotToBe();
        if(attendBooking){
            booking.attendBooking();
        } else {
            booking.cancelBooking();
        }
    }
}