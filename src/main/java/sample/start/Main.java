package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dataLayer.Serializator;
import sample.presentationLayer.ClientController;
import sample.presentationLayer.LoginController;
import sample.presentationLayer.RegisterController;
import sample.presentationLayer.StartController;

import java.io.File;
import java.net.URL;


public class Main extends Application {
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL urlStart=new File("src/main/java/sample/presentationLayer/StartView.fxml").toURI().toURL();
        URL urlLogin=new File("src/main/java/sample/presentationLayer/LoginView.fxml").toURI().toURL();
        URL urlRegister=new File("src/main/java/sample/presentationLayer/RegisterView.fxml").toURI().toURL();
        URL urlClient=new File("src/main/java/sample/presentationLayer/ClientView.fxml").toURI().toURL();
        window=primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(urlStart);
        Parent rootStart=loader.load();
        StartController controllerStart = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlLogin);
        Parent rootLogin=loader.load();
        LoginController controllerLogin = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlRegister);
        Parent rootRegister=loader.load();
        RegisterController controllerRegister = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlClient);
        Parent rootClient=loader.load();
        ClientController controllerClient = loader.getController();

        Scene startScene = new Scene(rootStart, 430, 500);
        Scene loginScene = new Scene(rootLogin, 450, 500);
        Scene registerScene=new Scene(rootRegister,450,500);
        Scene clientScene=new Scene(rootClient,500,500);

        controllerClient.setMain(this);
        controllerClient.setStartScene(startScene);
        controllerClient.setLoginScene(loginScene);
        controllerClient.setRegisterScene(registerScene);

        controllerStart.setLoginScene(loginScene);
        controllerStart.setRegisterScene(registerScene);
        controllerStart.setMain(this);
        controllerStart.setClientScene(clientScene);

        controllerLogin.setStartScene(startScene);
        controllerLogin.setRegisterScene(registerScene);
        controllerLogin.setMain(this);
        controllerLogin.setClientScene(clientScene);

        controllerRegister.setStartScene(startScene);
        controllerRegister.setLoginScene(loginScene);
        controllerRegister.setMain(this);
        controllerRegister.setClientScene(clientScene);
        window.setScene(startScene);
        window.setTitle("FOOD DELIVERY MANAGEMENT SYSTEM");
        window.show();
    }
    public void setScene(Scene scene){
        window.setScene(scene);
    }
    public static void main(String[] args) {
        Serializator.loadInfoUsers();
        launch(args);

    }
}