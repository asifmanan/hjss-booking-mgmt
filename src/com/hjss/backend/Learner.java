package com.hjss.backend;

import java.time.LocalDate;

public class Learner extends Person {
    private String learnerId;
    private Util.GradeEnum grade;
    private String emergencyContactNumber;

    public Learner(String firstName,
                   String lastName,
                   Util.Gender gender,
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
                   Util.Gender gender,
                   LocalDate dateOfBirth,
                   String emergencyContactNumber) {
        this(firstName, lastName, gender, dateOfBirth, 0,emergencyContactNumber);
    }
    public void verifyAge(){

    }

    private void setGradeLevel(int grade){
        if(grade < 0 || grade > 5){
            grade = 0;
        }
        this.grade = Util.GradeEnum.fromInt(grade);
    }
    public void gradeLevelUp(){
        this.grade = this.grade.increment();
    }
    private void setLearnerId(){
        this.learnerId = Util.generateId();
    }

    public String getLearnerId(){
        return this.learnerId;
    }

    public int getGradeLevel() {
        return grade.getValue();
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }
}
