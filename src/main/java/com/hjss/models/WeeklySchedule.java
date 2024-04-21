package com.hjss.models;

import java.util.ArrayList;
import java.util.List;

public class WeeklySchedule {
    private final List<DayTimeSlot> weeklySchedule;
    private final String[][] schedule;
    private final String[][] defaultSchedule = {
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
    public WeeklySchedule(){
        weeklySchedule = new ArrayList<>();
        this.schedule = defaultSchedule;
        initializeSchedule();
    }
    private void initializeSchedule(){
        for(String[] values : this.schedule){
            DayTimeSlot dts = createDayTimeSlot(values);
            addDayTimeSlot(dts);
        }
    }
    private DayTimeSlot createDayTimeSlot(String[] values){
        String day = values[0];
        String starTime = values[1];
        String endTime =values[2];

        TimeSlot ts = new TimeSlot(starTime, endTime);

        return new DayTimeSlot(day, ts);
    }
    private void addDayTimeSlot(DayTimeSlot dts){
        this.weeklySchedule.add(dts);
    }
    public List<DayTimeSlot> getSchedule(){
        return this.weeklySchedule;
    }
}
