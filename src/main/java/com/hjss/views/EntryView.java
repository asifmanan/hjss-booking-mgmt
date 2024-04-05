package com.hjss.views;

import com.hjss.controllers.BookingController;
import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.controllers.LessonController;
import com.hjss.servicelayer.ServiceManager;
import io.consolemenu.ConsoleMenu;
import io.consolemenu.Menu;

public class EntryView {
    private ServiceManager serviceManager = new ServiceManager();
    private LearnerController learnerController;
    private CoachController coachController;
    private LearnerCreateView learnerCreateView;
    private LessonController lessonController;
    private BookingController bookingController;
    private LearnerListView learnerListView;
    private LessonListView lessonListViewByWeek, lessonListViewByDay;
    private BookingCreateView bookingCreateView;
    private BookingListView bookingListView;


    public EntryView() {
//        serviceManager.initializeData();

        this.learnerController = serviceManager.getLearnerController();
        this.coachController = serviceManager.getCoachController();
        this.lessonController = serviceManager.getLessonController();
        this.bookingController = serviceManager.getBookingController();
        this.coachController = serviceManager.getCoachController();

        this.learnerCreateView = new LearnerCreateView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);

        this.lessonListViewByWeek = new LessonListViewByWeek(lessonController);
        this.lessonListViewByDay = new LessonListViewByDay(lessonController);

        this.bookingCreateView = new BookingCreateView(bookingController,
                                                        lessonController,
                                                        learnerController,
                                                        coachController);
        this.bookingListView = new BookingListView(bookingController);


    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");
        Menu learnerMenu = new Menu("Learner",mainMenu);
        Menu bookingMenu = new Menu("Booking",mainMenu);

        mainMenu.addSubMenu(learnerMenu, "to manage LEARNERS");
        mainMenu.addSubMenu(bookingMenu, "to manage BOOKINGS");

        learnerMenu.addMenuItem("CreateLearner", learnerCreateView::createLearner,"to create a new LEARNER");
        learnerMenu.addMenuItem("ListLearners", learnerListView::printLearnerList,"to view all LEARNERS");

//        bookingMenu.addMenuItem("BookByDay",);

//        bookingMenu.addMenuItem("BookLesson",bookingCreateView);
        bookingMenu.addMenuItem("Week", lessonListViewByWeek::getLessonFromPaginatedList, "to VIEW LESSONS by WEEK");
        bookingMenu.addMenuItem("Day", bookingCreateView::bookLessonByDay, "to VIEW LESSONS by DAY");
        bookingMenu.addMenuItem("Grade", bookingCreateView::bookLessonByGrade, "to VIEW LESSONS by GRADE");
        bookingMenu.addMenuItem("Coach", bookingCreateView::bookLessonByCoach, "to VIEW LESSONS by COACH");
        bookingMenu.addMenuItem("ViewBookings", bookingListView::printList, "to VIEW BOOKINGS");

        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
