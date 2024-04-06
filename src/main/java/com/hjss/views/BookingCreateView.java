package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
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
    private LessonListView lessonListView, lessonListViewByWeek, lessonListViewByDay, lessonListViewByGrade;


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

    private Optional<Learner> getLearner() {
        LearnerCreateView learnerCreateView = new LearnerCreateView(learnerController);
        return learnerCreateView.getOrCreateLearner();
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
        bookLesson();
    }
    public void bookLessonByDay(){
        this.lessonListView = new LessonListViewByDay(lessonController);
        bookLesson();
    }
    public void bookLessonByCoach(){
        this.lessonListView = new LessonListViewByCoach(lessonController, coachController);
        bookLesson();
    }
    public void bookLesson() {
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


            if (lesson == null) {
                System.out.println("   No lesson selected.\n");
                return;  // Exit if no lesson is selected (Cancelled Operation)
            } else {
                terminal.puts(InfoCmp.Capability.clear_screen);
                System.out.println("Booking for "+learner.getFormattedFullName()+", LearnerID: "+ learner.getId()+"\n");
            }

            // Check if the lesson is already booked by the learner
            if (bookingController.isAlreadyBookedByLearner(learner, lesson)) {
                System.out.println("Booking Unsuccessful, Lesson already booked by the same Learner.");
                return;
            }

            // Check if the lesson is fully booked (Max is 4)
            if (bookingController.isFullyBooked(lesson)) {
                System.out.println("Booking Unsuccessful, This lesson is fully booked!");
                return;
            }

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
}
