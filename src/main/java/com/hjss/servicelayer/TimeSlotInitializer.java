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
        List<DayTimeSlot> dayTimeSlots = this.weeklySchedule.getSchedule();
        for(DayTimeSlot dayTimeSlot : dayTimeSlots){
            WeekDayTimeSlot weekDayTimeSlot = createWeekDayTimeSlot(currentYearWeek, dayTimeSlot);
            add(weekDayTimeSlot);
        }
    }
    private WeekDayTimeSlot createWeekDayTimeSlot(YearWeek yearWeek, DayTimeSlot dayTimeSlot){
        return new WeekDayTimeSlot(yearWeek, dayTimeSlot);
    }
    private void add(WeekDayTimeSlot weekDayTimeSlot){
        this.timeSlotController.addObject(weekDayTimeSlot);
    }
}
