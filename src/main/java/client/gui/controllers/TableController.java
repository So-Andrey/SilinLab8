package client.gui.controllers;

import commands.Invoker;
import gui.utils.SpecialWindows;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import localization.Lang;
import model.Address;
import model.Coordinates;
import model.Organization;
import model.OrganizationType;
import services.CurrentUserManager;
import services.OrganizationController;
import utils.InputType;

import static gui.utils.SpecialWindows.showInfo;

public class TableController {

    private final CurrentUserManager userManager;
    private final OrganizationController controller;
    private final double width;
    private final double height;
    private final Scene scene;
    private final Parent parent;
    private Stage stage;
    public TableController(double width, double height, CurrentUserManager userManager, OrganizationController controller) {
        this.userManager = userManager;
        this.controller = controller;
        this.width = width;
        this.height = height;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/table.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = fxmlLoader.load();
            scene = new Scene(parent, this.width, this.height);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    public void launchTableScene(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);

        stage.hide();
        stage.show();
    }

    @FXML
    private TableColumn<Organization, Long> annual;

    @FXML
    private Button button_add;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_exit;

    @FXML
    private Button button_info;

    @FXML
    private Button button_map;
    @FXML
    private Button button_update;

    @FXML
    private Button button_script;

    @FXML
    private TableColumn<Organization, String> coordinates;

    @FXML
    private TableColumn<Organization, String> creator;

    @FXML
    private TableColumn<Organization, String> date;

    @FXML
    private TableColumn<Organization, Long> emploees;

    @FXML
    private TableColumn<Organization, Integer> id;

    @FXML
    private Label label_user;

    @FXML
    private TableColumn<Organization, String> name;

    @FXML
    private TableView<Organization> table;

    @FXML
    private TableColumn<Organization, String> type;

    @FXML
    private Button button_clear;

    @FXML
    void initialize() {
        setLang();
        setTable();
        updateTable();
        button_add.setOnAction(event -> setButton_add());
        button_delete.setOnAction(event -> setButton_delete());
        button_update.setOnAction(event -> setButton_update());
        button_map.setOnAction(event -> setButton_map());
        button_info.setOnAction(event -> setButton_info());
        button_exit.setOnAction(event -> setButton_exit());
        button_clear.setOnAction(event -> setButton_clear());
        button_script.setOnAction(event -> setButton_script());
    }

    private void setLang() {
        label_user.setText(Lang.getString("user") + " - " + userManager.getUserName());
        button_add.setText(Lang.getString("add"));
        button_delete.setText(Lang.getString("delete"));
        button_update.setText(Lang.getString("update"));
        name.setText(Lang.getString("name"));
        coordinates.setText(Lang.getString("coordinates"));
        annual.setText(Lang.getString("annual"));
        emploees.setText(Lang.getString("emploees"));
        type.setText(Lang.getString("type"));
        date.setText(Lang.getString("date"));
        creator.setText(Lang.getString("creator"));
    }

    private void setTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        coordinates.setCellValueFactory(data -> new SimpleStringProperty((data.getValue().getCoordinates().getX() + "; " + data.getValue().getCoordinates().getY()).replace(".", Lang.getString("separator"))));
        annual.setCellValueFactory(new PropertyValueFactory<>("annualTurnover"));
        emploees.setCellValueFactory(new PropertyValueFactory<>("employeesCount"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        date.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
    }

    private void updateTable() {
        table.setItems(null);
        table.setItems(FXCollections.observableList(controller.getDataSet().stream().toList()));
    }

    private void setButton_add() {
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #6E739D");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(20);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        Label label1 = new Label(Lang.getString("name") + ":");
        TextField textField1 = new TextField();
        textField1.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label1, 0, 0);
        gridPane.add(textField1, 1, 0);

        Label label2 = new Label(Lang.getString("zip") + ":");
        TextField textField2 = new TextField();
        textField2.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label2, 3, 0);
        gridPane.add(textField2, 4, 0);

