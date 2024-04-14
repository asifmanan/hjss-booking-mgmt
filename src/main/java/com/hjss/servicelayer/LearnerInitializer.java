package com.hjss.servicelayer;

import com.hjss.controllers.LearnerController;
import com.hjss.models.Learner;
import com.hjss.utilities.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LearnerInitializer {
    private final LearnerController learnerController;
    public LearnerInitializer(LearnerController learnerController){
        this.learnerController = learnerController;
    }
    private final String[][] learnerDetails = {
        {"Sophia", "Williams", "Female", "2016-05-08", "0", "+447123456789"},
        {"Logan", "Rodriguez", "Male", "2017-09-04", "0", "+447123456807"},
        {"Emily", "Brown", "Female", "2014-09-05", "1", "+447123456791"},
        {"Isla", "Davis", "Female", "2017-07-21", "1", "+447123456792"},
        {"Mason", "Perez", "Male", "2017-10-02", "2", "+447123456802"},
        {"Alexander", "Clark", "Male", "2013-07-18", "2", "+447123456806"},
        {"Charlotte", "Garcia", "Female", "2015-04-18", "2", "+447123456794"},
        {"Harper", "Taylor", "Female", "2017-01-27", "3", "+447123456797"},
        {"Lily", "Moore", "Female", "2012-12-09", "3", "+447123456798"},
        {"Noah", "Jackson", "Male", "2014-04-30", "3", "+447123456799"},
        {"Ava", "Martinez", "Female", "2013-11-30", "4", "+447123456793"},
        {"Liam", "Martin", "Male", "2013-03-25", "0", "+447123456800"},
        {"Mia", "Lee", "Female", "2016-03-29", "1", "+447123456795"},
        {"Jacob", "Lee", "Male", "2016-06-17", "2", "+447123456801"},
        {"Ethan", "White", "Male", "2015-08-11", "2", "+447123456803"},
        {"Amelia", "Anderson", "Female", "2014-08-14", "3", "+447123456796"},
        {"Lucas", "Thompson", "Male", "2016-01-09", "3", "+447123456804"},
        {"Oliver", "Harris", "Male", "2014-05-22", "4", "+447123456805"},
        {"Olivia", "Johnson", "Female", "2015-02-12", "0", "+447123456790"},
        {"Benjamin", "Lewis", "Male", "2015-11-13", "1", "+447123456808"}
    };
    public void populateLearners(){
        for(String[] details : learnerDetails){
            Learner learner = createLearner(details);
            learnerController.addObject(learner);
        }
    }
    private Learner createLearner(String[] values){
        String firstName = values[0];
        String lastName = values[1];
        String genderString = values[2];
        String dateOfBirthString = values[3];
        String gradeString = values[4];
        String contactNumber  = values[5];

        return createLearner(firstName, lastName, genderString,
                dateOfBirthString, gradeString, contactNumber);
    }
    private Learner createLearner(String firstName,
                                String lastName,
                                String genderString,
                                String dateOfBirthString,
                                String gradeString,
                                String contactNumber){

        Gender gender = Gender.fromString(genderString);
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int grade = Integer.parseInt(gradeString);

        return createLearner(firstName, lastName, gender,
                dateOfBirth, grade, contactNumber);
    }
    private Learner createLearner(String firstName,
                                  String lastName,
                                  Gender gender,
                                  LocalDate dateOfBirth,
                                  int grade,
                                  String contactNumber) {

        return new Learner(firstName, lastName, gender, dateOfBirth, grade, contactNumber);
    }
}
