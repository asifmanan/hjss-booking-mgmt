package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.WeekDayTimeSlot;

import java.util.List;

public class TimeSlotController implements ModelController<WeekDayTimeSlot> {
    private final ModelRegister<WeekDayTimeSlot> timeSlotRegister;
    public TimeSlotController(){
        this.timeSlotRegister = new ModelRegister<WeekDayTimeSlot>();
    }
    @Override
    public String addObject(WeekDayTimeSlot object) {
        return timeSlotRegister.add(object);
    }

    @Override
    public List<WeekDayTimeSlot> getAllObjects() {
        return null;
    }
    public WeekDayTimeSlot getAndIncrement(){
        return timeSlotRegister.getAndIncrement();
    }
}
