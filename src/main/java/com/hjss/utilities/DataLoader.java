package com.hjss.utilities;

import com.hjss.controllers.LearnerController;
import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.Learner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

                String firstName = getLearnerValue(values, 0);
                String lastName = getLearnerValue(values, 1);
                Gender gender = Gender.valueOf(getLearnerValue(values,2));
                LocalDate dateOfBirth = LocalDate.parse(getLearnerValue(values, 3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int grade = Integer.parseInt(getLearnerValue(values,4));
                String emergencyContact = getLearnerValue(values, 5);

                learnerController.createLearner(firstName, lastName, gender, dateOfBirth, grade, emergencyContact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getLearnerValue(String[] values, int index) {
        // can probably add validation here using regex
        return values[index];
    }

}
