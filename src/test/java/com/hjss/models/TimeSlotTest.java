package com.hjss.models;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSlotTest {
    // Test constructor with valid times
    @Test
    public void testValidConstructor() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        TimeSlot timeSlot = new TimeSlot(startTime, endTime);
        assertEquals(startTime, timeSlot.getStartTime());
        assertEquals(endTime, timeSlot.getEndTime());
    }

    // Test constructor with invalid times
    @Test
    public void testInvalidConstructor() {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(9, 0);
        assertThrows(IllegalArgumentException.class, () ->
            new TimeSlot(startTime,endTime));
    }

    // Test setting startTime to a valid value
    @Test
    public void testSetValidStartTime() {
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(11, 0));
        timeSlot.setStartTime(LocalTime.of(10, 0));
        assertEquals(LocalTime.of(10, 0), timeSlot.getStartTime());
    }

    // Test setting startTime to an invalid value
    @Test
    public void testSetInvalidStartTime() {
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0));
        assertThrows(IllegalArgumentException.class, () -> {
            timeSlot.setStartTime(LocalTime.of(11, 0));
        });
    }

    // Test setting endTime to a valid value
    @Test
    public void testSetValidEndTime() {
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(11, 0));
        timeSlot.setEndTime(LocalTime.of(12, 0));
        assertEquals(LocalTime.of(12, 0), timeSlot.getEndTime());
    }

    // Test setting endTime to an invalid value
    @Test
    public void testSetInvalidEndTime() {
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertThrows(IllegalArgumentException.class, () -> {
            timeSlot.setEndTime(LocalTime.of(9, 0));
        });
    }

    @Test
    public void testTimeSlotOverlap() {
        TimeSlot baseTimeSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(11, 0));
        TimeSlot overlapTimeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));
        assertTrue(baseTimeSlot.equals(overlapTimeSlot),"TimeSlots should overlap");

        TimeSlot nonOverlapTimeSlot = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(12, 0));
        assertFalse(baseTimeSlot.equals(nonOverlapTimeSlot),"TimeSlots should not overlap");
    }
}
