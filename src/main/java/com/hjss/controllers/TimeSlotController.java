package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.WeekDayTimeSlot;

import java.util.ArrayList;
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
        return new ArrayList<>(timeSlotRegister.getAllObjects());
    }
    public WeekDayTimeSlot getAndIncrement(){
        return timeSlotRegister.getAndIncrement();
    }
}
