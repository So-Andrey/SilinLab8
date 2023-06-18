package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;
import server.model.Vehicle;

/**
 * The type Reorder command.
 */
public class ReorderCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Reorder command.
     *
     * @param controller the controller
     */
    public ReorderCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        for (Vehicle vehicle : controller.reorder()) {
            System.out.println(vehicle);
        }
        System.out.println("Успешно развернуто.");
    }

    @Override
    public String description() {
        return "отсортировать коллекцию в порядке обратном нынешнему.";
    }
}
