package com.hjss.models;

import com.hjss.utilities.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LearnerTest {
    @Test
    public void isAgeValidTest(){
        Learner lb1 = new Learner("Kate", "Winslett", Gender.Male, LocalDate.of(2020,04,30),0,"+44 7769728661");
        Learner lb2 = new Learner("Some", "Learner", Gender.Male, LocalDate.of(2020,03,23),0,"+44 7769728661");
        boolean result = false;

//      boundary conditions, needs to adjusted as per current date, otherwise test will not pass
        result = lb1.isAgeValid();
        assertFalse(result);

        result = lb2.isAgeValid();
        assertTrue(result);
    }

}
