package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Graded;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.Gender;
import com.hjss.utilities.Grade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LearnerController implements ModelController<Learner> {
    private final ModelRegister<Learner> learnerRegister;
    public LearnerController(){
        this.learnerRegister = new ModelRegister<>();
    }
    public Learner getLearnerById(String id){
        return learnerRegister.get(id.toUpperCase());
    }
    @Override
    public String addObject(Learner learner){
        return learnerRegister.add(learner);
    }
    @Override
    public List<Learner> getAllObjects(){
        return new ArrayList<>(learnerRegister.getAllObjects());
    }



    public Learner createObject(String[] values){
        return createObject(values[0], values[1], values[2],
                values[3], values[4], values[5]);
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

        return new Learner(firstName, lastName, gender,
                dateOfBirth, grade, contactNumber);
    }
    public String createAndAddObject(String[] values){
        Learner learner = createObject(values);
        return addObject(learner);
    }
    public List<List<Learner>> getGradedLearners(){
        return Grade.categorizeByGrade(this.getAllObjects(), Learner::getGradeLevel);
    }
}
