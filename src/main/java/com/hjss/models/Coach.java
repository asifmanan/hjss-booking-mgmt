package com.hjss.models;

import com.hjss.utilities.Gender;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;
import java.time.Year;

public class Coach extends Person implements Identifiable {
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

        int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        if(sequenceNumber>=100){
            IdGenerator.resetSequenceForClass(this.getClass());
            sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        }
        String randomString = IdGenerator.generateRandomSequence(2);
        this.randomIdentifier = sequenceNumber;
        this.coachId = "12" + randomString + String.format("%02d", sequenceNumber);
    }

    public String getId() {
        return this.coachId;
    }

    public int getRandomIdentifier(){
        return this.randomIdentifier;
    }
}
