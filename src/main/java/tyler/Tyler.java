package tyler;

import java.io.IOException;
import java.nio.file.Paths;

import tyler.command.Command;
import tyler.exception.TylerException;
import tyler.parser.Parser;
import tyler.storage.Storage;
import tyler.task.TaskList;
import tyler.ui.Ui;

/**
 * The main class of Tyler that run Tyler.
 */
public class Tyler {
    protected Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructor for Tyler
     *
     * @param filePath The filepath of the saved file.
     */
    public Tyler(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Get response from the String input to the UI.
     *
     * @param input The input to get response.
     * @return A String representation of the input command, what input doing.
     */
    protected String getResponse(String input) throws TylerException {
        try {
            Command currentCommand = Parser.parse(input);
            return currentCommand.execute(tasks, ui, storage);
        } catch (TylerException e) {
            return e.getMessage();
        }
    }

}
