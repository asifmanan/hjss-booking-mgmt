package com.hjss.models;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DayTimeSlot {
    private DayOfWeek dayOfWeek;
    private List<TimeSlot> timeSlots;

    public DayTimeSlot() {
        this.dayOfWeek = null;
        this.timeSlots = new ArrayList<>();
    }
    public DayTimeSlot(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        this.timeSlots = new ArrayList<>();
    }
    public DayTimeSlot(String dayOfWeekString) {
        this.dayOfWeek = parseDayOfWeek(dayOfWeekString);
        this.timeSlots = new ArrayList<>();
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

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public boolean addTimeSlot(TimeSlot newTimeSlot) {
        for (TimeSlot existingSlot : timeSlots) {
            if (existingSlot.equals(newTimeSlot)) {
                return false;
            }
        }
        timeSlots.add(newTimeSlot);
        return true;
    }
    public boolean setTimeSlots(List<TimeSlot> timeSlots) {

        for (int i = 0; i < timeSlots.size(); i++) {
            for (int j = i + 1; j < timeSlots.size(); j++) {
                if (timeSlots.get(i).equals(timeSlots.get(j))) {
                    return false;
                }
            }
        }

        this.timeSlots = new ArrayList<>(timeSlots);
        return true;
    }
}
