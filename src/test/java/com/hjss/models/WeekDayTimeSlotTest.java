package com.hjss.models;

import org.junit.jupiter.api.Test;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class WeekDayTimeSlotTest {

    @Test
    public void testConstructorAndIdGeneration() {
        YearWeek yearWeek = YearWeek.of(2024, 15);
        DayTimeSlot dayTimeSlot = new DayTimeSlot(DayOfWeek.MONDAY, new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0)));
        WeekDayTimeSlot weekDayTimeSlot = new WeekDayTimeSlot(yearWeek, dayTimeSlot);

        assertNotNull(weekDayTimeSlot.getId(),"TimeSlot ID should not be null");
        assertTrue(weekDayTimeSlot.getId().matches("\\d{7}"),"Generated ID should match the expected format");
    }

    @Test
    public void testAllocation() {
        YearWeek yearWeek = YearWeek.of(2024, 15);
        WeekDayTimeSlot weekDayTimeSlot = new WeekDayTimeSlot(yearWeek);
        assertFalse(weekDayTimeSlot.isAllocated(),"Initially not allocated");
        weekDayTimeSlot.allocate();
        assertTrue(weekDayTimeSlot.isAllocated(),"Should be allocated after calling allocate()");
        weekDayTimeSlot.deAllocate();
        assertFalse(weekDayTimeSlot.isAllocated(),"Should be deallocated after calling deAllocate()");
    }

    @Test
    public void testIsValid() {
        YearWeek yearWeek = YearWeek.of(2024, 15);
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0));
        DayTimeSlot dayTimeSlot = new DayTimeSlot(DayOfWeek.MONDAY, timeSlot);
        WeekDayTimeSlot weekDayTimeSlot = new WeekDayTimeSlot(yearWeek, dayTimeSlot);
        assertTrue(weekDayTimeSlot.isValid(),"Should be valid when all components are valid");
    }
}
