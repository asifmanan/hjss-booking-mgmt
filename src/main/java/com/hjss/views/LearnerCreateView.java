package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.models.Learner;
import com.hjss.utilities.*;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LearnerCreateView {
    private LearnerController learnerController;
    private List<Learner> learnerList;

    public LearnerCreateView(LearnerController learnerController) {
        this.learnerController = learnerController;
        updateLearnerList();

    }
    private void updateLearnerList(){
        learnerList = learnerController.getAllObjects();
    }

    public Learner createLearner() {
        Learner learner = null;
        try {
            TerminalManager.disableAutocomplete();
            LineReader lineReader = TerminalManager.getLineReader();
            Terminal terminal = TerminalManager.getTerminal();

            terminal.puts(InfoCmp.Capability.clear_screen);

            HelpText helpText = new HelpText();
            helpText.setAppend("\nEnter :c to cancel\n");

            helpText.setText("First Name must start with a character, max length is 20 characters");;
            String firstName = InputValidator.getAndValidateString(terminal, lineReader, "First Name: ", "^[A-Za-z].{0,19}$",helpText);
            if (firstName==null) return null;

            helpText.setText("Last Name must start with a character, max length is 20 characters");
            String lastName = InputValidator.getAndValidateString(terminal, lineReader, "Last Name: ", "^[A-Za-z].{0,19}$",helpText);
            if (lastName==null) return null;

            TerminalManager.updateCompleter(Gender.getGenderValues());
            lineReader = TerminalManager.getLineReader();
            helpText.setText("Options (M/Male  //  F/Female  //  O/Other  //  U/Unknown)");
            String gender = InputValidator.getAndValidateString(terminal, lineReader, "Gender: ", "(?i)^(m|f|o|u|male|female|other|unknown)$",helpText);
            if (gender==null) return null;
            TerminalManager.disableAutocomplete();
            lineReader = TerminalManager.getLineReader();

            String dateOfBirth = null;
            do {
                helpText.setText("Format (YYYY MM DD / YYYY-MM-DD / YYYY/MM/DD)");
                dateOfBirth = InputValidator.getAndValidateString(terminal, lineReader, "Date of Birth: ",DateUtil.getDateFormatRegex(),helpText);
                if (dateOfBirth == null) return null;
                dateOfBirth = DateUtil.convertToHyphenFormat(dateOfBirth);
            } while(!DateUtil.isDateValid(dateOfBirth));

            helpText.setText("Valid input (0-5), 0 for Beginners");
            String grade = InputValidator.getAndValidateString(terminal, lineReader, "Grade (0-5) or (): ", "^([0-5])?$",helpText);
            if (grade == null) return null;

            helpText.setText("A phone number with country code in format (+XXXXXXXXX)");
            String contactNumber = InputValidator.getAndValidateString(terminal, lineReader, "Emergency Contact Number: ", "^\\+[1-9]{1}[0-9]{1,14}$",helpText);
            if (contactNumber == null) return null;

            learner = learnerController.createObject(firstName, lastName, gender,
                                                                dateOfBirth, grade, contactNumber);
            String learnerId = learnerController.addObject(learner);

            System.out.println("   Learner created with ID: "+ learnerId);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("   Failed to create learner.");
        }
        return learner;
    }
}
