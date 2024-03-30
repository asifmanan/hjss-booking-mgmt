package com.hjss.models;

import com.hjss.dataregistry.Identifiable;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;

public class DayTimeSlot {
    private YearWeek yearWeek;
    private DayOfWeek dayOfWeek;
    private TimeSlot timeSlot;

    public DayTimeSlot() {
        this.yearWeek = null;
        this.dayOfWeek = null;
        this.timeSlot = null;
    }
    public DayTimeSlot(YearWeek yearWeek){
        this.yearWeek = yearWeek;
    }
    public DayTimeSlot(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public DayTimeSlot(YearWeek yearWeek, DayOfWeek dayOfWeek, TimeSlot timeSlot) {
        this.yearWeek = yearWeek;
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
    }
    public DayTimeSlot(String dayOfWeekString, TimeSlot timeSlot) {
        this.dayOfWeek = parseDayOfWeek(dayOfWeekString);
        this.timeSlot = timeSlot;
    }
    public boolean isValid(){
        if (yearWeek == null || dayOfWeek == null) {
            return false;
        }
        return timeSlot.isValid();
    }

    private DayOfWeek parseDayOfWeek(String day) {
        switch (day.trim().toLowerCase()) {
            case "monday":
                return DayOfWeek.MONDAY;
            case "tuesday":
                return DayOfWeek.TUESDAY;
            case "wednesday":
                return DayOfWeek.WEDNESDAY;
            case "thursday":
                return DayOfWeek.THURSDAY;
            case "friday":
                return DayOfWeek.FRIDAY;
            case "saturday":
                return DayOfWeek.SATURDAY;
            case "sunday":
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of week: " + day);
        }
    }
    public YearWeek getYearWeek() {
        return this.yearWeek;
    }
    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    public LocalDate getDate(){
        if (this.yearWeek!=null){
            return yearWeek.atDay(dayOfWeek);
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DayTimeSlot otherDayTimeSlot = (DayTimeSlot) obj;
        return dayOfWeek == otherDayTimeSlot.dayOfWeek && Objects.equals(timeSlot, otherDayTimeSlot.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, timeSlot);
    }
}
