package io.consolemenu;

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
        this.addMenuItem(":q", this::quitApplication,"Quit");
    }
    public Menu(String menuTitle, Menu parentMenu){
        this.menuTitle = menuTitle;
        this.parentMenu = parentMenu;
        if (this.hasParentMenu()){
            this.addMenuItem(":q", this::quitApplication, "Quit");
            this.addMenuItem(":b", this::setBreakLoopFlag, "Back");
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
    public String displayMenuOptions(){
        menuItems.sort(Comparator.comparing(MenuItem::getDisplayName));
        StringBuilder sb = new StringBuilder();

        String itemName;

        for(int i = 0; i< menuItems.size(); i++){
            if(i != 0) {
                sb.append("      ");
            }
            itemName = menuItems.get(i).getDisplayName();
            if (menuItems.get(i).getHint() != null) {
                itemName = itemName + " ("+ menuItems.get(i).getHint()+")";
            }
            sb.append(itemName);
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
