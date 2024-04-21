package com.hjss.models;

import com.hjss.utilities.Gender;
import com.hjss.utilities.Grade;
import org.junit.jupiter.api.Test;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class LessonTest {

    // Test successful lesson creation with valid inputs
    @Test
    public void testValidLessonCreation() {
        Grade grade = Grade.FOUR;
        Coach coach = new Coach("John", "More");
        WeekDayTimeSlot weekDayTimeSlot = new WeekDayTimeSlot(YearWeek.of(2024, 10), new DayTimeSlot(DayOfWeek.MONDAY, new TimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0))));

        Lesson lesson = new Lesson(grade, coach, weekDayTimeSlot);
        assertEquals(grade.getValue(), lesson.getGradeLevel(),"Ensure the grade level is set correctly");
        assertNotNull(lesson.getId(),"Lesson ID should be generated");
        assertEquals(4, lesson.getMaxCapacity(),"Max capacity should be default to 4");
    }

    // Test grade level validation for a learner
    @Test
    public void testGradeValidation() {
        Grade grade = Grade.TWO;
        Coach coach = new Coach("Mike", "Johnson");
        WeekDayTimeSlot weekDayTimeSlot = new WeekDayTimeSlot(YearWeek.of(2024, 10), new DayTimeSlot(DayOfWeek.TUESDAY, new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0))));

        Lesson lesson = new Lesson(grade, coach, weekDayTimeSlot);
        Learner validLearner1 = new Learner("Learner", "One", Gender.Female, LocalDate.now().minusYears(10), 1, "1234567890");
        Learner validLearner2 = new Learner("Learner", "Two", Gender.Male, LocalDate.now().minusYears(10), 2, "1234567890");
        Learner invalidLearner = new Learner("Learner", "Three", Gender.Female, LocalDate.now().minusYears(8), 0, "1234567890");

        assertTrue(lesson.isLearnerEligible(validLearner1),"Learner should be valid");
        assertTrue(lesson.isLearnerEligible(validLearner2),"Learner should be valid");
        assertFalse(lesson.isLearnerEligible(invalidLearner),"Learner should not be valid");
    }

    // Test getters() for time and date related properties
    @Test
    public void testGetters() {
        Grade grade = Grade.ONE;
        Coach coach = new Coach("Emma", "Wilson");
        WeekDayTimeSlot weekDayTimeSlot = new WeekDayTimeSlot(YearWeek.of(2024, 16), new DayTimeSlot(DayOfWeek.WEDNESDAY, new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))));

        Lesson lesson = new Lesson(grade, coach, weekDayTimeSlot);
        assertEquals(LocalDate.of(2024, 4, 17), lesson.getLessonDate(),"Check correct lesson date");
        assertEquals(DayOfWeek.WEDNESDAY, lesson.getLessonDay(),"Check correct day of the week");
        assertEquals(grade, lesson.getGrade(),"Check correct lesson grade");
        assertEquals(LocalTime.of(9, 0), lesson.getStartTime(),"Start time should match");
        assertEquals(LocalTime.of(10, 0), lesson.getEndTime(),"End time should match");
    }
}
