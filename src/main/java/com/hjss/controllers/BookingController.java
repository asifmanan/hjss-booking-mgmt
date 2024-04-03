package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;

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

    @Override
    public List<Booking> getAllObjects() {
        return null;
    }
}
