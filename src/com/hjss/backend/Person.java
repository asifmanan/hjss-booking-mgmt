package com.hjss.backend;

import java.time.LocalDate;
import java.time.Period;

public abstract class Person {
    private String firstName;
    private String lastName;
    private Util.Gender gender;
    private LocalDate dateOfBirth;

    public Person(String firstName, String lastName, Util.Gender gender, LocalDate dateOfBirth){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public int calculateAge(){
        if(this.dateOfBirth != null){
            return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        }
        else {
            return -1;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Util.Gender getGender() {
        return gender;
    }

    public void setGender(Util.Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth accepts a LocalDate object and sets it for the person object
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

//  Static Methods

    /**
     * An overloaded static method to calculate age.
     * @param dateOfBirth an instance of a LocalDate class
     * @return age(integer) of a person at current date if dateOfBirth is not null, returns -1 otherwise
     */
    public static int calculateAge(LocalDate dateOfBirth){
        if(dateOfBirth != null){
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        else {
            return -1;
        }
    }
}
