package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.models.Learner;
import com.hjss.utilities.TablePrinter;

import java.util.*;

public class LearnerListView {
    private LearnerController learnerController;
    private List<String> headers;
    protected String leftMargin = " ".repeat(3);
    public LearnerListView(LearnerController learnerController){
        this.learnerController = learnerController;
        this.headers = Arrays.asList("Id", "fName", "lName", "Gender","Grade", "Emergency Contact");
    }
    public Map<String, Integer> getColumnWidths(){
        Map<String, Integer> columnWidths = new LinkedHashMap<>();

        columnWidths.put("Id", 10);
        columnWidths.put("fName", 15);
        columnWidths.put("lName", 15);
        columnWidths.put("Gender", 10);
        columnWidths.put("Grade", 10);
        columnWidths.put("Emergency Contact", 20);

        return columnWidths;
    }
    public void printLearnerList(){
        List<Learner> learnerList = learnerController.getAllObjects();
        Map<String, Integer> columnWidths = getColumnWidths();
        TablePrinter tablePrinter = new TablePrinter(this.headers, columnWidths);
        tablePrinter.printHeader();
        for(Learner learner : learnerList){
            List<String> learnerData = new ArrayList<>();

            learnerData.add(learner.getId());
            learnerData.add(learner.getFirstName());
            learnerData.add(learner.getLastName());
            learnerData.add(learner.getGenderString());
            learnerData.add(learner.getGradeLevelString());
            learnerData.add(learner.getEmergencyContactNumber());

            tablePrinter.printRow(learnerData);
        }
        System.out.print("\n\n");
    }
}
