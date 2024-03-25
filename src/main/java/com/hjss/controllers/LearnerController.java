package com.hjss.controllers;

import com.hjss.dataregistry.ModelRegister;
import com.hjss.models.Learner;
import com.hjss.utilities.DateUtil;
import com.hjss.utilities.Gender;
import com.hjss.utilities.Grade;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LearnerController {
    private ModelRegister<Learner> learnerRegister;
    public LearnerController(){
        this.learnerRegister = new ModelRegister<>();
    }
    public String createLearner(){
        String id="";
        try {
            LineReader lineReader = TerminalManager.getLineReader();

            String firstName = validateInput(lineReader, "First Name: ", "^[A-Za-z].{0,19}$");
            String lastName = validateInput(lineReader, "Last Name: ", "^[A-Za-z].{0,19}$");
            Gender gender = Gender.fromString(validateInput(lineReader, "Gender (M / Male // F / Female // O / Other // U / Unknown): ", "(?i)^(m|f|o|u|male|female|other|unknown)$"));
            LocalDate dateOfBirth = validateDateInput(lineReader, "Date of Birth(YYYY MM DD): ");
            int grade = Integer.parseInt(validateInput(lineReader, "Grade (0-5) or (): ", "^([0-5])?$"));
            String contactNumber = validateInput(lineReader, "Emergency Contact Number: ", "^\\+[1-9]{1}[0-9]{1,14}$");

            Learner learner = new Learner(firstName, lastName, gender, dateOfBirth, grade, contactNumber);
            id = learnerRegister.add(learner);

            System.out.println("Learner created with ID: " + learner.getLearnerId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create learner.");
        }
        return id;
    }
    private String validateInput(LineReader lineReader, String prompt, String regex) {
        String input;
        while (true) {
            input = lineReader.readLine(prompt);
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
