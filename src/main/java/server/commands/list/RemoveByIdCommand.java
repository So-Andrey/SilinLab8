package server.commands.list;

import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;
import server.exceptions.ValidationException;
import server.model.Vehicle;
import server.services.CurrentUserManager;

/**
 * The type Remove by id command.
 */
public class RemoveByIdCommand implements Command {
    private final VehicleController controller;
    private final CurrentUserManager userManager;

    /**
     * Instantiates a new Remove by id command.
     *
     * @param controller the controller
     */
    public RemoveByIdCommand(VehicleController controller, CurrentUserManager userManager) {
        this.controller = controller;
        this.userManager = userManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException("Команда содержит 1 аргумент - id.");
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
            if (id <= 0) {
                throw new ValidationException("Id больше нуля");
            }
            if (controller.getAllVehicle().stream()
                    .filter(p -> p.getCreatorName().equals(userManager.getUserName()))
                    .map(Vehicle::getId).toList().contains(id)) {
                controller.removeVehicleById(id);
                System.out.println("Vehicle удален.");
            } else {
                System.out.println("Vehicle с таким id не найден или вам не пренадлежит.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Id принимает целочисленное значение.");
        }
    }

    @Override
    public String description() {
        return "удаляет элемент по id.";
    }
}
