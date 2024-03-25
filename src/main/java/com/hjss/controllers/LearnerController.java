package com.hjss.controllers;

import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.Learner;
import com.hjss.utilities.Gender;

import java.time.LocalDate;


public class LearnerController {
    private ModelRegister<Learner> learnerRegister;
    public LearnerController(){
        this.learnerRegister = new ModelRegister<>();
    }
    public String createLearner(String firstName,
                                String lastName,
                                Gender gender,
                                LocalDate dateOfBirth,
                                int grade,
                                String contactNumber) {

        Learner learner = new Learner(firstName, lastName, gender, dateOfBirth, grade, contactNumber);
        return learnerRegister.add(learner);
    }

}
