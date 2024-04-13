package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import io.consolemenu.TerminalManager;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.Optional;

public class BookingCreateView {
    private BookingController bookingController;
    private LearnerController learnerController;
    private LessonController lessonController;
    private CoachController coachController;
    private LearnerGetOrCreateView learnerGetOrCreateView;
    private LessonListView lessonListView;


    public BookingCreateView(BookingController bookingController, LessonController lessonController, CoachController coachController, LearnerGetOrCreateView learnerGetOrCreateView){
        this.bookingController = bookingController;
        this.lessonController = lessonController;
        this.coachController = coachController;
        this.learnerGetOrCreateView = learnerGetOrCreateView;
    }
    public BookingCreateView(BookingController bookingController, LessonController lessonController, CoachController coachController, LearnerController learnerController){
        this.bookingController = bookingController;
        this.lessonController = lessonController;
        this.learnerController = learnerController;
        this.coachController = coachController;
    }

    public Optional<Learner> getSelectedLearner() {
        Learner learner = learnerGetOrCreateView.getSelectedLearner();
        if(learner!=null) return Optional.of(learner);
        else{
            learner = learnerGetOrCreateView.getAndSelectLearnerIfNotPresent();
            if (learner!=null) return Optional.of(learner);
        }

        return Optional.empty();
    }
    public void bookLessonByGrade(){
        this.lessonListView = new LessonListViewByGrade(lessonController);
        createBooking();
    }
    public void bookLessonByDay(){
        this.lessonListView = new LessonListViewByDay(lessonController);
        createBooking();
    }
    public void bookLessonByCoach(){
        this.lessonListView = new LessonListViewByCoach(lessonController, coachController);
        createBooking();
    }
    public void createBooking() {
        Optional<Learner> optionalLearner = getSelectedLearner();

        if (!optionalLearner.isPresent()) {
//            System.out.println("Booking operation cancelled.");
            return;  // Exit the method if no learner was selected (user cancelled the operation) :(
        }

        // A valid learner is present at this point :)
        Learner learner = optionalLearner.get();
        try {
            Terminal terminal = TerminalManager.getTerminal();

            System.out.println("   Creating Booking for "+learner.getFormattedFullName()+", LearnerID: "+ learner.getId()+"\n");
            // get lesson from the list
            Lesson lesson = lessonListView.getLessonFromPaginatedList();

            lesson = verifyLessonConstraints(lesson, learner);
            if(lesson == null) return;

            // If all checks pass, create the booking :)
            String bookingId = bookingController.createAndAddObject(learner, lesson);

            System.out.println("Booking successful.");
            Booking booking = bookingController.getBookingById(bookingId);
            BookingDetailView bookingDetailView = new BookingDetailView(booking);
            bookingDetailView.printBookingDetails();


        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public Booking updateBooking(Booking booking){
        Learner learner = booking.getLearner();
        LessonGetView lessonGetView = new LessonGetView(lessonController, coachController);
        Lesson newLesson = lessonGetView.getLessonsByChoice();
        if(newLesson==null) return null;
        Lesson verifiedLesson = verifyLessonConstraints(newLesson, learner);
        if(verifiedLesson==null) return null;
        booking.updateLesson(verifiedLesson);
        System.out.println(booking.getLesson().getId());
        return booking;
    }
    private Lesson verifyLessonConstraints(Lesson lesson, Learner learner){
        try{
            Terminal terminal = TerminalManager.getTerminal();
            if (lesson == null) {
                System.out.println("   No lesson selected.\n");
                return null;  // Exit if no lesson is selected (Cancelled Operation)
            }

            terminal.puts(InfoCmp.Capability.clear_screen);
            System.out.println("Booking for "+learner.getFormattedFullName()+", LearnerID: "+ learner.getId()+"\n");

            // Check if the lesson is already booked by the learner
            if(!bookingController.isGradeCriteriaValid(learner, lesson)){
                System.out.println("Booking Unsuccessful, minimum required grade for this lesson is "+lesson.getMinLearnerGradeRequired());
                return null;
            }
            if (bookingController.isAlreadyBookedByLearner(learner, lesson)) {
                System.out.println("Booking Unsuccessful, Lesson already booked by the same Learner.");
                return null;
            }

            // Check if the lesson is fully booked (Max is 4)
            if (bookingController.isFullyBooked(lesson)) {
                System.out.println("Booking Unsuccessful, This lesson is fully booked!");
                return null;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return lesson;
    }
    public BookingController getBookingController(){
        return this.bookingController;
    }
}
