package src.gui;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import src.database.UserAuthentication;
import src.localization.Language;
import src.vehicleData.Coordinates;
import src.vehicleData.Vehicle;
import src.vehicleData.VehicleCollection;
import src.vehicleData.VehicleType;

import static src.gui.Utils.showInfo;

public class MapController {
    private static final int WIDTH = 797;
    private static final int HEIGHT = 540;
    private static final double CENTER_X = WIDTH / 2.0;
    private static final double CENTER_Y = HEIGHT / 2.0;
    private static final double LOG_SCALE = 50;
    @FXML private Label Label_currentUser;
    @FXML private Button Button_info;
    @FXML private Button Button_help;
    @FXML private Button Button_table;
    @FXML private Label Label_map;
    @FXML private Pane pane_map;
    @FXML private ImageView iv_car_map;
    @FXML
    void initialize() {
        updateLanguage();
        Label_currentUser.setText("username: " + UserAuthentication.getCurrentUser());
        drawAxes();
        drawAxes();
        VehicleCollection.getVehicle().forEach(this::displayVehicle);
        iv_car_map.setVisible(false);
    }

    @FXML
    void setButton_ru() {
        GUI.setAppLanguage(Language.ru);
        updateLanguage();
    }
    @FXML
    void setButton_sv() {
        GUI.setAppLanguage(Language.sv);
        updateLanguage();
    }
    @FXML
    void setButton_et() {
        GUI.setAppLanguage(Language.et);
        updateLanguage();
    }
    @FXML
    void setButton_es() {
        GUI.setAppLanguage(Language.es);
        updateLanguage();
    }
    private void updateLanguage() {
        Button_help.setText(GUI.getAppLanguage().getString("b_help"));
        Button_info.setText(GUI.getAppLanguage().getString("info"));
        Button_table.setText(GUI.getAppLanguage().getString("table"));
        Label_map.setText(GUI.getAppLanguage().getString("map"));
        Label_currentUser.setText(GUI.getAppLanguage().getString("username") + ": " + UserAuthentication.getCurrentUser());
    }
    private void drawAxes() {
        Line xAxis = new Line(0, CENTER_Y, WIDTH, CENTER_Y);
        Line yAxis = new Line(CENTER_X, 0, CENTER_X, HEIGHT);
        pane_map.getChildren().addAll(xAxis, yAxis);
    }
    private static Color generateColor(String input) {
        int hashCode = input.hashCode();
        int red = (hashCode & 0xFF0000) >> 16;
        int green = (hashCode & 0x00FF00) >> 8;
        int blue = hashCode & 0x0000FF;
        return Color.rgb(red, green, blue);
    }

    @FXML
    void setButton_help() {
        showInfo("Help", GUI.getAppLanguage().getString("help"));
    }

    @FXML
    void setButton_info() {
        showInfo("Info", VehicleCollection.getInfo());
    }

    @FXML
    void setButton_table() {
        pane_map.getScene().getWindow().hide();
        GUI.openMainWindow();
    }
    @FXML private AnchorPane ap_main;
    private void displayVehicle(Vehicle vehicle) {
        double scaledX = CENTER_X;
        double scaledY = CENTER_Y;

        Coordinates coordinates = vehicle.getCoordinates();

        if (coordinates.getX() > 0) {
            scaledX += Math.log(coordinates.getX()) * LOG_SCALE;
        } else if (coordinates.getX() < 0) {
            scaledX -= Math.log(-coordinates.getX()) * LOG_SCALE;
        }

        if (coordinates.getY() > 0) {
            scaledY -= Math.log(coordinates.getY()) * LOG_SCALE;
        } else if (coordinates.getY() < 0) {
            scaledY += Math.log(-coordinates.getY()) * LOG_SCALE;
        }
        Bounds paneBounds = pane_map.getBoundsInLocal();
        double clampedX = clamp(scaledX, paneBounds.getMinX(), paneBounds.getMaxX());
        double clampedY = clamp(scaledY, paneBounds.getMinY(), paneBounds.getMaxY());

        Circle circle = new Circle(clampedX, clampedY, 5, generateColor(vehicle.getCreator()));

        try {
            Image image = new Image(parseTypeToImage(vehicle.getType()));
            ImageView imageView = new ImageView(image);
            imageView.setX(clampedX);
            imageView.setY(clampedY);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            circle.setOnMouseEntered(event -> pane_map.getChildren().add(imageView));
            circle.setOnMouseExited(event -> pane_map.getChildren().remove(imageView));
        } catch (NullPointerException ignored) {}

        circle.setOnMouseClicked(event ->{
            ImageView view = new ImageView("/images/car_map.png");
            view.setVisible(true);
            ap_main.getChildren().add(view);
            moveImageView(view);
            Utils.showVehicle(vehicle);
        });

        pane_map.getChildren().add(circle);
    }

    private void moveImageView(ImageView imageView) {
        imageView.setFitWidth(iv_car_map.getFitWidth());
        imageView.setFitHeight(iv_car_map.getFitHeight());

        imageView.setLayoutY(iv_car_map.getY());
        imageView.setLayoutX(-imageView.getBoundsInParent().getWidth());

        //Задать движение, которое будет длиться 5 секунд изображением
        TranslateTransition transition = new TranslateTransition(Duration.seconds(5), imageView);
        //Поехать до точки
        transition.setToX(WIDTH + imageView.getBoundsInParent().getWidth() + 300);

        // Вернуть на место
        transition.setOnFinished(event -> imageView.setVisible(false));

        transition.play();
    }
    private String parseTypeToImage(VehicleType vehicleType){
        return switch (vehicleType){
            case PLANE -> "/images/airplane.png";
            case SUBMARINE -> "/images/submarine.png";
            case HELICOPTER -> "/images/helicopter.png";
        };
    }
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
