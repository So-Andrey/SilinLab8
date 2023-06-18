package server.commands.list;

import server.builders.VehicleBuilder;
import server.commands.Command;
import server.controller.VehicleController;
import server.exceptions.ArgumentException;
import server.exceptions.ValidationException;
import server.model.Vehicle;
import server.services.CurrentUserManager;

/**
 * The type Update command.
 */
public class UpdateCommand implements Command {
    private final VehicleController controller;
    private final CurrentUserManager userManager;

    /**
     * Instantiates a new Update command.
     *
     * @param controller the controller
     */
    public UpdateCommand(VehicleController controller, CurrentUserManager userManager) {
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
                controller.updateVehicle(VehicleBuilder.build(), id);
                System.out.println("Vehicle обновлен.");
            } else {
                System.out.println("Vehicle с таким id не найден или вам не прендалежит.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Id принимает целочисленное значение.");
        }
    }

    @Override
    public String description() {
        return "обновить существующий объект по id.";
    }
}
