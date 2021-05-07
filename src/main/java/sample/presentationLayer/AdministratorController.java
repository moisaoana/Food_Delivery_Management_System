package sample.presentationLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.businessLayer.BaseProduct;
import sample.businessLayer.CompositeProduct;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.MenuItem;
import sample.start.Main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AdministratorController implements Initializable {
    private Main main;
    private Scene loginScene;
    private Scene registerScene;
    private Scene clientScene;
    private Scene startScene;
    private ObservableList<MenuItem> observableListChosenItems = FXCollections.observableArrayList();
    ObservableList<MenuItem> observableListMenu = FXCollections.observableArrayList();
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
    public void setStartScene(Scene scene1){
        this.startScene = scene1;
    }
    @FXML
    private Label titleLabel;

    @FXML
    private Button importButton;

    @FXML
    private TableView<MenuItem> menuTableView;

    @FXML
    private TableColumn<MenuItem, String> titleColumn;

    @FXML
    private TableColumn<MenuItem, Double> priceColumn;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<MenuItem> chosenItemsTableView;

    @FXML
    private TableColumn<MenuItem, String> chosenTitleColumn;

    @FXML
    private TableColumn<MenuItem, Double> chosenPriceColumn;

    @FXML
    private Button createButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label nameLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextField addTitleTextField;

    @FXML
    private TextField addRatingTextField;

    @FXML
    private TextField addCaloriesTextField;

    @FXML
    private TextField addProteinTextField;

    @FXML
    private TextField addFatTextField;

    @FXML
    private TextField addSodiumTextField;

    @FXML
    private TextField addPriceTextField;

    @FXML
    private Label addItemLabel;

    @FXML
    private Label addTitleLabel;

    @FXML
    private Label addRatingLabel;

    @FXML
    private Label addCaloriesLabel;

    @FXML
    private Label addProteinLabel;

    @FXML
    private Label addFatLabel;

    @FXML
    private Label addSodiumLabel;

    @FXML
    private Label addPriceLabel;

    @FXML
    private Button addButton;

    @FXML
    void clickAdd(ActionEvent event) {
       if(addTitleTextField.getText().isEmpty() || addRatingTextField.getText().isEmpty() || addCaloriesTextField.getText().isEmpty() || addProteinTextField.getText().isEmpty() || addFatTextField.getText().isEmpty() || addSodiumTextField.getText().isEmpty() || addPriceTextField.getText().isEmpty()){
           new ErrorMessage("Please fill all the required information!");
       }else{
           String title=addTitleTextField.getText();
           double rating=Double.parseDouble(addRatingTextField.getText());
           int calories=Integer.parseInt(addCaloriesTextField.getText());
           int protein=Integer.parseInt(addProteinTextField.getText());
           int fat=Integer.parseInt(addFatTextField.getText());
           int sodium=Integer.parseInt(addSodiumTextField.getText());
           double price=Double.parseDouble(addPriceTextField.getText());
           if(rating<0 || rating>5 || calories<0 || protein<0 || fat<0 || sodium<0 || price<=0){
               new ErrorMessage("Incorrect data!");
           }else{
              BaseProduct baseProduct=new BaseProduct(title,rating,calories,protein,fat,sodium,price);
              main.deliveryService.addNewBaseProduct(baseProduct);
              observableListMenu.add(baseProduct);
              menuTableView.refresh();
              addTitleTextField.clear();
              addRatingTextField.clear();
              addCaloriesTextField.clear();
              addProteinTextField.clear();
              addFatTextField.clear();
              addSodiumTextField.clear();
              addPriceTextField.clear();
           }
       }

    }


    @FXML
    void clickBack(ActionEvent event) {
        main.setScene(startScene);
    }

    @FXML
    void clickCreate(ActionEvent event) {
        if(nameTextField.getText().isEmpty()){
            new ErrorMessage("Choose a name for the new item!");
        }else{
            if(observableListChosenItems.isEmpty() || observableListChosenItems.size()==1){
                new ErrorMessage("Please choose at least 2 items!");
            }else{
                 CompositeProduct compositeProduct=main.deliveryService.addNewCompositeItem(observableListChosenItems,nameTextField.getText());
                 observableListMenu.add(compositeProduct);
                 menuTableView.refresh();
                 observableListChosenItems.clear();
                 nameTextField.clear();

            }

        }
    }

    @FXML
    void clickImport(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateMenuTable(DeliveryService.allMenuItems);
        addButtons(menuTableView,chosenItemsTableView,observableListChosenItems);
        ClientController.addButtonsRemove(menuTableView,observableListMenu);
        ClientController.addButtonsRemove(chosenItemsTableView,observableListChosenItems);
    }
    @FXML
    public void populateMenuTable(Set<MenuItem> menuItems){
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        chosenTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        chosenPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        observableListMenu.addAll(menuItems);
        menuTableView.setItems(observableListMenu);
        chosenItemsTableView.setItems((ObservableList<MenuItem>) observableListChosenItems);

    }
    public static void  addButtons(TableView<MenuItem> tableView,TableView<MenuItem>tableView2,ObservableList<MenuItem>observableList)
    {
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Order");
        Callback<TableColumn<MenuItem, Void>, TableCell<MenuItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MenuItem, Void> call(final TableColumn<MenuItem, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("+");{
                        ClientController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            MenuItem data = getTableView().getItems().get(getIndex());
                            if(!observableList.contains(data)) {
                                observableList.add(data);
                                tableView2.refresh();
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(newButton);
                        }
                    }
                };
            }
        };
        buttons.setCellFactory(cellFactory);
        tableView.getColumns().add(buttons);
    }

}
