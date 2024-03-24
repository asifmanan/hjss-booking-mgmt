package io.consolemenu;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class TerminalManager {
    private static Terminal terminal = null;
    private static LineReader lineReader = null;

    public static Terminal getTerminal() throws IOException {
        if (terminal == null) {
            terminal = TerminalBuilder.builder().build();
        }
        return terminal;
    }

    public static LineReader getLineReader() throws IOException {
        if (lineReader == null && terminal == null) {
            Terminal localTerminal = getTerminal(); // Ensuring terminal is initialized
            lineReader = LineReaderBuilder.builder().terminal(localTerminal).build();
        }
        return lineReader;
    }

    public static void updateCompleter(List<String> items) {
        if (terminal != null) {
            // Update the completer with the new items
            StringsCompleter completer = new StringsCompleter(items);
            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(completer)
                    .build();
        }
    }
}