        Label label3 = new Label(Lang.getString("annual") + ":");
        TextField textField3 = new TextField();
        textField3.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label3, 0, 1);
        gridPane.add(textField3, 1, 1);

        Label label4 = new Label(Lang.getString("emploees") + ":");
        TextField textField4 = new TextField();
        textField4.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label4, 3, 1);
        gridPane.add(textField4, 4, 1);

        Label label5 = new Label("X:");
        TextField textField5 = new TextField();
        textField5.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label5, 0, 2);
        gridPane.add(textField5, 1, 2);

        Label label6 = new Label("Y:");
        TextField textField6 = new TextField();
        textField5.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label6, 3, 2);
        gridPane.add(textField6, 4, 2);

        Label label7 = new Label(Lang.getString("type") + ":");
        RadioButton radioButton1 = new RadioButton("Public");
        radioButton1.setStyle("-fx-focus-color: #06498c");
        radioButton1.setUserData("PUBLIC");
        RadioButton radioButton2 = new RadioButton("Government");
        radioButton2.setStyle("-fx-focus-color: #06498c");
        radioButton2.setUserData("GOVERNMENT");
        RadioButton radioButton3 = new RadioButton("Trust");
        radioButton3.setStyle("-fx-focus-color: #06498c");
        radioButton3.setUserData("TRUST");
        RadioButton radioButton4 = new RadioButton("Private Limited");
        radioButton4.setStyle("-fx-focus-color: #06498c");
        radioButton4.setUserData("PRIVATE_LIMITED_COMPANY");
        ToggleGroup toggleGroup1 = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup1);
        radioButton2.setToggleGroup(toggleGroup1);
        radioButton3.setToggleGroup(toggleGroup1);
        radioButton4.setToggleGroup(toggleGroup1);
        gridPane.add(label7, 0, 3);
        gridPane.add(radioButton1, 1, 3);
        gridPane.add(radioButton2, 2, 3);
        gridPane.add(radioButton3, 3, 3);
        gridPane.add(radioButton4, 4, 3);
        Button button = new Button(Lang.getString("add"));
        gridPane.add(button, 2, 4);
        CheckBox checkBox = new CheckBox(Lang.getString("if_min"));
        gridPane.add(checkBox, 3, 4);
        button.setCursor(Cursor.HAND);
        button.setOnAction(event1 -> {
            textField1.setPromptText("");
            textField2.setPromptText("");
            textField3.setPromptText("");
            textField4.setPromptText("");
            textField5.setPromptText("");
            textField6.setPromptText("");
            label7.setTextFill(Color.BLACK);
            boolean error = false;
            if (textField1.getText().trim().isEmpty() | textField1.getText().contains("'")) {
                textField1.setText("");
                textField1.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            if (textField2.getText().trim().isEmpty() | textField2.getText().contains("'")) {
                textField2.setText("");
                textField2.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                long z = Long.parseLong(textField3.getText());
                if (z <= 0) {
                    textField3.setText("");
                    textField3.setPromptText("> 0");
                    error = true;
                }
            } catch (NumberFormatException numberFormatException) {
                textField3.setText("");
                textField3.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                long z = Long.parseLong(textField4.getText());
                if (z <= 0) {
                    textField4.setText("");
                    textField4.setPromptText("> 0");
                    error = true;
                }
            } catch (NumberFormatException numberFormatException) {
                textField4.setText("");
                textField4.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                double z = Double.parseDouble(textField5.getText());
                if (z <= -527.0) {
                    textField5.setText("");
                    textField5.setPromptText("> -527");
                    error = true;
                }
            } catch (NumberFormatException numberFormatException) {
                textField5.setText("");
                textField5.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                Double.parseDouble(textField6.getText());
            } catch (NumberFormatException numberFormatException) {
                textField6.setText("");
                textField6.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            if (toggleGroup1.getSelectedToggle() == null) {
                label7.setTextFill(Color.RED);
                error = true;
            }
            if (error) return;
            try {
                Organization organization = new Organization();
                organization.setName(textField1.getText());
                organization.setOfficialAddress(new Address(textField2.getText()));
                organization.setAnnualTurnover(Long.parseLong(textField3.getText()));
                organization.setEmployeesCount(Long.parseLong(textField4.getText()));
                organization.setCoordinates(new Coordinates(Double.parseDouble(textField5.getText()), Double.parseDouble(textField6.getText())));
                organization.setType(OrganizationType.valueOf(toggleGroup1.getSelectedToggle().getUserData().toString()));
                if (checkBox.isSelected()) {
                    if (controller.addOrganizationifMin(organization) == -1) {
                        textField3.setText("");
                        textField3.setPromptText(Lang.getString("not_min"));
                        return;
                    }
                } else {
                    controller.addOrganization(organization);
                }
                button.getScene().getWindow().hide();
                updateTable();
            } catch (Exception ignored) {}
        });
        Scene scene = new Scene(gridPane, 700, 200);
        scene.getStylesheets().add("/css/style.css");
        button.getStyleClass().add("submit-button");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setButton_delete() {
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #6E739D");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label label = new Label("ID:");
        gridPane.add(label, 0, 0);
        TextField textField = new TextField();
        textField.setStyle("-fx-focus-color: #06498c");
        gridPane.add(textField, 1, 0);
        Button submit = new Button(Lang.getString("delete"));
        gridPane.add(submit, 2, 0);

        Scene scene = new Scene(gridPane, 300, 80);
        scene.getStylesheets().add("/css/style.css");
        submit.getStyleClass().add("submit-button");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.show();
        submit.setOnAction(event -> {
            try {
                int id = Integer.parseInt(textField.getText());
                if (!controller.removeOrganizationById(id)) {
                    textField.setText("");
                    textField.setPromptText(Lang.getString("not_ur"));
                    return;
                }
                submit.getScene().getWindow().hide();
                updateTable();
            } catch (NumberFormatException numberFormatException) {
                textField.setText("");
                textField.setPromptText(Lang.getString("invalid"));
            }
        });
    }

    private void setButton_update() {
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #6E739D");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(20);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        Label label = new Label("ID:");
        TextField textField = new TextField();
        textField.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label, 0, 0);
        gridPane.add(textField, 1, 0);

        Label label1 = new Label(Lang.getString("name") + ":");
        TextField textField1 = new TextField();
        textField1.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label1, 0, 1);
        gridPane.add(textField1, 1, 1);

        Label label2 = new Label(Lang.getString("zip") + ":");
        TextField textField2 = new TextField();
        textField2.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label2, 3, 1);
        gridPane.add(textField2, 4, 1);

        Label label3 = new Label(Lang.getString("annual") + ":");
        TextField textField3 = new TextField();
        textField3.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label3, 0, 2);
        gridPane.add(textField3, 1, 2);

        Label label4 = new Label(Lang.getString("emploees") + ":");
        TextField textField4 = new TextField();
        textField4.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label4, 3, 2);
        gridPane.add(textField4, 4, 2);

        Label label5 = new Label("X:");
        TextField textField5 = new TextField();
        textField5.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label5, 0, 3);
        gridPane.add(textField5, 1, 3);

        Label label6 = new Label("Y:");
        TextField textField6 = new TextField();
        textField5.setStyle("-fx-focus-color: #06498c;");
        gridPane.add(label6, 3, 3);
        gridPane.add(textField6, 4, 3);

        Label label7 = new Label(Lang.getString("type") + ":");
        RadioButton radioButton1 = new RadioButton("Public");
        radioButton1.setStyle("-fx-focus-color: #06498c");
        radioButton1.setUserData("PUBLIC");
        RadioButton radioButton2 = new RadioButton("Government");
        radioButton2.setStyle("-fx-focus-color: #06498c");
        radioButton2.setUserData("GOVERNMENT");
        RadioButton radioButton3 = new RadioButton("Trust");
        radioButton3.setStyle("-fx-focus-color: #06498c");
        radioButton3.setUserData("TRUST");
        RadioButton radioButton4 = new RadioButton("Private Limited");
        radioButton4.setStyle("-fx-focus-color: #06498c");
        radioButton4.setUserData("PRIVATE_LIMITED_COMPANY");
        ToggleGroup toggleGroup1 = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup1);
        radioButton2.setToggleGroup(toggleGroup1);
        radioButton3.setToggleGroup(toggleGroup1);
        radioButton4.setToggleGroup(toggleGroup1);
        gridPane.add(label7, 0, 4);
        gridPane.add(radioButton1, 1, 4);
        gridPane.add(radioButton2, 2, 4);
        gridPane.add(radioButton3, 3, 4);
        gridPane.add(radioButton4, 4, 4);
        Button button = new Button(Lang.getString("update"));
        gridPane.add(button, 2, 5);
        button.setCursor(Cursor.HAND);
        button.setOnAction(event1 -> {
            textField1.setPromptText("");
            textField2.setPromptText("");
            textField3.setPromptText("");
            textField4.setPromptText("");
            textField5.setPromptText("");
            textField6.setPromptText("");
            label7.setTextFill(Color.BLACK);
            boolean error = false;
            try {
                Integer.parseInt(textField.getText());
            } catch (NumberFormatException numberFormatException) {
                textField.setText("");
                textField.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            if (textField1.getText().trim().isEmpty() | textField1.getText().contains("'")) {
                textField1.setText("");
                textField1.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            if (textField2.getText().trim().isEmpty() | textField2.getText().contains("'")) {
                textField2.setText("");
                textField2.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                long z = Long.parseLong(textField3.getText());
                if (z <= 0) {
                    textField3.setText("");
                    textField3.setPromptText("> 0");
                    error = true;
                }
            } catch (NumberFormatException numberFormatException) {
                textField3.setText("");
                textField3.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                long z = Long.parseLong(textField4.getText());
                if (z <= 0) {
                    textField4.setText("");
                    textField4.setPromptText("> 0");
                    error = true;
                }
            } catch (NumberFormatException numberFormatException) {
                textField4.setText("");
                textField4.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                double z = Double.parseDouble(textField5.getText());
                if (z <= -527.0) {
                    textField5.setText("");
                    textField5.setPromptText("> -527");
                    error = true;
                }
            } catch (NumberFormatException numberFormatException) {
                textField5.setText("");
                textField5.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            try {
                Double.parseDouble(textField6.getText());
            } catch (NumberFormatException numberFormatException) {
                textField6.setText("");
                textField6.setPromptText(Lang.getString("invalid"));
                error = true;
            }
            if (toggleGroup1.getSelectedToggle() == null) {
                label7.setTextFill(Color.RED);
                error = true;
            }
            if (error) return;
            try {
                Organization organization = new Organization();
                organization.setName(textField1.getText());
                organization.setOfficialAddress(new Address(textField2.getText()));
                organization.setAnnualTurnover(Long.parseLong(textField3.getText()));
                organization.setEmployeesCount(Long.parseLong(textField4.getText()));
                organization.setCoordinates(new Coordinates(Double.parseDouble(textField5.getText()), Double.parseDouble(textField6.getText())));
                organization.setType(OrganizationType.valueOf(toggleGroup1.getSelectedToggle().getUserData().toString()));
                if (!controller.updateOrganizationById(organization, Integer.parseInt(textField.getText()))) {
                    textField.setText("");
                    textField.setPromptText(Lang.getString("not_ur"));
                    return;
                }
                button.getScene().getWindow().hide();
                updateTable();
            } catch (Exception ignored) {}
        });
        Scene scene = new Scene(gridPane, 700, 230);
        scene.getStylesheets().add("/css/style.css");
        button.getStyleClass().add("submit-button");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setButton_info() {
        showInfo(Lang.getString("info"), controller.info());
    }

    private void setButton_exit() {
        if (SpecialWindows.showConfirmationDialog(Lang.getString("u_sure"))) {
            System.exit(1);
        }
    }

    private void setButton_map() {
        new MapController(width, height, userManager, controller).launchMapScene(stage);
    }

    private void setButton_clear() {
        if (controller.clear()) updateTable();
    }

    private void setButton_script() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(Lang.getString("choose"));
            String[] command = {"", fileChooser.showOpenDialog(table.getScene().getWindow()).getAbsolutePath()};
            new Invoker(InputType.FILE, userManager).getCommandMap().get("execute").execute(command);
            updateTable();
        } catch (Exception ignored) {}
    }
}
