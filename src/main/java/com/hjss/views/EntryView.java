package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.Learner;
import io.consolemenu.ConsoleMenu;
import io.consolemenu.Menu;

public class EntryView {

    private LearnerController learnerController;
    private CreateLearnerView createLearnerView;

    public EntryView() {
        this.learnerController = new LearnerController();
        this.createLearnerView = new CreateLearnerView(learnerController);
    }

    public void initializeMenu() {
        Menu mainMenu = new Menu("Main");
        Menu learnerMenu = new Menu("Learner",mainMenu);
        mainMenu.addSubMenu(learnerMenu);

        learnerMenu.addMenuItem("New Learner", createLearnerView::createLearner);


        ConsoleMenu consoleMenu = new ConsoleMenu(mainMenu);
        consoleMenu.initialize();
    }
}
