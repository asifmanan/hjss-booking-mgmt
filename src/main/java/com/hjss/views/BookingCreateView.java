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
    private String leftMargin = " ".repeat(3);


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
        this.lessonListView = new LessonListViewByGrade(lessonController, bookingController);
        createBooking();
    }
    public void bookLessonByDay(){
        this.lessonListView = new LessonListViewByDay(lessonController, bookingController);
        createBooking();
    }
    public void bookLessonByCoach(){
        this.lessonListView = new LessonListViewByCoach(lessonController, coachController, bookingController);
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

            terminal.writer().println(" ".repeat(3) + learner.getFormattedLearnerInfo()+"\n");

            // get lesson from the list
            Lesson lesson = lessonListView.getLessonFromPaginatedList(learner);

            lesson = verifyLessonConstraints(lesson, learner);
            if(lesson == null) return;

            // If all checks pass, create the booking :)
            String bookingId = bookingController.createAndAddObject(learner, lesson);

            System.out.println(leftMargin + "Booking successful.");
            Booking booking = bookingController.getBookingById(bookingId);
            BookingDetailView bookingDetailView = new BookingDetailView(booking);
            bookingDetailView.printBookingDetails();


        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public Booking updateBooking(Booking booking){
        Learner learner = booking.getLearner();
        LessonGetView lessonGetView = new LessonGetView(lessonController, coachController, bookingController);
        Lesson newLesson = lessonGetView.getLessonsByChoice(learner);
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
                System.out.println(leftMargin + "No lesson selected.\n");
                return null;  // Exit if no lesson is selected (Cancelled Operation)
            }

            terminal.puts(InfoCmp.Capability.clear_screen);

            terminal.writer().println(" ".repeat(3) + learner.getFormattedLearnerInfo()+"\n");

            // Check if the lesson is already booked by the learner
            if(!bookingController.isGradeCriteriaValid(learner, lesson)){
                System.out.println(leftMargin + "Booking Unsuccessful, the required grade for this lesson is either "+lesson.getMinLearnerGradeRequired()+" or "+lesson.getGradeLevel());
                return null;
            }
            if (bookingController.isAlreadyBookedByLearner(learner, lesson)) {
                /* need to investigate the behaviour here, although there is clear screen
                before "if conditions" but somehow its not getting invoked with this condition
                 so putting extra clear screen here for now. */
                terminal.puts(InfoCmp.Capability.clear_screen);
                System.out.println(leftMargin + "Booking Unsuccessful, Lesson actively booked or Attended by the same Learner.\n");
                return null;
            }

            // Check if the lesson is fully booked (Max is 4)
            if (bookingController.isFullyBooked(lesson)) {
                System.out.println(leftMargin + "Booking Unsuccessful, This lesson is fully booked!");
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
