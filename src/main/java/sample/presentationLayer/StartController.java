package sample.presentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.start.Main;

public class StartController {
    private Main main;
    private Scene loginScene;
    private Scene registerScene;
    private Scene clientScene;
    private Scene adminScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setLoginScene(Scene scene1){
        this.loginScene = scene1;
    }
    public void setRegisterScene(Scene scene1){
        this.registerScene = scene1;
    }
    public void setClientScene(Scene scene1){
        this.clientScene = scene1;
    }
    public void setAdminScene(Scene scene1){
        this.adminScene = scene1;
    }
    @FXML
    private Button registerButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Button loginButton;

    @FXML
    void clickLogin(ActionEvent event) {
        main.setScene(loginScene);
    }

    @FXML
    void clickRegister(ActionEvent event) {
        main.setScene(registerScene);
    }

}
