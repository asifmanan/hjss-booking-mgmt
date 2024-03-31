package com.hjss.views;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LearnerController;
import com.hjss.servicelayer.ServiceManager;
import io.consolemenu.ConsoleMenu;
import io.consolemenu.Menu;

public class EntryView {
    private ServiceManager serviceManager = new ServiceManager();
    private LearnerController learnerController;
    private CoachController coachController;
    private LearnerCreateView learnerCreateView;
    private LearnerListView learnerListView;

    public EntryView() {
//        serviceManager.initializeData();

        this.learnerController = serviceManager.getLearnerController();
        this.coachController = serviceManager.getCoachController();

        this.learnerCreateView = new LearnerCreateView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);

    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");
        Menu learnerMenu = new Menu("Learner",mainMenu);
        mainMenu.addSubMenu(learnerMenu, "to manage LEARNERS");

        learnerMenu.addMenuItem("CreateLearner", learnerCreateView::createLearner,"to create a new LEARNER");
        learnerMenu.addMenuItem("ListLearners", learnerListView::printLearnerList,"to view all LEARNERS");


        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
