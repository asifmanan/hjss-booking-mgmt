package com.hjss.controllers;

import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.Coach;

import java.util.ArrayList;
import java.util.List;

public class CoachController implements ModelController<Coach>{
    private final ModelRegister<Coach> coachRegister;

    public CoachController() {
        this.coachRegister = new ModelRegister<>();
    }
    public Coach createCoach(String firstName,
                             String lastName){
        return new Coach(firstName, lastName);
    }
    @Override
    public Coach createObject(String[] values) {
        String firstName = values[0];
        String lastName = values[1];

        return createObject(firstName, lastName);
    }
    public Coach createObject(String firstName,
                             String lastName){
        return createCoach(firstName, lastName);
    }

    @Override
    public String addObject(Coach coach) {
        return coachRegister.add(coach);
    }

    @Override
    public String createAndAddObject(String[] values) {
        Coach coach = createObject(values);
        return addObject(coach);
    }

    @Override
    public List<Coach> getAllObjects() {
        return new ArrayList<>(coachRegister.getAllObjects());
    }
}
