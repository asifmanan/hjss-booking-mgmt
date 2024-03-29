package com.hjss.models;

import com.hjss.dataregistry.Identifiable;

import java.time.DayOfWeek;
import java.util.Objects;

public class DayTimeSlot implements Identifiable {
    private DayOfWeek dayOfWeek;
    private TimeSlot timeSlot;

    public DayTimeSlot() {
        this.dayOfWeek = null;
        this.timeSlot = null;
    }
    public DayTimeSlot(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = new TimeSlot();
    }
    public DayTimeSlot(DayOfWeek dayOfWeek, TimeSlot timeSlot) {
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
    }
    public DayTimeSlot(String dayOfWeekString, TimeSlot timeSlot) {
        this.dayOfWeek = parseDayOfWeek(dayOfWeekString);
        this.timeSlot = timeSlot;
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
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
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
