package client.gui.controllers;

import gui.utils.SpecialWindows;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import localization.Lang;
import services.CurrentUserManager;
import services.OrganizationController;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label label_error_msg;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;
    private final CurrentUserManager userManager;
    private final OrganizationController controller;
    private final double width;
    private final double height;
    private final Scene scene;
    private final Parent parent;
    private Stage stage;
    private boolean login = true;
    public LoginController(double width, double height, CurrentUserManager userManager, OrganizationController controller) {
        this.userManager = userManager;
        this.controller = controller;
        this.width = width;
        this.height = height;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = fxmlLoader.load();
            scene = new Scene(parent, this.width, this.height);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    public void launchLoginScene(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);

        stage.hide();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLang();
        close_button.setOnAction(e -> close());
        button_login.setOnAction(e -> loginButtonOnAction());
        button_sign_up.setOnAction(e -> signupButtonOnAction());
        button_ru.setOnAction(e -> setButton_ru());
        button_es.setOnAction(e -> setButton_es());
        button_ua.setOnAction(e -> setButton_ua());
        button_sr.setOnAction(e -> setButton_sr());
    }

    private void setButton_ru() {
        Lang.setAppLang(Lang.ru);
        setLang();
    }
    private void setButton_es() {
        Lang.setAppLang(Lang.es);
        setLang();
    }
    private void setButton_sr() {
        Lang.setAppLang(Lang.sr);
        setLang();
    }
    private void setButton_ua() {
        Lang.setAppLang(Lang.ua);
        setLang();
    }

    @FXML
    private Button close_button;
    private void close(){
        if (SpecialWindows.showConfirmationDialog(Lang.getString("u_sure"))){
            System.exit(1);
        }
    }

    private boolean checkFields() {
        if (!tf_username.getText().isBlank() && !pf_password.getText().isBlank()) {
            label_error_msg.setText(Lang.getString("attempt"));
            return true;
        }
        if (tf_username.getText().isBlank() || pf_password.getText().isBlank()) {
            label_error_msg.setText(Lang.getString("invalid"));
        }
        return false;
    }

    @FXML
    private Button button_sign_up;
    @FXML
    private Label label_login;
    @FXML
    private Label label_username;
    @FXML
    private Label label_password;
    @FXML
    private Label label_no_acc;
    @FXML
    private Button button_login;
    @FXML
    private Button button_ru;
    @FXML
    private Button button_es;
    @FXML
    private Button button_ua;
    @FXML
    private Button button_sr;

    private void setLang() {
        if (!login){
            label_login.setText(Lang.getString("sign_up"));
            label_no_acc.setText(Lang.getString("have_acc"));
            button_login.setText(Lang.getString("sign_up"));
            button_sign_up.setText(Lang.getString("login"));
        } else {
            label_login.setText(Lang.getString("login"));
            label_no_acc.setText(Lang.getString("no_acc"));
            button_login.setText(Lang.getString("login"));
            button_sign_up.setText(Lang.getString("sign_up"));
        }
        label_username.setText(Lang.getString("user"));
        label_password.setText(Lang.getString("password"));
    }
    public void signupButtonOnAction() {
        if (login){
            login = false;
            label_login.setText(Lang.getString("sign_up"));
            label_no_acc.setText(Lang.getString("have_acc"));
            button_login.setText(Lang.getString("sign_up"));
            button_sign_up.setText(Lang.getString("login"));
        } else {
            login = true;
            label_login.setText(Lang.getString("login"));
            label_no_acc.setText(Lang.getString("no_acc"));
            button_login.setText(Lang.getString("login"));
            button_sign_up.setText(Lang.getString("sign_up"));
        }
    }

    public void loginButtonOnAction() {
        if (!login){
            if (checkFields()) {
                String username = tf_username.getText();
                if (!controller.getUserNameList().contains(username)) {
                    userManager.setUserName(username);
                    controller.userRegister(username, pf_password.getText());
                    label_error_msg.setText(Lang.getString("success"));
                    new TableController(width, height, userManager, controller).launchTableScene(stage);
                } else {
                    label_error_msg.setText(Lang.getString("exists"));
                }
            }
        } else {
            if (checkFields()) {
                String username = tf_username.getText();
                if (controller.checkUserPassword(username, pf_password.getText())) {
                    userManager.setUserName(username);
                    label_error_msg.setText(Lang.getString("success"));
                    new TableController(width, height, userManager, controller).launchTableScene(stage);                } else {
                    label_error_msg.setText(Lang.getString("invalid"));
                }
            }
        }
    }
}