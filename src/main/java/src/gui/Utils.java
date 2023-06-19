package src.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.vehicleData.Vehicle;

public class Utils {
    public static void showInfo(String title, String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #C89E9E");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setStyle("-fx-background-color: #F7E8E8; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #661B1B; -fx-cursor: HAND;");
        alert.showAndWait();
    }

    public static void showVehicle(Vehicle vehicle) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle(GUI.getAppLanguage().getString("vehicle") + " " + vehicle.getId());
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #C89E9E");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label label = new Label(GUI.getAppLanguage().getString("name") + ": " + vehicle.getName());
        label.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane = new StackPane(rectangle, label);
        gridPane.add(stackPane, 0, 0);

        Label label1 = new Label(GUI.getAppLanguage().getString("coords") + ": (" + (vehicle.getCoordinates().getX() + "").replace(".", GUI.getAppLanguage().getString("separator")) + "; " + vehicle.getCoordinates().getY());
        label1.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle1 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane1 = new StackPane(rectangle1, label1);
        gridPane.add(stackPane1, 0, 1);

        Label label2 = new Label(GUI.getAppLanguage().getString("creation_date") + ": " + vehicle.getCreationDate());
        label2.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle2 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane2 = new StackPane(rectangle2, label2);
        gridPane.add(stackPane2, 0, 2);

        Label label3 = new Label(GUI.getAppLanguage().getString("ep") + ": " + (vehicle.getEnginePower() + "").replace(".", GUI.getAppLanguage().getString("separator")));
        label3.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle3 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane3 = new StackPane(rectangle3, label3);
        gridPane.add(stackPane3, 0, 3);

        Label label4 = new Label(GUI.getAppLanguage().getString("capacity") + ": " + vehicle.getCapacity());
        label4.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle4 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane4 = new StackPane(rectangle4, label4);
        gridPane.add(stackPane4, 0, 4);

        Label label5 = new Label(GUI.getAppLanguage().getString("fc") + ": " + vehicle.getFuelConsumption());
        label5.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle5 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane5 = new StackPane(rectangle5, label5);
        gridPane.add(stackPane5, 0, 5);

        Label label6 = new Label(GUI.getAppLanguage().getString("type") + ": " + vehicle.getType());
        label6.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle6 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane6 = new StackPane(rectangle6, label6);
        gridPane.add(stackPane6, 0, 6);

        Label label7 = new Label(GUI.getAppLanguage().getString("creator") + ": " + vehicle.getCreator());
        label7.setTextFill(Paint.valueOf("#F7E8E8"));
        Rectangle rectangle7 = new Rectangle(400, 30, Color.valueOf("#661B1B"));
        StackPane stackPane7 = new StackPane(rectangle7, label7);
        gridPane.add(stackPane7, 0, 7);

        Button button = new Button("OK");
        button.setStyle("-fx-background-color: #F7E8E8; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #661B1B; -fx-cursor: HAND;");
        button.setPrefWidth(400);
        button.setPrefHeight(30);
        button.setOnAction(event -> button.getScene().getWindow().hide());
        gridPane.add(button, 0, 8);

        Scene scene = new Scene(gridPane, 420, 370);
        stage.setScene(scene);
        stage.show();
    }
}
