package com.hjss.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    public TimeSlot(){
    }
    public TimeSlot(LocalTime startTime, LocalTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        validateTimes();
    }
    public TimeSlot(String startTimeString, String endTimeString){
        this.startTime = parseTimeString(startTimeString);
        this.endTime = parseTimeString(endTimeString);
        validateTimes();

    }
    private LocalTime parseTimeString(String timeString) {
        try {
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + timeString, e);
        }
    }
    private void validateTimes() {
        if (this.startTime != null && this.endTime != null && !this.startTime.isBefore(this.endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }
    public boolean isValid() {
        return startTime != null && endTime != null;
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
