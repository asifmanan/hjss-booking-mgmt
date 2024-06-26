package com.hjss.models;

import com.hjss.utilities.Gender;

import java.time.LocalDate;
import java.time.Period;

public abstract class Person {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;

    public Person(String firstName, String lastName, Gender gender, LocalDate dateOfBirth){
        setFirstName(firstName);
        setLastName(lastName);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    public Person(String firstName, String lastName, Gender gender){
        this(firstName, lastName, gender, null);
    }
    public int getAge(){
        return calculateAge(this.dateOfBirth);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim().toLowerCase();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim().toLowerCase();
    }

    public Gender getGender() {
        return gender;
    }

    public String getGenderString() {
        return getGender().toString().toLowerCase();
    }

    public void setGender(Gender gender) {
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

    public String getFullName(){
        return getFirstName() +" "+ getLastName();
    }
    public String getFormattedFirstName(){
        return Character.toUpperCase(getFirstName().charAt(0)) + getFirstName().substring(1);
    }
    public String getFormattedLastName(){
        return Character.toUpperCase(getLastName().charAt(0)) + getLastName().substring(1);
    }
    public String getFormattedFullName(){
        return this.getFormattedFirstName() +" "+ this.getFormattedLastName();
    }
    public String getInitialLastName(){
        Character initial = Character.toUpperCase(getFirstName().charAt(0));
        String formattedLastName = Character.toUpperCase(getLastName().charAt(0)) + getLastName().substring(1);
        return initial +". " + formattedLastName;
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
