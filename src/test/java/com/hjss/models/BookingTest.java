package com.hjss.models;

import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.Gender;
import com.hjss.utilities.Grade;
import com.hjss.utilities.Rating;
import org.junit.jupiter.api.Test;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    // Test successful booking creation and properties
    @Test
    public void testBookingCreation() {
        Learner learner = new Learner("John", "Doe", Gender.Male, LocalDate.of(2010, 5, 15), 2, "1234567890");
        Lesson lesson = new Lesson(Grade.TWO, new Coach("Jane", "Doe"), new WeekDayTimeSlot(YearWeek.of(2024, 10), new DayTimeSlot(DayOfWeek.MONDAY, new TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0)))));
        Booking booking = new Booking(learner, lesson);

        assertNotNull(booking.getId(),"Booking ID should not be null");
        assertEquals(BookingStatus.Active, booking.getBookingStatus(),"Initial booking status should be Active");
        assertNotNull(booking.getCreatedOn(),"Created on date should be set");
    }

    // Test booking cancellation functionality
    @Test
    public void testCancelBooking() {
        Booking booking = createActiveBooking();
        assertTrue(booking.cancelBooking(),"Cancellation should succeed for active booking");
        assertEquals(BookingStatus.Cancelled, booking.getBookingStatus(),"Booking status should be updated to Cancelled");

        // Testing failed cancellation attempt after attending
        booking.attendBooking();
        assertFalse(booking.cancelBooking(),"Cancellation should fail for attended booking");
    }

    // Test attending a +1 Level booking (Learner Level Upgrade)
    @Test
    public void testLearnerGradeLevelForUpgrade() {
        Booking booking = createActiveBooking();
        Grade learnerGradeBeforeAttending = Grade.fromInt(booking.getLearner().getGradeLevel());
        booking.attendBooking();
        Grade learnerGradeAfterAttending = Grade.fromInt(booking.getLearner().getGradeLevel());

        assertNotEquals(learnerGradeBeforeAttending,learnerGradeAfterAttending,"Learner Grade level should increment if attending one grade level up lesson");
        assertEquals(learnerGradeBeforeAttending.increment(),learnerGradeAfterAttending,"Learner Grade level should increment if attending one grade level up lesson");

        assertEquals(BookingStatus.Attended, booking.getBookingStatus(),"Booking status should be updated to Attended");
        assertNull(booking.attendBooking(),"Attended booking should not be re-attended");
    }

    // Test attending a same Level booking (No learner upgrade)
    @Test
    public void testLearnerGradeLevelForNoUpgrade() {
        Booking booking = createActiveBookingForNoUpgrade();
        Grade learnerGradeBeforeAttending = Grade.fromInt(booking.getLearner().getGradeLevel());
        Boolean bookingAndLevelUp = booking.attendBooking();
        Grade learnerGradeAfterAttending = Grade.fromInt(booking.getLearner().getGradeLevel());

        assertEquals(learnerGradeBeforeAttending,learnerGradeAfterAttending,"Learner Grade level should not increment if attending same grade level lesson");

        assertNull(booking.attendBooking(),"Attended booking should not be re-attended");
        assertEquals(BookingStatus.Attended, booking.getBookingStatus(),"Booking status should be updated to Attended");
    }

    // Test setting and getting ratings
    @Test
    public void testSetAndGetRating() {
        Booking booking = createActiveBooking();
        booking.setRating(Rating.Satisfied);
        assertEquals(Rating.Satisfied, booking.getRating(),"Rating should be set to 4 (Satisfied)");
    }

    private Booking createActiveBooking() {
        Learner L2learner = new Learner("Alice", "Smith", Gender.Female, LocalDate.of(2009, 6, 12), 2, "+4499885432100");
        Lesson L3lesson = new Lesson(Grade.THREE, new Coach("Bob", "Jones"), new WeekDayTimeSlot(YearWeek.of(2024, 15), new DayTimeSlot(DayOfWeek.WEDNESDAY, new TimeSlot(LocalTime.of(9, 0), LocalTime.of(11, 0)))));
        return new Booking(L2learner, L3lesson);
    }
    private Booking createActiveBookingForNoUpgrade() {
        Learner L3learner = new Learner("Alice", "Smith", Gender.Female, LocalDate.of(2009, 6, 12), 3, "+4499885432100");
        Lesson L3lesson = new Lesson(Grade.THREE, new Coach("Bob", "Jones"), new WeekDayTimeSlot(YearWeek.of(2024, 15), new DayTimeSlot(DayOfWeek.WEDNESDAY, new TimeSlot(LocalTime.of(9, 0), LocalTime.of(11, 0)))));
        return new Booking(L3learner, L3lesson);
    }
}
