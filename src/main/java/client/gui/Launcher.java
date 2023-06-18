package client.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
    }
}