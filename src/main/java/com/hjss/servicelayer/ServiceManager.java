package com.hjss.servicelayer;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;

public class ServiceManager {
    private LearnerController learnerController = new LearnerController();
    private CoachController coachController = new CoachController();
    private LearnerInitializer learnerInitializer;
    private CoachInitializer coachInitializer;
    public ServiceManager(){
        learnerInitializer = new LearnerInitializer(learnerController);
        coachInitializer = new CoachInitializer(coachController);
        startServices();
    }
    private void startServices(){
        learnerInitializer.populateLearners();
        coachInitializer.populateCoaches();
    }
    public LearnerController getLearnerController(){
        return this.learnerController;
    }
    public CoachController getCoachController(){
        return this.coachController;
    }
}
