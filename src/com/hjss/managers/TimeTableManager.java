package com.hjss.managers;

import com.hjss.backend.TimeTable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

public class TimeTableManager {
    private Map<LocalDate, TimeTable> weeklyTimeTable;
    public TimeTableManager(){
        this.weeklyTimeTable = new HashMap<>();
    }

    public TimeTable getWeeklyScheduleContainingDate(LocalDate anchorDate){
        LocalDate adjustedDate = anchorDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return weeklyTimeTable.computeIfAbsent(adjustedDate, k -> new TimeTable());
    }
}
