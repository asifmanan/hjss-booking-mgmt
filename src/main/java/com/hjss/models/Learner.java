package com.hjss.models;

import com.hjss.utilities.Gender;
import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;
import io.consolemenu.FontStyles;

import java.time.LocalDate;
import java.time.Year;

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
        generateId();
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

    private void generateId(){
        int currentYear = Year.now().getValue() % 100;
        int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        if(sequenceNumber>=1000){
            IdGenerator.resetSequenceForClass(this.getClass());
            sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        }
        String randomSequence = IdGenerator.generateRandomSequence(3);
        this.learnerId = randomSequence + String.format("%03d%02d", sequenceNumber, currentYear);
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
    public String getFormattedLearnerInfo(){
        String margin = " ".repeat(3);
        return FontStyles.boldStart() + "Learner Info"
               + margin +">" + margin + FontStyles.boldEnd()
               + FontStyles.boldStart() + "Name : "+ FontStyles.boldEnd() + this.getFormattedFullName()
               + margin +"|" + margin
               + FontStyles.boldStart() + "ID : " + FontStyles.boldEnd() + this.getId()
               + margin +"|" + margin
               + FontStyles.boldStart() + "Grade : " + FontStyles.boldEnd() + this.getGradeLevel();
    }
}
