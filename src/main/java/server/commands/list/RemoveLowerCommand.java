package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;

/**
 * The type Remove lower command.
 */
public class RemoveLowerCommand implements Command {
    private final VehicleController controller;

    /**
     * Instantiates a new Remove lower command.
     *
     * @param controller the controller
     */
    public RemoveLowerCommand(VehicleController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException("Команда должна содержать 1 аргумент - значение engine power.");
        }
        try {
            int sizeBefore = controller.getAllVehicle().size();
            int enginePower = Integer.parseInt(args[1]);
            controller.removeLower(enginePower);
            int sizeAfter = controller.getAllVehicle().size();
            int sizeDiff = sizeBefore - sizeAfter;
            System.out.println("Успешно удалено " + sizeDiff + " элементов.");
        } catch (NumberFormatException e) {
            System.out.println("Значение engine power должно быть целочисленным.");
        }
    }

    @Override
    public String description() {
        return "удаляет Vehicle, если значение поля enginePower меньше заданного.";
    }
}
