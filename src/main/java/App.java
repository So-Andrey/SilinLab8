import client.Authentication;
import client.ConsoleUI;
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
        try {
            CurrentUserManager userManager = new CurrentUserManager();
            VehicleController controller = new VehicleControllerImpl(userManager);

            Authentication authentication = new Authentication(controller, userManager);

            ConsoleUI session = new ConsoleUI(new Invoker(authentication.start()));
            session.start();
        } catch (FileException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Необходимо ввести название файла базы данных при запуске программы.");
        }
    }
}
