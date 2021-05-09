package sample.presentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.start.Main;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginController {
    private Main main;
    private Scene registerScene;
    private Scene startScene;
    //private Scene clientScene;
    private Scene adminScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setStartScene(Scene scene1){
        this.startScene = scene1;
    }
    public void setRegisterScene(Scene scene1){
        this.registerScene = scene1;
    }
    //public void setClientScene(Scene scene1){
      // this.clientScene = scene1;
   // }
    public void setAdminScene(Scene scene1){
        this.adminScene = scene1;
    }
    @FXML
    private Label usernameLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private Button loginButton;

    @FXML
    private Button clearButton;
    @FXML
    private Label emptyUsernameLabel;

    @FXML
    private Label emptyPasswordLabel;
    @FXML
    private Button backButton;

    @FXML
    void clickBack(ActionEvent event) {
        clearAll();
        main.setScene(startScene);
    }

    @FXML
    void clickClear(ActionEvent event) {
       clearAll();
    }
    private void clearAll(){
        usernameTextfield.clear();
        passwordTextfield.clear();
        emptyPasswordLabel.setVisible(false);
        emptyUsernameLabel.setVisible(false);
    }

    @FXML
    void clickLogin(ActionEvent event) {
        boolean found=false;
        emptyUsernameLabel.setVisible(false);
        emptyPasswordLabel.setVisible(false);
        if(usernameTextfield.getText().isEmpty()){
            if(passwordTextfield.getText().isEmpty()){
                emptyPasswordLabel.setVisible(true);
            }
            emptyUsernameLabel.setVisible(true);
        }else if(passwordTextfield.getText().isEmpty()){
            emptyPasswordLabel.setVisible(true);
        }else{
            for(User user: DeliveryService.listOfUsers){
                System.out.println(user.getUsername());
                if(user.getUsername().equals(usernameTextfield.getText())){
                    if(user.getPassword().equals(passwordTextfield.getText())){
                        if(user.getType().equals("Client")){
                            Scene clientScene=initializeClientScene(user);
                            main.setScene(clientScene);


                        }else if(user.getType().equals("Administrator")){
                            main.setScene(adminScene);
                        }else{
                            //todo
                        }
                        clearAll();
                    }else{
                        new ErrorMessage("Incorrect password!");
                    }
                    found=true;
                }
            }
            if(!found){
                new ErrorMessage("This user doesn't exist!");
            }
        }
    }
    public Scene initializeClientScene(User user) {
        URL urlClient = null;
        try {
            urlClient = new File("src/main/java/sample/presentationLayer/ClientView.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(urlClient);
        try {
            Parent rootClient = loader.load();
            ClientController controllerClient = loader.getController();
            Scene clientScene = new Scene(rootClient, 1200, 500);
            controllerClient.setMain(main);
            controllerClient.setStartScene(startScene);
            controllerClient.setRegisterScene(registerScene);
            controllerClient.setAdminScene(adminScene);
            controllerClient.setUser(user);
            return clientScene;

        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }

}
