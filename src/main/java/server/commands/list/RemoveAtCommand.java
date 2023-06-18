package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;
import server.exceptions.ValidationException;
import server.model.Vehicle;

/**
 * The type Remove at command.
 */
public class RemoveAtCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Remove at command.
     *
     * @param controller the controller
     */
    public RemoveAtCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException("Команда содержит 1 аргумент - index.");
        }
        int index;
        try {
            index = Integer.parseInt(args[1]);
            if (index <= 0) {
                throw new ValidationException("Index должен быть больше нуля.");
            }
            if (index >= controller.getAllVehicle().size()) {
                throw new ValidationException("Index должен быть меньше максимального.");
            }
            if (controller.getAllVehicle().stream().map(Vehicle::getId).toList().contains(index)) {
                controller.removeAt(index);
                System.out.println("Vehicle с index - " + index + " удален.");
            } else {
                System.out.println("Vehicle с таким index не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Id принимает целочисленное значение.");
        }
    }

    @Override
    public String description() {
        return "удалить элемент, находящийся в заданной позиции коллекции.";
    }
}
