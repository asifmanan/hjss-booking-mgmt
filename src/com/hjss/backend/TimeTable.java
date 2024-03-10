package com.hjss.backend;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TimeTable {
    private Map<DayOfWeek, List<TimeSlot>> weeklySchedule;
    public TimeTable (){
        weeklySchedule = new EnumMap<>(DayOfWeek.class);
//      initializeSchedule() initialized the days in EnumMap of days
        initializeSchedule();
    }
    private void initializeSchedule(){
        for (DayOfWeek day : DayOfWeek.values()) {
            weeklySchedule.put(day, new ArrayList<>());
        }
    }
    public boolean addTimeSlotOnDay(DayOfWeek day, TimeSlot newTimeSlot){
        List<TimeSlot> timeSlots = weeklySchedule.get(day);
        // In java when get is used to fetch an object from a collection (list or map)
        // it creates a reference, any now the timeSlots object is the actual object.
        // any changes made affects the original object.
        for(TimeSlot timeSlot : timeSlots) {
            if(timeSlot.equals(newTimeSlot)) {
                return false;
            }
        }
        timeSlots.add(newTimeSlot);
        return true;
    }
    public void printSchedule() {
        weeklySchedule.forEach((day, slots) -> {
            if(!slots.isEmpty()) System.out.println(day + ":");
            slots.forEach(slot -> System.out.println(" Time Slot: " + slot.getStartTime() + " - " + slot.getEndTime()));
        });
    }
}
