package com.hjss.controllers;

import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.DayTimeSlot;
import com.hjss.models.TimeSlot;

import java.time.LocalTime;
import java.util.List;

public class DayTimeSlotController implements ModelController<DayTimeSlot>{
    private final ModelRegister<DayTimeSlot> dayTimeSlotRegister;

    public DayTimeSlotController() {
        dayTimeSlotRegister = new ModelRegister<>();
    }

    @Override
    public String addObject(DayTimeSlot dayTimeSlot) {
        return dayTimeSlotRegister.add(dayTimeSlot);
    }

    @Override
    public String createAndAddObject(String[] params) {
        return null;
    }

    @Override
    public List<DayTimeSlot> getAllObjects() {
        return null;
    }

    @Override
    public DayTimeSlot createObject(String[] params) {
        String dayOfWeekString = params[0];
        String startTimeString = params[1];
        String endTimeString = params[2];

        TimeSlot timeSlot = new TimeSlot(startTimeString, endTimeString);

        return new DayTimeSlot(dayOfWeekString, timeSlot);
    }
}
