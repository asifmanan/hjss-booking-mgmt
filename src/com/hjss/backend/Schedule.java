package com.hjss.backend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class Schedule {
    private LocalDate weekStartDate;
    private TimeTable timeTable;
    public Schedule(){
        this(LocalDate.now());
    }
    public Schedule(LocalDate anchorDate){
        this.weekStartDate = anchorDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        this.timeTable = new TimeTable();
    }
    public LocalDate getWeekStartDate(){
        return this.weekStartDate;
    }
    public TimeTable getTimeTable(){
        return this.timeTable;
    }
    public boolean addTimeSlotOnDay(DayOfWeek day, TimeSlot newTimeSlot) {
        return timeTable.addTimeSlotOnDay(day, newTimeSlot);
    }

    // Calls TimeTable's method to print its schedule
    public void printWeekPlan() {
        System.out.println("Schedule for week starting on: " + weekStartDate);
        timeTable.printSchedule();
    }
}
