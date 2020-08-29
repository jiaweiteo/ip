package seedu.duke;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class to run Duke program.
 * Creates and stores tasks such as todo, event and deadline.
 */

public class Duke {
    private Storage storage;
    private TaskList taskLists;
    private Ui ui;
    private Parser parser;

    /**
     * Initializes the Duke class and creates an instance of Storage, Tasklist, Ui and Parser.
     *
     * @param filePath directory of txt file
     */
    public Duke(String filePath) {
        try {
            this.storage = new Storage(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            taskLists = new TaskList(storage.load(new ArrayList<Task>()));
            this.parser = new Parser(taskLists);
            this.ui = new Ui(parser);
        } catch (IOException e) {
            this.parser = new Parser(taskLists);
            this.ui = new Ui(parser);
        }
    }

    /**
     * Method to run Duke program.
     */
    public void run() {
        ui.intro();
        while (ui.checkDukeStatus()) {
            ui.getNewInput();
        }
        ui.bye();
    }

    /**
     * Standard main method for Java to execute program.
     *
     * @param args Standard Java convection.
     */
    public static void main(String[] args) {
        Duke duke = new Duke("data/duke.txt");
        duke.run();
    }
}
