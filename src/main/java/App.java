import client.Authentication;
import client.ConsoleUI;
import client.gui.Launcher;
import server.commands.Invoker;
import server.controller.VehicleController;
import server.controller.VehicleControllerImpl;
import server.exceptions.FileException;
import server.services.CurrentUserManager;

/**
 * The type App. Главный класс, необходим для запуска программы
 */
public class App {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Launcher.main(args);
    }
}
