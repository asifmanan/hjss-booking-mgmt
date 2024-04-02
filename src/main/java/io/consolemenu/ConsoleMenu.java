package io.consolemenu;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class ConsoleMenu {
    private Menu menu;


    public ConsoleMenu(){
        menu = new Menu();

    }
    public ConsoleMenu(Menu menu) {
        this.menu = menu;
    }
    public void initialize(){
        Terminal terminal = null;
        LineReader lineReader = null;

        try {
            terminal = TerminalManager.getTerminal();
            lineReader = TerminalManager.getLineReader();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        terminal.flush();

        terminal.puts(InfoCmp.Capability.clear_screen);

        while(!menu.getBreakLoopFlag()){

//            System.out.println(menu.displayMenuOptions());
            menu.displayMenuDescription(terminal);

            try {
                TerminalManager.updateCompleter(menu.getItemsList());
                lineReader = TerminalManager.getLineReader();
            } catch (IOException e) {
                e.printStackTrace();
            }

            line = lineReader.readLine(menu.getMenuTitle()+">>");

            for(int i = 0; i < menu.getMenuItems().size(); i++){
                if(line.trim().equals(menu.getMenuItem(i).getDisplayName().trim())){
                    terminal.puts(InfoCmp.Capability.clear_screen);
                    menu.getMenuItem(i).execute();
                }
            }

            terminal.flush();
        }
    }
}

