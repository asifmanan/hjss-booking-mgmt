package io.consolemenu;

public class FontStyles {

    private static final String BOLD_START = "\u001B[1m";
    private static final String BOLD_END = "\u001B[22m";
    private static final String RESET_COLOR = "\u001B[39m";
    private static final String RESET_ALL = "\u001B[0m";

    private FontStyles() {
    }

    public static String boldStart() {
        return TerminalManager.isAnsiSupported() ? BOLD_START : "";
    }

    public static String boldEnd() {
        return TerminalManager.isAnsiSupported() ? BOLD_END : "";
    }

    public static String resetColor() {
        return TerminalManager.isAnsiSupported() ? RESET_COLOR : "";
    }
    public static String resetAll() {
        return TerminalManager.isAnsiSupported() ? RESET_ALL : "";
    }
}
