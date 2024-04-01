package com.hjss.servicelayer;

import com.hjss.controllers.TimeSlotController;

public class TimeSlotInitializer {
    private final TimeSlotController timeSlotController;
    public TimeSlotInitializer(TimeSlotController timeSlotController){
        this.timeSlotController = timeSlotController;
    }
    private final String[][] timeTable = {
            {"Monday","16:00","17:00"},
            {"Monday","17:00","18:00"},
            {"Monday","18:00","19:00"},

            {"Wednesday","16:00","17:00"},
            {"Wednesday","17:00","18:00"},
            {"Wednesday","18:00","19:00"},

            {"Friday","16:00","17:00"},
            {"Friday","17:00","18:00"},
            {"Friday","18:00","19:00"},

            {"Saturday","14:00","15:00"},
            {"Saturday","15:00","16:00"},
    };
}
