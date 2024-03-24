package io.muxmenu;

public class MenuItem {
    String displayName;
    Runnable action;
    public MenuItem(String displayName, Runnable action){
        this.displayName = displayName;
        this.action = action;
    }
    void execute() {
        action.run();
    }
}
