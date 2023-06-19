package src.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

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
}
