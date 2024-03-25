package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.utilities.DateUtil;
import com.hjss.utilities.Gender;
import com.hjss.utilities.HelpText;
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

            HelpText helpText = new HelpText();
            helpText.setAppend("\nEnter :c to cancel\n");



            helpText.setText("First Name must start with a character, max length is 20 characters");;
            String firstName = getAndValidateString(lineReader, "First Name: ", "^[A-Za-z].{0,19}$",helpText);
            if (firstName==null) return;

            helpText.setText("Last Name must start with a character, max length is 20 characters");
            String lastName = getAndValidateString(lineReader, "Last Name: ", "^[A-Za-z].{0,19}$",helpText);
            if (lastName==null) return;

            helpText.setText("Options (M/Male  //  F/Female  //  O/Other  //  U/Unknown)");
            String genderString = getAndValidateString(lineReader, "Gender: ", "(?i)^(m|f|o|u|male|female|other|unknown)$",helpText);
            if (genderString==null) return;
            Gender gender = Gender.fromString(genderString);

            helpText.setText("Format (YYYY MM DD / YYYY-MM-DD / YYYY/MM/DD)");
            LocalDate dateOfBirth = getAndValidateDate(lineReader, "Date of Birth: ",helpText);
            if (dateOfBirth == null) return;

            helpText.setText("Valid input (0-5), 0 for Beginners");
            String gradeString = getAndValidateString(lineReader, "Grade (0-5) or (): ", "^([0-5])?$",helpText);
            if (gradeString == null) return;
            int grade = Integer.parseInt(gradeString);

            helpText.setText("A phone number with country code in format (+XXXXXXXXX)");
            String contactNumber = getAndValidateString(lineReader, "Emergency Contact Number: ", "^\\+[1-9]{1}[0-9]{1,14}$",helpText);
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
    private String getAndValidateString(LineReader lineReader, String prompt, String regex, HelpText helpText){
        String input;
        while (true) {
            if(helpText!=null){
                System.out.println(helpText.getHelpText());
            }
            input = lineReader.readLine("    "+prompt);
            if (":c".equalsIgnoreCase(input.trim())) {
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
    private String getAndValidateString(LineReader lineReader, String prompt, String regex) {
        return getAndValidateString(lineReader, prompt,regex,null);
    }
    private LocalDate getAndValidateDate(LineReader lineReader, String prompt, HelpText helpText){
        String input;
        LocalDate dateOfBirth = null;
        boolean breakLoopFlag = false;
        while(!breakLoopFlag){
            if (helpText!= null){
                System.out.println(helpText.getHelpText());
            }
            input = lineReader.readLine("    "+prompt);
            dateOfBirth = DateUtil.convertToDate(input);
            if(dateOfBirth != null){
                breakLoopFlag = true;
            } else {
                System.out.println("Invalid date entered, please enter a valid date");
            }
        }
        return dateOfBirth;
    }
    private LocalDate getAndValidateDate(LineReader lineReader, String prompt) {
        return getAndValidateDate(lineReader, prompt, null);
    }
}
