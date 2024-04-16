package com.hjss.models;

import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class WeekDayTimeSlot implements Identifiable {
    private String timeSlotId;
    private final YearWeek yearWeek;
    private DayTimeSlot dayTimeSlot;
    private boolean allocation;

    public WeekDayTimeSlot(YearWeek yearWeek){
        this.yearWeek = yearWeek;
        this.allocation = false;
    }

    public WeekDayTimeSlot(YearWeek yearWeek, DayTimeSlot dayTimeSlot){
        this(yearWeek);
        this.dayTimeSlot = dayTimeSlot;
        generateId();
    }
    private void generateId(){
        int currentYear = this.yearWeek.getYear() % 100;
        int currentWeek = this.yearWeek.getWeek();
        int dayNumber = this.dayTimeSlot.getDayOfWeekNumber();
        int startTime = this.dayTimeSlot.getTimeSlot().getStartTime().getHour();

        this.timeSlotId = String.format("%02d%02d%01d%02d", currentYear, currentWeek, dayNumber, startTime);
    }
    public boolean isValid(){
        if (yearWeek == null) {
            return false;
        }
        return dayTimeSlot.isValid();
    }
    public boolean isAllocated(){
        return this.allocation;
    }
    public void allocate(){
        this.allocation = true;
    }
    public void deAllocate(){
        this.allocation = false;
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
    public DayOfWeek getDayOfWeek(){
        if(dayTimeSlot != null){
            return dayTimeSlot.getDayOfWeek();
        }
        return null;
    }
    public LocalTime getStartTime(){
        return this.dayTimeSlot.getTimeSlot().getStartTime();
    }
    public LocalTime getEndTime(){
        return this.dayTimeSlot.getTimeSlot().getEndTime();
    }
    public int getYear(){
        return this.yearWeek.getYear();
    }
    public int getMonth(){
        return this.getDate().getMonthValue();
    }
    public int getWeek(){
        return this.yearWeek.getWeek();
    }

    @Override
    public String getId() {
        return this.timeSlotId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WeekDayTimeSlot otherWeekDayTimeSlot = (WeekDayTimeSlot) obj;
        return Objects.equals(yearWeek, otherWeekDayTimeSlot.yearWeek) &&
                Objects.equals(dayTimeSlot, otherWeekDayTimeSlot.dayTimeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yearWeek, dayTimeSlot);
    }
}
