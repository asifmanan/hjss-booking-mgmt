package com.hjss.views;

import com.hjss.controllers.CoachController;
import com.hjss.models.Coach;
import com.hjss.utilities.TablePrinter;

import java.util.*;

public class CoachListView {
    private CoachController coachController;
    private List<String> headers;
    public CoachListView(CoachController coachController){
        this.coachController = coachController;
        this.headers = Arrays.asList("Id", "fName", "lName", "Gender");
    }

    private Map<String, Integer> getColumnWidths(){
        Map<String, Integer> columnWidths = new LinkedHashMap<>();

        columnWidths.put("Id", 10);
        columnWidths.put("fName", 12);
        columnWidths.put("lName", 12);
        columnWidths.put("Gender", 8);

        return columnWidths;
    }

    public void printCoachList(){
        List<Coach> coachList = coachController.getAllObjects();
        Map<String, Integer> columnWidths = getColumnWidths();
        TablePrinter tablePrinter = new TablePrinter(this.headers, columnWidths);
        tablePrinter.printHeader();
        for(Coach coach : coachList){
            List<String> coachData = new ArrayList<>();

            coachData.add(coach.getId());
            coachData.add(coach.getFirstName());
            coachData.add(coach.getLastName());
            coachData.add(coach.getGenderString());

            tablePrinter.printRow(coachData);
        }
        System.out.print("\n\n");
    }
}
