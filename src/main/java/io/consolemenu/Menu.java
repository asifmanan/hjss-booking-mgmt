package io.consolemenu;

import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private String menuTitle;
    private List<MenuItem> menuItems = new ArrayList<>();
    private Menu parentMenu;
    private boolean breakLoopFlag = false;
    public Menu(){
    }
    public Menu(String menuTitle){
        this(menuTitle, null);
        this.addMenuItem(":q", this::quitApplication,"to QUIT the application");
    }
    public Menu(String menuTitle, Menu parentMenu){
        this.menuTitle = menuTitle;
        this.parentMenu = parentMenu;
        if (this.hasParentMenu()){
            this.addMenuItem(":q", this::quitApplication, "to QUIT the application");
            this.addMenuItem(":b", this::setBreakLoopFlag, "to go up one level");
        }
    }
    public String getMenuTitle(){
        return this.menuTitle;
    }
    public MenuItem getMenuItem(int index){
        return this.menuItems.get(index);
    }
    public List<MenuItem> getMenuItems(){
        return this.menuItems;
    }
    public void addMenuItem(String displayName, Runnable action) {
        menuItems.add(new MenuItem(displayName, action));
    }
    public void addMenuItem(String displayName, Runnable action, String hint) {
        menuItems.add(new MenuItem(displayName, action, hint));
    }
    public void addSubMenu(Menu subMenu){
        menuItems.add(new MenuItem(subMenu.menuTitle, subMenuAction(subMenu)));
    }
    public Runnable subMenuAction(Menu subMenu){
        return () -> {
            ConsoleMenu sub = new ConsoleMenu(subMenu);
            sub.initialize();
            subMenu.resetBreakLoopFlag();
        };
    }
    public void quitApplication(){
        try{
            TerminalManager.getTerminal().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    public boolean hasParentMenu(){
        if(this.parentMenu != null) {
            return true;
        }
        return false;
    }
    public void setBreakLoopFlag(){
        this.breakLoopFlag = true;
    }
    public void resetBreakLoopFlag(){
        this.breakLoopFlag = false;
    }
    public boolean getBreakLoopFlag(){
        return this.breakLoopFlag;
    }
    public List<String> getItemsList(){
        List<String> itemList = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            itemList.add(menuItem.getDisplayName());
        }
        return itemList;
    }
    public void displayMenuDescription(Terminal terminal){
        menuItems.sort(Comparator.comparing(MenuItem::getDisplayName));
        String leftMargin = " ".repeat(3);
        StringBuilder menuItemString = new StringBuilder();
        StringBuilder infoString = new StringBuilder();

        for(int i = 0; i<menuItems.size(); i++){
            menuItemString.setLength(0);
            menuItemString.append(leftMargin);

            if(i==0){
                terminal.writer().print("\n");
            }
            menuItemString.append("TYPE ");

            menuItemString.append(FontStyles.boldStart());
            menuItemString.append(menuItems.get(i).getDisplayName());
            menuItemString.append(FontStyles.boldEnd());

            menuItemString.append(" and ENTER ");
            menuItemString.append(menuItems.get(i).getDescription());

            terminal.writer().println(menuItemString);
            terminal.flush();
        }
        infoString.append("\n");
        infoString.append(leftMargin);

        infoString.append("Press ");

        infoString.append(FontStyles.boldStart());
        infoString.append("TAB");
        infoString.append(FontStyles.boldEnd());

        infoString.append(" to activate AUTOCOMPLETE");
        terminal.writer().println(infoString+ "\n");

        terminal.flush();
    }
    public String displayMenuOptions(){
        menuItems.sort(Comparator.comparing(MenuItem::getDisplayName));
        StringBuilder sb = new StringBuilder();

        String item;

        for(int i = 0; i< menuItems.size(); i++){
            if(i != 0) {
                sb.append("      ");
            }
            item = menuItems.get(i).getDisplayName();
            if (menuItems.get(i).getDescription() != null) {
                item = item + " ("+ menuItems.get(i).getDescription()+")";
            }
            sb.append(item);
        }

        sb.append("\n\n(Press tab for autocomplete suggestions)\n");
        return sb.toString();
    }


//  Optional fallback function, but not setup yet.
    public void simpleDisplayMenuOptions(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + menuTitle);
            for (int i = 0; i < menuItems.size(); i++) {
                System.out.println((i + 1) + ". " + menuItems.get(i).getDisplayName());
            }

            int choice = scanner.nextInt();
            if (choice > 0 && choice <= menuItems.size()) {
                menuItems.get(choice - 1).execute();
            } else {
                System.out.println("Invalid option. Please try again.");
            }

            if (this.parentMenu != null) {
                break; // Return to the parent menu
            }
        }
    }
}
