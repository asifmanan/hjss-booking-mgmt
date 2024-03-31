package com.hjss.models;

import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekDayTimeSlot {
    private YearWeek yearWeek;
    private DayTimeSlot dayTimeSlot;

    public WeekDayTimeSlot(YearWeek yearWeek){
        this.yearWeek = yearWeek;
    }

    public WeekDayTimeSlot(YearWeek yearWeek, DayTimeSlot dayTimeSlot){
        this(yearWeek);
        this.dayTimeSlot = dayTimeSlot;
    }

    public LocalDate getDate(){
        if(this.yearWeek != null && this.dayTimeSlot != null ){
            return yearWeek.atDay(dayTimeSlot.getDayOfWeek());
        }
        return null;
    }
}
