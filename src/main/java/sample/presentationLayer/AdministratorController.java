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
import javafx.scene.input.InputMethodEvent;
import javafx.util.Callback;
import sample.businessLayer.BaseProduct;
import sample.businessLayer.CompositeProduct;
import sample.businessLayer.DeliveryService;
import sample.businessLayer.MenuItem;
import sample.dataLayer.Serializator;
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
    private Button modifyButton;
    @FXML
    private Button generateReport1;

    @FXML
    private Label report1Label;

    @FXML
    private TextField startHourTF;

    @FXML
    private TextField endHourTF;

    @FXML
    private Label report2Label;

    @FXML
    private TextField numberTF;

    @FXML
    private Button generateReport2;

    @FXML
    private Label report3Label;

    @FXML
    private TextField nrOfOrdersTF;

    @FXML
    private TextField sumTF;

    @FXML
    private Button generateReport3;

    @FXML
    private Label report1Label1;

    @FXML
    private TextField dayTF;

    @FXML
    private Button generateReport4;
    @FXML
    void clickModify(ActionEvent event) {
        if(addTitleTextField.getText().isEmpty() || addRatingTextField.getText().isEmpty() || addCaloriesTextField.getText().isEmpty() || addProteinTextField.getText().isEmpty() || addFatTextField.getText().isEmpty() || addSodiumTextField.getText().isEmpty() ||addPriceTextField.getText().isEmpty()){
            new ErrorMessage("Please fill all the required information!");
        }else {
            String title=addTitleTextField.getText();
            double rating=Double.parseDouble(addRatingTextField.getText());
            int calories=Integer.parseInt(addCaloriesTextField.getText());
            int protein=Integer.parseInt(addProteinTextField.getText());
            int fat=Integer.parseInt(addFatTextField.getText());
            int sodium=Integer.parseInt(addSodiumTextField.getText());
            double price=Double.parseDouble(addPriceTextField.getText());
            BaseProduct baseProduct=new BaseProduct(title,rating,calories,protein,fat,sodium,price);
            main.deliveryService.modifyProductAdd(baseProduct);
            observableListMenu.addAll(baseProduct);
            menuTableView.refresh();
            clearAll();


        }
    }

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
              clearAll();

           }
       }

    }
    private void clearAll(){
        addTitleTextField.clear();
        addRatingTextField.clear();
        addCaloriesTextField.clear();
        addProteinTextField.clear();
        addFatTextField.clear();
        addSodiumTextField.clear();
        addPriceTextField.clear();
    }


    @FXML
    void clickBack(ActionEvent event) {
        clearAll();
        observableListChosenItems.clear();
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
        main.deliveryService.loadBaseProducts();
        observableListMenu.clear();
        observableListChosenItems.clear();
        observableListMenu.addAll(DeliveryService.allMenuItems);
        menuTableView.refresh();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateMenuTable(DeliveryService.allMenuItems);
        addButtons(menuTableView,chosenItemsTableView,observableListChosenItems);
        addButtonsRemove(menuTableView,observableListMenu,observableListChosenItems,chosenItemsTableView);
        addButtonsRemove(chosenItemsTableView,observableListChosenItems);
        modifyButtons(menuTableView);
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
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Add");
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

    public void  addButtonsRemove(TableView<MenuItem> tableView,ObservableList<MenuItem>observableList,ObservableList<MenuItem>observableList2,TableView<MenuItem> tableView2)
    {
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Remove");
        Callback<TableColumn<MenuItem, Void>, TableCell<MenuItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MenuItem, Void> call(final TableColumn<MenuItem, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("-");
                    {
                        ClientController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            MenuItem menuItem = getTableView().getItems().get(getIndex());
                            observableList.remove(menuItem);
                            tableView.refresh();
                            main.deliveryService.removeProduct(menuItem);
                            observableList2.remove(menuItem);
                            tableView2.refresh();

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
    private void fillTextFields(MenuItem menuItem){
        if(menuItem instanceof BaseProduct){
            addTitleTextField.setText(((BaseProduct) menuItem).getTitle());
            addPriceTextField.setText(Double.toString(((BaseProduct) menuItem).getPrice()));
            addSodiumTextField.setText(Integer.toString(((BaseProduct) menuItem).getSodium()));
            addFatTextField.setText(Integer.toString(((BaseProduct) menuItem).getFat()));
            addProteinTextField.setText(Integer.toString(((BaseProduct) menuItem).getProtein()));
            addCaloriesTextField.setText(Integer.toString(((BaseProduct) menuItem).getCalories()));
            addRatingTextField.setText(Double.toString(((BaseProduct) menuItem).getRating()));
        }
    }
    public void  modifyButtons(TableView<MenuItem> tableView)
    {
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Modify");
        Callback<TableColumn<MenuItem, Void>, TableCell<MenuItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MenuItem, Void> call(final TableColumn<MenuItem, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("M");{
                        ClientController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            MenuItem data = getTableView().getItems().get(getIndex());
                            if(data instanceof BaseProduct) {
                                fillTextFields(data);
                                main.deliveryService.modifyProductDelete(data);
                                observableListMenu.remove(data);
                                observableListChosenItems.remove(data);
                                chosenItemsTableView.refresh();
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
    public  void  addButtonsRemove(TableView<MenuItem> tableView,ObservableList<MenuItem>observableList)
    {
        TableColumn<MenuItem, Void> buttons = new TableColumn<>("Remove");
        Callback<TableColumn<MenuItem, Void>, TableCell<MenuItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MenuItem, Void> call(final TableColumn<MenuItem, Void> param) {
                return new TableCell<>() {
                    private final Button newButton = new Button("-");
                    {
                        ClientController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            MenuItem menuItem = getTableView().getItems().get(getIndex());
                            observableList.remove(menuItem);
                            tableView.refresh();

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
    @FXML
    void clickGenerateReport1(ActionEvent event) {
        if(startHourTF.getText().isEmpty() || endHourTF.getText().isEmpty()){
            new ErrorMessage("Please fill all the required information!");
        }else{
            int startHour=Integer.parseInt(startHourTF.getText());
            int endHour=Integer.parseInt(endHourTF.getText());
            main.deliveryService.generateReport1(startHour,endHour);
        }
    }

    @FXML
    void clickGenerateReport2(ActionEvent event) {
        if(numberTF.getText().isEmpty()){
            new ErrorMessage("Please fill all the required information!");
        }else{

        }
    }

    @FXML
    void clickGenerateReport3(ActionEvent event) {

    }

    @FXML
    void clickGenerateReport4(ActionEvent event) {

    }



}



