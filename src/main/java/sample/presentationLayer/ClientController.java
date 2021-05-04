package sample.presentationLayer;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import sample.start.Main;

public class ClientController {
    private Main main;
    private Scene startScene;
    private Scene registerScene;
    private Scene loginScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setStartScene(Scene scene1){
        this.startScene = scene1;
    }
    public void setRegisterScene(Scene scene1){
        this.registerScene = scene1;
    }
    public void setLoginScene(Scene scene1){
        this.loginScene = scene1;
    }

    @FXML
    private AnchorPane titleLabel;

}
