package com.hjss.views;

import com.hjss.controllers.LearnerController;
import com.hjss.models.Learner;
import com.hjss.utilities.HelpText;
import com.hjss.utilities.InputValidator;
import com.hjss.utilities.Pair;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LearnerGetOrCreateView {
    private LearnerController learnerController;
    private List<Learner> learnerList;
    private LearnerCreateView learnerCreateView;
    private LearnerListView learnerListView;
    private String leftMargin = " ".repeat(3);
    private Learner selectedLearner;
    public LearnerGetOrCreateView(LearnerController learnerController){
        this.learnerController = learnerController;
        this.learnerCreateView = new LearnerCreateView(learnerController);
        this.learnerListView = new LearnerListView(learnerController);
        updateLearnerList();
    }
    public Learner getAndSelectLearnerIfNotPresent(){
        selectLearnerIfNotPresent();
        return getSelectedLearner();
    }
    public Learner getSelectedLearner(){
        return this.selectedLearner;
    }
    public void selectLearnerIfNotPresent(){
        if(getSelectedLearner()==null){
            updateSelectedLearner();
        }
    }
    private void updateLearnerList(){
        learnerList = learnerController.getAllObjects();
    }

    public void createLearner(){
        Learner learner = learnerCreateView.createLearner();
        TerminalManager.disableAutocomplete();
        try{
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            if (learner == null){
                terminal.puts(InfoCmp.Capability.clear_screen);
                terminal.writer().println(leftMargin+"Operation Aborted! Learner Not Selected");
            } else {
                this.selectedLearner=learner;
//                terminal.writer().println(leftMargin+"Selected Learner ID: " + selectedLearner.getId());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void updateSelectedLearner(){
        updateLearnerList();
        Learner learner = null;
        List<String> learnerIds = learnerList.stream().map(Learner::getId).toList();
        List<String> learnerIdsNew = new ArrayList<>(learnerIds);
        learnerIdsNew.add("New");
        TerminalManager.updateCompleter(learnerIdsNew);
        try{
            Terminal terminal = TerminalManager.getTerminal();
            LineReader lineReader = TerminalManager.getLineReader();
            while(true){
                Pair<Learner, Boolean> learnerBooleanPair = fetchLearner(terminal, lineReader);
                if(learnerBooleanPair == null) {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Operation Aborted! Learner Not Selected");
                    return;
                }
                if(learnerBooleanPair.getInvalidFlag()){
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin + "Invalid Input, please try again");
                    continue;
                }
                learner = learnerBooleanPair.getObj();
                if (learner == null) {
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    terminal.writer().println(leftMargin+"Learner Not Found!");
                }
                else {
                    if(!learner.isAgeValid()){
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        terminal.writer().print(leftMargin+"Learner " + learner.getFormattedFullName() + " is no longer a Junior, please select another Learner\n\n");
                    } else {
                        terminal.puts(InfoCmp.Capability.clear_screen);
                        this.selectedLearner=learner;
//                        terminal.writer().println(leftMargin+"Selected Learner ID: " + selectedLearner.getId());
                        break;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private Pair<Learner, Boolean> fetchLearner(Terminal terminal, LineReader lineReader){
        updateLearnerList();
        learnerListView.printLearnerList();
        HelpText helpText = new HelpText(leftMargin + "TYPE [LEARNER ID] and ENTER to SELECT a LEARNER" +
                                            "\n"+leftMargin + "TYPE new to CREATE a NEW LEARNER",
                                    leftMargin + "TYPE :c and ENTER to CANCEL\n","");

        String learnerPrompt = "LEARNER ID: ";
        String input = InputValidator.inputGetter(terminal, lineReader, learnerPrompt, helpText);
        if(input==null) return null;
        Learner learner = null;
        Pair<Learner, Boolean> learnerBooleanPair = new Pair<>(learner, true);
        if(input.matches("^\\d{8}")){
            learnerBooleanPair.setInvalidFlag(false);
            learner = learnerController.getLearnerById(input);
        } if(input.equalsIgnoreCase("new")){
            learnerBooleanPair.setInvalidFlag(false);
            learner = learnerCreateView.createLearner();
//            handle case when user cancels learner creation
        }
        learnerBooleanPair.setObject(learner);
        return learnerBooleanPair;
    }
}
