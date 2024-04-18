package com.hjss.servicelayer;

import com.hjss.controllers.CoachController;
import com.hjss.models.Coach;

public class CoachInitializer {
    private final CoachController coachController;
    public CoachInitializer(CoachController coachController){
        this.coachController = coachController;
    }
    private final String[][] coachDetails = {
//            {"Emily", "Watson", "Female"},
            {"Ethan", "Brown", "Male"},
            {"James", "Wilson", "Male"},
            {"Kate", "Russel", "Female"},
            {"Lara", "Croft", "Female"},
    };
    public void populateCoaches(){
        for (String[] details : coachDetails) {
            String firstName = details[0];
            String lastName = details[1];
            String gender = details[2];
            Coach coach = coachController.createObject(firstName,
                                                        lastName,
                                                        gender);
            String coachId = coachController.addObject(coach);
        }
    }
}
