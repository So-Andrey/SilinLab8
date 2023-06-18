package client.gui;

import gui.controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.CurrentUserManager;
import services.OrganizationController;

import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        CurrentUserManager userManager = new CurrentUserManager();
        OrganizationController controller = new OrganizationController(userManager);
        new LoginController(1080, 768, userManager, controller).launchLoginScene(stage);
    }
/* todo FIX

    private static ResourceBundle appLanguage = Locale.getDefault().getLanguage();

    public static ResourceBundle getAppLanguage() {
        return appLanguage;
    }

    public static void setAppLanguage(ResourceBundle appLanguage) {
        Launcher.appLanguage = appLanguage;
    }
*/
}