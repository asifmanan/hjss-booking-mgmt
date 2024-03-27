package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.models.Learner;
import com.hjss.utilities.DateUtil;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;

public class LearnerCreateView {
    private LearnerController learnerController;

    public LearnerCreateView(LearnerController learnerController) {
        this.learnerController = learnerController;
    }

    public void createLearner() {
        try {
            LineReader lineReader = TerminalManager.getLineReader();

            HelpText helpText = new HelpText();
            helpText.setAppend("\nEnter :c to cancel\n");

            helpText.setText("First Name must start with a character, max length is 20 characters");;
            String firstName = InputValidator.getAndValidateString(lineReader, "First Name: ", "^[A-Za-z].{0,19}$",helpText);
            if (firstName==null) return;

            helpText.setText("Last Name must start with a character, max length is 20 characters");
            String lastName = InputValidator.getAndValidateString(lineReader, "Last Name: ", "^[A-Za-z].{0,19}$",helpText);
            if (lastName==null) return;

            helpText.setText("Options (M/Male  //  F/Female  //  O/Other  //  U/Unknown)");
            String gender = InputValidator.getAndValidateString(lineReader, "Gender: ", "(?i)^(m|f|o|u|male|female|other|unknown)$",helpText);
            if (gender==null) return;

            String dateOfBirth = null;
            do {
                helpText.setText("Format (YYYY MM DD / YYYY-MM-DD / YYYY/MM/DD)");
                dateOfBirth = InputValidator.getAndValidateString(lineReader, "Date of Birth: ",DateUtil.getDateFormatRegex(),helpText);
                if (dateOfBirth == null) return;
                dateOfBirth = DateUtil.convertToHyphenFormat(dateOfBirth);
            } while(!DateUtil.isDateValid(dateOfBirth));

            helpText.setText("Valid input (0-5), 0 for Beginners");
            String grade = InputValidator.getAndValidateString(lineReader, "Grade (0-5) or (): ", "^([0-5])?$",helpText);
            if (grade == null) return;

            helpText.setText("A phone number with country code in format (+XXXXXXXXX)");
            String contactNumber = InputValidator.getAndValidateString(lineReader, "Emergency Contact Number: ", "^\\+[1-9]{1}[0-9]{1,14}$",helpText);
            if (contactNumber == null) return;

            Learner learner = learnerController.createObject(firstName, lastName, gender,
                                                                dateOfBirth, grade, contactNumber);
            String learnerId = learnerController.addObject(learner);

            System.out.println("Learner created with ID: "+ learnerId);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create learner.");
        }
    }
}
