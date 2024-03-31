package com.hjss.models;

import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekDayTimeSlot implements Identifiable {
    private String timeSlotId;
    private final YearWeek yearWeek;
    private DayTimeSlot dayTimeSlot;

    public WeekDayTimeSlot(YearWeek yearWeek){
        this.yearWeek = yearWeek;
    }

    public WeekDayTimeSlot(YearWeek yearWeek, DayTimeSlot dayTimeSlot){
        this(yearWeek);
        this.dayTimeSlot = dayTimeSlot;
        generateId();
    }
    private void generateId(){
        int currentYear = this.yearWeek.getYear() % 100;
        int currentWeek = this.yearWeek.getWeek();
        int startTime = this.dayTimeSlot.getTimeSlot().getStartTime().getHour();
        this.timeSlotId = String.format("%02d%02d%02d",currentYear,currentWeek,startTime);
    }
    public boolean isValid(){
        if (yearWeek == null) {
            return false;
        }
        return dayTimeSlot.isValid();
    }

    public YearWeek getYearWeek() {
        return this.yearWeek;
    }

    public LocalDate getDate(){
        if(this.yearWeek != null && this.dayTimeSlot != null ){
            return yearWeek.atDay(dayTimeSlot.getDayOfWeek());
        }
        return null;
    }
    public int getYear(){
        return this.yearWeek.getYear();
    }
    public int getWeek(){
        return this.yearWeek.getWeek();
    }

    @Override
    public String getId() {
        return this.timeSlotId;
    }
}
