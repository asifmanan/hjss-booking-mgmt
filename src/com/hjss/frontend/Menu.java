package com.hjss.frontend;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private LinkedHashMap<Integer, String> menuItems;
    private Scanner scanner;

    public Menu(){
        this.menuItems = new LinkedHashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void addMenuItem(Integer choice, String description){
        menuItems.put(choice, description);
    }
    public void displayMenu(){
        for(Map.Entry<Integer, String> entry : menuItems.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public int getUserChoice(){
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
//        scanner.nextLine();
        return choice;
    }

    public void run(){
        int choice;
        do{
            displayMenu();
            choice = getUserChoice();

        } while ( choice != 0 );
    }

}
