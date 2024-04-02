package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Coach;
import com.hjss.utilities.Gender;

import java.util.ArrayList;
import java.util.List;

public class CoachController implements ModelController<Coach>{
    private final ModelRegister<Coach> coachRegister;

    public CoachController() {
        this.coachRegister = new ModelRegister<>();
    }

    @Override
    public String addObject(Coach coach) {
        return coachRegister.add(coach);
    }
    @Override
    public List<Coach> getAllObjects() {
        return new ArrayList<>(coachRegister.getAllObjects());
    }
    //  Extra methods
    private Coach createObject(String firstName,
                             String lastName){
        return new Coach(firstName, lastName);
    }
    public Coach createObject(String firstName,
                              String lastName,
                              String genderString){
        Gender gender = Gender.fromString(genderString);
        return new Coach(firstName, lastName, gender);
    }
    public Coach createObject(String firstName,
                              String lastName,
                              Gender gender){
        return new Coach(firstName, lastName, gender);
    }
    public Coach getAndRotate(){
        return coachRegister.getAndRotate();
    }
}
