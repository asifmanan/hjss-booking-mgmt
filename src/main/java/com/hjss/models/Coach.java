package com.hjss.models;

import com.hjss.utilities.Gender;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;
import java.time.Year;

public class Coach extends Person{
    String coachId;
    public Coach(String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        super(firstName, lastName, gender, dateOfBirth);
        generateId();
    }
    public Coach(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }
    private void generateId(){
        int currentYear = Year.now().getValue() % 100;
        int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        this.coachId = "CH" + String.format("%02d%03d", currentYear, sequenceNumber);
    }

    public String getCoachId() {
        return this.coachId;
    }
}
