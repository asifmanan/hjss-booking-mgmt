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
        {"Sophia", "Williams", "Female", "2016-05-08", "2", "+447123456789"},
        {"Olivia", "Johnson", "Female", "2015-02-12", "1", "+447123456790"},
        {"Emily", "Brown", "Female", "2014-09-05", "0", "+447123456791"},
        {"Isla", "Davis", "Female", "2017-07-21", "2", "+447123456792"},
        {"Ava", "Martinez", "Female", "2013-11-30", "1", "+447123456793"},
        {"Charlotte", "Garcia", "Female", "2015-04-18", "0", "+447123456794"},
        {"Mia", "Lee", "Female", "2016-03-29", "2", "+447123456795"},
        {"Amelia", "Anderson", "Female", "2014-08-14", "1", "+447123456796"},
        {"Harper", "Taylor", "Female", "2017-01-27", "0", "+447123456797"},
        {"Lily", "Moore", "Female", "2012-12-09", "2", "+447123456798"},
        {"Noah", "Jackson", "Male", "2014-04-30", "2", "+447123456799"},
        {"Liam", "Martin", "Male", "2013-03-25", "1", "+447123456800"},
        {"Jacob", "Lee", "Male", "2016-06-17", "0", "+447123456801"},
        {"Mason", "Perez", "Male", "2017-10-02", "2", "+447123456802"},
        {"Ethan", "White", "Male", "2015-08-11", "1", "+447123456803"},
        {"Lucas", "Thompson", "Male", "2016-01-09", "0", "+447123456804"},
        {"Oliver", "Harris", "Male", "2014-05-22", "2", "+447123456805"},
        {"Alexander", "Clark", "Male", "2013-07-18", "1", "+447123456806"},
        {"Logan", "Rodriguez", "Male", "2017-09-04", "0", "+447123456807"},
        {"Benjamin", "Lewis", "Male", "2015-11-13", "2", "+447123456808"}
    };
    public void populateLearners(){
        for(String[] details : learnerDetails){
            Learner learner = createLearner(details);
            learnerController.addObject(learner);
        }
    }
    public Learner createLearner(String[] values){
        String firstName = values[0];
        String lastName = values[1];
        String genderString = values[2];
        String dateOfBirthString = values[3];
        String gradeString = values[4];
        String contactNumber  = values[5];

        return createLearner(firstName, lastName, genderString,
                dateOfBirthString, gradeString, contactNumber);
    }
    public Learner createLearner(String firstName,
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
