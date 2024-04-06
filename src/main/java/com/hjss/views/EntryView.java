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
    private LearnerGetOrCreateView learnerGetOrCreateView;
    private LessonController lessonController;
    private BookingController bookingController;
    private LearnerListView learnerListView;
    private LessonListView lessonListViewByWeek, lessonListViewByDay;
    private BookingCreateView bookingCreateView;
    private BookingManagementView bookingManagementView;
    private BookingListViewByLearner bookingListViewByLearner;


    public EntryView() {
//        serviceManager.initializeData();

        this.learnerController = serviceManager.getLearnerController();
        this.coachController = serviceManager.getCoachController();
        this.lessonController = serviceManager.getLessonController();
        this.bookingController = serviceManager.getBookingController();
        this.coachController = serviceManager.getCoachController();

        this.learnerCreateView = new LearnerCreateView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);
        this.learnerGetOrCreateView = new LearnerGetOrCreateView(learnerController);


        this.lessonListViewByWeek = new LessonListViewByWeek(lessonController);
        this.lessonListViewByDay = new LessonListViewByDay(lessonController);
        this.bookingManagementView = new BookingManagementView(bookingController, learnerGetOrCreateView);

//        this.bookingCreateView = new BookingCreateView(bookingController,
//                                                        lessonController,
//                                                        coachController,
//                                                        learnerController
//                                                        );
        this.bookingCreateView = new BookingCreateView(bookingController,
                                                        lessonController,
                                                        coachController,
                                                        learnerGetOrCreateView
                                                        );

//        this.bookingListViewByLearner = new BookingListViewByLearner(bookingController, learnerGetOrCreateView);


    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");

        Menu bookingMenu = new Menu("Book",mainMenu);

        mainMenu.addMenuItem("Attend",null,"to ATTEND a Swimming Lesson");;
        mainMenu.addMenuItem("Register",learnerGetOrCreateView::createAndSelectLearner,"to REGISTER a New Learner");;
        mainMenu.addMenuItem("Manage",bookingManagementView::cancelOrChangeBooking,"to CANCEL/CHANGE an existing BOOKING");;

        mainMenu.addSubMenu(bookingMenu, "to BOOK a Swimming Lesson");
        bookingMenu.addMenuItem("Day", bookingCreateView::bookLessonByDay, "to VIEW LESSONS by DAY");
        bookingMenu.addMenuItem("Grade", bookingCreateView::bookLessonByGrade, "to VIEW LESSONS by GRADE");
        bookingMenu.addMenuItem("Coach", bookingCreateView::bookLessonByCoach, "to VIEW LESSONS by COACH");

        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
