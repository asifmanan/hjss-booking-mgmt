package com.hjss.servicelayer;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.TimeSlotController;

public class ServiceManager {
    private LearnerController learnerController = new LearnerController();
    private CoachController coachController = new CoachController();
    private TimeSlotController timeSlotController = new TimeSlotController();
    private LearnerInitializer learnerInitializer;
    private CoachInitializer coachInitializer;
    private TimeSlotInitializer timeSlotInitializer;
    public ServiceManager(){
        learnerInitializer = new LearnerInitializer(learnerController);
        coachInitializer = new CoachInitializer(coachController);
        timeSlotInitializer = new TimeSlotInitializer(timeSlotController);

        startServices();
    }
    private void startServices(){
        learnerInitializer.populateLearners();
        coachInitializer.populateCoaches();
        timeSlotInitializer.populateTimeSlots();
    }
    public LearnerController getLearnerController(){
        return this.learnerController;
    }
    public CoachController getCoachController(){
        return this.coachController;
    }
    public TimeSlotController getTimeSlotController(){
        return this.timeSlotController;
    }
}
