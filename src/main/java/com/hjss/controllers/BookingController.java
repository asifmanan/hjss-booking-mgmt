package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Booking;

import java.util.List;

public class BookingController implements ModelController<Booking> {
    private final ModelRegister<Booking> bookingRegister;
    public BookingController(){
        this.bookingRegister = new ModelRegister<>();
    }
    public Booking createObject(String learnerId, String lessonId){
        return new Booking(learnerId, lessonId);
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
