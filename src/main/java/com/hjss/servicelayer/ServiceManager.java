package com.hjss.servicelayer;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;

public class ServiceManager {
    private LearnerController learnerController = new LearnerController();
    private CoachController coachController = new CoachController();
    public ServiceManager(){

    }
    public LearnerController getLearnerController(){
        return this.learnerController;
    }
    public CoachController getCoachController(){
        return this.coachController;
    }
}
