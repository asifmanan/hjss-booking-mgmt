package io.consolemenu;

public class MenuItem {
    private String displayName;
    private String description;
    private Runnable action;
    public MenuItem(String displayName, Runnable action){
        this.displayName = displayName;
        this.action = action;
    }
    public MenuItem(String displayName, Runnable action, String description){
        this(displayName, action);
        this.description = description;
    }
    public void execute() {
        this.action.run();
    }

    public String getDisplayName(){
        return this.displayName;
    }
    public Runnable getAction(){
        return this.action;
    }
    public String getDescription(){
        return this.description;
    }

}
