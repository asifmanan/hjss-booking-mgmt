package com.hjss.models;

import com.hjss.utilities.Gender;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;
import java.time.Year;

public class Coach extends Person{
    String coachId;
    int randomIdentifier;
    public Coach(String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        super(firstName, lastName, gender, dateOfBirth);
        generateId();
    }
    public Coach(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }
    public Coach(String firstName, String lastName){
        this(firstName, lastName, Gender.Unknown, null);
    }
    private void generateId(){
        int currentYear = Year.now().getValue() % 100;
        int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        this.randomIdentifier = sequenceNumber;
        this.coachId = "CH" + String.format("%02d%03d", currentYear, sequenceNumber);
    }

    public String getCoachId() {
        return this.coachId;
    }
    public int getRandomIdentifier(){
        return this.randomIdentifier;
    }
}
