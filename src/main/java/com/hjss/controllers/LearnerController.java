package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Learner;
import com.hjss.utilities.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LearnerController implements ModelController<Learner> {
    private final ModelRegister<Learner> learnerRegister;
    public LearnerController(){
        this.learnerRegister = new ModelRegister<>();
    }
    @Override
    public String addObject(Learner learner){
        return learnerRegister.add(learner);
    }
    @Override
    public List<Learner> getAllObjects(){
        return new ArrayList<>(learnerRegister.getAllObjects());
    }

    private Learner createLearner(String firstName,
                                String lastName,
                                Gender gender,
                                LocalDate dateOfBirth,
                                int grade,
                                String contactNumber) {

        return new Learner(firstName, lastName, gender, dateOfBirth, grade, contactNumber);
    }
    public Learner createObject(String[] values){
        String firstName = values[0];
        String lastName = values[1];
        String genderString = values[2];
        String dateOfBirthString = values[3];
        String gradeString = values[4];
        String contactNumber  = values[5];

        return createObject(firstName, lastName, genderString,
                dateOfBirthString, gradeString, contactNumber);
    }
    public Learner createObject(String firstName,
                                String lastName,
                                String genderString,
                                String dateOfBirthString,
                                String gradeString,
                                String contactNumber){

        Gender gender = Gender.fromString(genderString);
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int grade = Integer.parseInt(gradeString);

        return createLearner(firstName, lastName, gender,
                dateOfBirth, grade, contactNumber);
    }
    public String createAndAddObject(String[] values){
        Learner learner = createObject(values);
        return addObject(learner);
    }
}
