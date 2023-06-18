package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;
import server.model.Vehicle;

/**
 * The type Min by num of wheels command.
 */
public class MinByNumOfWheelsCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Min by num of wheels command.
     *
     * @param controller the controller
     */
    public MinByNumOfWheelsCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        Vehicle min = controller.minByNumberOfWheels();
        if (min == null) {
            System.out.println("Нет элементов.");
        } else {
            System.out.println(min);
        }
    }

    @Override
    public String description() {
        return "вывести любой объект из коллекции, значение поля numberOfWheels которого является минимальным.";
    }
}
