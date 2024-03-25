package com.hjss.views;

import com.hjss.controllers.LearnerController;

public class CreateLearnerView {
    private LearnerController learnerController;

    public CreateLearnerView(LearnerController learnerController) {
        this.learnerController = learnerController;
    }

    public void createLearner() {
        learnerController.createLearner();
    }
}
