package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.utilities.DateUtil;
import com.hjss.utilities.Gender;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;

import java.time.LocalDate;

public class CreateLearnerView {
    private LearnerController learnerController;

    public CreateLearnerView(LearnerController learnerController) {
        this.learnerController = learnerController;
    }

    public void createLearner() {
        try {
            LineReader lineReader = TerminalManager.getLineReader();

            String firstName = validateInput(lineReader, "First Name: ", "^[A-Za-z].{0,19}$");
            if (firstName==null) return;

            String lastName = validateInput(lineReader, "Last Name: ", "^[A-Za-z].{0,19}$");
            if (lastName==null) return;

            String genderString = validateInput(lineReader, "Gender (M / Male // F / Female // O / Other // U / Unknown): ", "(?i)^(m|f|o|u|male|female|other|unknown)$");
            if (genderString==null) return;
            Gender gender = Gender.fromString(genderString);

            LocalDate dateOfBirth = validateDateInput(lineReader, "Date of Birth(YYYY MM DD): ");
            if (dateOfBirth == null) return;

            String gradeString = validateInput(lineReader, "Grade (0-5) or (): ", "^([0-5])?$");
            if (gradeString == null) return;
            int grade = Integer.parseInt(gradeString);

            String contactNumber = validateInput(lineReader, "Emergency Contact Number: ", "^\\+[1-9]{1}[0-9]{1,14}$");
            if (contactNumber == null) return;

            String learnerId = learnerController.createLearner(firstName, lastName, gender,
                                                                dateOfBirth, grade, contactNumber);
            System.out.println("Learner created with ID: "+ learnerId);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create learner.");
        }
    }

    private String validateInput(LineReader lineReader, String prompt, String regex) {
        String input;
        while (true) {
            input = lineReader.readLine(prompt);
            if (":c".equals(input.trim().toLowerCase())) {
                System.out.println("Operation canceled by user.");
                return null; // User canceled the operation
            }
            if (input.matches(regex)) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return input;
    }
    private LocalDate validateDateInput(LineReader lineReader, String prompt) {
        String input;
        LocalDate dateOfBirth = null;
        boolean breakLoopFlag = false;
        while(!breakLoopFlag){
            input = lineReader.readLine(prompt);
            dateOfBirth = DateUtil.convertToDate(input);
            if(dateOfBirth != null){
                breakLoopFlag = true;
            } else {
                System.out.println("Invalid date entered, please enter a valid date");
            }
        }
        return dateOfBirth;
    }
}
