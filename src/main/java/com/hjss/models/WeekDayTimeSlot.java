package com.hjss.models;

import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekDayTimeSlot {
    private YearWeek yearWeek;
    private List<DayTimeSlot> dayTimeSlots;
    public WeekDayTimeSlot(YearWeek yearWeek){
        this.yearWeek = yearWeek;
        this.dayTimeSlots = new ArrayList<>();
    }
    public WeekDayTimeSlot(YearWeek yearWeek, DayTimeSlot dayTimeSlot){
        this(yearWeek);
        boolean addSuccess = addDayTimeSlot(dayTimeSlot);
        if(!addSuccess){
            throw new IllegalStateException("Duplicate DayTimeSlot detected. Ensure unique time slots.");
        }
    }
    public WeekDayTimeSlot(YearWeek yearWeek, List<DayTimeSlot> dayTimeSlots){
        this.yearWeek = yearWeek;
        boolean addSuccess = setDayTimeSlots(dayTimeSlots);
        if(!addSuccess){
            throw new IllegalStateException("Duplicate DayTimeSlot detected. Ensure unique time slots.");
        }
    }
    public boolean addDayTimeSlot(DayTimeSlot newDayTimeSlot) {
        for (DayTimeSlot existingSlot : dayTimeSlots) {
            if (existingSlot.equals(newDayTimeSlot)) {
                // If duplicates found, return false.
                return false;
            }
        }
        // If no duplicates are found, add the new time slot.
        dayTimeSlots.add(newDayTimeSlot);
        return true;
    }

    public boolean setDayTimeSlots(List<DayTimeSlot> dayTimeSlots) {
        List<DayTimeSlot> tempSlots = new ArrayList<>(); // Temporary list to store new slots
        boolean hasDuplicate = false;

        for (DayTimeSlot newDayTimeSlot : dayTimeSlots) {
            // Check if the newDayTimeSlot is a duplicate within the tempSlots
            if (tempSlots.stream().anyMatch(existingSlot -> existingSlot.equals(newDayTimeSlot))) {
                hasDuplicate = true;
            } else {
                tempSlots.add(newDayTimeSlot); // Add if it's not a duplicate
            }
        }

        // If no duplicates were found, assign tempSlots to this.dayTimeSlots
        if (!hasDuplicate) {
            this.dayTimeSlots = tempSlots;
        }

        return !hasDuplicate;
    }
    public LocalDate getDateForDay(DayOfWeek day){
        if(this.yearWeek!=null){
            return yearWeek.atDay(day);
        }
        return null;
    }
}
