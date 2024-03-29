package com.hjss.models;

import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    public TimeSlot(){
        this(null, null);
    }
    public TimeSlot(LocalTime startTime, LocalTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        validateTimes();
    }
    private void validateTimes() {
        if (this.startTime != null && this.endTime != null && !this.startTime.isBefore(this.endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        if (endTime != null && !startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (startTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        this.endTime = endTime;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TimeSlot timeSlot = (TimeSlot) obj;
//      Casting is done because otherwise the compiler still see obj as an Object and not an instance of TimeSlot
        boolean isSameTime = Objects.equals(startTime, timeSlot.startTime) &&
                                Objects.equals(endTime, timeSlot.endTime);
        boolean isStartTimeWithin = startTime.isAfter(timeSlot.startTime) && startTime.isBefore(timeSlot.endTime);
        boolean isEndTimeWithin = endTime.isAfter(timeSlot.startTime) && !endTime.isAfter(timeSlot.endTime);
        boolean isEncompassing = startTime.isBefore(timeSlot.startTime) && endTime.isAfter(timeSlot.endTime);

        boolean isOverLapping = isStartTimeWithin || isEndTimeWithin || isEncompassing;

        return isSameTime || isOverLapping;

    }
    @Override
    public int hashCode(){
        return Objects.hash(startTime, endTime);
    }
}
