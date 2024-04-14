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
        {"Alexander", "Clark", "Male", "2013-07-18", "4", "+447123456806"},
        {"Charlotte", "Garcia", "Female", "2015-04-18", "2", "+447123456794"},
        {"Harper", "Taylor", "Female", "2017-01-27", "2", "+447123456797"},
        {"Lily", "Moore", "Female", "2012-12-09", "2", "+447123456798"},
        {"Noah", "Jackson", "Male", "2014-04-30", "3", "+447123456799"},
        {"Ava", "Martinez", "Female", "2013-11-30", "3", "+447123456793"},
        {"Liam", "Martin", "Male", "2013-03-25", "3", "+447123456800"},
        {"Mia", "Lee", "Female", "2016-03-29", "4", "+447123456795"},
        {"Jacob", "Lee", "Male", "2016-06-17", "3", "+447123456801"},
        {"Ethan", "White", "Male", "2015-08-11", "1", "+447123456803"},
        {"Amelia", "Anderson", "Female", "2014-08-14", "1", "+447123456796"},
        {"Lucas", "Thompson", "Male", "2016-01-09", "1", "+447123456804"},
        {"Oliver", "Harris", "Male", "2014-05-22", "2", "+447123456805"},
        {"Olivia", "Johnson", "Female", "2015-02-12", "4", "+447123456790"},
        {"Benjamin", "Lewis", "Male", "2015-11-13", "2", "+447123456808"},
        {"Evelyn", "Roberts", "Female", "2018-04-22", "0", "+447123456810"},
        {"James", "Wilson", "Male", "2017-03-18", "0", "+447123456811"},
        {"Scarlett", "Johnson", "Female", "2016-10-31", "1", "+447123456812"},
        {"Theodore", "Smith", "Male", "2014-08-24", "1", "+447123456813"},
        {"Zoe", "Walker", "Female", "2015-05-15", "3", "+447123456814"},
        {"Elijah", "Hall", "Male", "2016-01-30", "0", "+447123456815"},
        {"Stella", "Allen", "Female", "2019-07-03", "3", "+447123456816"},
        {"Nathan", "Young", "Male", "2018-11-12", "3", "+447123456817"},
        {"Grace", "Hernandez", "Female", "2014-12-21", "2", "+447123456818"},
        {"Owen", "King", "Male", "2016-06-05", "4", "+447123456819"},
        {"Chloe", "Scott", "Female", "2015-02-17", "0", "+447123456820"},
        {"Aiden", "Green", "Male", "2014-09-09", "1", "+447123456821"}
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
