package client.gui.controllers;

import gui.utils.SpecialWindows;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import localization.Lang;
import model.Coordinates;
import model.Organization;
import model.OrganizationType;
import services.CurrentUserManager;
import services.OrganizationController;

import java.net.URL;
import java.util.ResourceBundle;

import static gui.utils.SpecialWindows.showInfo;

public class MapController implements Initializable {
    private static final int WIDTH = 1018;
    private static final int HEIGHT = 604;
    private static final double CENTER_X = WIDTH / 2.0;
    private static final double CENTER_Y = HEIGHT / 2.0;
    private static final double LOG_SCALE = 50;
    private final CurrentUserManager userManager;
    private final OrganizationController controller;
    private final double width;
    private final double height;
    private final Scene scene;
    private final Parent parent;
    private Stage stage;
    public MapController(double width, double height, CurrentUserManager userManager, OrganizationController controller) {
        this.userManager = userManager;
        this.controller = controller;
        this.width = width;
        this.height = height;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = fxmlLoader.load();
            scene = new Scene(parent, this.width, this.height);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    public void launchMapScene(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);

        stage.hide();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_exit.setOnAction(e -> close());
        button_info.setOnAction(e -> setButton_info());
        button_table.setOnAction(e -> new TableController(width, height, userManager, controller).launchTableScene(stage));
        button_clear.setOnAction(e -> setButton_clear());
        drawAxes();
        controller.getAll().forEach(this::displayOrganization);
        setLang();
    }

    @FXML
    private Button button_exit;
    private void close(){
        if (SpecialWindows.showConfirmationDialog("U sure wanna exit?")){
            System.exit(1);
        }
    }

    @FXML
    private Button button_info;
    private void setButton_info() {
        showInfo(Lang.getString("info"), "");
    }

    @FXML
    private Button button_clear;

    @FXML
    private Button button_table;

    @FXML
    private Label label_user;

    @FXML
    private Pane pane_map;
    private void displayOrganization(Organization organization) {
        double scaledX = CENTER_X;
        double scaledY = CENTER_Y;

        Coordinates coordinates = organization.getCoordinates();

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

        Circle circle = new Circle(clampedX, clampedY, 5, generateColor(organization.getCreator()));
        Image image = new Image(parseTypeToImage(organization.getType()));
        ImageView imageView = new ImageView(image);

        imageView.setX(clampedX);
        imageView.setY(clampedY);

        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        circle.setOnMouseEntered(event -> pane_map.getChildren().add(imageView));
        circle.setOnMouseExited(event -> pane_map.getChildren().remove(imageView));
        circle.setOnMouseClicked(event -> {
            showInfo(organization.getName(), organization.toString());
        });

        pane_map.getChildren().add(circle);
    }


    private String parseTypeToImage(OrganizationType type){
        return switch (type){
            case TRUST -> "/images/types/trust.png";
            case GOVERNMENT -> "/images/types/goverment.png";
            case PUBLIC -> "/images/types/public.png";
            case PRIVATE_LIMITED_COMPANY -> "/images/types/private.png";
        };
    }
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
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
    private void setLang() {
        label_user.setText(Lang.getString("user") + " - " + userManager.getUserName());
    }

    private void setButton_clear() {
        if (controller.clear()){
            pane_map.getChildren().clear();

            drawAxes();
            controller.getAll().forEach(this::displayOrganization);

            ImageView iv = new ImageView(new Image("/clear.gif"));
            iv.setX(CENTER_X - 300);
            iv.setY(CENTER_Y - 200);
            iv.setFitWidth(600);
            iv.setFitHeight(400);
            pane_map.getChildren().add(iv);
        }
    }
}