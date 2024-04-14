package com.hjss.servicelayer;

import com.hjss.controllers.TimeSlotController;
import com.hjss.models.DayTimeSlot;
import com.hjss.models.WeekDayTimeSlot;
import com.hjss.models.WeeklySchedule;
import org.threeten.extra.YearWeek;

import java.time.LocalDate;
import java.util.List;

public class TimeSlotInitializer {
    private final TimeSlotController timeSlotController;
    private final WeeklySchedule weeklySchedule;
    private YearWeek currentYearWeek;
    public TimeSlotInitializer(TimeSlotController timeSlotController){
        this.timeSlotController = timeSlotController;
        this.weeklySchedule = new WeeklySchedule();
        this.currentYearWeek = YearWeek.from(LocalDate.now());
    }
    public void populateTimeSlots(){
        populateTimeSlots(-8,24);
    }
    public void populateTimeSlots(int startWeeks, int endWeeks){
        List<DayTimeSlot> dayTimeSlots = this.weeklySchedule.getSchedule();
        for(int i=startWeeks; i <= endWeeks; i++){
            YearWeek yearWeek = currentYearWeek.plusWeeks(i);
            createWeekDayTimeSlotCollection(yearWeek, dayTimeSlots);
        }
    }

    private void createWeekDayTimeSlotCollection(YearWeek yearWeek, List<DayTimeSlot> dayTimeSlots){
        for(DayTimeSlot dayTimeSlot : dayTimeSlots){
            WeekDayTimeSlot weekDayTimeSlot = createWeekDayTimeSlotObject(yearWeek, dayTimeSlot);
            add(weekDayTimeSlot);
        }
    }

    private WeekDayTimeSlot createWeekDayTimeSlotObject(YearWeek yearWeek, DayTimeSlot dayTimeSlot){
        return new WeekDayTimeSlot(yearWeek, dayTimeSlot);
    }

    private void add(WeekDayTimeSlot weekDayTimeSlot){
        this.timeSlotController.addObject(weekDayTimeSlot);
    }
}
