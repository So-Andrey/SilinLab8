package server.commands.list;

import server.builders.VehicleBuilder;
import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;

/**
 * The type Add command.
 */
public class AddCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Add command.
     *
     * @param controller the controller
     */
    public AddCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        if (controller.addVehicle(VehicleBuilder.build()) > 0){
            System.out.println("Успешно добавлен!");
        } else {
            System.out.println("Элемент не был добавлен.");
        }

    }

    @Override
    public String description() {
        return "добавить новый элемент в коллекцию.";
    }
}
