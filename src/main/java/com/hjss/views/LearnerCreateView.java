package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.models.Learner;
import com.hjss.models.Person;
import com.hjss.utilities.*;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.time.LocalDate;
import java.util.List;

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
            boolean isDateOfBirthValid;
            do {
                helpText.setText("Format (YYYY MM DD / YYYY-MM-DD / YYYY/MM/DD)");
                dateOfBirth = InputValidator.getAndValidateString(terminal, lineReader, "Date of Birth: ",DateUtil.getDateFormatRegex(),helpText);
                if (dateOfBirth == null) return null;
                dateOfBirth = DateUtil.convertToHyphenFormat(dateOfBirth);
                isDateOfBirthValid = DateUtil.isDateValid(dateOfBirth);
                if(isDateOfBirthValid){
                    if(isLearnerAgeValid(LocalDate.parse(dateOfBirth))){
                        break;
                    }
                    else {
                        String confirmationInput = dobRetryConfirmation(terminal, lineReader);
                        if(confirmationInput == null
                                || confirmationInput.equalsIgnoreCase("n"))
                            return null;
                    }
                }
            } while(true);

            helpText.setText("Valid input (0-5), 0 for Beginners");
            String grade = InputValidator.getAndValidateString(terminal, lineReader, "Grade (0-5) or (): ", "^([0-5])?$",helpText);
            if (grade == null) return null;

            helpText.setText("A phone number with country code in format (+XXXXXXXXX)");
            String contactNumber = InputValidator.getAndValidateString(terminal, lineReader, "Emergency Contact Number: ", "^\\+[1-9]{1}[0-9]{1,14}$",helpText);
            if (contactNumber == null) return null;

            learner = learnerController.createObject(firstName, lastName, gender,                     dateOfBirth, grade, contactNumber);
            String learnerId = learnerController.addObject(learner);

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("   Failed to create learner.");
        }
        return learner;
    }
    private boolean isLearnerAgeValid(LocalDate dateOfBirth){
        int age = Person.calculateAge(dateOfBirth);
        if(age >= 4 && age <=11){
            return true;
        }
        return false;
    }
    private String dobRetryConfirmation(Terminal terminal, LineReader lineReader){
        String leftMargin = " ".repeat(3);
        String textPrepend = "\n"+leftMargin+"Registration requires the learner to be between 4 and 11 years old.";
        String text = "\n"+leftMargin+"Registration process halted!\n";
        String textAppend = "\n"+leftMargin+"If there was a mistake, enter (y) to try again or (n) to cancel registration"+
                            "\n"+leftMargin+"Enter :c to cancel\n";
        HelpText confirmationHelpText = new HelpText(text,textAppend,textPrepend);
        String regex = "^[yYnN]$";
        String prompt = "ConfirmRetry(y/n) > ";
        return InputValidator.getAndValidateString(terminal, lineReader,prompt,regex,confirmationHelpText);
    }
}
