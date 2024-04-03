package com.hjss.views;

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
    private LearnerListView learnerListView;
    private LessonListView lessonListView;

    public EntryView() {
//        serviceManager.initializeData();

        this.learnerController = serviceManager.getLearnerController();
        this.coachController = serviceManager.getCoachController();
        this.lessonController = serviceManager.getLessonController();

        this.learnerCreateView = new LearnerCreateView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);

        this.lessonListView = new LessonListView(lessonController);

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
        bookingMenu.addMenuItem("Week", lessonListView::viewLessonsByWeekPaginated, "to VIEW LESSONS by WEEK");
        bookingMenu.addMenuItem("Day", lessonListView::viewLessonsByDayPaginated, "to VIEW LESSONS by DAY");

        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
