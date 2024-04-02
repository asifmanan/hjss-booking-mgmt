package com.hjss.servicelayer;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.controllers.TimeSlotController;
import com.hjss.models.Lesson;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.IOException;

public class ServiceManager {
    private LearnerController learnerController = new LearnerController();
    private CoachController coachController = new CoachController();
    private TimeSlotController timeSlotController = new TimeSlotController();
    private LessonController lessonController = new LessonController();
    private LearnerInitializer learnerInitializer;
    private CoachInitializer coachInitializer;
    private TimeSlotInitializer timeSlotInitializer;
    private LessonInitializer lessonInitializer;
    public ServiceManager(){
        learnerInitializer = new LearnerInitializer(learnerController);
        coachInitializer = new CoachInitializer(coachController);
        timeSlotInitializer = new TimeSlotInitializer(timeSlotController);


        startServices();
    }
    private void startServices() {
        learnerInitializer.populateLearners();
        coachInitializer.populateCoaches();
        timeSlotInitializer.populateTimeSlots();
        lessonInitializer = new LessonInitializer(lessonController, timeSlotController, coachController);
        lessonInitializer.initializeLessons();
        try{
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            lineReader.readLine(">>");
        } catch (IOException e){
            e.printStackTrace();
        }

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
