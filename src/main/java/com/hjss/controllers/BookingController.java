package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class BookingController implements ModelController<Booking> {
    private final ModelRegister<Booking> bookingRegister;
    public BookingController(){
        this.bookingRegister = new ModelRegister<>();
    }
    public Booking createObject(Learner learner, Lesson lesson){
        return new Booking(learner, lesson);
    }
    @Override
    public String addObject(Booking object) {
        return bookingRegister.add(object);
    }

    public String createAndAddObject(Learner learner, Lesson lesson){
        Booking booking = createObject(learner, lesson);
        return addObject(booking);
    }
    public Booking getBookingById(String bookingId){
        return bookingRegister.get(bookingId);
    }

    @Override
    public List<Booking> getAllObjects() {
        return new ArrayList<>(bookingRegister.getAllObjects());
    }
    public List<Booking> getBookingsByLesson(Lesson lesson){
        List<Booking> allBookings = getAllObjects();
        List<Booking> bookingsFilteredByLesson = allBookings.stream()
                .filter(booking -> booking.getLesson().getId()
                        .equalsIgnoreCase(lesson.getId()))
                            .toList();
        return bookingsFilteredByLesson;
    }
    public Integer getBookingByLessonCount(Lesson lesson){
        List<Booking> lessonBookings = getBookingsByLesson(lesson);
        return lessonBookings.size();
    }
    public List<Booking> getBookingsByLearner(Learner learner){
        List<Booking> allBookings = getAllObjects();
        List<Booking> bookingsFilteredByLearner = allBookings.stream()
                .filter(booking -> booking.getLearner().getId()
                        .equalsIgnoreCase(learner.getId()))
                .toList();
        return bookingsFilteredByLearner;
    }
    public Booking getBookingByLearnerAndLesson(Learner learner, Lesson lesson){
        List<Booking> allBookings = getAllObjects();
        Booking bookingFilteredByLearnerAndLesson = allBookings.stream()
                .filter(booking -> booking.getLearner().getId().equalsIgnoreCase(learner.getId()) &&
                    booking.getLesson().getId().equalsIgnoreCase(lesson.getId())).
                findFirst()
                .orElse(null);
        return bookingFilteredByLearnerAndLesson;
    }
    public boolean isAlreadyBookedByLearner(Learner learner, Lesson lesson){
        Booking booking = getBookingByLearnerAndLesson(learner, lesson);
        if(booking == null){
            return false;
        }
        return true;
    }
    public boolean isFullyBooked(Lesson lesson){
        int bookingCount = getBookingByLessonCount(lesson);
        return bookingCount >= lesson.getMaxCapacity();
    }
    public boolean isGradeCriteriaValid(Learner learner, Lesson lesson){
        return learner.getGradeLevel() == lesson.getMinLearnerGradeRequired() ||
                learner.getGradeLevel() == lesson.getGradeLevel();
    }
}
