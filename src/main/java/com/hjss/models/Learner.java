package com.hjss.models;

import com.hjss.dataregistry.Identifiable;
import com.hjss.utilities.Gender;
import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;

public class Learner extends Person implements Identifiable {
    private String learnerId;
    private Grade grade;
    private String emergencyContactNumber;

    public Learner(String firstName,
                   String lastName,
                   Gender gender,
                   LocalDate dateOfBirth,
                   int grade,
                   String emergencyContactNumber) {

        super(firstName, lastName, gender, dateOfBirth);
        setLearnerId();
        setGradeLevel(grade);
        this.emergencyContactNumber = emergencyContactNumber;
    }
    public Learner(String firstName,
                   String lastName,
                   Gender gender,
                   LocalDate dateOfBirth,
                   String emergencyContactNumber) {

        this(firstName, lastName, gender, dateOfBirth, 0,emergencyContactNumber);
    }

    public boolean isAgeValid(){
        int age = calculateAge(this.getDateOfBirth());
        if(age >= 4 && age <=11){
            return true;
        }
        return false;
    }

    private void setGradeLevel(int grade){
        if(grade < 0 || grade > 5){
            grade = 0;
        }
        this.grade = Grade.fromInt(grade);
    }
    public void gradeLevelUp(){
        this.grade = this.grade.increment();
    }
    private void setLearnerId(){
        this.learnerId = "LR" + IdGenerator.generateSequentialId(this.getClass());
    }

    @Override
    public String getId(){
        return this.learnerId;
    }

    public int getGradeLevel() {
        return grade.getValue();
    }
    public String getGradeLevelString(){
        return Integer.toString(this.getGradeLevel());
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber.trim();
    }
}
