package sample.presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import sample.start.Main;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.User;
import sample.dataLayer.Serializator;

public class RegisterController {
    ObservableList<String> typesList;
    private Main main;
    private Scene loginScene;
    private Scene startScene;
    private Scene clientScene;
    private Scene adminScene;
    public void setMain(Main main){
        this.main = main;
        init();
    }
    public void setStartScene(Scene scene1){
        this.startScene = scene1;
    }
    public void setLoginScene(Scene scene1){
        this.loginScene = scene1;
    }
    public void setClientScene(Scene scene1){
        this.clientScene = scene1;
    }
    public void setAdminScene(Scene scene1){
        this.adminScene = scene1;
    }
    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private Label emptyUsernameLabel;

    @FXML
    private Label emptyPasswordLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label typeLabel;
    @FXML
    private ComboBox<String> chooseType;
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
        emptyUsernameLabel.setVisible(false);
        emptyPasswordLabel.setVisible(false);
        chooseType.setValue("Client");
    }

    @FXML
    void clickRegister(ActionEvent event) {
        boolean uniqueUser=true;
        emptyPasswordLabel.setVisible(false);
        emptyUsernameLabel.setVisible(false);
        if(usernameTextfield.getText().isEmpty()){
            emptyUsernameLabel.setVisible(true);
        }else if(passwordTextfield.getText().isEmpty()){
            if(usernameTextfield.getText().isEmpty()){
                emptyUsernameLabel.setVisible(true);
            }
            emptyPasswordLabel.setVisible(true);
        }else {
            String username = usernameTextfield.getText();
            String password = passwordTextfield.getText();
            String type = chooseType.getValue();
            for (User user : DeliveryService.listOfUsers) {
                if (user.getUsername().equals(username)) {
                    new ErrorMessage("User already registered!");
                    uniqueUser = false;
                }
            }
            if (uniqueUser) {
                User newUser;
                if (DeliveryService.listOfUsers.size() == 0) {
                    newUser = new User(1, username, password, type);
                } else {
                    newUser = new User(DeliveryService.listOfUsers.size() + 1, username, password, type);
                }
                if(newUser.getType().equals("Employee")){
                  EmployeeController employeeController=new EmployeeController(newUser);
                  DeliveryService.observers.add(employeeController);
                  //????
                  main.deliveryService.addListener(employeeController);
                  Serializator.writeToFileEmployees(DeliveryService.observers,"employees.txt");
                }
                DeliveryService.listOfUsers.add(newUser);
                clearAll();
                main.setScene(startScene);
                Serializator.writeToFile(DeliveryService.listOfUsers,"users.txt");
            }
        }
    }
    @FXML
    void init(){
        typesList= FXCollections.observableArrayList("Client","Administrator","Employee");
        chooseType.setValue("Client");
        chooseType.setItems(typesList);
    }

}
