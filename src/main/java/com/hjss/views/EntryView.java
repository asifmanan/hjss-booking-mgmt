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
    private LearnerGetOrCreateView learnerGetOrCreateView;
    private LessonController lessonController;
    private BookingController bookingController;
    private LearnerListView learnerListView;
    private LessonListView lessonListViewByWeek, lessonListViewByDay;
    private BookingCreateView bookingCreateView;
    private BookingManagementView bookingManagementView;
    private MonthlyLearnerReportView monthlyLearnerReportView;
    private MonthlyCoachReportView monthlyCoachReportView;
    private BookingListViewByLearner bookingListViewByLearner;

    public EntryView() {
        initializeControllers();
        initializeViews();
    }
    private void initializeControllers(){
        this.learnerController = serviceManager.getLearnerController();
        this.coachController = serviceManager.getCoachController();
        this.lessonController = serviceManager.getLessonController();
        this.bookingController = serviceManager.getBookingController();
        this.coachController = serviceManager.getCoachController();
    }
    private void initializeViews(){
        this.learnerListView = new LearnerListView(learnerController);
        this.learnerGetOrCreateView = new LearnerGetOrCreateView(learnerController);

        this.lessonListViewByWeek = new LessonListViewByWeek(lessonController);
        this.lessonListViewByDay = new LessonListViewByDay(lessonController);

        this.bookingCreateView = new BookingCreateView(bookingController,
                lessonController,
                coachController,
                learnerGetOrCreateView
        );
        this.bookingManagementView = new BookingManagementView(bookingCreateView,learnerGetOrCreateView);
        this.monthlyLearnerReportView = new MonthlyLearnerReportView(bookingController, learnerController);
        this.monthlyCoachReportView = new MonthlyCoachReportView(bookingController, coachController);
    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");

        mainMenu.addMenuItem("attend",bookingManagementView::attendBooking,"to ATTEND a Swimming Lesson");;
        mainMenu.addMenuItem("register",learnerGetOrCreateView::createAndSelectLearner,"to REGISTER a New Learner");;
        mainMenu.addMenuItem("manage",bookingManagementView::cancelOrChangeBooking,"to CANCEL/CHANGE an existing BOOKING");;
        mainMenu.addMenuItem("logout",learnerGetOrCreateView::logoutLearner, "to LOGOUT the current selected LEARNER");
        mainMenu.addMenuItem("mlr",monthlyLearnerReportView::printLearnerReport, "to GENERATE the MONTHLY LEARNER REPORT");
        mainMenu.addMenuItem("mcr",monthlyCoachReportView::generateCoachReport, "to GENERATE the MONTHLY COACH REPORT");

        Menu bookingMenu = new Menu("book",mainMenu);
        mainMenu.addSubMenu(bookingMenu, "to BOOK a Swimming Lesson");
        bookingMenu.addMenuItem("day", bookingCreateView::bookLessonByDay, "to VIEW LESSONS by DAY");
        bookingMenu.addMenuItem("grade", bookingCreateView::bookLessonByGrade, "to VIEW LESSONS by GRADE");
        bookingMenu.addMenuItem("coach", bookingCreateView::bookLessonByCoach, "to VIEW LESSONS by COACH");

        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
