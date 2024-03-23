package com.hjss.models;

import com.hjss.utilities.Gender;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;

public class Coach extends Person{
    String coachId;
    public Coach(String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        super(firstName, lastName, gender, dateOfBirth);
        this.coachId = generateCoachId();
    }
    public Coach(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }
    private String generateCoachId(){
        return "ch"+ IdGenerator.generateId();
    }

    public String getCoachId() {
        return coachId;
    }
}
