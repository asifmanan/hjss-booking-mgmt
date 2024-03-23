package com.hjss.models;

import java.time.LocalDate;

public class Coach extends Person{
    String coachId;
    public Coach(String firstName, String lastName, Util.Gender gender, LocalDate dateOfBirth) {
        super(firstName, lastName, gender, dateOfBirth);
        this.coachId = generateCoachId();
    }
    public Coach(String firstName, String lastName, Util.Gender gender) {
        this(firstName, lastName, gender, null);
    }
    private String generateCoachId(){
        return "ch"+Util.generateId();
    }

    public String getCoachId() {
        return coachId;
    }
}
