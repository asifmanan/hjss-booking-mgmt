package com.hjss.models;

import com.hjss.utilities.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LearnerTest {

    // Test the constructor and ensure that the Learner is initialized correctly
    @Test
    public void testLearnerConstructor() {
        LocalDate dob = LocalDate.of(2015, 5, 15); // Set a specific birth date
        Learner learner = new Learner("max", "verstappen", Gender.Male, dob, 1, "+447788654321");

        assertEquals("max", learner.getFirstName());
        assertEquals("verstappen", learner.getLastName());
        assertEquals("Max", learner.getFormattedFirstName());
        assertEquals("Verstappen", learner.getFormattedLastName());
        assertEquals("Max Verstappen", learner.getFormattedFullName());
        assertEquals(Gender.Male, learner.getGender());
        assertEquals(dob, learner.getDateOfBirth());
        assertEquals(1, learner.getGradeLevel());
        assertEquals("+447788654321", learner.getEmergencyContactNumber());
    }

    // Test age validation logic
    @Test
    public void testIsAgeValid() {
        LocalDate dobValid = LocalDate.now().minusYears(5);
        Learner learnerValid = new Learner("Alisa", "Milano", Gender.Female, dobValid, 1, "+447788654322");
        assertTrue(learnerValid.isAgeValid());

        LocalDate dobInvalid = LocalDate.now().minusYears(12);
        Learner learnerInvalid = new Learner("Helen", "Hunt", Gender.Female, dobInvalid, 1, "+447788654323");
        assertFalse(learnerInvalid.isAgeValid());
    }

    // Test grade level up functionality
    @Test
    public void testGradeLevelUp() {
        Learner learner = new Learner("Charles", "Leclerc", Gender.Male, LocalDate.of(2012, 4, 20), 2, "+447788654324");
        learner.gradeLevelUp();
        assertEquals(3, learner.getGradeLevel());
    }
}

