package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;

/**
 * The type Print ascending command.
 */
public class PrintAscendingCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Print ascending command.
     *
     * @param controller the controller
     */
    public PrintAscendingCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        System.out.println(controller.printAscending());
    }

    @Override
    public String description() {
        return "вывести элементы коллекции в порядке возрастания";
    }
}
