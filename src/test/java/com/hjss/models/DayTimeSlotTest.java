package com.hjss.models;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class DayTimeSlotTest {

    // Test constructor with valid day of week as string
    @Test
    public void testValidDayOfWeekStringConstructor() {
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0));
        DayTimeSlot dayTimeSlot = new DayTimeSlot("Monday", timeSlot);
        assertEquals(DayOfWeek.MONDAY, dayTimeSlot.getDayOfWeek());
        assertTrue(dayTimeSlot.isValid());
    }

    // Test constructor with invalid day of week string
    @Test
    public void testInvalidDayOfWeekStringConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DayTimeSlot("Someday", null);
        });
    }

    // Test setting and getting the time slot
    @Test
    public void testSetAndGetTimeSlot() {
        DayTimeSlot dayTimeSlot = new DayTimeSlot(DayOfWeek.TUESDAY);
        TimeSlot newTimeSlot = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(12, 0));
        dayTimeSlot.setTimeSlot(newTimeSlot);
        assertEquals(newTimeSlot, dayTimeSlot.getTimeSlot());
    }

    // Test the validity of the dayTimeSlot combination
    @Test
    public void testIsValid() {
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));
        DayTimeSlot dayTimeSlot = new DayTimeSlot(DayOfWeek.WEDNESDAY, timeSlot);
        assertTrue(dayTimeSlot.isValid());
    }
}
