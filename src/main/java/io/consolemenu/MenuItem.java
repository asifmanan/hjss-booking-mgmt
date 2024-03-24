package io.consolemenu;

public class MenuItem {
    private String displayName;
    private String hint;
    private Runnable action;
    public MenuItem(String displayName, Runnable action){
        this.displayName = displayName;
        this.action = action;
    }
    public MenuItem(String displayName, Runnable action, String hint){
        this(displayName, action);
        this.hint = hint;
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
    public String getHint(){
        return this.hint;
    }

}
