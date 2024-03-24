package io.muxmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    String title;
    List<MenuItem> items = new ArrayList<>();
    Menu parentMenu;
    public Menu(String title, Menu parentMenu) {
        this.title = title;
        this.parentMenu = parentMenu;
    }
    public void addMenuItem(String displayName, Runnable action) {
        items.add(new MenuItem(displayName, action));
    }
    public void addSubMenu(Menu subMenu) {
        items.add(new MenuItem(subMenu.title, subMenu::display));
    }
    public void display() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\n" + title);
            for (int i = 0; i < items.size(); i++){
                System.out.println((i+1) + ". "+ items.get(i).displayName);
            }
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= items.size()) {
                items.get(choice-1).execute();
            } else {
                System.out.println("Invalid option, please input a valid option");
            }
        }
    }

}
