package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;

import java.util.Map;

/**
 * The type Group counting by name command.
 */
public class GroupCountingByNameCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Group counting by name command.
     *
     * @param controller the controller
     */
    public GroupCountingByNameCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        for (Map.Entry<String, Integer> entry : controller.groupCountingByName().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    @Override
    public String description() {
        return "сгруппировать элементы коллекции по значению поля name, вывести количество элементов в каждой группе";
    }
}
