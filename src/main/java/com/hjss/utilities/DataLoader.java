package com.hjss.utilities;

import com.hjss.controllers.LearnerController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private final String LEARNER_FILE_PATH = "data" + File.separator + "learners.csv";
    private final LearnerController learnerController;
    public DataLoader(LearnerController learnerController){
        this.learnerController = learnerController;
    }
    public void loadLearnersFromCSV() {
        List<String> fieldValues = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(LEARNER_FILE_PATH))){
            String line;
            boolean isFirstLine = true;

            while((line = br.readLine()) != null){
                if(isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");

                trimValues(values);

                if(values.length == 6) {
                    learnerController.addObject(
                            learnerController.createObject(values)
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getLearnerValue(String[] values, int index) {
        // can probably add validation here using regex
        return values[index];
    }
    private static void trimValues(String[] values){
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }
    }

}
