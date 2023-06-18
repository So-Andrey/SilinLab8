package client.gui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;

import java.util.Optional;

public class SpecialWindows {
    public static boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #6A7FA7");
        ButtonType yesButton = new ButtonType("yes"); //todo
        ButtonType noButton = new ButtonType("no");
        alert.getButtonTypes().setAll(yesButton, noButton);
        dialogPane.lookupButton(yesButton).setStyle("""
                    -fx-background-color: #CDD4E2;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    -fx-border-color: #072A6C;
                    -fx-cursor: HAND;
                    """);
        dialogPane.lookupButton(noButton).setStyle("""
                    -fx-background-color: #CDD4E2;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    -fx-border-color: #072A6C;
                    -fx-cursor: HAND;
                    """);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    public static void showInfo(String title, String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #6E739D");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #260768; -fx-cursor: HAND;");
        alert.showAndWait();
    }


}
