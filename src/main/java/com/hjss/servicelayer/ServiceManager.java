package com.hjss.servicelayer;

import com.hjss.controllers.*;
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
    private BookingController bookingController = new BookingController();
    private LearnerInitializer learnerInitializer;
    private CoachInitializer coachInitializer;
    private TimeSlotInitializer timeSlotInitializer;
    private LessonInitializer lessonInitializer;
    private BookingInitializer bookingInitializer;
    public ServiceManager(){
        learnerInitializer = new LearnerInitializer(learnerController);
        coachInitializer = new CoachInitializer(coachController);
        timeSlotInitializer = new TimeSlotInitializer(timeSlotController);
        lessonInitializer = new LessonInitializer(lessonController, timeSlotController, coachController);
        bookingInitializer = new BookingInitializer(bookingController, lessonController, learnerController);

        startServices();
    }
    private void startServices() {
        learnerInitializer.populateLearners();
        coachInitializer.populateCoaches();
        timeSlotInitializer.populateTimeSlots();

        lessonInitializer.initializeLessons();
        bookingInitializer.initializeBookings();


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
    public LessonController getLessonController(){
        return this.lessonController;
    }
    public BookingController getBookingController(){
        return this.bookingController;
    }
}
