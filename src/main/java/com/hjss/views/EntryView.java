package com.hjss.views;

import com.hjss.controllers.LearnerController;
import io.consolemenu.ConsoleMenu;
import io.consolemenu.Menu;

public class EntryView {

    private LearnerController learnerController;
    private LearnerCreateView learnerCreateView;
    private LearnerListView learnerListView;

    public EntryView() {
        this.learnerController = new LearnerController();
        this.learnerCreateView = new LearnerCreateView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);

    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");
        Menu learnerMenu = new Menu("Learner",mainMenu);
        mainMenu.addSubMenu(learnerMenu);

        learnerMenu.addMenuItem("New Learner", learnerCreateView::createLearner);
        learnerMenu.addMenuItem("Learners List", learnerListView::printLearnerList);


        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
