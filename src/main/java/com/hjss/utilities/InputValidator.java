package com.hjss.utilities;

import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.time.LocalDate;

public class InputValidator {
    public static String getAndValidateString(Terminal terminal, LineReader lineReader, String prompt, String regex, HelpText helpText){
        String input;

        while (true) {
            if(helpText!=null){
                terminal.writer().println(helpText.getHelpText());
            }
            input = lineReader.readLine("   "+prompt);
            if (":c".equalsIgnoreCase(input.trim())) {
                clearScreen();
                terminal.writer().println("   Operation canceled by user.");
                return null; // User canceled the operation
            }
            if (input.trim().matches(regex)) {
                break;
            } else {
                clearScreen();
                terminal.writer().println("   Invalid input. Please try again.\n");
            }
        }
        clearScreen();
        return input;
    }
    public static Pair<String,Boolean> getAndValidateStringSingleRun(Terminal terminal, LineReader lineReader, String prompt, String regex, HelpText helpText){
        String input;
        if(helpText!=null){
            terminal.writer().println(helpText.getHelpText());
        }
        input = lineReader.readLine("   "+prompt);
        if (":c".equalsIgnoreCase(input.trim())) {
            clearScreen();
            terminal.writer().println("   Operation canceled by user.");
            return null; // User canceled the operation
        }
        if (input.trim().matches(regex)) {
            clearScreen();
            return new Pair<>(input.trim(),false);
        }
        clearScreen();
//        true in the pair is to identify invalid input
        return new Pair<>(input.trim(), true);
    }
    public static String getAndValidateString(Terminal terminal, LineReader lineReader, String prompt, String regex) {
        return getAndValidateString(terminal, lineReader, prompt,regex,null);
    }
    public static LocalDate getAndValidateDate(LineReader lineReader, String prompt, HelpText helpText){
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
        clearScreen();
        return dateOfBirth;
    }
    public static LocalDate getAndValidateDate(LineReader lineReader, String prompt) {
        return getAndValidateDate(lineReader, prompt, null);
    }
    public static void clearScreen(){
        try {
            Terminal terminal = TerminalManager.getTerminal();
            terminal.puts(InfoCmp.Capability.clear_screen);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
