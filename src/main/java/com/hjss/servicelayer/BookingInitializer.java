package com.hjss.servicelayer;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.models.Booking;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.utilities.Grade;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BookingInitializer {
    private final BookingController bookingController;
    private final LessonController lessonController;
    private final LearnerController learnerController;
    private List<List<Lesson>> gradedLessonList;
    private List<List<Learner>> gradedLearnerList;
    private static final Random random = new Random();
    private static final Random randomRating = new Random();


    public BookingInitializer(BookingController bookingController, LessonController lessonController, LearnerController learnerController) {
        this.bookingController = bookingController;
        this.lessonController = lessonController;
        this.learnerController = learnerController;
        populateGradedLearners();
        populateGradedLessons();
    }
    private void populateGradedLessons(){
        this.gradedLessonList = lessonController.getGradedLessons();
    }
    private void populateGradedLearners(){
        this.gradedLearnerList = learnerController.getGradedLearners();
    }

    public List<Learner> getGradedLearnerListByGrade(int grade) {
        return gradedLearnerList.get(grade);
    }
    public List<Lesson> getGradedLessonListByGrade(int grade){
        return this.gradedLessonList.get(grade);
    }

    private void initializePastBookings() {
        temporalBookingInitializer(
                date -> date.isBefore(LocalDate.now()),
                this::randomAttendanceAndCancellation
        );
    }

    private void initializeFutureBookings() {
        temporalBookingInitializer(
                date -> date.isAfter(LocalDate.now()),
                booking -> {} // No attend or cancel for future booking
        );
    }

    private void temporalBookingInitializer(Predicate<LocalDate> dateFilter, Consumer<Booking> postBookingAction) {
        for (int i = 0; i <= Grade.getMaxGrade(); i++) {
            if (learnerController.filterByGrade(i).isEmpty()) continue;

            for (Lesson lesson : lessonController.filterByGrade(i)) {
                if (!dateFilter.test(lesson.getWeekDayTimeSlot().getDate())) continue;

//                List<Learner> learnersList = new ArrayList<>(i == 0
//                        ? learnerController.filterByEligibleGrade(i)
//                        : learnerController.filterByGrade(i));

                List<Learner> learnersList = new ArrayList<>(learnerController.filterByEligibleGrade(i));

                if (learnersList.isEmpty()) continue;

                Collections.shuffle(learnersList);
                lesson = lessonController.getLesson(lesson.getId());

                for (Learner learner : learnersList) {
                    if (bookingController.isFullyBooked(lesson) || !toBookOrNotToBook()) break;
                    if (lesson.getWeekDayTimeSlot().getDate().isBefore(LocalDate.now()) && !toBookOrNotToBook()) break;
                    learner = learnerController.getLearnerById(learner.getId());

                    if(LearnerHasBookingWithGrade(learner, i)) continue;

                    String bookingId = bookingController.createAndAddObject(learner, lesson);
                    Booking booking = bookingController.getBookingById(bookingId);
                    postBookingAction.accept(booking);
                }
            }
        }
    }
    public boolean LearnerHasBookingWithGrade(Learner learner, int gradeLevel) {
        List<Booking> bookingList = bookingController.getBookingsByLearner(learner);
        Optional<Booking> bookingWithGrade = bookingList.stream()
           .filter(booking -> booking.getLesson().getGradeLevel() == gradeLevel).findFirst();
        // Return true if a booking with the specified grade level is found
        return bookingWithGrade.isPresent();
    }


    public void initializeBookings(){
        initializePastBookings();
        initializeFutureBookings();
    }
    private boolean toBookOrNotToBook(){
        int chance = random.nextInt(100);
        return chance < 60;
    }
    private boolean toAttendOrCancel(){
        int chance = random.nextInt(100);
        return chance < 75;
    }
    private void randomAttendanceAndCancellation(Booking booking){
        boolean attendBooking = toAttendOrCancel();
        if(attendBooking){
            int rating = getRandomRating();
            booking.attendAndRate(rating);
        } else {
            booking.cancelBooking();
        }
    }
    private int getRandomRating() {
        int[] ratings = { 1, 2, 3, 4, 5, 3, 4, 5, 3, 4, 5}; // More entries for 3, 4, 5
        return ratings[randomRating.nextInt(ratings.length)];
    }
}