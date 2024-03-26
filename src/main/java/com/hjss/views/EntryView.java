package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.Learner;
import io.consolemenu.ConsoleMenu;
import io.consolemenu.Menu;

public class EntryView {

    private LearnerController learnerController;
    private CreateLearnerView createLearnerView;
    private LearnerListView learnerListView;

    public EntryView() {
        this.learnerController = new LearnerController();
        this.createLearnerView = new CreateLearnerView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);

    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");
        Menu learnerMenu = new Menu("Learner",mainMenu);
        mainMenu.addSubMenu(learnerMenu);

        learnerMenu.addMenuItem("New Learner", createLearnerView::createLearner);
        learnerMenu.addMenuItem("Learners List", learnerListView::printLearnerList);


        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
